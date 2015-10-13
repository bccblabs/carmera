package carmera.io.carmera.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;

/**
 * Created by bski on 10/12/15.
 */
public class MechanicalSearchFragment extends SearchFragment {

    public static MechanicalSearchFragment newInstance() {
        return new MechanicalSearchFragment();
    }

    @Bind(R.id.mechanical_container) public ObservableScrollView mechanical_container;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatanceState) {
        View v = inflater.inflate(R.layout.mechanical_search, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mechanical_container, null);
    }

}
