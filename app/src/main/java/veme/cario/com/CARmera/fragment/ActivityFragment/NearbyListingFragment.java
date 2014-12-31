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

import java.util.List;

import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.SettingsActivity;
import veme.cario.com.CARmera.model.UserModels.NearbyListingsList;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.VehicleListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class NearbyListingFragment extends Fragment {

    private static final String TAG = "NEABBY_LISTINGS_FRAGMENT";

    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private VehicleListAdapter vehicleListAdapter;

    private ListView nearby_listings_listview;
    private LinearLayout no_nearby_listings_overlay;

    private OnNearbyListingSelectedListener nearbyListingsCallback;

    public interface OnNearbyListingSelectedListener {
        public abstract void OnNearbyListingsSelected (int pos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            nearbyListingsCallback = (OnNearbyListingSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnNearbyListingsSelectedListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nearby_listings, container, false);
        nearby_listings_listview = (ListView) view.findViewById(R.id.nearby_listings_listview);
        no_nearby_listings_overlay = (LinearLayout) view.findViewById(R.id.no_nearby_listings_overlay);
        no_nearby_listings_overlay.setClickable(true);
        no_nearby_listings_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
            }
        });
        nearby_listings_listview.setEmptyView(no_nearby_listings_overlay);

        vehicleListAdapter = new VehicleListAdapter(inflater.getContext());
        nearby_listings_listview.setAdapter(vehicleListAdapter);

        List<TaggedVehicle> nearbyListings = NearbyListingsList.get().getNearbyListings();
        for (TaggedVehicle vehicle : nearbyListings) {
            vehicleListAdapter.add (vehicle);
        }
        vehicleListAdapter.notifyDataSetChanged();

        nearby_listings_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) nearby_listings_listview.getItemAtPosition(position);
                Bundle args = new Bundle();
                byte[] vehicle_image = null;
                try {
                    vehicle_image = taggedVehicle.getTagPhoto().getData();

                } catch (com.parse.ParseException e) {
                    Log.d(TAG, " - " + e.getMessage());
                }

                args.putByteArray("imageData", vehicle_image);
                args.putString("vehicle_year", taggedVehicle.getYear());
                args.putString("vehicle_make", taggedVehicle.getMake());
                args.putString("vehicle_model", taggedVehicle.getModel());

                FragmentManager fm = getChildFragmentManager();
                VehicleInfoDialog vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

}
