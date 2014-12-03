package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.location.LocationRequest;

/**
 * Created by bski on 11/22/14.
 */
public class BaseActivity extends FragmentActivity {
//                                implements LocationListener,
//                                           GooglePlayServicesClient.ConnectionCallbacks,
//                                           GooglePlayServicesClient.OnConnectionFailedListener {
    /* Access user location object */
//    private LocationClient locationClient;
//    private LocationRequest locationRequest;
//    private Location curr_location;
//    private Location last_location;
    private final static int LOCATION_UPDATE_INTERVAL = 5000;
    private final static int LOCATION_UPDATE_CEILING = 60 * 1000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    static final private String TAG = "BASEACTIVITY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        /* Set up location, orientation listeners, gesture detector */
//        locationRequest = LocationRequest.create();
//        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
//        locationRequest.setFastestInterval(LOCATION_UPDATE_CEILING);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationClient = new LocationClient(this, null, null); // cxt, gs cnx cb, gs bad cnx cb

    }

//    @Override
//    public void onPause () {
//        if (locationClient.isConnected()) {
//        }
//        locationClient.disconnect();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        locationClient.connect();
//    }
//
//    @Override
//    public void onStop() {
//        if (locationClient.isConnected()) {
//            stopLocationUpdates();
//        }
//        locationClient.disconnect();
//    }
//
//    /* Google location services functions */
//    private Location getLocation() {
//        if (servicesConnected()) {
//            return locationClient.getLastLocation();
//        } else {
//            return null;
//        }
//    }
//
//    private void startLocationUpdates() {
//    }
//
//    public void stopLocationUpdates() {
//    }
//
//    @Override
//    public void onConnected(Bundle savedBundleInst) {
//        curr_location = getLocation();
//        startLocationUpdates();
//    }
//
//    @Override
//    public void onDisconnected() {
//        Log.d(TAG, " - service disconnected.");
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult con_res) {
//        if (con_res.hasResolution()) {
//            try {
//                con_res.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//            } catch (IntentSender.SendIntentException e) {
//                Log.d (TAG, " - error connecting location services.");
//            }
//        } else {
//            Log.d (TAG, " - resolution not available.");
//        }
//    }
//
//    private boolean servicesConnected() {
//        int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (res == ConnectionResult.SUCCESS) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.action_nearby: {
                if (!(this instanceof NearbyActivity)) {
                    Intent i = new Intent(this, NearbyActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }

            case R.id.action_capture: {
                Intent i = new Intent (this, CaptureActivity.class);
                startActivity(i);
//                startActivityForResult(i, 0);
                finish();
            }

            case R.id.action_profile: {
                if (!(this instanceof ProfileActivity)) {
                    Intent i = new Intent(this, ProfileActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }

            case R.id.action_notifications: {
                if (!(this instanceof NotificationsActivity)) {
                    Intent i = new Intent(this, NotificationsActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }

            case R.id.action_settings: {
                if (!(this instanceof SettingsActivity)) {
                    Intent i = new Intent(this, SettingsActivity.class);
                    startActivityForResult(i, 0);
                    finish();
                }
                break;
            }
            default: {
                finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }
}