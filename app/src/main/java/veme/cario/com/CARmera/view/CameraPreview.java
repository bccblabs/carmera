package veme.cario.com.CARmera.view;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by bski on 11/6/14.
 */
public class CameraPreview extends SurfaceView 
                           implements SurfaceHolder.Callback {

    /* SurfaceView class provides a surface in which a secondary thread
        can render into the screen.
            - Be cautious of SurfaceView and SurfaceHolder.Callback methods coming 
                from thread running SurfaceView's window, they need to be synchronized
                with the drawing thread.
            - Ensure that the drawing thread oly touches the underlying Surface while it's 
                valid. (surfaceCreated -> surfaceDestroyed)
    */

    private Context cxt;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private static String TAG = "SURFACE_VIEW";
    /* camera variables */
    private int zoom_factor = 0;


    public CameraPreview (Context cxt_, Camera camera_, Bundle savedBundleInstance) {
        super(cxt_);

        /* initialize camera object */
        camera = camera_;
        /* initialize surface holder callback */
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        /* deprecated, required on android version < 3.0 */
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        /* retrieve the last zoom factor */
        if (savedBundleInstance != null) {
            zoom_factor = savedBundleInstance.getInt("zoom_factor");
        }
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
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            Log.d(TAG, "surfaceChanged: Error stopping camera preview - " + e.getMessage());
        }
        try {
            camera.setPreviewDisplay(s_holder);
            camera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "surfaceChanged: Error starting camera preview - " + e.getMessage());
        }
    }

}
