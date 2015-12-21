package carmera.io.carmera.fragments.search_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;

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
        View v = inflater.inflate (R.layout.data_viewer_layout, container, false);
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
