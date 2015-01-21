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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.ProfileActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedListingsList;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.ListingsAdapter;
import veme.cario.com.CARmera.util.VehicleListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class SavedListingsFragment extends Fragment {

    private static final String TAG = "SAVED_LISTINGS_FRAGMENT";


    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private ListingsAdapter vehicleListAdapter;
    private ListView saved_listings_listview;
    private LinearLayout no_saved_listings_overlay;
    private OnSavedListingSelectedListener listingCallback;

    private TextView header_view;
    private boolean has_header = true;
    public interface OnSavedListingSelectedListener {
        public abstract void OnSavedListingSelected (int pos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listingCallback = (OnSavedListingSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
        if (activity instanceof ProfileActivity) {
            has_header = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_listings, container, false);
        saved_listings_listview = (ListView) view.findViewById(R.id.saved_listings_listview);
        no_saved_listings_overlay = (LinearLayout) view.findViewById(R.id.no_saved_listings_overlay);
        no_saved_listings_overlay.setClickable(true);
        no_saved_listings_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NearbyActivity.class);
                startActivity(i);
            }
        });
        saved_listings_listview.setEmptyView(no_saved_listings_overlay);

        vehicleListAdapter = new ListingsAdapter(inflater.getContext());
        saved_listings_listview.setAdapter(vehicleListAdapter);

        /* load data from parse query */
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

        saved_listings_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) saved_listings_listview.getItemAtPosition(position);
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

        header_view = (TextView) view.findViewById(R.id.saved_listings_header);
        if (!has_header)
            header_view.setVisibility(View.GONE);
        return view;
    }

}