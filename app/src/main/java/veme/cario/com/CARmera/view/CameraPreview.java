package veme.cario.com.CARmera.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private static String TAG = "SURFACE_VIEW";


    /* zooming variables */
    private boolean has_zoom = false;
    private int zoom_factor = 0, max_zoom_factor = 0;
    private ScaleGestureDetector scaleGestureDetector;
    private List<Integer> zoom_ratios = null;
    private boolean touch_was_multitouch = false;
    private class ZoomListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            CameraPreview.this.scaleZoom(detector.getScaleFactor());
            return true;
        }
    }


    /* Focus Variables */
    private boolean has_focus_area = false;
    private int focus_x, focus_y;
    private List<String> supported_focus_modes;
    private List<String> supported_flash_modes;
    private int focus_success = FOCUS_DONE;
    private boolean successfully_focused = false;
    private long successfully_focused_time = -1;
    private long focus_complete_time = -1;
    private static final int FOCUS_WAITING = 0;
    private static final int FOCUS_SUCCESS = 1;
    private static final int FOCUS_FAILED = 2;
    private static final int FOCUS_DONE = 3;
    private String set_flash_value_after_autofocus = "";
    private Matrix camera_to_preview_matrix = new Matrix();
    private Matrix preview_to_camera_matrix = new Matrix();


    public CameraPreview (Context cxt_, Camera camera_) {
        super(cxt_);

        /* initialize camera object */
        camera = camera_;

        /* initialize surface holder callback */
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        /* zooming */
        Camera.Parameters params = camera.getParameters();
        has_zoom = params.isZoomSupported();
        if (has_zoom) {
            zoom_ratios = params.getZoomRatios();
            max_zoom_factor = params.getMaxZoom();
        }
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ZoomListener());

        /* autofocus */
        supported_focus_modes = params.getSupportedFocusModes();
        supported_flash_modes = params.getSupportedFlashModes();
    }

    static class Area {
        public Rect rect = null;
        public int weight = 0;

        public Area(Rect rect, int weight) {
            this.rect = rect;
            this.weight = weight;
        }
    }

    private void scaleZoom(float scale_factor) {
        if (this.has_zoom ) {
            float zoom_ratio = this.zoom_ratios.get(zoom_factor)/100.0f;
            zoom_ratio *= scale_factor;
            int new_zoom_factor = zoom_factor;
            if (zoom_ratio <= 1.0f ) {
                new_zoom_factor = 0;
            }
            else if (zoom_ratio >= zoom_ratios.get(max_zoom_factor) /100.0f ) {
                new_zoom_factor = max_zoom_factor;
            }
            else {
                if (scale_factor > 1.0f ) {
                    for (int i = zoom_factor; i < zoom_ratios.size(); i++) {
                        if (zoom_ratios.get(i)/100.0f >= zoom_ratio) {
                            new_zoom_factor = i;
                            break;
                        }
                    }
                } else {
                    for (int i = zoom_factor; i >= 0; i--) {
                        if (zoom_ratios.get(i)/100.0f <= zoom_ratio) {
                            new_zoom_factor = i;
                            break;
                        }
                    }
                }
            }
            zoomTo(new_zoom_factor);
        }
    }

    private void zoomTo(int new_zoom_factor) {
        if (new_zoom_factor < 0)
            new_zoom_factor = 0;
        if (new_zoom_factor > max_zoom_factor)
            new_zoom_factor = max_zoom_factor;
        if (new_zoom_factor != zoom_factor) {
            if (this.has_zoom) {
                this.setZoom(new_zoom_factor);
                zoom_factor = new_zoom_factor;
                clearFocusAreas();
            }
        }
    }

    private void setZoom(int value) {
        Camera.Parameters parameters = this.camera.getParameters();
        Log.d(TAG, "zoom " + parameters.getZoom() + " to " + value);
        parameters.setZoom(value);
        camera.setParameters(parameters);
    }

    private void cancelAutoFocus() {
        camera.cancelAutoFocus();
        autoFocusCompleted(false, false, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        this.invalidate();
        if (event.getPointerCount() != 1) {
            touch_was_multitouch = true;
            return true;
        }
        if (event.getAction() != MotionEvent.ACTION_UP) {
            if( event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1 ) {
                touch_was_multitouch = false;
            }
            return true;
        }
        if (touch_was_multitouch) {
            return true;
        }

        cancelAutoFocus();
        this.has_focus_area = false;
        ArrayList<Area> areas = getAreas(event.getX(), event.getY());
            if( setFocusAndMeteringArea(areas) ) {
                this.has_focus_area = true;
                this.focus_x = (int)event.getX();
                this.focus_y = (int)event.getY();
            }
        tryAutoFocus(false, true);
        return true;
    }

    private void autoFocusCompleted (boolean manual, boolean success, boolean cancelled) {
        if( cancelled ) {
            focus_success = FOCUS_DONE;
        }
        else {
            focus_success = success ? FOCUS_SUCCESS : FOCUS_FAILED;
            focus_complete_time = System.currentTimeMillis();
        }
        if( manual && !cancelled && success ) {
            successfully_focused = true;
            successfully_focused_time = focus_complete_time;
        }
        if( set_flash_value_after_autofocus.length() > 0 ) {
            Log.d(TAG, "set flash back to: " + set_flash_value_after_autofocus);
            setFlashValue(set_flash_value_after_autofocus);
            set_flash_value_after_autofocus = "";
        }
    }

    private void tryAutoFocus(final boolean startup, final boolean manual) {
            // it's only worth doing autofocus when autofocus has an effect (i.e., auto or macro mode)
            if( supportsAutoFocus() ) {
                set_flash_value_after_autofocus = "";
                String old_flash_value = getFlashValue();
                if( startup && !old_flash_value.equals("flash_off") ) {
                    set_flash_value_after_autofocus = old_flash_value;
                    setFlashValue("flash_off");
                }
                Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        autoFocusCompleted(manual, success, false);
                    }
                };

                this.focus_success = FOCUS_WAITING;
                this.focus_complete_time = -1;
                this.successfully_focused = false;
                try {
                    camera.autoFocus(autoFocusCallback);
                }
                catch(RuntimeException e) {
                    // just in case? We got a RuntimeException report here from 1 user on Google Play
                    autoFocusCallback.onAutoFocus(false, camera);
                    e.printStackTrace();
                }
            }
            else if( has_focus_area ) {
                // do this so we get the focus box, for focus modes that support focus area, but don't support autofocus
                focus_success = FOCUS_SUCCESS;
                focus_complete_time = System.currentTimeMillis();
            }
    }

    @Override
    public void surfaceCreated (SurfaceHolder s_holder) {
        Log.d (TAG, "surfaceCreated called.");
        try {
            camera.setPreviewDisplay(s_holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.v(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed (SurfaceHolder s_holder) {
        Log.d (TAG, "surfaceDestroyed called.");
    }

    @Override
    public void surfaceChanged (SurfaceHolder s_holder, int fmt, int w, int h) {
        Log.d (TAG, "surfaceChanged called.");
    }

    private String convertFlashModeToValue(String flash_mode) {
        // flash_mode may be null, meaning flash isn't supported; we return ""
        String flash_value = "";
        if( flash_mode.equals(Camera.Parameters.FLASH_MODE_OFF) ) {
            flash_value = "flash_off";
        }
        else if( flash_mode.equals(Camera.Parameters.FLASH_MODE_AUTO) ) {
            flash_value = "flash_auto";
        }
        else if( flash_mode.equals(Camera.Parameters.FLASH_MODE_ON) ) {
            flash_value = "flash_on";
        }
        else if( flash_mode.equals(Camera.Parameters.FLASH_MODE_TORCH) ) {
            flash_value = "flash_torch";
        }
        else if( flash_mode.equals(Camera.Parameters.FLASH_MODE_RED_EYE) ) {
            flash_value = "flash_red_eye";
        }
        return flash_value;
    }

    private String convertFlashValueToMode(String flash_value) {
        String flash_mode = "";
        if( flash_value.equals("flash_off") ) {
            flash_mode = Camera.Parameters.FLASH_MODE_OFF;
        }
        else if( flash_value.equals("flash_auto") ) {
            flash_mode = Camera.Parameters.FLASH_MODE_AUTO;
        }
        else if( flash_value.equals("flash_on") ) {
            flash_mode = Camera.Parameters.FLASH_MODE_ON;
        }
        else if( flash_value.equals("flash_torch") ) {
            flash_mode = Camera.Parameters.FLASH_MODE_TORCH;
        }
        else if( flash_value.equals("flash_red_eye") ) {
            flash_mode = Camera.Parameters.FLASH_MODE_RED_EYE;
        }
        return flash_mode;
    }

    private void calculatePreviewToCameraMatrix() {
        calculateCameraToPreviewMatrix();
        if( !camera_to_preview_matrix.invert(preview_to_camera_matrix) ) {
            Log.d(TAG, "calculatePreviewToCameraMatrix failed to invert matrix!?");
        }
    }

    private void calculateCameraToPreviewMatrix() {
        camera_to_preview_matrix.reset();
        // Need mirror for front camera (No mirroring, cars dun typically take selfies */
        camera_to_preview_matrix.setScale(1, 1);

        /* o no, we need something here */
//        camera_to_preview_matrix.postRotate(camera_controller.getDisplayOrientation());
        camera_to_preview_matrix.postScale(this.getWidth() / 2000f, this.getHeight() / 2000f);
        camera_to_preview_matrix.postTranslate(this.getWidth() / 2f, this.getHeight() / 2f);
    }

    private ArrayList<CameraPreview.Area> getAreas(float x, float y) {
        float [] coords = {x, y};
        calculatePreviewToCameraMatrix();
        preview_to_camera_matrix.mapPoints(coords);
        float focus_x = coords[0];
        float focus_y = coords[1];

        int focus_size = 50;
        Rect rect = new Rect();
        rect.left = (int)focus_x - focus_size;
        rect.right = (int)focus_x + focus_size;
        rect.top = (int)focus_y - focus_size;
        rect.bottom = (int)focus_y + focus_size;
        if( rect.left < -1000 ) {
            rect.left = -1000;
            rect.right = rect.left + 2*focus_size;
        }
        else if( rect.right > 1000 ) {
            rect.right = 1000;
            rect.left = rect.right - 2*focus_size;
        }
        if( rect.top < -1000 ) {
            rect.top = -1000;
            rect.bottom = rect.top + 2*focus_size;
        }
        else if( rect.bottom > 1000 ) {
            rect.bottom = 1000;
            rect.top = rect.bottom - 2*focus_size;
        }
        ArrayList<CameraPreview.Area> areas = new ArrayList<CameraPreview.Area>();
        areas.add(new CameraPreview.Area(rect, 1000));
        return areas;
    }

    void clearFocusAndMetering() {
        Camera.Parameters parameters = camera.getParameters();
        boolean update_parameters = false;
        if( parameters.getMaxNumFocusAreas() > 0 ) {
            parameters.setFocusAreas(null);
            update_parameters = true;
        }
        if( parameters.getMaxNumMeteringAreas() > 0 ) {
            parameters.setMeteringAreas(null);
            update_parameters = true;
        }
        if( update_parameters ) {
            camera.setParameters(parameters);
        }
    }

    public void clearFocusAreas() {
        cancelAutoFocus();
        clearFocusAndMetering();
        has_focus_area = false;
        focus_success = FOCUS_DONE;
        successfully_focused = false;
    }

    private void setFocusValue (String focus_value) {
        Camera.Parameters parameters = camera.getParameters();
        if( focus_value.equals("focus_mode_auto") || focus_value.equals("focus_mode_manual") ) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        else if( focus_value.equals("focus_mode_infinity") ) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        }
        else if( focus_value.equals("focus_mode_macro") ) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        }
        else if( focus_value.equals("focus_mode_fixed") ) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        }
        else if( focus_value.equals("focus_mode_edof") ) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_EDOF);
        }
        else if( focus_value.equals("focus_mode_continuous_video") ) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        else {
            Log.d(TAG, "setFocusValue() received unknown focus value " + focus_value);
        }
        camera.setParameters(parameters);
    }

    private boolean supportsAutoFocus() {
        Camera.Parameters parameters = camera.getParameters();
        String focus_mode = parameters.getFocusMode();
        // getFocusMode() is documented as never returning null, however I've had null pointer exceptions reported in Google Play from the below line (v1.7),
        // on Galaxy Tab 10.1 (GT-P7500), Android 4.0.3 - 4.0.4; HTC EVO 3D X515m (shooteru), Android 4.0.3 - 4.0.4
        if( focus_mode != null && ( focus_mode.equals(Camera.Parameters.FOCUS_MODE_AUTO) || focus_mode.equals(Camera.Parameters.FOCUS_MODE_MACRO) ) ) {
            return true;
        }
        return false;
    }

    public String getFlashValue() {
        // returns "" if flash isn't supported
        Camera.Parameters parameters = camera.getParameters();
        String flash_mode = parameters.getFlashMode();
        return convertFlashModeToValue(flash_mode);
    }

    void setFlashValue(String flash_value) {
        Camera.Parameters parameters = camera.getParameters();
        String flash_mode = convertFlashValueToMode(flash_value);
        if( flash_mode.length() > 0 && !flash_mode.equals(parameters.getFlashMode()) ) {
            if( parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH) && !flash_mode.equals(Camera.Parameters.FLASH_MODE_OFF) ) {
                // workaround for bug on Nexus 5 where torch doesn't switch off until we set FLASH_MODE_OFF
                parameters.setFlashMode(flash_mode);
                camera.setParameters(parameters);
                parameters = camera.getParameters();
            }
            parameters.setFlashMode(flash_mode);
            camera.setParameters(parameters);
        }
    }

    boolean setFocusAndMeteringArea(List<CameraPreview.Area> areas) {
        List<Camera.Area> camera_areas = new ArrayList<Camera.Area>();
        for (CameraPreview.Area area : areas) {
            camera_areas.add(new Camera.Area(area.rect, area.weight));
        }
        Camera.Parameters parameters = camera.getParameters();
        String focus_mode = parameters.getFocusMode();
        if (parameters.getMaxNumFocusAreas() != 0 && focus_mode != null && ( focus_mode.equals(Camera.Parameters.FOCUS_MODE_AUTO) || focus_mode.equals(Camera.Parameters.FOCUS_MODE_MACRO) || focus_mode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) || focus_mode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO) ) ) {
            parameters.setFocusAreas(camera_areas);
            if (parameters.getMaxNumMeteringAreas() == 0) {
                Log.d(TAG, "metering areas not supported");
            } else {
                parameters.setMeteringAreas(camera_areas);
            }
            camera.setParameters(parameters);
            return true;
        }
        else if (parameters.getMaxNumMeteringAreas() != 0) {
            parameters.setMeteringAreas(camera_areas);
            camera.setParameters(parameters);
        }
        return false;
    }


}