package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.CaptureActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.VehicleGridAdapter;

public class TaggedVehicleFragment extends Fragment {

    private static final String TAG = "TAGGED_VEHICLE_FRAGMENT";

    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */

    private VehicleGridAdapter vehicleGridAdapter;
    private GridView vehicle_grid_view;

    private LinearLayout no_vehicles_tagged_overlay;

    private OnSeeListingsSelectedListener listingCallback;

    public interface OnSeeListingsSelectedListener {
        public abstract void OnSeeListingsSelected(String year, String make, String model);
    }

    private OnVehicleSelectedListener vehicleSelectedCallback;

    public interface OnVehicleSelectedListener {
//            public void OnVehicleSelected (byte[] imageData, String year, String make, String model);
            public void OnVehicleSelected (String post_id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listingCallback = (OnSeeListingsSelectedListener) activity;
            vehicleSelectedCallback = (OnVehicleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tagged_vehicle, container, false);
        Log.i(TAG, " - tagged vehicle list created");
        vehicle_grid_view = (GridView) view.findViewById(R.id.vehicle_grid_view);

        no_vehicles_tagged_overlay = (LinearLayout) view.findViewById(R.id.no_tagged_vehicle_overlay);
        no_vehicles_tagged_overlay.setClickable(true);
        no_vehicles_tagged_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CaptureActivity.class);
                startActivity(i);
            }
        });
        vehicle_grid_view.setEmptyView(no_vehicles_tagged_overlay);
        vehicleGridAdapter = new VehicleGridAdapter(inflater.getContext());
        vehicle_grid_view.setAdapter(vehicleGridAdapter);
        /* sets data for all tagged vehicles */

        ParseQuery<TaggedVehicle> query = ParseQuery.getQuery("TaggedVehicle");
        query.setLimit(5);
        query.findInBackground(new FindCallback<TaggedVehicle>() {
            @Override
            public void done(List<TaggedVehicle> taggedVehicles, ParseException e) {
                for (TaggedVehicle vehicle : taggedVehicles) {
                    vehicleGridAdapter.add (vehicle);
                }
            }
        });
        vehicleGridAdapter.notifyDataSetChanged();

        vehicle_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) vehicle_grid_view.getItemAtPosition(position);
                vehicleSelectedCallback.OnVehicleSelected(taggedVehicle.getObjectId());
            }
        });
        return view;
    }
}
