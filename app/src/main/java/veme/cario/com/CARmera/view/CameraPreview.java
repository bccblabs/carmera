package veme.cario.com.CARmera.view;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by bski on 11/6/14.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Context cxt;
    private SurfaceHolder surfaceHolder;

    /* camera variables */
    private int zoom_factor = 0;
    public CameraPreview (Context cxt_, Bundle savedBundleInstance) {
        super(cxt_);
        /* initialize surface holder callback */
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        /* retrieve the last zoom factor */
        if (savedBundleInstance != null) {
            zoom_factor = savedBundleInstance.getInt("zoom_factor");
        }
    }

    public void surfaceCreated (SurfaceHolder holder) {

    }

    public void surfaceDestroyed (SurfaceHolder holder) {

    }

    public void surfaceChanged (SurfaceHolder holder, int fmt, int w, int h) {

    }

    /* Event callbacks:
        1. Single Touch on Auto Focus
        2. Zoom gesture
     */



}
