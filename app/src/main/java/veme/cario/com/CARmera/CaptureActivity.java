package veme.cario.com.CARmera;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;

import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.CameraPreview;
import veme.cario.com.CARmera.view.SimpleTaggedVehicleDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class CaptureActivity extends BaseActivity
                                    implements ImageFragment.ImageResultListener,
                                               SimpleTaggedVehicleDialog.OnSimpleTagSelectedListener,
                                               SelectStyleFragment.SelectResultListener{

    private final static String TAG = "CAPTURE_ACTIVITY";
    /* Camera Object */
    private Camera camera;
    private byte[] imageData;
    private ImageButton tagged_btn;
    private CameraPreview cameraPreview = null;
    private VehicleInfoDialog vehicleInfoDialog = null;
    private SimpleTaggedVehicleDialog simpleTaggedVehicleDialog = null;
    private int rotate_deg = 0;
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, " - picture length: " + data.length);
            Bitmap orignalImage = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap bitmapImage = rotate(orignalImage, rotate_deg);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageData = stream.toByteArray();

            Bundle args = new Bundle();
            args.putByteArray("imageData", imageData);
            args.putString("dialog_type", "preview");
            FragmentManager fm = getSupportFragmentManager();
            vehicleInfoDialog = new VehicleInfoDialog();
            vehicleInfoDialog.setArguments(args);
            vehicleInfoDialog.show(fm, "previewOverlay");
        }
    };


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        releaseCamera();
        setContentView(R.layout.activity_capture);
        initUIComponents();


    }     /* Activity lifecycle */

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        /* for camera orientation change */

        /* Initialize camera layout */
        setContentView(R.layout.activity_capture);
        initUIComponents();
    }

    private void initUIComponents() {
        camera = getCameraInstance();
        /* Draw layout */
        cameraPreview = new CameraPreview(this, camera);
        Log.v(TAG, " - cameraPreview attached.");

        /* Settings camera parameters */
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        parameters.setPictureFormat(ImageFormat.JPEG);

        camera.setParameters(parameters);
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

        /* auto-focus setup */
//        setupAutofocus();

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
        setCameraDisplayOrientation(camera);

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
        Camera.Parameters params = camera.getParameters();
        params.setRotation(result);
        camera.setParameters(params);
        rotate_deg = result;
    }

    /* when a vehicle is recognized from the cloud server */
    @Override
    public void onRecognitionResult (String year, String make, String model) {
        /* once the image is recognized, adding the new fragments to the dialog */
        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", year);
        args.putString("vehicle_make", make);
        args.putString("vehicle_model", model);
        args.putByteArray("imageData", imageData);
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);

        vehicleInfoDialog.dismiss();
        vehicleInfoDialog.show(fm, "styleChooserOverlay");
    }

    @Override
    public void onStyleSelected (String trim_id, String trim_name, String yr, String mk, String md) {
        /* save to parse here */
        TaggedVehicle taggedVehicle = new TaggedVehicle();
        taggedVehicle.setYear(yr);
        taggedVehicle.setMake(mk);
        taggedVehicle.setModel(md);
        taggedVehicle.setTrimId(trim_id);
        taggedVehicle.setTrimName(trim_name);
        taggedVehicle.setTagPhoto(new ParseFile(imageData));
        taggedVehicle.setUser(ParseUser.getCurrentUser());
        taggedVehicle.saveInBackground();

        Bundle args = new Bundle();
        args.putString ("dialog_type", "vehicle_info");
        args.putString ("vehicle_id", trim_id);
        args.putString ("vehicle_name", yr + " " + mk + " " + md);
        args.putString ("vehicle_trim_name", trim_name);
        args.putByteArray("imageData", imageData);

        vehicleInfoDialog.dismiss();
        FragmentManager fm = getSupportFragmentManager();
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


    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}