package veme.cario.com.CARmera.fragment;

import android.support.v4.app.ListFragment;

/**
 * Created by bski on 12/7/14.
 */
public class NearbyListingFragment extends ListFragment {

    private OnNearbyListingSelectedListener listingCallback;
    public interface OnNearbyListingSelectedListener {
        public abstract void OnNearbyListingSelected (int pos);
    }

}
