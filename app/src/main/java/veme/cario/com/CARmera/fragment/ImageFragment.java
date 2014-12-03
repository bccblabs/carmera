package veme.cario.com.CARmera.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Vehicle;
import veme.cario.com.CARmera.requests.VehicleRequest;

public class ImageFragment extends Fragment {

    /* TODO: disable the scroll on vehicle request not complete*/


    ImageResultListener imageResultCallback = null;

    public interface ImageResultListener {
        public abstract void onRecognitionResult (String year, String make, String model);
    }

    private static final String JSON_HASH_KEY = "image_preview_json";
    private ImageView preview_view;
    private Button upload_btn;
    private Bitmap bitmap;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private static final String TAG = "IMAGE_FRAGMENT";

    private final class VehicleRequestListener implements RequestListener<Vehicle> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            ImageFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(Vehicle vehicle) {
            Log.i(TAG, " - " + vehicle.getResult().getYear() + " "
                    + vehicle.getResult().getMake() + " "
                    + vehicle.getResult().getModel());

            imageResultCallback.onRecognitionResult(vehicle.getResult().getYear(),
                                                    vehicle.getResult().getMake(),
                                                    vehicle.getResult().getModel());

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            imageResultCallback = (ImageResultListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the ImageResultListener!");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(Vehicle.class, JSON_HASH_KEY,
                new VehicleRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void initUIComponents() {
        preview_view = (ImageView) getView().findViewById(R.id.preview_view);

        upload_btn = (Button) getView().findViewById(R.id.upload_btn);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRequest();
            }
        });

        new BitmapLoaderTask().execute();

    }

    private void performRequest() {
//        ImageFragment.this.getActivity().setProgressBarIndeterminate(true);
//        /* image file */
//        VehicleRequest vehicleRequest = new VehicleRequest (bitmap);
//        spiceManager.execute(vehicleRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
//                new VehicleRequestListener());
        imageResultCallback.onRecognitionResult("2014", "bmw", "x3");
    }

    public class BitmapLoaderTask extends AsyncTask <Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground (Void... params) {
            byte[] newImageBytes;
            byte[] imageData = getArguments().getByteArray("imageData");
            /* first, make a bitmap out of original */
            Bitmap raw_bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            /* second, compress it using a byte array */
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            raw_bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            /* third, create a new image out of byte array */
            newImageBytes = bos.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(newImageBytes, 0, newImageBytes.length);
            Bitmap scaled_bitmap = Bitmap.createScaledBitmap(raw_bitmap, 640, 480, false);
            return scaled_bitmap;
        }

        @Override
        protected void onPostExecute (Bitmap res) {
            preview_view.setImageBitmap(res);
        }
    }



}