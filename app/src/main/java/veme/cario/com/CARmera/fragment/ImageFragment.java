package veme.cario.com.CARmera.fragment;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Vehicle;
import veme.cario.com.CARmera.requests.VehicleRequest;

/**
 * Created by bski on 11/24/14.
 */
public class ImageFragment extends Fragment {

    /* TODO: disable the scroll on vehicle request not complete*/

    private static final String JSON_HASH_KEY = "image_preview_json";
    private ImageView preview_view;
    private Button discard_photo_btn;
    private Button upload_btn;
    private LinearLayout preview_container;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class VehicleRequestListener implements RequestListener<Vehicle> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            ImageFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(Vehicle vehicle) {
            getArguments().putString("year", vehicle.getYear());
            getArguments().putString("make", vehicle.getMake());
            getArguments().putString("model", vehicle.getModel());
            preview_container.setHorizontalScrollBarEnabled(true);
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
//        status_view = (TextView) getView().findViewById(R.id.status_view);
        discard_photo_btn = (Button) getView().findViewById(R.id.discard_photo_btn);
        upload_btn = (Button) getView().findViewById(R.id.upload_btn);
        preview_container = (LinearLayout) getView().findViewById(R.id.image_preview_container);
        preview_container.setHorizontalScrollBarEnabled(false);

        discard_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* dismiss the dialog fragment */
            }
        });


        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRequest();
                /* override the imageview with animation */
            }
        });


    }

    private void performRequest() {
        ImageFragment.this.getActivity().setProgressBarIndeterminate(true);
        /* image file */
        byte[] imageData  = getArguments().getByteArray("imageData");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        VehicleRequest vehicleRequest = new VehicleRequest (bitmap);
        spiceManager.execute(vehicleRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VehicleRequestListener());
    }


}