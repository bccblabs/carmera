package veme.cario.com.CARmera;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.facebook.AppEventsLogger;
import java.util.List;

import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.view.CameraPreview;
import veme.cario.com.CARmera.view.SimpleTaggedVehicleDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class CaptureActivity extends BaseActivity
                                    implements ImageFragment.ImageResultListener,
                                               SimpleTaggedVehicleDialog.OnSimpleTagSelectedListener {

    private final static String TAG = "CAPTURE_ACTIVITY";
    /* Camera Object */
    private Camera camera;
    private byte[] imageData;
    private ImageButton tagged_btn;
    private CameraPreview cameraPreview = null;
    private VehicleInfoDialog vehicleInfoDialog = null;
    private SimpleTaggedVehicleDialog simpleTaggedVehicleDialog = null;
    private Camera.PictureCallback pictureCallback  = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, " - picture length: " + data.length);
            imageData = data;
            Bundle args = new Bundle();
            args.putByteArray("imageData", imageData);
            FragmentManager fm = getSupportFragmentManager();
            vehicleInfoDialog = new VehicleInfoDialog();
            vehicleInfoDialog.setArguments(args);
            vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
        }
    };


    /* Activity lifecycle */
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        /* Initialize camera layout */
        setContentView(R.layout.activity_capture);
        camera = getCameraInstance();

        /* Draw layout */
        cameraPreview = new CameraPreview(this, camera, savedBundleInstance);
        Log.v(TAG, " - cameraPreview attached.");

        /* Settings camera parameters */
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPictureSize(640, 480);
        camera.setParameters(parameters);
        setCameraDisplayOrientation(camera);

        /* Wire up camera views */
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (vehicleInfoDialog == null) {
                    camera.takePicture(null, null, pictureCallback);
                } else {
                    Log.i(TAG, "restart camera preview");
                    if (vehicleInfoDialog != null) {
                        vehicleInfoDialog.dismiss();
                        vehicleInfoDialog = null;
                    }
                    if (simpleTaggedVehicleDialog != null) {
                        simpleTaggedVehicleDialog.dismiss();
                        simpleTaggedVehicleDialog = null;
                    }
                    if (camera == null) {
                        Log.i(TAG, "camera null");
                        camera = getCameraInstance();
                    }
                    camera.startPreview();
                }
                return false;
            }
        });
        preview.addView(cameraPreview);

        tagged_btn = (ImageButton) findViewById(R.id.tagged_photo_btn);

        tagged_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, " - tagged button");
                FragmentManager fm = getSupportFragmentManager();
                simpleTaggedVehicleDialog = new SimpleTaggedVehicleDialog();
                simpleTaggedVehicleDialog.show(fm, "taggedVehiclesOverlay");
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
        // AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onStop() {
        releaseCamera();
        super.onStop();
    }

    /* Camera helper functions */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            Log.e(TAG," - camera opening error: " + e.getMessage());
        }
        return c;
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
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

    /* when a vehicle is recognized from the cloud server */
    @Override
    public void onRecognitionResult (String year, String make, String model) {
        /* once the image is recognized, adding the new fragments to the dialog */
        Bundle args = new Bundle();
        args.putString("vehicle_year", year);
        args.putString("vehicle_make", make);
        args.putString("vehicle_model", model);
        args.putByteArray("imageData", imageData);
        /* save to parse here */
        vehicleInfoDialog.dismiss();
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
    }

    /* when a vehicle is selected from the list of previously tagged cars using the yellow button */
    @Override
    public void onSimpleTagSelected (String year, String make, String model) {
        Intent i = new Intent(CaptureActivity.this, NearbyActivity.class);
        Bundle args = new Bundle();
        args.putString("vehicle_search_make", make);
        args.putString("vehicle_search_model", model);
        i.putExtra("vehicle_search_criteria", args);
        startActivity(i);
    }

}