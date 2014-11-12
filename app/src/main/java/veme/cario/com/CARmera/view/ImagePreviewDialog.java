package veme.cario.com.CARmera.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 11/11/14.
 */
public class ImagePreviewDialog extends ProgressDialog {

    @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.image_preview_dialog);
    }
}
