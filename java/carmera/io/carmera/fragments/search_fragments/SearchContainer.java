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
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.models.queries.ApiQuery;
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
        ListingsQuery listingsQuery = new ListingsQuery();
        listingsQuery.car = SearchFragment.getGenQuery();
        listingsQuery.api = SearchFragment.getApiQuery();
        search_callback.OnSearchListings(Parcels.wrap(ListingsQuery.class, listingsQuery));
    }

    private OnSearchVehiclesListener search_callback = null;

    public interface OnSearchVehiclesListener {
        void OnSearchListings (Parcelable car_query);
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
                .add(R.string.basic_search, BasicSearchFragment.class)
                .add (R.string.price_mileage_search, PricingMileage.class)
                .add (R.string.equipments_search, ColorEquipSearchFragment.class)
                .add(R.string.mechanical_search, MechanicalSearchFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

        /* reset the static query variable */
        SearchFragment.genQuery = new CarQuery();
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
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
