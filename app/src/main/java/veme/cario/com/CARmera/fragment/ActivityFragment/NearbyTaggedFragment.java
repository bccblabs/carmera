package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.support.v4.app.ListFragment;

/**
 * Created by bski on 12/7/14.
 */
public class NearbyTaggedFragment extends ListFragment {

    private OnNearbyTaggedSelectedListener listingCallback;
    public interface OnNearbyTaggedSelectedListener {
        public abstract void OnNearbyTaggedSelected (int pos);
    }

}
