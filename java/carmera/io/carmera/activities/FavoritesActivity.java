package carmera.io.carmera.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.fragments.favorites_fragments.SavedModelsFragment;
import carmera.io.carmera.fragments.favorites_fragments.SavedSearchFragment;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 1/20/16.
 */
public class FavoritesActivity extends AppCompatActivity {

    @Bind (R.id.toolbar) Toolbar toolbar;
    @Bind (R.id.data_viewpager) public ViewPager viewPager;
    @Bind (R.id.viewpagertab) public SmartTabLayout viewPagerTab;
    @Bind (R.id.data_viewer_model_text) public TextView toolbar_title_text;
    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
        setContentView(R.layout.activity_data_viewer);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        toolbar_title_text.setText("Favorites");

        FragmentPagerItems.Creator page_creator = new FragmentPagerItems.Creator(this);
        add_page(R.string.models, Constants.EXTRAS_LISTINGS_FAV, SavedModelsFragment.class, page_creator);
        add_page(R.string.search_history, Constants.EXTRAS_LISTINGS_SEEN, SavedSearchFragment.class, page_creator);
//        add_page(R.string.listings, Constants.EXTRAS_LISTINGS_FAV, ListingsFragment.class, page_creator);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                page_creator.create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

    }

    public void add_page (@StringRes int StringResId, String extras_id, Class<? extends Fragment> clzz, FragmentPagerItems.Creator page_creator) {
        Parcelable data = getIntent().getParcelableExtra(extras_id);
        page_creator.add (StringResId, clzz, new Bundler().putParcelable(extras_id, data).get());
    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent i = new Intent(FavoritesActivity.this, SearchActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
