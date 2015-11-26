package carmera.io.carmera.fragments.main_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import carmera.io.carmera.R;

/**
 * Created by bski on 11/26/15.
 */
public class LoadingFragment extends Fragment {

    public static LoadingFragment newInstance() {
        return new LoadingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loading, container, false);
        return v;
    }
}
