package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.support.v4.app.ListFragment;

/**
 * Created by bski on 12/7/14.
 */
public class NearbyListingFragment extends ListFragment {


    /* TODO: Display a title: "10 vehicles based on tag and favorite results"
       TODO: OnClick, display a search box, on click, add/remove cretaria
       TODO: If empty, specify other cretaria
     */

    /* if all user actions are to be done in adapter code, then this is redundant */
    private OnNearbyListingSelectedListener listingCallback;
    public interface OnNearbyListingSelectedListener {
        public abstract void OnNearbyListingSelected (int pos);
    }

}
