package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleDetails;
import veme.cario.com.CARmera.model.Json.EquipmentOption;
import veme.cario.com.CARmera.model.Json.Option;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.requests.VINRequest;
import veme.cario.com.CARmera.util.AnimatedExpandableListView;
import veme.cario.com.CARmera.util.EquipmentListAdapter;

public class ListingsDetailFragment extends Fragment {

    private final class VinRequestListener implements RequestListener<VehicleDetails> {
        @Override
        public void onRequestFailure (SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            ListingsDetailFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess (VehicleDetails vehicleBaseInfo) {

            if (ListingsDetailFragment.this.isAdded()) {
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

                city_mpg_tv.setText(vehicleBaseInfo.getMPG().getCity());
                hw_mgp_tv.setText(vehicleBaseInfo.getMPG().getHighway());

                for (Option option : vehicleBaseInfo.getOptions()) {
                    EquipmentListAdapter.EquipmentItem equipmentItem = new EquipmentListAdapter.EquipmentItem();
                    equipmentItem.package_name = option.getCategory();

                    for (EquipmentOption equipment_option : option.getOptions()) {
                        EquipmentListAdapter.EquipmentChildItem equipmentChildItem = new EquipmentListAdapter.EquipmentChildItem();
                        equipmentChildItem.equipment_name = equipment_option.getName();
                        equipmentChildItem.equipment_price = "$" + equipment_option.getPrice().getBaseMSRP();
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

    private static final String TAG = ListingsDetailFragment.class.getSimpleName();
    private String vin;
    private TaggedVehicle taggedVehicle;
    private AnimatedExpandableListView equipment_listview;
    private EquipmentListAdapter equipment_list_adapter;
    private List<EquipmentListAdapter.EquipmentItem> equipment_items = new ArrayList<EquipmentListAdapter.EquipmentItem>();
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private static String JSON_HASH_KEY;


    private ParseImageView tagged_photo_view;
    private TextView vehicle_info_tv, post_date_tv, mileage_tv, pricing_tv;  /* user name, time */

    private TextView engine_spec_textview, engine_hp_textview, engine_torque_textview;
    private TextView transmission_type_textview, transmission_speed_textview, driven_wheels_textview;
    private TextView city_mpg_tv, hw_mgp_tv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings_details, container, false);

        final String vehicle_id = getArguments().getString("vehicle_id");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TaggedVehicle");
        query.getInBackground(vehicle_id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject taggedVehicle, ParseException e) {
                if (e == null) {
                    TaggedVehicle vehicle = (TaggedVehicle) taggedVehicle;
                    ListingsDetailFragment.this.taggedVehicle = vehicle;
                    ListingsDetailFragment.this.vin = vehicle.getVin();
                    post_date_tv.setText(vehicle.getCreatedAt().toString());
                    vehicle_info_tv.setText(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
                    mileage_tv.setText(vehicle.getMileage());
                    pricing_tv.setText(vehicle.getPrice());
                    tagged_photo_view.setParseFile(vehicle.getTagPhoto());
                    tagged_photo_view.loadInBackground();

                } else {
                    Log.d (TAG, "problem getting shit");
                }
            }
        });
        return view;
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
        JSON_HASH_KEY = getArguments().getString("vehicle_id") + now.toString() + "vin_info";
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleDetails.class, JSON_HASH_KEY,
                new VinRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void initUIComponents () {

        tagged_photo_view = (ParseImageView) getView().findViewById(R.id.listings_photo);
        vehicle_info_tv = (TextView) getView().findViewById(R.id.listing_vehicle_info_view);
        post_date_tv = (TextView) getView().findViewById(R.id.listing_post_date);
        mileage_tv = (TextView) getView().findViewById(R.id.listings_mileage_view);
        pricing_tv = (TextView) getView().findViewById(R.id.listings_pricing_view);


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
        ListingsDetailFragment.this.getActivity().setProgressBarIndeterminate(true);
        VINRequest vinRequest = new VINRequest(this.vin);
        spiceManager.execute(vinRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                new VinRequestListener());
    }


}
