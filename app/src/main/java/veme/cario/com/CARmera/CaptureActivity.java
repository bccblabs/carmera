package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;

import java.io.ByteArrayOutputStream;
import java.util.List;

import veme.cario.com.CARmera.view.CameraPreview;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 11/5/14.
 */
public class CaptureActivity extends FragmentActivity {

    private final static String TAG = "CAPTURE_ACTIVITY";
    /* Camera Object */
    private Camera camera;
    private CameraPreview cameraPreview = null;

    private VehicleInfoDialog vehicleInfoDialog = null;

    /* Activity lifecycle */
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_capture);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        camera = getCameraInstance();

        /* Draw layout */
        cameraPreview = new CameraPreview(this, camera, savedBundleInstance);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (vehicleInfoDialog == null) {
                    FragmentManager fm = getSupportFragmentManager();
                    vehicleInfoDialog = new VehicleInfoDialog();
                    vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
                    camera.stopPreview();
                } else {
                    vehicleInfoDialog.dismiss();
                    vehicleInfoDialog = null;
                    camera.startPreview();
                }
                return false;
            }
        });
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

    }

    @Override
    public void onPause() {
        camera.release();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        camera.release();
        super.onStop();
    }


    /* Camera helper functions */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
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
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
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
}