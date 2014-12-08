package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import veme.cario.com.CARmera.fragment.NearbyListingFragment;
import veme.cario.com.CARmera.fragment.NearbyTaggedFragment;

/**
 * Created by bski on 11/22/14.
 */
public class NearbyActivity extends BaseActivity {

    private ViewPager viewPager;
    private NeabyPagerAdapter nearbyPagerAdapter;
    /* TODO:
        1. Query by geolocation
        2. Query by Edmund's API/other api
     */
    @Override
    public void onCreate (Bundle savedBundleinst) {
        super.onCreate(savedBundleinst);
        setContentView(R.layout.activity_nearby);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        nearbyPagerAdapter = new NeabyPagerAdapter (getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.neaby_pager);
        viewPager.setAdapter(nearbyPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                actionBar.setSelectedNavigationItem(pos);
            }
        });
        /* 1. nearby tags */
        actionBar.addTab(actionBar.newTab()
                                  .setText("Listings")
                                  .setTabListener(new ActionBar.TabListener() {
                                      @Override
                                      public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                                          viewPager.setCurrentItem(tab.getPosition());
                                      }

                                      @Override
                                      public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                                      }

                                      @Override
                                      public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                                      }
                                  }));

        /* 2. nearby listings */
        actionBar.addTab((actionBar.newTab()
                                   .setText("Tags")
                                   .setTabListener(new ActionBar.TabListener() {
                                       @Override
                                       public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                                           viewPager.setCurrentItem(tab.getPosition());
                                       }

                                       @Override
                                       public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                                       }

                                       @Override
                                       public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                                       }
                                   })));
    }

    private static class NeabyPagerAdapter extends FragmentPagerAdapter {
        public NeabyPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment frag;
            switch(pos) {
                case 0:
                    frag = new NearbyTaggedFragment();
                    break;
                case 1:
                    frag = new NearbyListingFragment();
                    break;
                default:
                    return null;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
