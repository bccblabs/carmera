package veme.cario.com.CARmera.view;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by bski on 11/6/14.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Context cxt;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private static String TAG = "SURFACE_VIEW";

    /* zooming variables */
    private boolean has_zoom = false;
    private int zoom_factor = 0;
    private int max_zoom_factor = 0;
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

    public void scaleZoom(float scale_factor) {
        if( this.has_zoom ) {
            float zoom_ratio = this.zoom_ratios.get(zoom_factor)/100.0f;
            zoom_ratio *= scale_factor;
            int new_zoom_factor = zoom_factor;
            if( zoom_ratio <= 1.0f ) {
                new_zoom_factor = 0;
            }
            else if( zoom_ratio >= zoom_ratios.get(max_zoom_factor)/100.0f ) {
                new_zoom_factor = max_zoom_factor;
            }
            else {
                if( scale_factor > 1.0f ) {
                    for(int i=zoom_factor;i<zoom_ratios.size();i++) {
                        if( zoom_ratios.get(i)/100.0f >= zoom_ratio ) {
                            new_zoom_factor = i;
                            break;
                        }
                    }
                } else {
                    for(int i=zoom_factor;i>=0;i--) {
                        if( zoom_ratios.get(i)/100.0f <= zoom_ratio ) {
                            new_zoom_factor = i;
                            break;
                        }
                    }
                }
            }
            zoomTo(new_zoom_factor);
        }
    }

    public void zoomTo(int new_zoom_factor) {
        if( new_zoom_factor < 0 )
            new_zoom_factor = 0;
        if( new_zoom_factor > max_zoom_factor )
            new_zoom_factor = max_zoom_factor;
        if(new_zoom_factor != zoom_factor) {
            if( this.has_zoom ) {
                this.setZoom(new_zoom_factor);
                zoom_factor = new_zoom_factor;
//                clearFocusAreas();
            }
        }
    }

    void setZoom(int value) {
        Camera.Parameters parameters = this.camera.getParameters();
        Log.d(TAG, "zoom " + parameters.getZoom() + " to " + value);
        parameters.setZoom(value);
        camera.setParameters(parameters);
    }


    public CameraPreview (Context cxt_, Camera camera_) {
        super(cxt_);

        /* initialize camera object */
        camera = camera_;
        /* initialize surface holder callback */
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        /* deprecated, required on android version < 3.0 */
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Camera.Parameters params = camera.getParameters();
        has_zoom = params.isZoomSupported();
        if (has_zoom) {
            zoom_ratios = params.getZoomRatios();
            max_zoom_factor = params.getMaxZoom();
        }
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ZoomListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        if( event.getPointerCount() != 1 ) {
            touch_was_multitouch = true;
            return true;
        }
        if( event.getAction() != MotionEvent.ACTION_UP ) {
            if( event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1 ) {
                touch_was_multitouch = false;
            }
            return true;
        }
        if( touch_was_multitouch ) {
            return true;
        }
//        if( !this.is_video && this.isTakingPhotoOrOnTimer() ) {
            // if video, okay to refocus when recording
//            return true;
//        }

        // note, we always try to force start the preview (in case is_preview_paused has become false)
        // except if recording video (firstly, the preview should be running; secondly, we don't want to reset the phase!)
//        if( !this.is_video ) {
//            startCameraPreview();
//        }
//        cancelAutoFocus();

//        if( camera_controller != null && !this.using_face_detection ) {
//            this.has_focus_area = false;
//            ArrayList<CameraController.Area> areas = getAreas(event.getX(), event.getY());
//            if( camera_controller.setFocusAndMeteringArea(areas) ) {
//                if( MyDebug.LOG )
//                    Log.d(TAG, "set focus (and metering?) area");
//                this.has_focus_area = true;
//                this.focus_screen_x = (int)event.getX();
//                this.focus_screen_y = (int)event.getY();
//            }
//            else {
//                if( MyDebug.LOG )
//                    Log.d(TAG, "didn't set focus area in this mode, may have set metering");
//                // don't set has_focus_area in this mode
//            }
//        }

//        tryAutoFocus(false, true);
        return true;
    }


    @Override
    public void surfaceCreated (SurfaceHolder s_holder) {
    /*  
        Called immediately after the surface is first created.
        One thread can ever draw into a Surface.
        Don't draw here if normal rendering will be in different thread.
    */
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

    /*
        Once called, should not access the surface if we have a rendering thread.
    */
        Log.d (TAG, "surfaceDestroyed called.");
    }

    @Override
    public void surfaceChanged (SurfaceHolder s_holder, int fmt, int w, int h) {

        /* 
            Called after structural changes have been made to the surface
            Update the imagery in the surface
        */
        Log.d (TAG, "surfaceChanged called.");
//        if (surfaceHolder.getSurface() == null) {
//            return;
//        }
//
//        try {
//            camera.stopPreview();
//        } catch (Exception e) {
//            Log.d(TAG, "surfaceChanged: Error stopping camera preview - " + e.getMessage());
//        }
//        try {
//            camera.setPreviewDisplay(s_holder);
//            camera.startPreview();
//
//        } catch (Exception e) {
//            Log.d(TAG, "surfaceChanged: Error starting camera preview - " + e.getMessage());
//        }
    }



}