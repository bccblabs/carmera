package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleBaseInfo;
import veme.cario.com.CARmera.requests.VehicleBaseInfoRequest;


public class CarInfoFragment extends Fragment {

    private static final String TAG = "CAR_INFO_FRAGMENT";
    private static String JSON_HASH_KEY;
    private ImageView preview_view;
    private ImageButton share_btn;
    private ImageButton reselect_styles_btn;
    private TextView car_base_info;
    private TextView car_style_info;

    private TextView used_tmv_textview;
    private TextView msrp_textview;
    private TextView city_mpg_textview;
    private TextView highway_mpg_textview;

    private OnReselectClickListener onReselectClickCallback = null;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class VehicleInfoRequestListener implements RequestListener<VehicleBaseInfo> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            CarInfoFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleBaseInfo vehicleBaseInfo) {
            if (CarInfoFragment.this.isAdded()) {

                car_base_info.setText(vehicleBaseInfo.getYear().getYear() + " " + vehicleBaseInfo.getMake().getName()
                        + " " + vehicleBaseInfo.getModel().getName());
                car_style_info.setText(vehicleBaseInfo.getTrim());

                String tmv_price = vehicleBaseInfo.getPrice().getUsedTmvRetail();
                String msrp_price = vehicleBaseInfo.getPrice().getBaseMSRP();
                if (tmv_price == null) {
                    used_tmv_textview.setText("N/A");
                }
                if (msrp_price == null) {
                    msrp_textview.setText("N/A");
                }
                used_tmv_textview.setText("$" + vehicleBaseInfo.getPrice().getUsedTmvRetail());
                msrp_textview.setText("$" + vehicleBaseInfo.getPrice().getBaseMSRP());
                city_mpg_textview.setText(vehicleBaseInfo.getMPG().getCity());
                highway_mpg_textview.setText(vehicleBaseInfo.getMPG().getHighway());

                CarInfoFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    public interface OnReselectClickListener {
        public abstract void OnReselectClick(byte[] raw_photo, String yr, String mk, String md);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            onReselectClickCallback = (OnReselectClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the OnReselectClickListener!");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i (TAG, " - car info fragment created");
        return inflater.inflate(R.layout.fragment_car_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    @Override
    public void onStart() {
        super.onStart();
        Time now = new Time();
        now.setToNow();
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "car_info";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleBaseInfo.class, JSON_HASH_KEY,
                new VehicleInfoRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }


    private void initUIComponents() {
        preview_view = (ImageView) getView().findViewById(R.id.info_preview_view);

        share_btn = (ImageButton) getView().findViewById(R.id.share_tagged_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* TODO: share image via intent to other media (insta, fb, wechat, whatsapp, etc.) */
            }
        });

        reselect_styles_btn = (ImageButton) getView().findViewById(R.id.reselect_style_btn);
        reselect_styles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onReselectClickCallback.OnReselectClick(getArguments().getByteArray("imageData"),
                                                        getArguments().getString("vehicle_year"),
                                                        getArguments().getString("vehicle_make"),
                                                        getArguments().getString("vehicle_model"));
            }
        });

        car_base_info = (TextView) getView().findViewById(R.id.car_base_info);
        car_style_info = (TextView) getView().findViewById(R.id.car_style_info);
        used_tmv_textview = (TextView) getView().findViewById(R.id.used_tmv_textview);
        msrp_textview = (TextView) getView().findViewById(R.id.msrp_textview);
        city_mpg_textview = (TextView) getView().findViewById(R.id.city_mpg_textview);
        highway_mpg_textview = (TextView) getView().findViewById(R.id.highway_mpg_textview);

        performRequest();
        new BitmapLoaderTask().execute();
    }
    public class BitmapLoaderTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground (Void... params) {
            byte[] imageData = getArguments().getByteArray("imageData");
            /* first, make a bitmap out of original */
            Bitmap raw_bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            /* second, compress it using a byte array */
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            raw_bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            /* third, create a new image out of byte array */
            Bitmap scaled_bitmap = Bitmap.createScaledBitmap(raw_bitmap, 640, 480, false);
            return raw_bitmap;
        }

        @Override
        protected void onPostExecute (Bitmap res) {
            preview_view.setImageBitmap(res);
        }
    }

    private void performRequest() {
        CarInfoFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleBaseInfoRequest vehicleBaseInfoRequest =
                new VehicleBaseInfoRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(vehicleBaseInfoRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VehicleInfoRequestListener());
    }

}
