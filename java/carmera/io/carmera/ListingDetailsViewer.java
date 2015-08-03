package carmera.io.carmera;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.ListingDetailsFragment;
import carmera.io.carmera.fragments.ListingsV2Fragment;
import carmera.io.carmera.fragments.TrimDetailsFragment;
import carmera.io.carmera.models.ListingV2;
import carmera.io.carmera.models.TrimData;

/**
 * Created by bski on 7/15/15.
 */
public class ListingDetailsViewer extends ActionBarActivity {
    public final String TAG = getClass().getCanonicalName();
    private Parcelable listings_stats;
    private Parcelable listing;
    private ListingV2 listing_data;

    @Bind(R.id.trims_listings_viewpager)
    MaterialViewPager listing_view_pager;

    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        setContentView(R.layout.trims_listings_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = listing_view_pager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
        listing = getIntent().getExtras().getParcelable(ListingsV2Fragment.EXTRA_LISTING_DATA);
        listings_stats = getIntent().getExtras().getParcelable(ListingsV2Fragment.EXTRA_LISTINGS_STAT);
        listing_data = Parcels.unwrap(listing);

        listing_view_pager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            int init_position = -1;
            @Override
            public Fragment getItem(int position) {
                Bundle args = new Bundle();
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = ListingDetailsFragment.newInstance();
                        args.putParcelable(ListingsV2Fragment.EXTRA_LISTING_DATA, listing);
                        args.putParcelable(ListingsV2Fragment.EXTRA_LISTINGS_STAT, listings_stats);
                        fragment.setArguments(args);
                        return fragment;
                    case 1:
                        fragment = TrimDetailsFragment.newInstance();
                        ListingV2 listingData = Parcels.unwrap(listing);
                        args.putParcelable(TrimDetailsFragment.EXTRA_TRIM_DATA, Parcels.wrap(TrimData.class, listingData.snapshot));
                        fragment.setArguments(args);
                        return fragment;
                    default:
                        return ListingDetailsFragment.newInstance();
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
                        return "Listing";
                    case 1:
                        return "Trim Details";
                }
                return "";
            }
        });
        listing_view_pager.getPagerTitleStrip().setViewPager(listing_view_pager.getViewPager());
        listing_view_pager.getViewPager().setCurrentItem(0);
    }
}
