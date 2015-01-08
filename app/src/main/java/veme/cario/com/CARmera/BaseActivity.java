package veme.cario.com.CARmera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.nav_drawer.DrawerAdapter;
import veme.cario.com.CARmera.nav_drawer.DrawerItem;


public class BaseActivity extends FragmentActivity {

    /* Navigation Drawer Variables */
    protected FrameLayout frame_layout;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    protected ListView drawer_listview;
    protected static int drawer_pos;
    private static boolean is_launch = true;
    protected DrawerAdapter drawer_adapter;
    protected List<DrawerItem> drawer_item_list;

    private CharSequence drawerTitle;
    private CharSequence title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_base);
        frame_layout = (FrameLayout) findViewById(R.id.content_frame);
        title = drawerTitle = getTitle();

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawer_listview = (ListView) findViewById(R.id.left_drawer);

        /* Initializing drawer items */
        drawer_item_list = new ArrayList<DrawerItem>();
        drawer_item_list.add(new DrawerItem("My Tags", R.drawable.ic_action_tags));
        drawer_item_list.add(new DrawerItem("Listings", R.drawable.ic_action_list_2));
        drawer_item_list.add(new DrawerItem("Nearby", R.drawable.ic_action_location));
        drawer_item_list.add(new DrawerItem("Capture", R.drawable.ic_action_camera_blue));
        drawer_item_list.add(new DrawerItem("Notifications", R.drawable.ic_action_globe));
        drawer_item_list.add(new DrawerItem("Friends", R.drawable.ic_action_emo_cool));
        drawer_item_list.add(new DrawerItem("Logout", R.drawable.ic_action_exit));

        /* set item actions */
        drawer_adapter = new DrawerAdapter(this, R.layout.drawer_item_layout, drawer_item_list);
        drawer_listview.setAdapter(drawer_adapter);
        drawer_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(position);
            }
        });

        /* set up drawer toggle */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };

        drawer_layout.setDrawerListener(actionBarDrawerToggle);
        if (is_launch) {
            is_launch = false;
            openActivity(0);
        }
    }

    protected void openActivity(int position) {
        drawer_layout.closeDrawer(drawer_listview);
        BaseActivity.drawer_pos = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ListingsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, NearbyActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, CaptureActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, NotificationsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, FriendsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer_layout.isDrawerOpen(drawer_listview);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(drawer_listview)) {
            drawer_layout.closeDrawer(drawer_listview);
        } else {
            drawer_layout.openDrawer(drawer_listview);
        }
    }
}
