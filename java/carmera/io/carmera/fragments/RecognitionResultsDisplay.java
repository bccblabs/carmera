package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.CarGenAdapter;
import carmera.io.carmera.models.CapturedVehicle;
import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.Prediction;
import carmera.io.carmera.models.Predictions;
import carmera.io.carmera.requests.GenDataRequest;
import carmera.io.carmera.requests.PredictionsRequest;
import carmera.io.carmera.utils.InMemorySpiceService;
import carmera.io.carmera.TrimsListingsActivity;
import carmera.io.carmera.widgets.SquareImageView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class RecognitionResultsDisplay extends Fragment implements ScreenShotable {

    public static final String EXTRA_GEN_DATA = "extra_gen_data";

    @Bind(R.id.car_gen_recycler)
    RecyclerView car_gen_recycler;

    @Bind (R.id.content_container)
    View containerView;

    @Bind(R.id.preview_container)
    View preview_container;

    @OnClick(R.id.retake_photo_btn)
    public void backToCamera () {
        spiceManager.cancelAllRequests();
        retakePhotoListener.retakePhoto();
    }

    @Bind (R.id.photo_holder)
    SquareImageView photo;

    @Bind (R.id.upload_progress_bar)
    ProgressBarCircularIndeterminate progress_bar;

    @Bind(R.id.retake_photo_btn)
    View retake_btn;

    private CarGenAdapter carGenAdapter;
    private Context context;

    private Bitmap bitmap;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);
    private SpiceManager genSpiceManager = new SpiceManager (InMemorySpiceService.class);

    private CapturedVehicle capturedVehicle;
    private RetakePhotoListener retakePhotoListener;
    private SaveCallback parseImageSaveCallback = new SaveCallback() {
        @Override
        public void done(ParseException e) {
            String photo_url = capturedVehicle.getTagPhoto().getUrl();
            Picasso.with(context) //
                    .load(photo_url) //
                    .placeholder(R.drawable.placeholder) //
                    .error(R.drawable.error) //
                    .fit() //
                    .centerCrop()
                    .into(photo);
            PredictionsRequest predictionsRequest = new PredictionsRequest(photo_url);
            spiceManager.execute(predictionsRequest, capturedVehicle.getObjectId(), DurationInMillis.ALWAYS_RETURNED, new PredictionsRequestListener());
        }
    };

    public interface RetakePhotoListener {
        public void retakePhoto();
    }

    private final class GenerationDataRequestListener implements RequestListener<GenerationData> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess (GenerationData result) {
            Gson gson = new Gson();
            Log.i(TAG, gson.toJson(result));
            carGenAdapter.add(result);
            carGenAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Adapter length: " + carGenAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
        }
    }


    private final class PredictionsRequestListener implements RequestListener<Predictions> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess (Predictions result) {
            if (result!=null) {
                Toast.makeText(getActivity(), "Predictions Received: " + result.predictions.size(), Toast.LENGTH_SHORT).show();
                for (Prediction prediction : result.predictions) {
                    GenQuery genQuery = new GenQuery();
                    genSpiceManager.addListenerIfPending(GenerationData.class, prediction.class_name, new GenerationDataRequestListener());
                    Toast.makeText(getActivity(), prediction.class_name, Toast.LENGTH_SHORT).show();
                    List<String> labels = new ArrayList<>();
                    labels.add(prediction.class_name);
                    Log.i (TAG, labels.toString());
                    genQuery.setLabels(labels);
                    GenDataRequest generationDataRequest = new GenDataRequest(genQuery);
                    genSpiceManager.execute(generationDataRequest, prediction.class_id, DurationInMillis.ALWAYS_RETURNED, new GenerationDataRequestListener());
                }
                preview_container.setVisibility(View.GONE);
                retake_btn.setVisibility(View.GONE);
                car_gen_recycler.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void takeScreenShot () {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                RecognitionResultsDisplay.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() { return bitmap; }

    public static RecognitionResultsDisplay newInstance() {
        return new RecognitionResultsDisplay();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        ParseFile captured_image = new ParseFile(args.getByteArray("image_data"));
        capturedVehicle = new CapturedVehicle();
        capturedVehicle.setTagPhoto(captured_image);
        captured_image.saveInBackground(parseImageSaveCallback);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.results_display, container, false);
        ButterKnife.bind(this, v);
        carGenAdapter = new CarGenAdapter();
        car_gen_recycler.setAdapter(carGenAdapter);
        car_gen_recycler.setLayoutManager(new LinearLayoutManager(context));
        car_gen_recycler.setHasFixedSize(true);
        carGenAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<GenerationData>() {
            @Override
            public void onItemClick(View v, GenerationData item, int position) {
                Intent i = new Intent (getActivity(), TrimsListingsActivity.class);
                Bundle args = new Bundle();
                Parcelable gen_data = Parcels.wrap(item);
                args.putParcelable(EXTRA_GEN_DATA, gen_data);
                i.putExtras(args);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            retakePhotoListener = (RetakePhotoListener) context;
        } catch (ClassCastException e) {
            Log.e (TAG, e.getMessage());
        }
    }


    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start (getActivity());
        genSpiceManager.start (getActivity());
        if (capturedVehicle != null )
            spiceManager.addListenerIfPending(Predictions.class, capturedVehicle.getObjectId(), new PredictionsRequestListener());
    }

    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        if (genSpiceManager.isStarted()) {
            genSpiceManager.shouldStop();
        }
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
