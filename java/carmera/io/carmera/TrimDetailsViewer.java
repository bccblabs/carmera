package carmera.io.carmera;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.ListingsFragment;
import carmera.io.carmera.fragments.RentalFragment;
import carmera.io.carmera.fragments.TrimDetails;
import carmera.io.carmera.models.TrimData;
import carmera.io.carmera.models.VehicleQueries;
import carmera.io.carmera.models.VehicleQuery;

/**
 * Created by bski on 7/13/15.
 */
public class TrimDetailsViewer extends ActionBarActivity {

    public static final String EXTRA_TRIM_DATA = "extra_trim_data";

    @Bind(R.id.trim_view_pager)
    MaterialViewPager trims_view_pager;

    private Parcelable trimDataParcelable;
    private TrimData trimData;

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.trim_viewer);
        ButterKnife.bind(this);
        this.trimDataParcelable = getIntent().getExtras().getParcelable(EXTRA_TRIM_DATA);
        this.trimData = Parcels.unwrap(this.trimDataParcelable);
        trims_view_pager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            int init_position = -1;
            @Override
            public Fragment getItem(int position) {
                Bundle args = new Bundle();
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = TrimDetails.newInstance();
                        args.putParcelable(EXTRA_TRIM_DATA, TrimDetailsViewer.this.trimDataParcelable);
                        fragment.setArguments(args);
                        return fragment;
                    case 1:
                        fragment = ListingsFragment.newInstance();
                        VehicleQueries queries = new VehicleQueries();
                        VehicleQuery query = new VehicleQuery();
                        query.setTrim(TrimDetailsViewer.this.trimData.getTrim());
                        queries.addQueries(query);
                        args.putParcelable(ListingsFragment.EXTRA_LISTING_QUERY, Parcels.wrap(queries));
                        fragment.setArguments(args);
                        return fragment;

                    case 2:
                        return RentalFragment.newInstance();
                    default:
                        return TrimDetails.newInstance();
                }
            }


            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                if (position == init_position)
                    return;
                init_position = position;
                int color = 0;
                String imageUrl = "";
                switch (position) {
                    case 0:
                        imageUrl = getResources().getString (R.string.edmunds_baseurl) + trimData.getImages().getExterior().get(0);
                        break;
                    case 1:
                        imageUrl = getResources().getString (R.string.edmunds_baseurl) + trimData.getImages().getExterior().get(1);
                        break;
                    case 2:
                        imageUrl = getResources().getString (R.string.edmunds_baseurl) + trimData.getImages().getExterior().get(2);
                        break;
                }
                final int fadeDuration = 100;
                trims_view_pager.setImageUrl(imageUrl, fadeDuration);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Highlights";
                    case 1:
                        return "Listings";
                    case 2:
                        return "Rentals";
                }
                return "";
            }
        });

        trims_view_pager.getPagerTitleStrip().setViewPager(trims_view_pager.getViewPager());
        trims_view_pager.getViewPager().setCurrentItem(0);
    }

}
