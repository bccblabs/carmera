package carmera.io.carmera.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.fragments.data_fragments.Comments;
import carmera.io.carmera.fragments.data_fragments.Complaints;
import carmera.io.carmera.fragments.data_fragments.Costs;
import carmera.io.carmera.fragments.data_fragments.Dimensions;
import carmera.io.carmera.fragments.data_fragments.Equipments;
import carmera.io.carmera.fragments.data_fragments.Features;
import carmera.io.carmera.fragments.data_fragments.Incentives;
import carmera.io.carmera.fragments.data_fragments.Performance;
import carmera.io.carmera.fragments.data_fragments.Prices;
import carmera.io.carmera.fragments.data_fragments.Options;
import carmera.io.carmera.fragments.data_fragments.Ratings;
import carmera.io.carmera.fragments.data_fragments.Recalls;
import carmera.io.carmera.fragments.data_fragments.Safety;
import carmera.io.carmera.fragments.data_fragments.SafetyEquipments;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/11/15.
 */
public class DataViewerActivity extends AppCompatActivity {
    private String TAG = getClass().getCanonicalName();

    @Bind(R.id.data_viewpager) public ViewPager viewPager;

    @Bind(R.id.viewpagertab) public SmartTabLayout viewPagerTab;

    @Bind (R.id.data_viewer_model_text) TextView model_name;

    public void add_page (@StringRes int StringResId, String extras_id, Class<? extends Fragment> clzz, FragmentPagerItems.Creator page_creator) {
        Parcelable data = getIntent().getParcelableExtra(extras_id);

        if (data != null) {

            if (extras_id.equals(Constants.EXTRA_RECALLS)) {
                carmera.io.carmera.models.car_data_subdocuments.Recalls recalls = Parcels.unwrap(data);
                if (recalls.numberOfRecalls == null || recalls.numberOfRecalls < 1 )
                    return;
            }

            if (extras_id.equals(Constants.EXTRA_CMPL)) {
                carmera.io.carmera.models.car_data_subdocuments.Complaints complaints = Parcels.unwrap(data);
                if (complaints.count == null || complaints.count < 1)
                    return;
            }

            if (extras_id.equals(Constants.EXTRA_SAFETY) && StringResId == R.string.safety_equipments) {
                carmera.io.carmera.models.car_data_subdocuments.Safety safety = Parcels.unwrap(data);
                if (safety.equipments == null || safety.equipments.size() < 1)
                    return;
            }

            if (extras_id.equals(Constants.EXTRA_INCENTIVES)) {
                carmera.io.carmera.models.car_data_subdocuments.Incentives incentives = Parcels.unwrap(data);
                if (incentives.getCount() < 1)
                    return;
            }

            page_creator.add (StringResId, clzz, new Bundler().putParcelable(extras_id, data).get());

        }
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_data_viewer);
        ButterKnife.bind(this);
        model_name.setText(getIntent().getStringExtra(Constants.EXTRA_MODEL_NAME));

        FragmentPagerItems.Creator page_creator = new FragmentPagerItems.Creator(this);

        add_page(R.string.recalls, Constants.EXTRA_RECALLS, Recalls.class, page_creator);
        add_page(R.string.complaints, Constants.EXTRA_CMPL, Complaints.class, page_creator);
        add_page(R.string.safety, Constants.EXTRA_SAFETY, Safety.class, page_creator);
        add_page(R.string.safety_equipments, Constants.EXTRA_SAFETY, SafetyEquipments.class, page_creator);

        add_page(R.string.performance, Constants.EXTRA_POWERTRAIN, Performance.class, page_creator);
        add_page(R.string.dimensions, Constants.EXTRA_DIMENSIONS, Dimensions.class, page_creator);

        add_page(R.string.prices, Constants.EXTRA_PRICES, Prices.class, page_creator);
        add_page(R.string.costs, Constants.EXTRA_COSTS, Costs.class, page_creator);

        add_page(R.string.ratings, Constants.EXTRA_RATINGS, Ratings.class, page_creator);
        add_page(R.string.favorites, Constants.EXTRA_FAV, Comments.class, page_creator);
        add_page(R.string.improvements, Constants.EXTRA_IMPR, Comments.class, page_creator);
        add_page(R.string.reviews, Constants.EXTRA_REVIEW, Comments.class, page_creator);

        add_page(R.string.equipments, Constants.EXTRA_EQUIPMENTS, Equipments.class, page_creator);
        add_page(R.string.features, Constants.EXTRA_FEATURES, Features.class, page_creator);
        add_page(R.string.options, Constants.EXTRA_OPTIONS, Options.class, page_creator);

        add_page (R.string.incentives, Constants.EXTRA_INCENTIVES, Incentives.class, page_creator);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                page_creator.create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);


    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
