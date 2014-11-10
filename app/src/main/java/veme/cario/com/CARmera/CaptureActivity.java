package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.app.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import veme.cario.com.CARmera.fragment.FragmentDialog;
import veme.cario.com.CARmera.model.TaggedVehicle;
import veme.cario.com.CARmera.view.CameraPreview;
import veme.cario.com.CARmera.view.TouchFocusView;



/**
 * Created by bski on 11/5/14.
 */
public class CaptureActivity extends FragmentActivity
                             implements LocationListener,
                                        GooglePlayServicesClient.ConnectionCallbacks,
                                        GooglePlayServicesClient.OnConnectionFailedListener {

    /* TODO: background overlay when picture is taken */
    /* TODO: upload->render fragment overlay */
    /* TODO: implement touch view for auto-focus gesture */
    /* onDestroy will not need override */
    /* onPause, remove orientation listener, close all open frags, tell preview to onPause() */

    private final static String TAG = "CAPTURE_ACTIVITY";
    /* Dependent objects */
    private Camera camera;
    private CameraPreview cameraPreview = null;
    private TouchFocusView touchFocusView;
    /* Access user location object */
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private Location curr_location;
    private Location last_location;
    private final static int LOCATION_UPDATE_INTERVAL = 5000;
    private final static int LOCATION_UPDATE_CEILING = 60*1000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    /* On orientation change, pause->redraw */
    private OrientationEventListener orientationEventListener;

    /* Gesture manager, not sure if we really need it */
    private GestureDetector gestureDetector;

    private boolean lockedScreen = false;

    /* Camera callbacks */
    private Camera.ShutterCallback shutterCallback = null ;
    private Camera.PictureCallback pictureCallback = null;
    private Camera.AutoFocusCallback AFCallback = null;


    /* not sure if we really need it, seem like used for lock screen */
    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
    }

    /* Activity lifecycle */
    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate (savedBundleInstance);
        setContentView(R.layout.activity_capture);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentDialog dialogOverlay = new FragmentDialog();
//        dialogOverlay.show(fm, "dialogOverlay");

        camera = getCameraInstance();
        /* Set up location, orientation listeners, gesture detector */
//        locationRequest = LocationRequest.create();
//        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
//        locationRequest.setFastestInterval(LOCATION_UPDATE_CEILING);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationClient = new LocationClient(this, null, null); // cxt, gs cnx cb, gs bad cnx cb
//
//        orientationEventListener = new OrientationEventListener(this) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                CaptureActivity.this.onOrientationChanged(orientation);
//            }
//        };

//       gestureDetector = new GestureDetector(this, new );

        /* Draw layout */
        cameraPreview = new CameraPreview(this, camera, savedBundleInstance);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);
        Log.v(TAG, " - cameraPreview attached.");
        /* Camera initialization */
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        camera.setParameters(parameters);
        setCameraDisplayOrientation(camera);
        /* initialize camera listeners */
        shutterCallback = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
            /* play a sound, draw animation, etc */
            }
        };
        pictureCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

            /* send data to parse, camFind, and drawing stuff */
            /*  TODO: progress update while uploading
                TODO: marsh up recognition request
                TODO: marsh up Edmund's request
                TODO: put all in an overlay
                TODO: put all in asyncTasks
             */
                TaggedVehicle taggedVehicle = new TaggedVehicle();
                ParseUser curr_user = ParseUser.getCurrentUser();

                Location location = (curr_location == null) ? last_location : curr_location;
                ParseGeoPoint geo_point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
                SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                String timestamp = s.format(new Date());

            /* wire up the new taggedVehicle */
                taggedVehicle.setFavorite(false);
                taggedVehicle.setLocation(geo_point);
                ParseFile photo_file = new ParseFile(timestamp + ".jpg", getScaledPhoto(data));
                taggedVehicle.setTagPhoto(photo_file);

            /* save them all to Parse! */
                photo_file.saveInBackground();
                curr_user.getRelation("taggedVehicles").add(taggedVehicle);
                curr_user.saveInBackground();
            }
        };

        AFCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                camera.takePicture(shutterCallback, null, pictureCallback);
            }
        };

//        camera.autoFocus(AFCallback);

        /* Listeners for buttons */
        ImageButton fav_btn = (ImageButton) findViewById(R.id.favorite_button);
        ImageButton tagged_btn = (ImageButton) findViewById(R.id.tagged_photo_btn);
        ImageButton settings_btn = (ImageButton) findViewById(R.id.settings_btn);
        ImageButton album_upl_btn = (ImageButton) findViewById(R.id.upload_from_album_btn);

        /* Camera UI initializer */
//        fav_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        tagged_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        settings_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        album_upl_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

     }

    @Override
    public void onPause () {
//        if (locationClient.isConnected()) {
//        }
//        locationClient.disconnect();
//        orientationEventListener.disable();
        camera.release();
        super.onPause();
    }

    @Override
    public void onStart () {
        super.onStart();
//        locationClient.connect();
    }

    @Override
    public void onStop () {
//        if (locationClient.isConnected()) {
//            stopLocationUpdates();
//        }
//        locationClient.disconnect();
//        orientationEventListener.disable();
        camera.release();
        super.onStop();
    }


    /* Camera helper functions */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private byte[] getScaledPhoto(byte[] raw_data) {
        // Resize photo from camera byte array
        Bitmap vehicleImage = BitmapFactory.decodeByteArray(raw_data, 0, raw_data.length);
        Bitmap vehicleImageScaled = Bitmap.createScaledBitmap(vehicleImage, 200, 200
                * vehicleImage.getHeight() / vehicleImage.getWidth(), false);

        // Override Android default landscape orientation and save portrait
        Matrix matrix = new Matrix();
        Bitmap rotatedScaledMealImage = Bitmap.createBitmap(vehicleImageScaled, 0,
                0, vehicleImageScaled.getWidth(), vehicleImageScaled.getHeight(),
                matrix, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        return bos.toByteArray();
    }

    public void setCameraDisplayOrientation(android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(0, info);
        int rotation = CaptureActivity.this.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /* UI helper functions */
    public void onOrientationChanged (int orientation) {
    }

    /* Google location services functions */
    private Location getLocation() {
        if (servicesConnected()) {
            return locationClient.getLastLocation();
        } else {
            return null;
        }
    }

    private void startLocationUpdates() {
//       locationClient.requestLocationUpdates(locationRequest, this);
    }

    public void stopLocationUpdates () {
//       locationClient.removeLocationUpdates(this);
    }

    @Override
    public void onConnected (Bundle savedBundleInst) {
//        curr_location = getLocation();
//        startLocationUpdates();
    }

    @Override
    public void onDisconnected () {
        Log.d (TAG, " - service disconnected.");
    }

    @Override
    public void onConnectionFailed (ConnectionResult con_res) {
//        if (con_res.hasResolution()) {
//            try {
//                con_res.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
//            } catch (IntentSender.SendIntentException e) {
//                Log.d (TAG, " - error connecting location services.");
//            }
//        } else {
//            Log.d (TAG, " - resolution not available.");
//        }
    }

    private boolean servicesConnected() {
//        int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (res == ConnectionResult.SUCCESS) {
//            return true;
//        } else {
            return false;
//        }
    }

    @Override
    public void onProviderEnabled (String provider) {

    }

    @Override
    public void onLocationChanged (Location location) {

    }

    @Override
    public void onStatusChanged (String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled (String provider) {

    }

}