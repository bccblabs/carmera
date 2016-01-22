package carmera.io.carmera;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.fragments.main_fragments.ListingsFragment;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 1/20/16.
 */
public class FavoritesActivity extends FragmentActivity {

    private String TAG = getClass().getCanonicalName();

    @Bind (R.id.data_viewpager) public ViewPager viewPager;

    @Bind (R.id.viewpagertab) public SmartTabLayout viewPagerTab;

    @Bind (R.id.fab_search) public FloatingActionButton fab_search;

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.data_viewer_layout);
        ButterKnife.bind(this);
        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this, SearchActivity.class));
            }
        });
        FragmentPagerItems.Creator page_creator = new FragmentPagerItems.Creator(this);
        add_page(R.string.search_history, Constants.EXTRAS_LISTINGS_SEEN, ListingsFragment.class, page_creator);
        add_page(R.string.models, Constants.EXTRAS_LISTINGS_FAV, ListingsFragment.class, page_creator);
        add_page(R.string.listings, Constants.EXTRAS_LISTINGS_FAV, ListingsFragment.class, page_creator);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                page_creator.create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

    }

    public void add_page (@StringRes int StringResId, String extras_id, Class<? extends Fragment> clzz, FragmentPagerItems.Creator page_creator) {
        Parcelable data = getIntent().getParcelableExtra(extras_id);
//
//        if (data != null) {
//
//            if (extras_id.equals(Constants.EXTRA_RECALLS)) {
//                carmera.io.carmera.models.car_data_subdocuments.Recalls recalls = Parcels.unwrap(data);
//                if (recalls.numberOfRecalls == null || recalls.numberOfRecalls < 1)
//                    return;
//            }
            page_creator.add (StringResId, clzz, new Bundler().putParcelable(extras_id, data).get());
//        }
    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
