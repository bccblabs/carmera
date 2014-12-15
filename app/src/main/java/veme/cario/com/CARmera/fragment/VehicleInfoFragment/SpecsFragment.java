package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.requests.VehicleSpecsRequest;

public class SpecsFragment extends Fragment {

    private static final String TAG = "SPEC_FRAGMENT";
    private static String JSON_HASH_KEY;

    /* "engine" */
    private TextView engine_name_textview; // name
    private TextView engine_spec_textview;

    private TextView engine_hp_textview;    // horsepower
    private TextView engine_torque_textview; // torque

    /* transmission */
    private TextView transmission_type_textview; //transmissionType
    private TextView transmission_speed_textview; // numberOfSpeeds

    /* drivenWheels */
    private TextView driven_wheels_textview;

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private final class VehicleSpecsRequestListener implements RequestListener<VehicleSpecs> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            SpecsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleSpecs vehicleBaseInfo) {

            if (SpecsFragment.this.isAdded()) {
                engine_name_textview.setText(vehicleBaseInfo.getEngine().getCode());
                engine_spec_textview.setText(vehicleBaseInfo.getEngine().getSize() + "l "
                                    + vehicleBaseInfo.getEngine().getConfiguration() + " "
                                    + vehicleBaseInfo.getEngine().getCylinder()+ " "
                                    + vehicleBaseInfo.getEngine().getTotalValves() + "v");
                engine_hp_textview.setText(vehicleBaseInfo.getEngine().getHorsepower() + " hp");
                engine_torque_textview.setText(vehicleBaseInfo.getEngine().getTorque() + " lb-ft");

                driven_wheels_textview.setText(vehicleBaseInfo.getDrivenWheels());
                String transmission_type = vehicleBaseInfo.getTransmission().getTransmissionType();
                if (transmission_type.equals("AUTOMATIC"))
                    transmission_type = "Auto";
                else
                    transmission_type = "Manual";
                transmission_type_textview.setText(transmission_type);

                Log.i(TAG, " - number of speeds: " + vehicleBaseInfo.getTransmission().getNumberOfSpeeds());
                transmission_speed_textview.setText(vehicleBaseInfo.getTransmission().getNumberOfSpeeds() + " speed");

                SpecsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specs, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }


    @Override
    public void onStart() {
        super.onStart();
        Time now = new Time();
        now.setToNow();
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "specs_info";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleSpecs.class, JSON_HASH_KEY,
                new VehicleSpecsRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void initUIComponents () {
        engine_name_textview = (TextView) getView().findViewById(R.id.engine_name_textview);
        engine_spec_textview = (TextView) getView().findViewById(R.id.engine_spec_textview);
        engine_hp_textview = (TextView) getView().findViewById(R.id.engine_hp_textview);
        engine_torque_textview = (TextView) getView().findViewById(R.id.engine_torque_textview);
        transmission_type_textview = (TextView) getView().findViewById(R.id.transmission_type_textview);
        transmission_speed_textview = (TextView) getView().findViewById(R.id.transmission_speed_textview);
        driven_wheels_textview = (TextView) getView().findViewById(R.id.driven_wheels_textview);


        performRequest();
    }

    private void performRequest() {
        SpecsFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleSpecsRequest vehicleSpecsRequest = new VehicleSpecsRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(vehicleSpecsRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VehicleSpecsRequestListener());
    }

}
