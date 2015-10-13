package carmera.io.carmera;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.ListingsFragment;
import carmera.io.carmera.fragments.TrimDetailsFragment;
import carmera.io.carmera.fragments.TrimsFragment;
import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.TrimData;
import carmera.io.carmera.models.VehicleQueries;
import carmera.io.carmera.models.VehicleQuery;

/**
 * Created by bski on 7/25/15.
 */
public class TrimsListingsActivity extends ActionBarActivity {


    public final String TAG = getClass().getCanonicalName();
    public static final String EXTRA_GEN_DATA = "extra_gen_data";

    @Bind(R.id.trims_listings_viewpager)
    MaterialViewPager trims_listings_viewpager;

    GenerationData generationData;
    List<TrimData> trimsData;
    Parcelable generationDataParcelable;

    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        setContentView(R.layout.trims_listings_activity);
        ButterKnife.bind(this);
        this.generationDataParcelable = getIntent().getExtras().getParcelable(EXTRA_GEN_DATA);
        this.generationData = Parcels.unwrap(this.generationDataParcelable);
        this.trimsData = generationData.getTrims();
        Toolbar toolbar = trims_listings_viewpager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

        trims_listings_viewpager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            int init_position = -1;
            @Override
            public Fragment getItem(int position) {
                Bundle args = new Bundle();
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = TrimsFragment.newInstance();
                        args.putParcelable(TrimsFragment.EXTRA_TRIMS_DATA, Parcels.wrap (trimsData));
                        fragment.setArguments(args);
                        return fragment;
                    case 1:
//                        fragment = ListingsV2Fragment.newInstance();
//                        GenQuery query = new GenQuery();
//                        List<Integer> styleIds = new ArrayList<>();
//                        for (TrimData trimData : trimsData) {
//                            styleIds.addAll(trimData.getStyleIds());
//                        }
//                        query.setStyleIds(styleIds);
//                        args.putParcelable(ListingsV2Fragment.EXTRA_LISTING_QUERY, Parcels.wrap(query));
//                        fragment.setArguments(args);
//                        return fragment;
                        fragment = ListingsFragment.newInstance();
                        VehicleQueries queries = new VehicleQueries();
                        for (TrimData trim : trimsData) {
                            VehicleQuery query = new VehicleQuery();
                            query.setTrim(trim.getTrim());
                            queries.addQueries(query);

                        }
                        args.putParcelable(ListingsFragment.EXTRA_LISTING_QUERY, Parcels.wrap(queries));
                        fragment.setArguments(args);
                        return fragment;
                    default:
                        return TrimDetailsFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Trims";
                    case 1:
                        return "Listings";
                }
                return "";
            }
        });
        trims_listings_viewpager.getPagerTitleStrip().setViewPager(trims_listings_viewpager.getViewPager());
        trims_listings_viewpager.getViewPager().setCurrentItem(0);
    }
}
