package carmera.io.carmera.activities;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.fragments.dealer_fragments.DealerInfoFragment;
import carmera.io.carmera.fragments.dealer_fragments.DealerReviews;

import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/30/15.
 */
public class DealerViewerActivity extends AppCompatActivity {
    public String TAG = getClass().getCanonicalName();

    @Bind(R.id.data_viewpager) public ViewPager viewPager;
    @Bind(R.id.viewpagertab) public SmartTabLayout viewPagerTab;
    @Bind (R.id.data_viewer_model_text) public TextView data_viewer_model_text;

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_data_viewer);
        ButterKnife.bind(this);
        FragmentPagerItems.Creator page_creator = new FragmentPagerItems.Creator(this);
        data_viewer_model_text.setText(getIntent().getStringExtra(Constants.EXTRA_DEALER_NAME));

        add_page(R.string.dealer_info, Constants.EXTRA_DEALERID, DealerInfoFragment.class, page_creator);
        add_page(R.string.reviews, Constants.EXTRA_DEALERID, DealerReviews.class, page_creator);

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

    private void add_page (@StringRes int StringResId, String extras_id, Class<? extends Fragment> clzz, FragmentPagerItems.Creator page_creator) {
        String data = getIntent().getStringExtra(extras_id);
        if (data != null) {
            Bundle args = new Bundle();
            args.putString(extras_id, data);
            page_creator.add (StringResId, clzz, args);
        }
    }

}
