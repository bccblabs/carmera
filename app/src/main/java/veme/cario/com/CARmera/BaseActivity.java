package veme.cario.com.CARmera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.TaggedVehicleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.TaggedPostFragment;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.nav_drawer.DrawerAdapter;
import veme.cario.com.CARmera.nav_drawer.DrawerItem;
import veme.cario.com.CARmera.view.VehicleInfoDialog;


public class BaseActivity extends FragmentActivity implements
        SelectStyleFragment.SelectResultListener,
        CarInfoFragment.OnReselectClickListener,
        TaggedPostFragment.DetailsSelectedListener,
        TaggedVehicleFragment.OnVehicleSelectedListener,
        TaggedPostFragment.CreateSearchListner,
        SavedListingsFragment.ListingSelectedListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
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

    private VehicleInfoDialog vehicleInfoDialog;

    /* facebook utilities */
    private static final String CURRENT_FB_USER_KEY = "current_fb_user";
    private FacebookRequestError fb_req_err = null;
    private List<GraphUser> friends;
    private List<JSONObject> invitable_friends;
    private static final String FRIENDS_KEY = "friends";
    private boolean has_denied_friend_permission = false;
    private static final Uri FACEBOOK_URL = Uri.parse("http://m.facebook.com");
    public static String getCurrentFbUserKey() {
        return CURRENT_FB_USER_KEY;
    }
    public static String getFriendsKey() {
        return FRIENDS_KEY;
    }
    public FacebookRequestError getFBReqErr() {
        return fb_req_err;
    }
    public void setFBReqErr(FacebookRequestError fb_req_err) {
        this.fb_req_err = fb_req_err;
    }
    public List<GraphUser> getFriends() {
        return friends;
    }
    public void setFriends(List<GraphUser> friends) {
        this.friends = friends;
    }
    public boolean hasDeniedFriendPermission() {
        return has_denied_friend_permission;
    }
    public void setHasDeniedFriendPermission(boolean val) {
        this.has_denied_friend_permission = val;
    }
    public List<JSONObject> getInvitableFriends() {
        return invitable_friends;
    }
    public void setInvitableFriends(List<JSONObject> invitableFriends) {
        this.invitable_friends = invitableFriends;
    }
    public ArrayList<String> getFriendsAsArrayListOfStrings() {
        ArrayList<String> friendsAsArrayListOfStrings = new ArrayList<String>();
        Iterator<GraphUser> friendsIterator = friends.iterator();
        while (friendsIterator.hasNext()) {
            friendsAsArrayListOfStrings.add(friendsIterator.next().getInnerJSONObject().toString());
        }
        return friendsAsArrayListOfStrings;
    }
    public GraphUser getFriend(int index) {
        if (friends != null && friends.size() > index) {
            return friends.get(index);
        } else {
            return null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent (BaseActivity.this, WelcomeActivity.class));
        }
        AppEventsLogger.activateApp(this, getString(R.string.facebook_app_id));
    }

    private void makeMeRequest() {
        RequestBatch requestBatch = new RequestBatch();
        /* req 1 */
        final Session session = ParseFacebookUtils.getSession();
        Request meRequest = Request.newMeRequest(session ,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {
                            // Create a JSON object to hold the profile info
                            JSONObject userProfile = new JSONObject();
                            try {
                                // Populate the JSON object
                                userProfile.put("facebookId", user.getId());
                                Log.i (TAG, "facebook id : " + user.getId());
                                userProfile.put("name", user.getName());
                                if (user.getProperty("gender") != null) {
                                    userProfile.put("gender", user.getProperty("gender"));
                                }
                                if (user.getProperty("email") != null) {
                                    userProfile.put("email", user.getProperty("email"));
                                }
                                ParseUser currentUser = ParseUser.getCurrentUser();
                                currentUser.put("profile", userProfile);
                                currentUser.saveInBackground();
                            } catch (JSONException e) {
                                Log.d(TAG, "Error parsing returned user data. " + e);
                            }

                        } else if (response.getError() != null) {
                            if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY) ||
                                    (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
                                Log.d(TAG, "The facebook session was invalidated." + response.getError());
                                logout();
                            } else {
                                Log.d(TAG, "Some other error: " + response.getError());
                            }
                        }
                    }
                }
        );
        requestBatch.add(meRequest);

        /* req 2 */
        Request friendsRequest = Request.newMyFriendsRequest(session,
                new Request.GraphUserListCallback() {
                    @Override
                    public void onCompleted(List<GraphUser> users, Response response) {
                        FacebookRequestError error = response.getError();
                        if (error != null) {
                            handleError(error, true);
                        } else {
                            setFriends(users);
                        }
                    }
                });
        Bundle params = new Bundle();
        params.putString("fields", "name,first_name,last_name");
        friendsRequest.setParameters(params);
        requestBatch.add(friendsRequest);

        requestBatch.executeAsync();
    }

    public void handleError(FacebookRequestError error, boolean logout) {
        Log.d(TAG, "handleError: " + error.getErrorMessage());

        DialogInterface.OnClickListener listener = null;
        String dialogBody = null;

        if (error == null) {
            dialogBody = getString(R.string.error_dialog_default_text);
        } else {
            switch (error.getCategory()) {
                case AUTHENTICATION_RETRY:
                    // tell the user what happened by getting the message id, and
                    // retry the operation later
                    String userAction = (error.shouldNotifyUser()) ? "" :
                            getString(error.getUserActionMessageId());
                    dialogBody = getString(R.string.error_authentication_retry, userAction);
                    listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, FACEBOOK_URL);
                            startActivity(intent);
                        }
                    };
                    break;

                case AUTHENTICATION_REOPEN_SESSION:
                    // close the session and reopen it.
                    dialogBody = getString(R.string.error_authentication_reopen);
                    listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Session session = Session.getActiveSession();
                            if (session != null && !session.isClosed()) {
                                session.closeAndClearTokenInformation();
                            }
                        }
                    };
                    break;

                case PERMISSION:
                    // request the publish permission
                    dialogBody = getString(R.string.error_permission);
                    listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    };
                    break;

                case SERVER:
                case THROTTLING:
                    // this is usually temporary, don't clear the fields, and
                    // ask the user to try again
                    dialogBody = getString(R.string.error_server);
                    break;

                case BAD_REQUEST:
                    // this is likely a coding error, ask the user to file a bug
                    dialogBody = getString(R.string.error_bad_request, error.getErrorMessage());
                    break;

                case CLIENT:
                    // this is likely an IO error, so tell the user they have a network issue
                    dialogBody = getString(R.string.network_error);
                    break;

                case OTHER:
                default:
                    // an unknown issue occurred, this could be a code error, or
                    // a server side issue, log the issue, and either ask the
                    // user to retry, or file a bug
                    dialogBody = getString(R.string.error_unknown, error.getErrorMessage());
                    break;
            }
        }

        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.error_dialog_button_text, listener)
                .setTitle(R.string.error_dialog_title)
                .setMessage(dialogBody)
                .show();

        if (logout) {
            logout();
        }
    }

    private void logout() {
        Log.d(TAG, "Logging user out.");
        if (ParseUser.getCurrentUser() != null)
            ParseUser.logOut();
        Session.getActiveSession().closeAndClearTokenInformation();
    }

    protected void openActivity(int position) {
        drawer_layout.closeDrawer(drawer_listview);
        BaseActivity.drawer_pos = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ListingsActivity_v2.class));
                break;
            case 2:
                startActivity(new Intent(this, NearbyActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, CaptureActivity.class));
                break;
            case 4:
                logout();
                startActivity(new Intent(this, WelcomeActivity.class));
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
    public void OnVehicleSelected (String post_id) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "post_details");
        args.putString("tagged_post_id", post_id);

        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show (fm, "postDetailsOverlay");
    }

    @Override
    public void onDetailsSelected (byte[] imageData, String year, String make, String model) {
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
    public void onCreateSearch (String yr, String mk, String model) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "create_search");
        args.putString("vehicle_year", yr);
        args.putString("vehicle_make", mk);
        args.putString("vehicle_model", model);

        if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "createSearchOverlay");
    }


    @Override
    public void OnListingSelectedCallback (String vehicle_post_id, String vehicle_style_id) {
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
            Bundle args = new Bundle();
            args.putString("dialog_type", "listing_details");
            args.putString("vehicle_post_id", vehicle_post_id);
            args.putString("vehicle_id", vehicle_style_id);


            FragmentManager fm = getSupportFragmentManager();
            vehicleInfoDialog = new VehicleInfoDialog();
            vehicleInfoDialog.setArguments(args);
            vehicleInfoDialog.show(fm, "listingDetailOverlay");
    }

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
        drawer_item_list.add(new DrawerItem("Profile", R.drawable.ic_action_user));
        drawer_item_list.add(new DrawerItem("Dis.car.vr", R.drawable.ic_action_search_purple));
        drawer_item_list.add(new DrawerItem("Near me", R.drawable.ic_action_location));
        drawer_item_list.add(new DrawerItem("Capture", R.drawable.ic_action_camera_blue));
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

        Session session = ParseFacebookUtils.getSession();
        if (session != null && session.isOpened()) {
            makeMeRequest();
        }

    }
}

