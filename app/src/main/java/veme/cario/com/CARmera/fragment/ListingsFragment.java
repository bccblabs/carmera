package veme.cario.com.CARmera.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class ListingsFragment extends ListFragment {

    OnListingSelectedListener listingCallback;

    public interface OnListingSelectedListener {
        public abstract void onListingSelected (int pos);
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
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
