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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.PhotoUploadFragment;
import carmera.io.carmera.R;
import carmera.io.carmera.Trims;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.CarGenAdapter;
import carmera.io.carmera.models.CapturedVehicle;
import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.Prediction;
import carmera.io.carmera.models.Predictions;
import carmera.io.carmera.requests.GenerationDataRequest;
import carmera.io.carmera.requests.PredictionsRequest;
import carmera.io.carmera.utils.InMemorySpiceService;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class RecognitionResultsDisplay extends Fragment implements ScreenShotable {


    @Bind(R.id.car_gen_recycler)
    RecyclerView car_gen_recycler;

    @Bind (R.id.content_container)
    LinearLayout containerView;

    private CarGenAdapter carGenAdapter;
    final Context context = getActivity();

    private Bitmap bitmap;
    private String TAG = getClass().getCanonicalName();
    private SpiceManager spiceManager = new SpiceManager(InMemorySpiceService.class);
    private SpiceManager genSpiceManager = new SpiceManager (InMemorySpiceService.class);

    private CapturedVehicle capturedVehicle;
    private PhotoUploadFragment photoUploadFragment;
    private SaveCallback parseImageSaveCallback = new SaveCallback() {
        @Override
        public void done(ParseException e) {
            Bundle args = new Bundle();
            photoUploadFragment = PhotoUploadFragment.newInstance();
            args.putString("image_url", capturedVehicle.getTagPhoto().getUrl());
            photoUploadFragment.setArguments(args);
            photoUploadFragment.show (getChildFragmentManager(), "upload_progress");
            PredictionsRequest predictionsRequest = new PredictionsRequest(capturedVehicle.getTagPhoto().getUrl());
            spiceManager.execute(predictionsRequest, capturedVehicle.getObjectId(), DurationInMillis.ALWAYS_RETURNED, new PredictionsRequestListener());
        }
    };

    private final class GenerationDataRequestListener implements RequestListener<GenerationData> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess (GenerationData result) {
            Toast.makeText(getActivity(), "Data Received: " + result.snapshot.count, Toast.LENGTH_SHORT).show();
            carGenAdapter.add(result);
            Toast.makeText(getActivity(), "Adapter length: " + carGenAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
            carGenAdapter.notifyDataSetChanged();
            photoUploadFragment.dismiss();
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
                    genSpiceManager.addListenerIfPending(GenerationData.class, prediction.class_name, new GenerationDataRequestListener());
                    Toast.makeText(getActivity(), prediction.class_name, Toast.LENGTH_SHORT).show();
                    GenerationDataRequest generationDataRequest = new GenerationDataRequest(prediction.class_name);
                    genSpiceManager.execute(generationDataRequest, prediction.class_name, DurationInMillis.ALWAYS_RETURNED, new GenerationDataRequestListener());
                }
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
                Intent i = new Intent (getActivity(), Trims.class);
                Bundle args = new Bundle();
                Parcelable gen_data = Parcels.wrap(item);
                args.putParcelable(Trims.EXTRA_GEN_DATA, gen_data);
                i.putExtras(args);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
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
