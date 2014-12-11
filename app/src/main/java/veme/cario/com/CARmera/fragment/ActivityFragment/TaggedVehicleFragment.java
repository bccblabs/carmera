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

        List<TaggedVehicle> taggedVehicles = TaggedVehicleList.get().getTaggedVehicleList();
        for (TaggedVehicle vehicle : taggedVehicles) {
            vehicleListAdapter.add (vehicle);
        }
        vehicleListAdapter.notifyDataSetChanged();

        tagged_vehicles_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) tagged_vehicles_listview.getItemAtPosition(position);
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

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setTitle("searching for some car?");
        SearchView sv = new SearchView( ((ProfileActivity) getActivity()).getActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
//
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
////                System.out.println("search query submit");
                return true;
            }
            //
            @Override
            public boolean onQueryTextChange(String newText) {
//                System.out.println("tap");
                return true;
            }
        });
    }



    /* need to have a specilized adapter */
}
