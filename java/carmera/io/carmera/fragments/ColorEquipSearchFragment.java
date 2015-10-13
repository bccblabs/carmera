package carmera.io.carmera.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class ColorEquipSearchFragment extends SearchFragment {

    public static ColorEquipSearchFragment newInstance() {
        return new ColorEquipSearchFragment();
    }

    @Bind(R.id.colors_container) public ObservableScrollView color_container;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatanceState) {
        View v = inflater.inflate(R.layout.colors_equipments_search, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialViewPagerHelper.registerScrollView(getActivity(), color_container, null);
    }

}
