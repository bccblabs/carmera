package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.CaptureActivity;
import veme.cario.com.CARmera.ProfileActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicleList;
import veme.cario.com.CARmera.util.VehicleListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class TaggedVehicleFragment extends Fragment {

    private static final String TAG = "TAGGED_VEHICLE_FRAGMENT";

    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private VehicleListAdapter vehicleListAdapter;

    private ListView tagged_vehicles_listview;
    private LinearLayout no_vehicles_tagged_overlay;

    private OnTaggedListingSelectedListener listingCallback;

    public interface OnTaggedListingSelectedListener {
        public abstract void OnTaggedListingSelected (int pos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listingCallback = (OnTaggedListingSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tagged_vehicle, container, false);
        Log.i(TAG, " - tagged vehicle list created");
        tagged_vehicles_listview = (ListView) view.findViewById(R.id.tagged_cars_listview);
        no_vehicles_tagged_overlay = (LinearLayout) view.findViewById(R.id.no_tagged_vehicle_overlay);
        no_vehicles_tagged_overlay.setClickable(true);
        no_vehicles_tagged_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CaptureActivity.class);
                startActivity(i);
            }
        });
        tagged_vehicles_listview.setEmptyView(no_vehicles_tagged_overlay);

        vehicleListAdapter = new VehicleListAdapter(inflater.getContext());
        tagged_vehicles_listview.setAdapter(vehicleListAdapter);

        /* sets data for all tagged vehicles */
        ParseQuery<TaggedVehicle> query = ParseQuery.getQuery("TaggedVehicle");
        query.findInBackground(new FindCallback<TaggedVehicle>() {
            @Override
            public void done(List<TaggedVehicle> taggedVehicles, ParseException e) {
                for (TaggedVehicle vehicle : taggedVehicles) {
                    vehicleListAdapter.add (vehicle);
                }
            }
        });
        vehicleListAdapter.notifyDataSetChanged();
        return view;
    }
}
