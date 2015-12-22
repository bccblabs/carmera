package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bowyer.app.fabtransitionlayout.FooterLayout;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnEditBodyTypes;
import carmera.io.carmera.listeners.OnEditDriveTrain;
import carmera.io.carmera.listeners.OnEditHp;
import carmera.io.carmera.listeners.OnEditMakes;
import carmera.io.carmera.listeners.OnEditMpg;
import carmera.io.carmera.listeners.OnEditTags;
import carmera.io.carmera.listeners.OnEditTorque;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.requests.MakesQueryRequest;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 7/20/15.
 */
public class SearchContainer extends Fragment {

    public String TAG = getClass().getCanonicalName();

    @Bind(R.id.data_viewpager) public ViewPager viewPager;
    @Bind(R.id.viewpagertab) public SmartTabLayout viewPagerTab;

    public static SearchContainer newInstance () {
        SearchContainer fragment = new SearchContainer();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.search_container, container, false);
        ButterKnife.bind(this, v);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(),
                FragmentPagerItems.with (getContext())
                        .add(R.string.fast_search, SpecialSearchFragment.class)
                        .add (R.string.basic_search, BasicSearchFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        return v;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

}
