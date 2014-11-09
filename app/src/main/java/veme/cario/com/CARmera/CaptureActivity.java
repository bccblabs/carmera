package veme.cario.com.CARmera;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.OrientationEventListener;
import android.widget.FrameLayout;

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

import veme.cario.com.CARmera.model.TaggedVehicle;
import veme.cario.com.CARmera.view.CameraPreview;

/**
 * Created by bski on 11/5/14.
 */
public class CaptureActivity extends Activity
                             implements LocationListener,
                                        GooglePlayServicesClient.ConnectionCallbacks,
                                        GooglePlayServicesClient.OnConnectionFailedListener {

    /* TODO: background overlay when picture is taken */
    /* TODO: upload->render fragment overlay */
    /* onDestroy will not need override */
    /* onPause, remove orientation listener, close all open frags, tell preview to onPause() */

    private static String TAG = "CAPTURE_ACTIVITY";
    private static int LOCATION_UPDATE_INTERVAL = 5000;
    private static int LOCATION_UPDATE_CEILING = 60*1000;
    /* Camera Object */
    private Camera camera;
    private CameraPreview cameraPreview;

    /* Access user location object */
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private Location curr_location;
    private Location last_location;

    /* not sure if they are needed either */
    private LocationManager locationManager;
    private LocationListener locationListener;

    /* On orientation change, pause->redraw */
    private OrientationEventListener orientationEventListener;

    /* Gesture manager, not sure if we really need it */
    private GestureDetector gestureDetector;

    private boolean lockedScreen = false;


    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            /* play a sound, draw animation, etc */
        }
    };

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            /* send data to parse, camFind, and drawing stuff */
            /*  TODO: progress update while uploading
                TODO: marsh up recognition request
                TODO: marsh up Edmunds request
                TODO: put all in an overlay
                TODO: put all in asyncTasks
             */

            TaggedVehicle taggedVehicle = new TaggedVehicle();
            ParseUser curr_user = ParseUser.getCurrentUser();

            Location location = locationClient.getLastLocation();
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

    private Camera.AutoFocusCallback AFCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            camera.takePicture(shutterCallback, null, pictureCallback);
        }
    };

    /* not sure if we really need it, seem like used for lock screen */
    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
    }

    /* Activity lifecycle */
    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate (savedBundleInstance);

        camera = getCameraInstance();
        cameraPreview = new CameraPreview(this, savedBundleInstance);

        /* Set up location, orientation listeners, gesture detector */
        locationClient = new LocationClient(this, null, null); // cxt, gs cnx cb, gs bad cnx cb
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_UPDATE_CEILING);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        /* do we really need this? if so, implement locationListener */
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                CaptureActivity.this.onOrientationChanged(orientation);
            }
        };

        gestureDetector = new GestureDetector(this, new )

        /* Draw layout */
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);

        /* Camera initialization */
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        /* preview, auto focus loop, upload loop, etc */
        camera.setParameters(parameters);
        camera.autoFocus(AFCallback);

	}

    @Override
    public void onPause () {
        if (locationClient.isConnected()) {
        }
        locationClient.disconnect();
        orientationEventListener.disable();
        super.onPause();
    }

    @Override
    public void onStart () {
        super.onStart();
        locationClient.connect();
    }

    @Override
    public void onStop () {
        if (locationClient.isConnected()) {
            stopLocationUpdates();
        }
        locationClient.disconnect();
        orientationEventListener.disable();
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

    /* UI helper functions */
    public void onOrientationChanged (int orientation) {
    }


    /* Google location services functions */
    private void startLocationUpdates() {
        locationClient.requestLocationUpdates(locationRequest, this);
    }

    public void stopLocationUpdates () {
        locationClient.removeLocationUpdates(this);
    }

    private boolean servicesConnected() {
        int res = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == res) {
            return true;
        } else {
            return false;
        }
    }


}
''