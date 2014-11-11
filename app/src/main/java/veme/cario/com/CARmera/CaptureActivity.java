package veme.cario.com.CARmera;

import android.app.ActionBar;

import android.support.v4.app.FragmentActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;

import android.os.Bundle;
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
import java.util.List;

import veme.cario.com.CARmera.cv_detectors.ColorBlobDetector;
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
    private final static int LOCATION_UPDATE_CEILING = 60*1000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    /* On orientation change, pause->redraw */
    private OrientationEventListener orientationEventListener;

    private boolean lockedScreen = false;

    /* Camera callbacks */
    private Camera.ShutterCallback shutterCallback = null ;
    private Camera.PictureCallback pictureCallback = null;
    private Camera.AutoFocusCallback AFCallback = null;

    /* OpenCV objects */
    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    cvPreview.enableView();
                    cvPreview.setOnTouchListener(CaptureActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    private boolean              mIsColorSelected = false;
    private Mat                  mRgba;
    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private ColorBlobDetector    mDetector;
    private Mat                  mSpectrum;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;




    /* Activity lifecycle */
    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate (savedBundleInstance);

        setContentView(R.layout.activity_cv_capture);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentDialog dialogOverlay = new FragmentDialog();
//        dialogOverlay.show(fm, "dialogOverlay");

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


        /* Draw CV layout */
        cvPreview = (CVPortraitView) findViewById(R.id.activity_capture_cv_preview);
        cvPreview.setVisibility(SurfaceView.VISIBLE);
        cvPreview.setCvCameraViewListener(this);


//        pictureCallback = new Camera.PictureCallback() {
//
//            @Override
//            public void onPictureTaken(byte[] data, Camera camera) {
//
//            /* send data to parse, camFind, and drawing stuff */
//            /*  TODO: progress update while uploading
//                TODO: marsh up recognition request
//                TODO: marsh up Edmund's request
//                TODO: put all in an overlay
//                TODO: put all in asyncTasks
//             */
//                TaggedVehicle taggedVehicle = new TaggedVehicle();
//                ParseUser curr_user = ParseUser.getCurrentUser();
//
//                Location location = (curr_location == null) ? last_location : curr_location;
//                ParseGeoPoint geo_point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
//                SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
//                String timestamp = s.format(new Date());
//
//            /* wire up the new taggedVehicle */
//                taggedVehicle.setFavorite(false);
//                taggedVehicle.setLocation(geo_point);
//                ParseFile photo_file = new ParseFile(timestamp + ".jpg", getScaledPhoto(data));
//                taggedVehicle.setTagPhoto(photo_file);
//
//            /* save them all to Parse! */
//                photo_file.saveInBackground();
//                curr_user.getRelation("taggedVehicles").add(taggedVehicle);
//                curr_user.saveInBackground();
//            }
//        };
//
//        AFCallback = new Camera.AutoFocusCallback() {
//            @Override
//            public void onAutoFocus(boolean success, Camera camera) {
//                camera.takePicture(shutterCallback, null, pictureCallback);
//            }
//        };

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
    public void onDestroy () {
        if (cvPreview != null)
            cvPreview.disableView();
        super.onPause();

    }

    @Override
    public void onPause () {
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
    public void onResume () {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, loaderCallback);
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



    /* OpenCV functions */
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255,0,0,255);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

        if (mIsColorSelected) {
            mDetector.process(mRgba);
            List<MatOfPoint> contours = mDetector.getContours();
            Log.e(TAG, "Contours count: " + contours.size());
            Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

            Mat colorLabel = mRgba.submat(4, 68, 4, 68);
            colorLabel.setTo(mBlobColorRgba);

            Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
            mSpectrum.copyTo(spectrumLabel);
        }

        return mRgba;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG,"onTouch event");
//        cvPreview.takePicture();

        int cols = mRgba.cols();
        int rows = mRgba.rows();

        int xOffset = (cvPreview.getWidth() - cols) / 2;
        int yOffset = (cvPreview.getHeight() - rows) / 2;

        int x = (int)event.getX() - xOffset;
        int y = (int)event.getY() - yOffset;

        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");

        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;

        Rect touchedRect = new Rect();

        touchedRect.x = (x>4) ? x-4 : 0;
        touchedRect.y = (y>4) ? y-4 : 0;

        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region
        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width*touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);

        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");

        mDetector.setHsvColor(mBlobColorHsv);

        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);

        mIsColorSelected = true;

        touchedRegionRgba.release();
        touchedRegionHsv.release();

        return false;
    }

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }

}