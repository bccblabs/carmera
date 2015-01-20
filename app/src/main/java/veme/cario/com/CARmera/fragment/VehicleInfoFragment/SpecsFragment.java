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

import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleDetails;
import veme.cario.com.CARmera.model.Json.CustomerReview;
import veme.cario.com.CARmera.model.Json.EquipmentOption;
import veme.cario.com.CARmera.model.Json.Option;
import veme.cario.com.CARmera.requests.VehicleSpecsRequest;
import veme.cario.com.CARmera.util.AnimatedExpandableListView;
import veme.cario.com.CARmera.util.EquipmentListAdapter;
import veme.cario.com.CARmera.util.ReviewListAdapter;

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

    /* MPG view */
    private TextView city_mpg_tv;
    private TextView hw_mgp_tv;

    /* equipment list */
    private AnimatedExpandableListView equipment_listview;
    private EquipmentListAdapter equipment_list_adapter;
    private List<EquipmentListAdapter.EquipmentItem> equipment_items = new ArrayList<EquipmentListAdapter.EquipmentItem>();


    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    /* loading and table view */
//    private View engineloadingView;
    private View enginetableLayout;
//    private View transmissionloadingView;
    private View transmissiontableLayout;


    private final class VehicleSpecsRequestListener implements RequestListener<VehicleDetails> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            SpecsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleDetails vehicleBaseInfo) {

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
                transmission_speed_textview.setText(vehicleBaseInfo.getTransmission().getNumberOfSpeeds() + " speed");
                SpecsFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
                enginetableLayout.setAlpha(0f);
                transmissiontableLayout.setAlpha(0f);
                enginetableLayout.setVisibility(View.VISIBLE);
                transmissiontableLayout.setVisibility(View.VISIBLE);
                enginetableLayout.animate().alpha(1f);
                transmissiontableLayout.animate().alpha(1f);

                city_mpg_tv.setText(vehicleBaseInfo.getMPG().getCity());
                hw_mgp_tv.setText(vehicleBaseInfo.getMPG().getHighway());

                for (Option option : vehicleBaseInfo.getOptions()) {
                    EquipmentListAdapter.EquipmentItem equipmentItem = new EquipmentListAdapter.EquipmentItem();
                    equipmentItem.package_name = option.getCategory();

                    for (EquipmentOption equipment_option : option.getOptions()) {
                        EquipmentListAdapter.EquipmentChildItem equipmentChildItem = new EquipmentListAdapter.EquipmentChildItem();
                        equipmentChildItem.equipment_name = equipment_option.getName();
                        equipmentChildItem.equipment_price = equipment_option.getPrice().getBaseMSRP();
                        equipmentChildItem.equipment_desc = equipment_option.getDescription();
                        equipmentItem.items.add(equipmentChildItem);
                    }
                    equipment_items.add(equipmentItem);
                    equipmentItem.package_cnt = Integer.toString(option.getOptions().size());
                }

                equipment_list_adapter.setData(equipment_items);
                equipment_list_adapter.notifyDataSetChanged();
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
        spiceManager.addListenerIfPending(VehicleDetails.class, JSON_HASH_KEY,
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

        enginetableLayout = getView().findViewById(R.id.engine_table);
        transmissiontableLayout = getView().findViewById(R.id.transmission_table);

        transmissiontableLayout.setVisibility(View.GONE);
        enginetableLayout.setVisibility(View.GONE);


        engine_name_textview = (TextView) getView().findViewById(R.id.engine_name_textview);
        engine_spec_textview = (TextView) getView().findViewById(R.id.engine_spec_textview);
        engine_hp_textview = (TextView) getView().findViewById(R.id.engine_hp_textview);
        engine_torque_textview = (TextView) getView().findViewById(R.id.engine_torque_textview);
        transmission_type_textview = (TextView) getView().findViewById(R.id.transmission_type_textview);
        transmission_speed_textview = (TextView) getView().findViewById(R.id.transmission_speed_textview);
        driven_wheels_textview = (TextView) getView().findViewById(R.id.driven_wheels_textview);

        city_mpg_tv = (TextView) getView().findViewById(R.id.city_mpg_textview);
        hw_mgp_tv = (TextView) getView().findViewById(R.id.highway_mpg_textview);

        equipment_listview = (AnimatedExpandableListView) getView().findViewById(R.id.equipment_listview);
        equipment_list_adapter = new EquipmentListAdapter(getActivity());
        equipment_list_adapter.setData(equipment_items);
        equipment_listview.setAdapter(equipment_list_adapter);


        performRequest();
    }

    private void performRequest() {
        SpecsFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleSpecsRequest vehicleSpecsRequest = new VehicleSpecsRequest(getArguments().getString("vehicle_id"));
        spiceManager.execute(vehicleSpecsRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VehicleSpecsRequestListener());
    }

}
