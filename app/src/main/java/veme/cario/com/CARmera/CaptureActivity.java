package veme.cario.com.CARmera;

import android.app.ActionBar;

import android.support.v4.app.FragmentActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.hardware.Camera;
import android.location.Location;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import veme.cario.com.CARmera.cv_detectors.ColorBlobDetector;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.APIModels.VehicleBaseInfo;
import veme.cario.com.CARmera.view.CVPortraitView;
import veme.cario.com.CARmera.view.ImagePreviewDialog;
//import veme.cario.com.CARmera.view.ImagePreviewDialog;


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

    /* On orientation change, pause->redraw */
    private OrientationEventListener orientationEventListener;

    /* Camera callbacks */
    private Camera.ShutterCallback shutterCallback = null;
    private Camera.PictureCallback pictureCallback = null;
    private Camera.AutoFocusCallback AFCallback = null;
    private boolean mTouchInBound = false;

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

    private boolean mIsColorSelected = false;
    private Mat mRgba;
    private Scalar mBlobColorRgba;
    private Scalar mBlobColorHsv;
    private ColorBlobDetector mDetector;
    private Mat mSpectrum;
    private Size SPECTRUM_SIZE;
    private Scalar CONTOUR_COLOR;

    /* SpiceManager Class */
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private String last_request;
    private VehicleBaseInfo vehicleBaseInfo;


    /* Camera dialog */
    ImagePreviewDialog imagePreviewDialog = null;

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }

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
        // Resize photo from camera byte array
        Bitmap vehicleImage = BitmapFactory.decodeByteArray(raw_data, 0, raw_data.length);
        int scaleFactor = vehicleImage.getHeight() / vehicleImage.getWidth();
        Bitmap vehicleImageScaled = Bitmap.createScaledBitmap(vehicleImage,
                                                            640 * scaleFactor,
                                                            480 * scaleFactor,
                                                            false);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        vehicleImageScaled.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        // Override Android default landscape orientation and save portrait
//        Matrix matrix = new Matrix();
//        Bitmap rotatedScaledMealImage = Bitmap.createBitmap(vehicleImageScaled, 0,
//                0, vehicleImageScaled.getWidth(), vehicleImageScaled.getHeight(),
//                matrix, true);

//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        return bos.toByteArray();
    }

    private void saveToParse(byte[] raw_data) {
        TaggedVehicle taggedVehicle = new TaggedVehicle();
        final ParseUser curr_user = ParseUser.getCurrentUser();
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String timestamp = s.format(new Date());
//        Location location = (curr_location == null) ? last_location : curr_location;
//        ParseGeoPoint geo_point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
//        taggedVehicle.setLocation(geo_point);
        taggedVehicle.setFavorite(false);
        ParseFile photo_file = new ParseFile(timestamp + ".jpg", getScaledPhoto(raw_data));
        taggedVehicle.setTagPhoto(photo_file);
        photo_file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast toast = Toast.makeText(CaptureActivity.this,
                            "Image saved...",
                            Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    Toast toast = Toast.makeText(CaptureActivity.this,
                            "Parse save image error",
                            Toast.LENGTH_SHORT);
                    curr_user.saveEventually();
                    toast.show();
                }
            }
        });
        curr_user.getRelation("taggedVehicles").add(taggedVehicle);
        curr_user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast toast = Toast.makeText(CaptureActivity.this,
                            "TaggedV saved, waiting image rec...",
                            Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    Toast toast = Toast.makeText(CaptureActivity.this,
                            "Parse save taggedV error",
                            Toast.LENGTH_SHORT);
                    curr_user.saveEventually();
                    toast.show();
                }
            }
        });
        Log.d(TAG, " parse object saved!");
    }


    /* OpenCV functions */
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255, 255, 0, 255);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

        if (mIsColorSelected) {
            mDetector.process(mRgba);
            List<MatOfPoint> contours = mDetector.getContours();
            // fill a polygon instead of drawing contours
            for (MatOfPoint contour : contours) {
                Rect boundingRect = Imgproc.boundingRect(contour);
                Core.rectangle(mRgba, boundingRect.tl(), boundingRect.br(), CONTOUR_COLOR, 5);
            }
        }

        return mRgba;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        List<Rect> boundingRects = new ArrayList<Rect>();
        Point touch_point = new Point(event.getRawX(), event.getRawY());
        Log.i(TAG, "ON_TOUCH COLOR SELECT EVENT");

        int cols = mRgba.cols();
        int rows = mRgba.rows();

        int xOffset = (cvPreview.getWidth() - cols) / 2;
        int yOffset = (cvPreview.getHeight() - rows) / 2;

        int x = (int) event.getX() - xOffset;
        int y = (int) event.getY() - yOffset;

        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");

        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) {
            Log.i(TAG, " - touched point not in region.");
            return true;
        }

        updateDetector(x, y, cols, rows);
        mDetector.process(mRgba);
        mIsColorSelected = true;
        List<MatOfPoint> contours = mDetector.getContours();
        for (int i = 0; i < contours.size(); i++) {
            Rect rect = Imgproc.boundingRect(contours.get(i));
            if (rect.contains(touch_point)) {
                mDetector.clearContours();
                Core.rectangle(mRgba, rect.tl(), rect.br(), CONTOUR_COLOR, 7);
                mIsColorSelected = false;
                imagePreviewDialog.show();
                return true;
            }
        }
        return false;
    }

    private void updateDetector (int x, int y, int cols, int rows) {

        Rect touchedRect = new Rect();

        touchedRect.x = (x > 4) ? x - 4 : 0;
        touchedRect.y = (y > 4) ? y - 4 : 0;

        touchedRect.width = (x + 4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y + 4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();

        // CONVERT THE COLOR OF TOUCH REGION FROM RGBA TO HSV
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region
        mBlobColorHsv = Core.sumElems(touchedRegionHsv);

        // mBlobColorHsv/Rgba: Scalar of colors
        int pointCount = touchedRect.width * touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);

        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");

        // mDetector: Color Detector
        mDetector.setHsvColor(mBlobColorHsv);

        // Resize the Detector's spectrum to mSpectrum as a 200x64
        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);

        touchedRegionRgba.release();
        touchedRegionHsv.release();

    }
}