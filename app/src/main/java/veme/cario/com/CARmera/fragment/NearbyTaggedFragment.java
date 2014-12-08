package veme.cario.com.CARmera.fragment;

import android.support.v4.app.ListFragment;

/**
 * Created by bski on 12/7/14.
 */
public class NearbyTaggedFragment extends ListFragment {
    private OnListingSelectedListener listingCallback;
    public interface OnListingSelectedListener {
        public abstract void onListingSelected (int pos);
    }

}
