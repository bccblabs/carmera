package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AppEventsLogger;

import veme.cario.com.CARmera.fragment.ActivityFragment.NearbyListingFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.NearbyTaggedFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 11/22/14.
 */
public class NearbyActivity extends BaseActivity
                            implements NearbyListingFragment.OnNearbyListingSelectedListener,
                                       NearbyTaggedFragment.OnNearbyTaggedSelectedListener,
                                       SelectStyleFragment.SelectResultListener,
                                       CarInfoFragment.OnReselectClickListener,
                                       ImageFragment.ImageResultListener {


    private ViewPager viewPager;
    private NeabyPagerAdapter nearbyPagerAdapter;
    private VehicleInfoDialog vehicleInfoDialog = null;

    @Override
    public void onStyleSelected (byte[] imageData, String trim_id, String trim_name, String yr, String mk, String md) {
        Bundle args = new Bundle();
        args.putString ("dialog_type", "vehicle_info");
        args.putString ("vehicle_id", trim_id);
        args.putString ("vehicle_year", yr);
        args.putString ("vehicle_make", mk);
        args.putString ("vehicle_model", md);
        args.putString ("vehicle_trim_name", trim_name);
        args.putByteArray("imageData", imageData);


        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
    }

    @Override
    public void OnReselectClick (byte[] raw_photo, String yr, String mk, String md) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", yr);
        args.putString("vehicle_make", mk);
        args.putString("vehicle_model", md);

        args.putByteArray("imageData", raw_photo);
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "styleChooserOverlay");

    }

    @Override
    public void onRecognitionResult (byte[] imageData, String year, String make, String model) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", year);
        args.putString("vehicle_make", make);
        args.putString("vehicle_model", model);
        args.putByteArray("imageData", imageData);

        if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }

        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "styleChooserOverlay");

    }

    @Override
    public void OnNearbyListingsSelected(int pos) {
        /* needs it to display listings details */
    }

    @Override
    public void OnNearbyTaggedSelected(int pos) {
    }

    /* TODO:
        1. Query by geolocation
        2. Query by Edmund's API/other api
     */
    @Override
    public void onCreate (Bundle savedBundleinst) {
        super.onCreate(savedBundleinst);

        getLayoutInflater().inflate(R.layout.activity_nearby, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Nearby");

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        Intent i = getIntent();
        Bundle args = i.getExtras();

        nearbyPagerAdapter = new NeabyPagerAdapter (getSupportFragmentManager(), args);

        viewPager = (ViewPager) findViewById(R.id.nearby_pager);
        viewPager.setAdapter(nearbyPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                actionBar.setSelectedNavigationItem(pos);
            }
        });

        /* nearby listings */
        actionBar.addTab((actionBar.newTab()
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
                })));

        /* nearby tags */
        actionBar.addTab(actionBar.newTab()
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
                                  }));


    }

    @Override
    public void onPause() {
        super.onPause();
        // AppEventsLogger.deactivateApp(this);
    }


    private static class NeabyPagerAdapter extends FragmentPagerAdapter {
        private Bundle args;

        public NeabyPagerAdapter (FragmentManager fm, Bundle args_) {
            super(fm);
            this.args = args_;
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment frag;
            switch(pos) {
                case 0:
                    frag = new NearbyListingFragment();
                    frag.setArguments(this.args);
                    break;
                case 1:
                    frag = new NearbyTaggedFragment();
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
