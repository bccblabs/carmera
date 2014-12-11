package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import veme.cario.com.CARmera.NearbyActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedListingsList;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.VehicleListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class SavedListingsFragment extends Fragment {

    private static final String TAG = "SAVED_LISTINGS_FRAGMENT";


    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private VehicleListAdapter vehicleListAdapter;

    private ListView saved_listings_listview;
    private LinearLayout no_saved_listings_overlay;

    private OnSavedListingSelectedListener listingCallback;

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

        vehicleListAdapter = new VehicleListAdapter(inflater.getContext());
        saved_listings_listview.setAdapter(vehicleListAdapter);

        List<TaggedVehicle> savedVehicles = SavedListingsList.get().getFavorites();
        for (TaggedVehicle vehicle : savedVehicles) {
            vehicleListAdapter.add (vehicle);
        }
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

        return view;
    }
}
