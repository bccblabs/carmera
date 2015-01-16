package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.Vehicle;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;

public class ImageFragment extends Fragment {

    /* TODO: disable the scroll on vehicle request not complete*/

    private UploadListener uploadCallback = null;

    public interface UploadListener {
        public abstract void onUploadResult (String tagged_vehicle_id);
    }


    private ImageView preview_view;
    private ButtonRectangle upload_btn, discard_btn;
    private Bitmap bitmap = null;
    private byte[] imageData;
    private static final String TAG = "IMAGE_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            uploadCallback = (UploadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the ImageResultListener!");
        }
    }

    private void initUIComponents() {
        preview_view = (ImageView) getView().findViewById(R.id.preview_view);
        new BitmapLoaderTask().execute();

        upload_btn = (ButtonRectangle) getView().findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BitmapCompressTask().execute();
            }
        });
        discard_btn = (ButtonRectangle) getView().findViewById(R.id.discard_image_btn);
        discard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCallback.onUploadResult(null);
            }
        });
    }

    public class BitmapLoaderTask extends AsyncTask <Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground (Void... params) {
            imageData = getArguments().getByteArray("imageData");
            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            return bitmap;
        }
        @Override
        protected void onPostExecute (Bitmap res) {
            preview_view.setImageBitmap(res);
        }
    }

    public class BitmapCompressTask extends AsyncTask <Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground (Void... params) {
            Bitmap cropped_image = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            return cropped_image;
        }
        @Override
        protected void onPostExecute (Bitmap cropped_image) {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                cropped_image.compress(Bitmap.CompressFormat.PNG, 80, stream);
                byte[] thumbnail = stream.toByteArray();
                TaggedVehicle taggedVehicle = new TaggedVehicle();
                taggedVehicle.setTagPhoto(new ParseFile(imageData));
                taggedVehicle.setThumbnail(new ParseFile(thumbnail));
                taggedVehicle.save();
                uploadCallback.onUploadResult(taggedVehicle.getObjectId());
            } catch (ParseException e) {
                Log.i (TAG, e.getMessage());
            }
        }
    }
}