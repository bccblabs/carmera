package veme.cario.com.CARmera;

import android.app.ActionBar;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
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
import org.opencv.core.MatOfPoint;
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
import veme.cario.com.CARmera.fragment.ImagePreviewDialog;
import veme.cario.com.CARmera.fragment.VehicleInfoDialog;
import veme.cario.com.CARmera.model.TaggedVehicle;
import veme.cario.com.CARmera.view.CVPortraitView;


/**
 * Created by bski on 11/5/14.
 */
public class CaptureActivity extends FragmentActivity
                             implements LocationListener,
                                        GooglePlayServicesClient.ConnectionCallbacks,
                                        GooglePlayServicesClient.OnConnectionFailedListener,
                                        CameraBridgeViewBase.CvCameraViewListener2,
                                        View.OnTouchListener {

    /* TODO: background overlay when picture is taken */
    /* TODO: upload->render fragment overlay */
    /* TODO: implement touch view for auto-focus gesture */
    /* onDestroy will not need override */
    /* onPause, remove orientation listener, close all open frags, tell preview to onPause() */

    private final static String TAG = "CAPTURE_ACTIVITY";

    /* Dependent objects */
    private CVPortraitView cvPreview;

    /* Access user location object */
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private Location curr_location;
    private Location last_location;
    private final static int LOCATION_UPDATE_INTERVAL = 5000;
    private final static int LOCATION_UPDATE_CEILING = 60 * 1000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    /* On orientation change, pause->redraw */
    private OrientationEventListener orientationEventListener;

    private boolean lockedScreen = false;

    /* Camera callbacks */
    private Camera.ShutterCallback shutterCallback = null;
    private Camera.PictureCallback pictureCallback = null;
    private Camera.AutoFocusCallback AFCallback = null;
    private boolean inPreview = false;

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


    /* Activity lifecycle */
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        setContentView(R.layout.activity_cv_capture);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

//
//        /* Set up location, orientation listeners, gesture detector */
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
//

        /* Draw CV layout */
        cvPreview = (CVPortraitView) findViewById(R.id.activity_capture_cv_preview);
        cvPreview.setVisibility(SurfaceView.VISIBLE);
        cvPreview.setCvCameraViewListener(this);


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
    public void onDestroy() {
        if (cvPreview != null)
            cvPreview.disableView();
        super.onPause();

    }

    @Override
    public void onPause() {
//        if (locationClient.isConnected()) {
//        }
//        locationClient.disconnect();
//        orientationEventListener.disable();
//        camera.release();
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
    public void onStart() {
        super.onStart();
//        locationClient.connect();
    }

    @Override
    public void onStop() {
//        if (locationClient.isConnected()) {
//            stopLocationUpdates();
//        }
//        locationClient.disconnect();
//        orientationEventListener.disable();
//        camera.release();
        if (cvPreview != null)
            cvPreview.disableView();
        super.onStop();
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
    public void onOrientationChanged(int orientation) {
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

    public void stopLocationUpdates() {
//       locationClient.removeLocationUpdates(this);
    }

    @Override
    public void onConnected(Bundle savedBundleInst) {
//        curr_location = getLocation();
//        startLocationUpdates();
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, " - service disconnected.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult con_res) {
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
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

    private void saveToParse(byte[] raw_data) {
        TaggedVehicle taggedVehicle = new TaggedVehicle();
        final ParseUser curr_user = ParseUser.getCurrentUser();
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String timestamp = s.format(new Date());
        Location location = (curr_location == null) ? last_location : curr_location;
        ParseGeoPoint geo_point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
        taggedVehicle.setLocation(geo_point);
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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mIsColorSelected == false) {

            if (inPreview == false) {
                Log.i(TAG, "starting preview");
                Camera mCamera = cvPreview.getCVCamera();
                mCamera.startPreview();
                inPreview = true;
            }

            Log.i(TAG, "ON_TOUCH COLOR SELECT EVENT");

            int cols = mRgba.cols();
            int rows = mRgba.rows();

            int xOffset = (cvPreview.getWidth() - cols) / 2;
            int yOffset = (cvPreview.getHeight() - rows) / 2;

            int x = (int) event.getX() - xOffset;
            int y = (int) event.getY() - yOffset;

            Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");

            if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;

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

            mIsColorSelected = true;
            touchedRegionRgba.release();
            touchedRegionHsv.release();

        } else {
            Log.i(TAG, "Taking picture");

            final Camera mCamera = cvPreview.getCVCamera();
            mIsColorSelected = inPreview = false;
            mCamera.stopPreview();
            FragmentManager fm = getSupportFragmentManager();
            ImagePreviewDialog previewOverlay = new ImagePreviewDialog();
            previewOverlay.show(fm, "previewOverlay");

            /* should have listeners for dismiss, etc. */

//            mCamera.stopPreview();
//            mCamera.takePicture(null, null, new Camera.PictureCallback() {
//                @Override
//                public void onPictureTaken(byte[] data, Camera camera) {
//                    FragmentManager fm = getSupportFragmentManager();
//                    ImagePreviewDialog previewOverlay = new ImagePreviewDialog();
//                    previewOverlay.show(fm, "previewOverlay");
//                    mCamera.startPreview();
//                }
//            });

        }
        return false;
    }

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }


}