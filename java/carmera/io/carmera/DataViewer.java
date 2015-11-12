package carmera.io.carmera;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import org.parceler.Parcel;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.data_fragments.CarImages;
import carmera.io.carmera.fragments.data_fragments.Complaints;
import carmera.io.carmera.fragments.data_fragments.Costs;
import carmera.io.carmera.fragments.data_fragments.Dimensions;
import carmera.io.carmera.fragments.data_fragments.Favorites;
import carmera.io.carmera.fragments.data_fragments.Improvements;
import carmera.io.carmera.fragments.data_fragments.Performance;
import carmera.io.carmera.fragments.data_fragments.Prices;
import carmera.io.carmera.fragments.data_fragments.Ratings;
import carmera.io.carmera.fragments.data_fragments.Recalls;
import carmera.io.carmera.fragments.data_fragments.Reviews;
import carmera.io.carmera.fragments.data_fragments.Safety;
import carmera.io.carmera.models.car_data_subdocuments.Powertrain;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/11/15.
 */
public class DataViewer extends FragmentActivity {
    public String TAG = getClass().getCanonicalName();

    private FragmentPagerItems.Creator page_creator;

    @Bind(R.id.data_viewpager) public ViewPager viewPager;

    @Bind(R.id.viewpagertab) public SmartTabLayout viewPagerTab;

    private void add_page (@StringRes int StringResId, String extras_id, Class<? extends Fragment> clzz) {
        Parcelable data = getIntent().getParcelableExtra(extras_id);
        if (data != null)
            page_creator.add (StringResId, clzz, new Bundler().putParcelable(extras_id, data).get());
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.data_viewer_layout);
        ButterKnife.bind(this);
        page_creator = new FragmentPagerItems.Creator(this);

        add_page(R.string.complaints, Constants.EXTRA_CMPL, Complaints.class);
        add_page(R.string.safety, Constants.EXTRA_SAFETY, Safety.class);
        add_page(R.string.recalls, Constants.EXTRA_RECALLS, Recalls.class);

        add_page(R.string.performance, Constants.EXTRA_POWERTRAIN, Performance.class);
        add_page(R.string.dimensions, Constants.EXTRA_DIMENSIONS, Dimensions.class);
        add_page(R.string.car_images, Constants.EXTRA_IMAGES, CarImages.class);

        add_page(R.string.prices, Constants.EXTRA_PRICES, Prices.class);
        add_page(R.string.costs, Constants.EXTRA_COSTS, Costs.class);

        add_page(R.string.ratings, Constants.EXTRA_RATINGS, Ratings.class);
        add_page(R.string.favorites, Constants.EXTRA_FAV, Favorites.class);
        add_page(R.string.improvements, Constants.EXTRA_IMPR, Improvements.class);
        add_page(R.string.reviews, Constants.EXTRA_REVIEW, Reviews.class);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                page_creator.create());

        int primary = getResources().getColor(R.color.primaryDark);
        int secondary = getResources().getColor(R.color.transparent);

        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(SlidrPosition.RIGHT)
                .velocityThreshold(2400)
                .distanceThreshold(.25f)
                .touchSize(com.r0adkll.deadskunk.utils.Utils.dpToPx(this, 32))
                .build();

        // Attach the Slidr Mechanism to this activity
        Slidr.attach(this, config);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);


    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
