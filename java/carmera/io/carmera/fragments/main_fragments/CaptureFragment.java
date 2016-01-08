package carmera.io.carmera.fragments.main_fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import com.commonsware.cwac.camera.CameraUtils;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.dd.processbutton.iml.SubmitProcessButton;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnSearchFragmentVisible;
import carmera.io.carmera.models.queries.ImageQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 6/2/15.
 */
public class CaptureFragment extends MySupportCameraFragment implements
        SeekBar.OnSeekBarChangeListener,
        View.OnTouchListener {


    String flashMode = null;
    boolean isFlashMode = false;

    private Activity activity;

    public final String TAG = getClass().getCanonicalName();
    private static final String KEY_USE_FFC = "com.commonsware.cwac.camera.demo.USE_FFC";

    private SeekBar zoom = null;

    @Bind (R.id.capture_btn) public SubmitProcessButton capture_btn;

    @OnClick (R.id.capture_btn)
    void takePhoto () {
        takeSimplePicture();
    }

    @Bind(R.id.camera_preview) public FrameLayout camera_preview;

    @Bind (R.id.loading) public View loading;



    private OnCameraResultListener camera_result_callback = null;
    private OnSearchFragmentVisible baseFabVisibleCallback = null;

    public interface OnCameraResultListener {
        void OnCameraResult (ImageQuery query);
    }

    public static CaptureFragment newInstance () {
        CaptureFragment captureFragment = new CaptureFragment();
        return captureFragment;
    }


    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            camera_result_callback = (OnCameraResultListener) activity;
            baseFabVisibleCallback = (OnSearchFragmentVisible) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        activity = getActivity();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            baseFabVisibleCallback.SetFabInvisible();
        }
        new Thread() {
            @Override
            public void run() {
                SimpleCameraHost.Builder builder = new SimpleCameraHost.Builder(new CaptureHost(activity));
                setHost(builder.useFullBleedPreview(true).build());
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cameraView = super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.capture, container, false);
        ButterKnife.bind(this, v);
        zoom = (SeekBar) v.findViewById(R.id.zoombar);
        zoom.setKeepScreenOn(true);
        camera_preview.addView(cameraView);
        loading.setVisibility(View.INVISIBLE);
        camera_preview.setOnTouchListener(this);

        return v;
    }

    @Override
    public boolean onTouch (View v, MotionEvent e) {
        autoFocus();
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            zoom.setEnabled(false);
            zoomTo(zoom.getProgress()).onComplete(new Runnable() {
                @Override
                public void run() {
                    zoom.setEnabled(true);
                }
            }).go();
        }
    }

    class CaptureHost extends SimpleCameraHost {
        public CaptureHost (Context cxt) {
            super(cxt);
        }

        @Override
        public boolean useFrontFacingCamera() {
            if (getArguments() == null) {
                return false;
            }
            return getArguments().getBoolean(KEY_USE_FFC);
        }

        @Override
        public void saveImage (PictureTransaction xact, byte[] image) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, Constants.BITMAP_QUALITY, blob);

            ImageQuery imageQuery = new ImageQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss.mmm", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            imageQuery.setImageData(Base64.encodeToString(blob.toByteArray(), Base64.DEFAULT));
            imageQuery.setUserId(ParseUser.getCurrentUser().getUsername());

            camera_result_callback.OnCameraResult(imageQuery);
            CaptureFragment.this.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    capture_btn.setVisibility(View.INVISIBLE);
                    loading.setVisibility(View.VISIBLE);
                    zoom.setVisibility(View.INVISIBLE);
                }
            });

        }

        @Override
        public Camera.Parameters adjustPreviewParameters(Camera.Parameters parameters) {
            flashMode=
                    CameraUtils.findBestFlashModeMatch(parameters,
                            Camera.Parameters.FLASH_MODE_RED_EYE,
                            Camera.Parameters.FLASH_MODE_AUTO,
                            Camera.Parameters.FLASH_MODE_ON);

            if (doesZoomReallyWork() && parameters.getMaxZoom() > 0) {
                zoom.setMax(parameters.getMaxZoom());
                zoom.setOnSeekBarChangeListener(CaptureFragment.this);
            }
            else {
                zoom.setEnabled(false);
            }
            return(super.adjustPreviewParameters(parameters));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    void takeSimplePicture () {
        PictureTransaction xact = new PictureTransaction(getCameraHost());
        if (isFlashMode) {
            xact.flashMode(flashMode);
        }
        takePicture(xact);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}