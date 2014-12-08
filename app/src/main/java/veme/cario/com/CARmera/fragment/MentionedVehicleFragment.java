package veme.cario.com.CARmera.fragment;

import android.support.v4.app.ListFragment;

/**
 * Created by bski on 12/7/14.
 */
public class MentionedVehicleFragment extends ListFragment {
    private OnMentionedListingSelectedListener onMentionedListingSelectedListener;

    public interface OnMentionedListingSelectedListener {
        public abstract void OnMentionedListingSelected(int pos);
    }
}
