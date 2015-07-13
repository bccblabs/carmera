package carmera.io.carmera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.widgets.SquareImageView;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class PhotoUploadFragment extends SupportBlurDialogFragment {


    @Bind(R.id.upload_progress_bar)
    ProgressBarCircularIndeterminate upload_progress_bar;

    @Bind(R.id.photo)
    SquareImageView photo;

    @Bind(R.id.cancel_upload)
    ButtonRectangle cancel_upload;

    Context cxt;

    String url;

    public static PhotoUploadFragment newInstance() {
        return new PhotoUploadFragment();
    }


                                                   @Override
    public void onCreate (Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        this.cxt = getActivity();
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 5.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

    @Override
    protected boolean isActionBarBlurred() {
        // Enable or disable the blur effect on the action bar.
        // Disabled by default.
        return true;
    }

    @Override
    protected boolean isDimmingEnable() {
        // Enable or disable the dimming effect.
        // Disabled by default.
        return true;
    }

    @Override
    public Dialog onCreateDialog (Bundle savedBundleInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.photo_upload, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        this.url = getArguments().getString("image_url");
        Picasso.with(cxt) //
                .load(url) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .into(photo);
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
