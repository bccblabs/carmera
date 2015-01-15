package veme.cario.com.CARmera;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;

import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.CameraPreview;
import veme.cario.com.CARmera.view.SimpleTaggedVehicleDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class CaptureActivity extends BaseActivity
                                    implements ImageFragment.UploadListener {

    private final static String TAG = "CAPTURE_ACTIVITY";
    /* Camera Object */
    private Camera camera;
    private byte[] imageData;
    private FloatingActionButton tagged_btn, listings_btn, nearby_btn;
    private FloatingActionButton capture_btn;

    private CameraPreview cameraPreview = null;
    private VehicleInfoDialog vehicleInfoDialog = null;
    private SimpleTaggedVehicleDialog simpleTaggedVehicleDialog = null;
    private int rotate_deg = 0;
    private TaggedVehicle taggedVehicle;

    private int img_height, img_width;
    public Camera.AutoFocusCallback autoFocusCallback = null;


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
            vehicleInfoDialog = null;
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
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
    }
    /* Activity lifecycle */

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        getActionBar().hide();
        /* sets up drawer list */
        getLayoutInflater().inflate(R.layout.activity_capture, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Capture");

        initUIComponents();
    }

    private void initUIComponents() {
        camera = getCameraInstance();
        /* Draw layout */
        cameraPreview = new CameraPreview(this, camera);
        Log.v(TAG, " - cameraPreview attached.");

        capture_btn = (FloatingActionButton) findViewById(R.id.capture_btn);
        capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });
        /* Settings camera parameters */
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        parameters.setPictureSize(640, 480);
        camera.setParameters(parameters);
        /* Wire up camera views */
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

        preview.addView(cameraPreview);

        tagged_btn = (FloatingActionButton) findViewById(R.id.start_tags_activity_btn);
        tagged_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(0);
            }
        });
        listings_btn = (FloatingActionButton) findViewById(R.id.start_listings_activity_btn);
        listings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(1);
            }
        });

        nearby_btn = (FloatingActionButton) findViewById(R.id.starts_nearby_activity_btn);
        nearby_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(2);
            }
        });

        img_height = parameters.getPictureSize().height;
        img_width = parameters.getPictureSize().width;
        setCameraDisplayOrientation(camera);
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
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

    @Override
    public void onUploadResult (String tagged_vehicle_id) {
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        if (tagged_vehicle_id != null) {
            Bundle args = new Bundle();
            args.putString("tagged_vehicle_id", tagged_vehicle_id);
            FragmentManager fm = getSupportFragmentManager();
            vehicleInfoDialog = new VehicleInfoDialog();
            vehicleInfoDialog.setArguments(args);
            vehicleInfoDialog.show(fm, "recognitionOverlay");
        }
    }

    /* when a vehicle is recognized from the cloud server */
    public void onRecognitionResult (byte[] imageData, String year, String make, String model) {
        /* once the image is recognized, adding the new fragments to the dialog */

        Bitmap orig_img = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        Bitmap cropped_image = Bitmap.createScaledBitmap(orig_img, 100, 100, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cropped_image.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] thumbnail = stream.toByteArray();

        taggedVehicle.setYear(year);
        taggedVehicle.setMake(make);
        taggedVehicle.setModel(model);
        taggedVehicle = new TaggedVehicle();
        taggedVehicle.setTagPhoto(new ParseFile(imageData));
        taggedVehicle.setThumbnail(new ParseFile(thumbnail));
        taggedVehicle.setFavorite(true);
        taggedVehicle.setPrice("51,102");
        taggedVehicle.setSellerInfo("BMW of BimmerVille");
        taggedVehicle.setSellerEmail("info@bmwbimmerville.com");
        taggedVehicle.setSellerPhone("888-888-8888");
        taggedVehicle.setListing(true);

        taggedVehicle.setUser(ParseUser.getCurrentUser());
        taggedVehicle.saveInBackground();


        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", year);
        args.putString("vehicle_make", make);
        args.putString("vehicle_model", model);
        args.putByteArray("imageData", imageData);

        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        Log.i (TAG, year + " " + make + " " + model);
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "styleChooserOverlay");
    }

    @Override
    public void onStyleSelected (byte[] imageData, String trim_id, String trim_name, String yr, String mk, String md) {
        super.onStyleSelected(imageData, trim_id, trim_name, yr, mk, md);
        taggedVehicle.setStyleId(trim_id);
        taggedVehicle.saveInBackground();
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
}