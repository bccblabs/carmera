package veme.cario.com.CARmera.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 12/8/14.
 */

public class CarInfoFragment extends Fragment {

    private ImageView preview_view;
    private Button save_local_btn;
    private Button share_btn;
    private EditText edit_mention_et;

    private TextView info_year_tv;
    private TextView info_make_tv;
    private TextView info_model_tv;

    private static final String TAG = "CAR_INFO_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIComponents();
    }

    private void initUIComponents() {
        preview_view = (ImageView) getView().findViewById(R.id.info_preview_view);

        save_local_btn = (Button) getView().findViewById(R.id.save_local_btn);
        share_btn = (Button) getView().findViewById(R.id.share_tagged_btn);
        edit_mention_et = (EditText) getView().findViewById(R.id.edit_mention_et);

        save_local_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* TODO: put image in local store */
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* TODO: share image via intent to other media (insta, fb, wechat, whatsapp, etc.) */
            }
        });

        info_year_tv = (TextView) getView().findViewById(R.id.car_info_year_tv);
        info_year_tv.setText(getArguments().getString("vehicle_year"));
        info_make_tv = (TextView) getView().findViewById(R.id.car_info_make_tv);
        info_make_tv.setText(getArguments().getString("vehicle_make"));
        info_model_tv = (TextView) getView().findViewById(R.id.car_info_model_tv);
        info_model_tv.setText(getArguments().getString("vehicle_model"));

        new BitmapLoaderTask().execute();
    }
    public class BitmapLoaderTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground (Void... params) {
            byte[] newImageBytes;
            byte[] imageData = getArguments().getByteArray("imageData");
            /* first, make a bitmap out of original */
            Bitmap raw_bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            /* second, compress it using a byte array */
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            raw_bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            /* third, create a new image out of byte array */
            newImageBytes = bos.toByteArray();
            Bitmap scaled_bitmap = Bitmap.createScaledBitmap(raw_bitmap, 640, 480, false);
            return scaled_bitmap;
        }

        @Override
        protected void onPostExecute (Bitmap res) {
            preview_view.setImageBitmap(res);
        }
    }

}
