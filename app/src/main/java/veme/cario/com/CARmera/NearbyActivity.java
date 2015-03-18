package veme.cario.com.CARmera;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

import veme.cario.com.CARmera.fragment.RecognitionResultFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.VehicleGridAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 11/22/14.
 */
public class NearbyActivity extends BaseActivity
                            implements ImageFragment.UploadListener,
                                       RecognitionResultFragment.RecognitionResultCallback {


    private VehicleInfoDialog vehicleInfoDialog = null;
    private GridView nearby_vehicles_gridview;
    private LinearLayout no_nearby_vehicles_layout;
    private VehicleGridAdapter vehicleGridAdapter;
    private final String TAG = NearbyActivity.class.getSimpleName();

    @Override
    public void onCreate (Bundle savedBundleinst) {
        super.onCreate(savedBundleinst);

        getLayoutInflater().inflate(R.layout.activity_nearby, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Near Me");

        nearby_vehicles_gridview = (GridView) findViewById(R.id.nearby_vehicle_grid_view);

        no_nearby_vehicles_layout = (LinearLayout) findViewById(R.id.no_tagged_vehicle_overlay);

        nearby_vehicles_gridview.setEmptyView(no_nearby_vehicles_layout);

        vehicleGridAdapter = new VehicleGridAdapter(NearbyActivity.this, true);

        nearby_vehicles_gridview.setAdapter(vehicleGridAdapter);

        /* sets data for all tagged vehicles */
        ParseQuery<TaggedVehicle> query = ParseQuery.getQuery("TaggedVehicle");
        query.findInBackground(new FindCallback<TaggedVehicle>() {
            @Override
            public void done(List<TaggedVehicle> taggedVehicles, ParseException e) {
                for (TaggedVehicle vehicle : taggedVehicles) {
                    vehicleGridAdapter.add (vehicle);
                }
            }
        });
        vehicleGridAdapter.notifyDataSetChanged();

        nearby_vehicles_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) nearby_vehicles_gridview.getItemAtPosition(position);
                Bundle args = new Bundle();
                args.putString("dialog_type", "post_details");
                args.putString("tagged_post_id", taggedVehicle.getObjectId());

                if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
                    vehicleInfoDialog.dismiss();
                    vehicleInfoDialog = null;
                }
                FragmentManager fm = getSupportFragmentManager();
                vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show (fm, "postDetailsOverlay");
            }
        });
    }

    @Override
    public void onUploadResult (String tagged_vehicle_id) {
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        if (tagged_vehicle_id != null) {
            Log.i(TAG, "Tagged Vehicle id: " + tagged_vehicle_id);
            Bundle args = new Bundle();
            args.putString("dialog_type", "recognition_dialog");
            args.putString("tagged_vehicle_id", tagged_vehicle_id);
            FragmentManager fm = getSupportFragmentManager();
            vehicleInfoDialog = new VehicleInfoDialog();
            vehicleInfoDialog.setArguments(args);
            vehicleInfoDialog.show(fm, "recognitionOverlay");
        }
    }

    /* when a vehicle is recognized from the cloud server */
    public void onRecognitionResult (String tagged_vehicle_id, final String year, final String make, final String model) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TaggedVehicle");
        Bundle args = new Bundle();
        try {
            TaggedVehicle taggedVehicle =  (TaggedVehicle) query.get(tagged_vehicle_id);
            taggedVehicle.setYear(year);
            taggedVehicle.setMake(make);
            taggedVehicle.setModel(model);
            taggedVehicle.saveInBackground();
            args.putByteArray("imageData", taggedVehicle.getTagPhoto().getData());
            args.putBoolean("is_tagged_post" , true);
            args.putString ("tagged_vehicle_id" , tagged_vehicle_id);
            args.putString("dialog_type", "choose_style");
            args.putString("vehicle_year", year);
            args.putString("vehicle_make", make);
            args.putString("vehicle_model", model);
        } catch (ParseException parse_err) {
            Log.i (TAG, parse_err.getMessage());
        }

        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        Log.i (TAG, year + " " + make + " " + model);
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "styleChooserOverlay");
    }
}
