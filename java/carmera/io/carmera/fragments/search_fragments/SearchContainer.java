package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
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
import carmera.io.carmera.fragments.main_fragments.CaptureFragment;
import carmera.io.carmera.listeners.OnSearchFragmentVisible;
/**
 * Created by bski on 7/20/15.
 */
public class SearchContainer extends Fragment {

    public String TAG = getClass().getCanonicalName();
    private OnSearchFragmentVisible onSearchFragmentVisible;

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
                        .add(R.string.staggered, StaggeredSearch.class)
//                        .add(R.string.str_image_search, CaptureFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPagerTab.setViewPager(viewPager);
        return v;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
    }
}
