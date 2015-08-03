package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
public class CaptureFragment extends SupportCameraFragment implements SeekBar.OnSeekBarChangeListener,
                                                              ScreenShotable,
                                                              View.OnTouchListener {

    private static final String KEY_USE_FFC = "com.commonsware.cwac.camera.demo.USE_FFC";
    private SeekBar zoom = null;
    private ButtonFloat capture_btn = null;

    private ButtonFloat flash_btn = null;
    String flashMode = null;
    boolean isFlashMode = false;

    private FrameLayout camera_preview;
    public final String TAG = getClass().getCanonicalName();

//    private Bitmap bitmap;
//    private View containerView;

    private OnCameraResultListener camera_result_callback = null;

    public interface OnCameraResultListener {
        public void OnCameraResult (byte[] image_data);
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
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

    @Override
    public void takeScreenShot () {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                Capture.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();
    }

    @Override
    public Bitmap getBitmap() { return null; }


    static CaptureFragment newInstance (boolean useFFC) {
        CaptureFragment capture_Fragment_frag = new CaptureFragment();
        Bundle args=new Bundle();
        args.putBoolean(KEY_USE_FFC, useFFC);
        capture_Fragment_frag.setArguments(args);
        return capture_Fragment_frag;
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
        camera_preview = (FrameLayout) v.findViewById(R.id.camera_preview);

        ((ViewGroup)v.findViewById(R.id.camera_preview)).addView(cameraView);
        zoom = (SeekBar) v.findViewById(R.id.zoombar);
        capture_btn = (ButtonFloat) v.findViewById(R.id.capture_btn);
        flash_btn = (ButtonFloat) v.findViewById(R.id.flash_btn);

        zoom.setKeepScreenOn(true);
        capture_btn.setKeepScreenOn(true);
        capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeSimplePicture ();
            }
        });

        flash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                isFlashMode = !(isFlashMode);
                if (isFlashMode)
                    flash_btn.setBackgroundColor(getActivity().getResources().getColor(R.color.material_blue_grey_800));
                else
                    flash_btn.setBackgroundColor(0x1E88E5);

            }
        });

        ButterKnife.bind(this, v);
        camera_preview.setOnTouchListener(this);
        return v;
    }


    @Override
    public boolean onTouch (View v, MotionEvent e) {
        autoFocus();
        return true;
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
        PictureTransaction xact = new PictureTransaction(getHost());
        if (isFlashMode) {
            xact.flashMode(flashMode);
        }
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
