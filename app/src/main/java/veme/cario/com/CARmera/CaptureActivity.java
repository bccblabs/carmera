package veme.cario.com.CARmera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.location.LocationClient;
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
public class CaptureActivity extends Activity {

    /* TODO: background overlay when picture is taken */
    /* TODO: upload->render fragment overlay */

    private Camera camera;
    private CameraPreview cameraPreview;
    private LocationClient locationClient;

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

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate (savedBundleInstance);
        camera = getCameraInstance();
        cameraPreview = new CameraPreview(this, savedBundleInstance);

        /* Set up location client */
        LocationClient locationCLient = new LocationClient(this, null, null);

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
}
