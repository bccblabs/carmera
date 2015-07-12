package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.commonsware.cwac.camera.CameraUtils;
import com.commonsware.cwac.camera.PictureTransaction;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.gc.materialdesign.views.ButtonFloat;

import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.widgets.SupportCameraFragment;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by bski on 6/2/15.
 */
public class Capture extends SupportCameraFragment implements SeekBar.OnSeekBarChangeListener,ScreenShotable{

    private static final String KEY_USE_FFC = "com.commonsware.cwac.camera.demo.USE_FFC";
    private SeekBar zoom = null;
    private ButtonFloat capture_btn = null;
    String flashMode = null;
    private String TAG = getClass().getCanonicalName();

    private Bitmap bitmap;
    private View containerView;

    private OnCameraResultListener camera_result_callback = null;

    public interface OnCameraResultListener {
        public void OnCameraResult (byte[] image_data);
    }

    public static Capture newInstance () {
        Capture capture = new Capture();
        return capture;
    }


    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            camera_result_callback = (OnCameraResultListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    @Override
    public void takeScreenShot () {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                Capture.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() { return bitmap; }


    static Capture newInstance (boolean useFFC) {
        Capture capture_frag = new Capture();
        Bundle args=new Bundle();
        args.putBoolean(KEY_USE_FFC, useFFC);
        capture_frag.setArguments(args);
        return capture_frag;
    }

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        SimpleCameraHost.Builder builder = new SimpleCameraHost.Builder(new CaptureHost(getActivity()));
        setHost(builder.useFullBleedPreview(true).build());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cameraView = super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.capture, container, false);
        ((ViewGroup)v.findViewById(R.id.camera_preview)).addView(cameraView);
        zoom = (SeekBar) v.findViewById(R.id.zoombar);
        capture_btn = (ButtonFloat) v.findViewById(R.id.capture_btn);
        zoom.setKeepScreenOn(true);
        capture_btn.setKeepScreenOn(true);
        capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeSimplePicture ();
            }
        });
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
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

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.camera_preview);
    }

    class CaptureHost extends SimpleCameraHost {
        public CaptureHost (Context cxt) {
            super (cxt);
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
            camera_result_callback.OnCameraResult(image);
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
                zoom.setOnSeekBarChangeListener(Capture.this);
            }
            else {
                zoom.setEnabled(false);
            }

            return(super.adjustPreviewParameters(parameters));
        }
    }

    void takeSimplePicture () {
        PictureTransaction xact = new PictureTransaction(getHost());
        takePicture(xact);
    }

    interface Contract {
        boolean isSingleShotMode();
        void setSingleShotMode (boolean mode);
    }

    Contract getContract() {
        return ((Contract) getActivity());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

}
