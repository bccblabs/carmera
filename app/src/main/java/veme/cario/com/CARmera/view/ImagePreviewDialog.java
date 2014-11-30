package veme.cario.com.CARmera.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 11/11/14.
 */
public class ImagePreviewDialog extends ProgressDialog {

    private ImageView preview_view;
    private TextView status_view;
    private Button discard_photo_btn;
    private Button upload_btn;

    public ImagePreviewDialog (Context cxt) {
        super (cxt);
    }
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_preview_dialog);
        preview_view = (ImageView) findViewById (R.id.preview_view);
        status_view = (TextView) findViewById(R.id.status_view);
        discard_photo_btn = (Button) findViewById(R.id.discard_photo_btn);
        upload_btn = (Button) findViewById(R.id.upload_btn);

        status_view.setText("Let's upload!");
        discard_photo_btn.setText("Retake Photo");
        upload_btn.setText("Upload this!");

        discard_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePreviewDialog.this.dismiss();
            }
        });
    }

    public void setImageView (Bitmap bitmap) {
        preview_view.setImageBitmap(bitmap);
        preview_view.setVisibility(ImageView.VISIBLE);

    }

    public void drawAnimation() {
        RotateAnimation anim = new RotateAnimation( 0.0f,
                                                    360.0f ,
                                                    Animation.RELATIVE_TO_SELF,
                                                    .5f,
                                                    Animation.RELATIVE_TO_SELF,
                                                    .5f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(Animation.INFINITE);

        discard_photo_btn.setVisibility(Button.GONE);
        upload_btn.setText("Close Window");
        preview_view.setAnimation(anim);
        preview_view.startAnimation(anim);
    }
}
