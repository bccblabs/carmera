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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.ByteArrayOutputStream;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.VehicleStyles;
import veme.cario.com.CARmera.model.Json.Style;
import veme.cario.com.CARmera.requests.VehicleStylesRequest;
import veme.cario.com.CARmera.util.VehicleStylesAdapter;

/**
 * Created by bski on 12/12/14.
 */
public class SelectStyleFragment extends Fragment {

    public interface SelectResultListener {
        public void onStyleSelected (byte[] imageData, String trim_id, String trim_name, String yr, String mk, String md);
    }

    private final class StyleListRequestListener implements RequestListener<VehicleStyles> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
            SelectStyleFragment.this.getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(VehicleStyles vehicleStyles) {
            if (vehicleStyles == null)
                return;
            vehicleStylesAdapter.clear();
            for (Style style : vehicleStyles.getStyles()) {
                vehicleStylesAdapter.add(style);
                Log.i(TAG, " - style name: " + style.getName());
            }
            vehicleStylesAdapter.notifyDataSetChanged();
            getActivity().setProgressBarIndeterminateVisibility(false);

        }
    }

    private SelectResultListener selectResultCallback = null;
    private ImageView preview_view;
    private ListView styles_list_view;
    private LinearLayout no_styles_overlay;
    private VehicleStylesAdapter vehicleStylesAdapter;
    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private static final String TAG = "SELECT_STYLE_FRAGMENT";
    private static final String JSON_HASH_KEY = "select_style_json";
    private String year;
    private String make;
    private String model;
    private Bitmap bitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        year = getArguments().getString("vehicle_year");
        make = getArguments().getString("vehicle_make");
        model = getArguments().getString("vehicle_model");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_style, container, false);

        styles_list_view = (ListView) view.findViewById(R.id.styles_list_view);
        no_styles_overlay = (LinearLayout) view.findViewById(R.id.no_styles_overlay);
        preview_view = (ImageView) view.findViewById(R.id.sel_style_preview_view);
        new BitmapLoaderTask().execute();

        vehicleStylesAdapter = new VehicleStylesAdapter(inflater.getContext());
        styles_list_view.setAdapter(vehicleStylesAdapter);
        styles_list_view.setEmptyView(no_styles_overlay);
        performRequest();
        styles_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Style style = vehicleStylesAdapter.getItem(position);
                selectResultCallback.onStyleSelected(getArguments().getByteArray("imageData"),
                                                style.getId(), style.getName(), year, make, model);
                Log.i (TAG, "style.id: " + style.getId());
            }
        });
        return view;
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            selectResultCallback = (SelectResultListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the SelectResultListener!");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
        spiceManager.addListenerIfPending(VehicleStyles.class, JSON_HASH_KEY,
                new StyleListRequestListener());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void performRequest() {
        SelectStyleFragment.this.getActivity().setProgressBarIndeterminate(true);
        VehicleStylesRequest vehicleStylesRequest = new VehicleStylesRequest(year, make, model);
        spiceManager.execute(vehicleStylesRequest, JSON_HASH_KEY, DurationInMillis.ALWAYS_RETURNED,
                                new StyleListRequestListener());

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
            bitmap = BitmapFactory.decodeByteArray(newImageBytes, 0, newImageBytes.length);
            Bitmap scaled_bitmap = Bitmap.createScaledBitmap(raw_bitmap, 640, 480, false);
            return scaled_bitmap;
        }

        @Override
        protected void onPostExecute (Bitmap res) {
            preview_view.setImageBitmap(res);
        }
    }


}
