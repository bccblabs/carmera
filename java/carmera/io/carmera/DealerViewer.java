package carmera.io.carmera;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.dealer_fragments.DealerInfoFragment;
import carmera.io.carmera.fragments.dealer_fragments.DealerReviews;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;

import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/30/15.
 */
public class DealerViewer extends FragmentActivity {
    public String TAG = getClass().getCanonicalName();

    @Bind(R.id.data_viewpager) public ViewPager viewPager;

    @Bind(R.id.viewpagertab) public SmartTabLayout viewPagerTab;


    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.data_viewer_layout);
        ButterKnife.bind(this);
        FragmentPagerItems.Creator page_creator = new FragmentPagerItems.Creator(this);


        add_page(R.string.dealer_info, Constants.EXTRA_DEALERID, DealerInfoFragment.class, page_creator);
        add_page(R.string.reviews, Constants.EXTRA_DEALERID, DealerReviews.class, page_creator);
        add_page(R.string.franchise_listings, Constants.EXTRA_FRANCHISEID, ListingsFragment.class, page_creator);
//        add_page(R.string.start_chat, Constants.EXTRA_LISTINGS_CHAT_INFO, DealerChatFragment.class, page_creator);

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
