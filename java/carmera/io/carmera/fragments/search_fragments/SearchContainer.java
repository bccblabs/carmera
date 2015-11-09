package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.parceler.Parcels;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.models.queries.CarQuery;

/**
 * Created by bski on 7/20/15.
 */
public class SearchContainer extends Fragment {

    public String TAG = getClass().getCanonicalName();

    @Bind(R.id.search_viewpager) public ViewPager viewPager;

    @Bind(R.id.viewpagertab) public SmartTabLayout viewPagerTab;

    @OnClick(R.id.search_listings_base)
    public void search_listings() {
        search_callback.OnSearchListings(Parcels.wrap(CarQuery.class, SearchFragment.genQuery));
    }

    private OnSearchVehiclesListener search_callback = null;

    public interface OnSearchVehiclesListener {
        void OnSearchListings (Parcelable query);
    }

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
                FragmentPagerItems.with(getActivity())
                .add (R.string.basic_search, BasicSearchFragment.class)
                .add (R.string.mechanical_search, MechanicalSearchFragment.class)
                .add (R.string.equipments_search, ColorEquipSearchFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            search_callback = (OnSearchVehiclesListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate (savedBundleInstance);
    }
}
