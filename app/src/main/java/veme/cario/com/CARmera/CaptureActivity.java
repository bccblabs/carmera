package veme.cario.com.CARmera;

import android.app.ActionBar;

import android.support.v4.app.FragmentActivity;

import org.opencv.android.Utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.model.APIModels.VehicleBaseInfo;
import veme.cario.com.CARmera.view.CVPortraitView;
import veme.cario.com.CARmera.view.ImagePreviewDialog;
import veme.cario.com.CARmera.view.VehicleInfoDialog;


/**
 * Created by bski on 11/5/14.
 */
public class CaptureActivity extends FragmentActivity
                             implements CameraBridgeViewBase.CvCameraViewListener2,
                                        View.OnTouchListener {

    /* TODO: background overlay when picture is taken */
    /* TODO: upload->render fragment overlay */
    /* TODO: implement touch view for auto-focus gesture */
    /* onDestroy will not need override */
    /* onPause, remove orientation listener, close all open frags, tell preview to onPause() */

    private final static String TAG = "CAPTURE_ACTIVITY";

    /* Dependent objects */
    private CVPortraitView cvPreview;


    /* OpenCV objects */
    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    cvPreview.enableView();
                    cvPreview.setOnTouchListener(CaptureActivity.this);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private Mat mRgba;

    /* SpiceManager Class */
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private String last_request;
    private VehicleBaseInfo vehicleBaseInfo;


    /* Camera dialog */
    ImagePreviewDialog imagePreviewDialog = null;
    VehicleInfoDialog vehicleInfoDialog = null;
    boolean imageSet = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_capture);

        /* Draw CV layout */
        cvPreview = (CVPortraitView) findViewById(R.id.activity_capture_cv_preview);
        cvPreview.setVisibility(SurfaceView.VISIBLE);
        cvPreview.setCvCameraViewListener(this);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        imagePreviewDialog = new ImagePreviewDialog(CaptureActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.capture_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                if (cvPreview != null)
                    cvPreview.disableView();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        if (cvPreview != null)
            cvPreview.disableView();
        super.onPause();

    }

    @Override
    public void onPause() {
        if (cvPreview != null)
            cvPreview.disableView();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, loaderCallback);
    }


    @Override
    public void onStop() {
        if (cvPreview != null)
            cvPreview.disableView();
        super.onStop();
    }

    private byte[] getScaledPhoto(byte[] raw_data) {

        Bitmap vehicleImage = BitmapFactory.decodeByteArray(raw_data, 0, raw_data.length);
        int scaleFactor = vehicleImage.getHeight() / vehicleImage.getWidth();
        Bitmap vehicleImageScaled = Bitmap.createScaledBitmap(vehicleImage,
                                                            640 * scaleFactor,
                                                            480 * scaleFactor,
                                                            false);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        vehicleImageScaled.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }



    /* OpenCV functions */
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        /* set image frame here */
        if (imagePreviewDialog.isShowing() && !imageSet) {
            Log.i (TAG, " - Setting Image!");
            imageSet = true;
        }
        return mRgba;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!imagePreviewDialog.isShowing()) {
            imagePreviewDialog.show();
        } else {
            imagePreviewDialog.dismiss();
        }
        imageSet = false;
        return false;
    }

}