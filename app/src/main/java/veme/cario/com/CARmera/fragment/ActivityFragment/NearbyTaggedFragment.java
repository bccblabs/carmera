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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.SettingsActivity;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicleList;
import veme.cario.com.CARmera.util.VehicleListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class NearbyTaggedFragment extends Fragment {

    private static final String TAG = "NEABBY_TAGGED_FRAGMENT";

    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private VehicleListAdapter vehicleListAdapter;

    private ListView nearby_tags_listview;
    private LinearLayout no_nearby_tags_overlay;

    private OnNearbyTaggedSelectedListener nearbyTagsCallback;

    public interface OnNearbyTaggedSelectedListener {
        public abstract void OnNearbyTaggedSelected (int pos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            nearbyTagsCallback = (OnNearbyTaggedSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnNearbyTaggedSelectedListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nearby_tags, container, false);
        nearby_tags_listview = (ListView) view.findViewById(R.id.nearby_tags_listview);
        no_nearby_tags_overlay = (LinearLayout) view.findViewById(R.id.no_nearby_tags_overlay);
        no_nearby_tags_overlay.setClickable(true);
        no_nearby_tags_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
            }
        });
        nearby_tags_listview.setEmptyView(no_nearby_tags_overlay);

        vehicleListAdapter = new VehicleListAdapter(inflater.getContext());
        nearby_tags_listview.setAdapter(vehicleListAdapter);

        List<TaggedVehicle> taggedVehicles = TaggedVehicleList.get().getTaggedVehicleList();
        for (TaggedVehicle vehicle : taggedVehicles) {
            vehicleListAdapter.add (vehicle);
        }
        vehicleListAdapter.notifyDataSetChanged();

        nearby_tags_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) nearby_tags_listview.getItemAtPosition(position);
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
        SearchView sv = new SearchView( ((NearbyActivity) getActivity()).getActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
//
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return true;
            }
//
            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return true;
            }
        });
    }


}
