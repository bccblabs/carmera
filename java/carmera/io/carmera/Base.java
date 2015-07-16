package carmera.io.carmera;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.fragments.Capture;
import carmera.io.carmera.fragments.RecognitionResultsDisplay;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by bski on 6/3/15.
 */
public class Base extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener,
                                                       Capture.OnCameraResultListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private Capture captureFragment;
    private RecognitionResultsDisplay recognitionResultsFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;

    private String TAG = this.getClass().getCanonicalName();

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
        captureFragment = Capture.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, captureFragment)
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
        viewAnimator = new ViewAnimator<>(this, list, captureFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem close = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(close);
        SlideMenuItem menuItem0 = new SlideMenuItem("Capture", R.drawable.ic_action_camera_white_small);
        list.add(menuItem0);
        SlideMenuItem menuItem1 = new SlideMenuItem("Favorites", R.drawable.ic_favorite_border_white_24dp);
        list.add(menuItem1);
        SlideMenuItem menuItem2 = new SlideMenuItem("Search", R.drawable.ic_search_white_24dp);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem("History", R.drawable.ic_stars_white_24dp);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem("Nearby", R.drawable.ic_location_on_white_24dp);
        list.add(menuItem4);

    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
                return super.onOptionsItemSelected(item);
//        }
    }


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;

    }

    private ScreenShotable replaceCapture (ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        captureFragment = Capture.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, captureFragment).commit();
        return captureFragment;

    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;
            case "Capture": {
                return replaceCapture(screenShotable, position);
            }
            default:
                return replaceFragment(screenShotable, position);
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
