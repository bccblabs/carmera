package veme.cario.com.CARmera.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Predictions;
import veme.cario.com.CARmera.model.Json.Prediction;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.requests.PredictionsRequest;
import veme.cario.com.CARmera.util.RecognitionResultAdapter;

/**
 * Created by bski on 1/15/15.
 */
public class RecognitionResultFragment extends Fragment {

    private String tagged_vehicle_id;
    private ListView recognition_res_listview;
    private TextView no_recognitions_view;
    private View recognition_progress_view;
    private ParseImageView tagged_car_image;
    private final static String TAG = RecognitionResultFragment.class.getSimpleName();
    private RecognitionResultAdapter recognitionResultAdapter;
    private TaggedVehicle taggedVehicle = null;
    private byte[] image_bytes;

    private static String JSON_HASH_KEY;
    RecognitionResultCallback recognitionCallback = null;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);


    public interface RecognitionResultCallback {
        public abstract void onRecognitionResult (String tagged_vehicle_id, final String yr, final String mk, final String md);
    }

    private final class RecognitionResultListener implements RequestListener<Predictions> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            recognition_progress_view.animate().alpha(1f);
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            RecognitionResultFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(Predictions predictions) {
            recognition_res_listview.setEmptyView(no_recognitions_view);
            if (predictions == null) {
                return;
            }
            recognitionResultAdapter.clear();
            for (Prediction prediction : predictions.getPredictions()) {
                recognitionResultAdapter.add (prediction);
            }
            recognitionResultAdapter.notifyDataSetChanged();
            recognition_res_listview.setVisibility(View.VISIBLE);
            recognition_res_listview.animate().alpha(1f);
            recognition_progress_view.animate().alpha(0f);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        RecognitionResultFragment.this.tagged_vehicle_id = getArguments().getString("tagged_vehicle_id");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recognition_result, container, false);
        recognition_res_listview = (ListView) view.findViewById(R.id.recognition_res_listview);
        no_recognitions_view = (TextView) view.findViewById(R.id.no_recognitions_view);
        recognition_progress_view = view.findViewById(R.id.recognition_progress_view);
        tagged_car_image = (ParseImageView) view.findViewById(R.id.tagged_vehicle_parse_view);

        recognitionResultAdapter = new RecognitionResultAdapter(inflater.getContext());
        recognition_res_listview.setAdapter(recognitionResultAdapter);
        recognition_res_listview.setEmptyView(no_recognitions_view);

        recognition_res_listview.setVisibility(View.GONE);
        recognition_progress_view.setAlpha(0f);
        recognition_progress_view.setVisibility(View.VISIBLE);
        recognition_progress_view.animate().alpha(1f);


        recognition_res_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prediction style = recognitionResultAdapter.getItem(position);
                String[] car_info = style.getClass_name().split("\\s+");
                recognitionCallback.onRecognitionResult(tagged_vehicle_id, car_info[0], car_info[1], car_info[2]);
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TaggedVehicle");
        query.getInBackground(tagged_vehicle_id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject taggedVehicle_, ParseException e) {
                if (e == null) {
                    try {
                        RecognitionResultFragment.this.taggedVehicle = (TaggedVehicle) taggedVehicle_;
                        image_bytes = ((TaggedVehicle) taggedVehicle_).getTagPhoto().getData();
                        tagged_car_image.setParseFile(((TaggedVehicle) taggedVehicle_).getTagPhoto());
                        tagged_car_image.loadInBackground();
                        performRequest();
                    } catch (ParseException img_e) {
                        Log.i (TAG, "ParseException: " + img_e.getMessage());
                    }
                } else {
                    Log.d (TAG, "problem getting shit");
                }
            }
        });

        return view;
    }
    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            recognitionCallback = (RecognitionResultCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the RecognitionResultCallback interface!");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Time now = new Time();
        now.setToNow();
        JSON_HASH_KEY = getArguments().getString("tagged_vehicle_id") + now.toString() + "recognition";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(Predictions.class, JSON_HASH_KEY,
                new RecognitionResultListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        RecognitionResultFragment.this.getActivity().setProgressBarIndeterminate(true);
        PredictionsRequest predictionsRequest = new PredictionsRequest(taggedVehicle.getTagPhoto().getUrl());
        spiceManager.execute(predictionsRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new RecognitionResultListener());

    }
}
