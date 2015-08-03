package carmera.io.carmera;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.fragments.BasicSearchFragment;
import carmera.io.carmera.fragments.CaptureFragment;
import carmera.io.carmera.fragments.ListingsV2Fragment;
import carmera.io.carmera.fragments.RecognitionResultsDisplay;
import carmera.io.carmera.fragments.SettingsFragment;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener,
                                                       CaptureFragment.OnCameraResultListener,
                                                       BasicSearchFragment.OnSearchVehiclesListener,
                                                       RecognitionResultsDisplay.RetakePhotoListener{

    public static final String CLOSE = "Close";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private CaptureFragment captureFragmentFragment;
    private BasicSearchFragment searchFragment;
    private SettingsFragment settingsFragment;
    private RecognitionResultsDisplay recognitionResultsFragment;
    private ListingsV2Fragment listingsV2Fragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;

    private String TAG = this.getClass().getCanonicalName();

    @Override
    public void retakePhoto() {
        captureFragmentFragment = CaptureFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, captureFragmentFragment)
                .addToBackStack("CAPTURE")
                .commit();
    }

    @Override
    public void OnSearchListings (Parcelable query) {
        Bundle args = new Bundle();
        args.putParcelable(ListingsV2Fragment.EXTRA_LISTING_QUERY, query);
        listingsV2Fragment = ListingsV2Fragment.newInstance();
        listingsV2Fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, listingsV2Fragment)
                .addToBackStack("LISTINGS")
                .commit();
    }

    @Override
    public void OnCameraResult (byte[] image_data) {
        Bundle args = new Bundle();
        args.putByteArray("image_data", image_data);
        recognitionResultsFragment = new RecognitionResultsDisplay();
        recognitionResultsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.content_frame, recognitionResultsFragment)
                                   .addToBackStack("RECOGNITION_RESULTS")
                                   .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);
        captureFragmentFragment = CaptureFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, captureFragmentFragment)
                .addToBackStack("CAPTURE")
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, captureFragmentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem close = new SlideMenuItem(CLOSE, R.drawable.icn_close);
        list.add(close);
        SlideMenuItem menuItem0 = new SlideMenuItem("Capture", R.drawable.ic_action_camera_white_small);
        list.add(menuItem0);
        SlideMenuItem menuItem2 = new SlideMenuItem("Search", R.drawable.ic_search_white_24dp);
        list.add(menuItem2);
        SlideMenuItem menuItem1 = new SlideMenuItem("Favorites", R.drawable.ic_favorite_border_white_24dp);
        list.add(menuItem1);
        SlideMenuItem menuItem3 = new SlideMenuItem("History", R.drawable.ic_stars_white_24dp);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem("Nearby", R.drawable.ic_location_on_white_24dp);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem("Settings", R.drawable.ic_settings_white_24dp);
        list.add(menuItem5);

    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.background_floating_material_light));
        setSupportActionBar(toolbar);
        ActionBar actionbar  = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setHomeButtonEnabled(true);
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, String fragmentName) {
        switch (fragmentName) {
            case "Capture":
                captureFragmentFragment = CaptureFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, captureFragmentFragment).commit();
                return captureFragmentFragment;
            case "Search":
                searchFragment = BasicSearchFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, searchFragment).commit();
                return searchFragment;
        }
        return null;
    }


    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CLOSE:
                return screenShotable;
            case "Capture": {
                return replaceFragment(screenShotable, position, "Capture");
            }
            case "Search": {
                return replaceFragment(screenShotable, position, "Search");
            }
            case "Settings": {
                startActivity(new Intent(this, SettingsFragment.class));
            }
            default:
                return replaceFragment(screenShotable, position, "Search");
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
