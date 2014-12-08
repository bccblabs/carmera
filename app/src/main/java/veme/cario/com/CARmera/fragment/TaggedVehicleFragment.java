package veme.cario.com.CARmera.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import veme.cario.com.CARmera.model.UserModels.FavoriteVehicleList;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicleList;
import veme.cario.com.CARmera.util.VehicleListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class TaggedVehicleFragment extends ListFragment {


    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private VehicleListAdapter vehicleListAdapter;

    private OnListingSelectedListener listingCallback;
    public interface OnListingSelectedListener {
        public abstract void onListingSelected (int pos);
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        vehicleListAdapter = new VehicleListAdapter(getActivity());
        this.setListAdapter(vehicleListAdapter);
        List<TaggedVehicle> taggedVehicles = TaggedVehicleList.get().getTaggedVehicleList();
        for (TaggedVehicle vehicle : taggedVehicles) {
            vehicleListAdapter.add (vehicle);
        }
    }

    @Override
    public void onStart () {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listingCallback = (OnListingSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }

    @Override
    public void onListItemClick (ListView l, View v, int pos, long id) {
        listingCallback.onListingSelected(pos);
        VehicleInfoDialog vehicleInfoDialog = new VehicleInfoDialog();
        /* year, make, model, listing_id */
        Bundle args = getArguments();
        vehicleInfoDialog.setArguments(args);
        FragmentManager fm = getFragmentManager();
        vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
    }

    /* need to have a specilized adapter */
}
