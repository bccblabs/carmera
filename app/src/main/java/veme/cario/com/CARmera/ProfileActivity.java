package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.facebook.AppEventsLogger;

import veme.cario.com.CARmera.fragment.FavoriteVehicleFragment;
import veme.cario.com.CARmera.fragment.MentionedVehicleFragment;
import veme.cario.com.CARmera.fragment.TaggedVehicleFragment;

/**
 * Created by bski on 11/23/14.
 */
public class ProfileActivity extends BaseActivity
                             implements TaggedVehicleFragment.OnTaggedListingSelectedListener,
                                        FavoriteVehicleFragment.OnSavedListingSelectedListener,
                                        MentionedVehicleFragment.OnMentionedListingSelectedListener{

    private ViewPager viewPager;
    private ProfileSectionsAdapter profileSectionsAdapter;

    @Override
    public void OnTaggedListingSelected (int pos) {
    }

    @Override
    public void OnSavedListingSelected (int pos) {
    }

    @Override
    public void OnMentionedListingSelected (int pos) {
    }


    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        setContentView(R.layout.activity_user_profile);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        profileSectionsAdapter = new ProfileSectionsAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.profile_pager);
        viewPager.setAdapter(profileSectionsAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                                        @Override
                                        public void onPageSelected(int pos) {
                                            actionBar.setSelectedNavigationItem(pos);
                                        }
                                    });

        /* 1. tagged/favorited */
        actionBar.addTab(actionBar.newTab()
                                  .setText("Tagged")
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

        /* 2. saved/favorites */
        actionBar.addTab(actionBar.newTab()
                                    .setText("Saved Listings")
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

        /* 3. tagged by others */
        actionBar.addTab(actionBar.newTab()
                                    .setText("Mentioned")
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
    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private static class ProfileSectionsAdapter extends FragmentPagerAdapter {
        public ProfileSectionsAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment frag;
            switch(pos) {
                case 0:
                    frag = new TaggedVehicleFragment();
                    break;
                case 1:
                    frag = new FavoriteVehicleFragment();
                    break;
                case 2:
                    frag = new MentionedVehicleFragment();
                    break;
                default:
                    return null;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
