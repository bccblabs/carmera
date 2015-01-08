package veme.cario.com.CARmera;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import java.util.List;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.VehicleGridAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 11/22/14.
 */
public class NearbyActivity extends BaseActivity
                            implements SelectStyleFragment.SelectResultListener,
                                       CarInfoFragment.OnReselectClickListener,
                                       ImageFragment.ImageResultListener {


    private VehicleInfoDialog vehicleInfoDialog = null;
    private GridView nearby_vehicles_gridview;
    private LinearLayout no_nearby_vehicles_layout;
    private VehicleGridAdapter vehicleGridAdapter;

    @Override
    public void onStyleSelected (byte[] imageData, String trim_id, String trim_name, String yr, String mk, String md) {
        Bundle args = new Bundle();
        args.putString ("dialog_type", "vehicle_info");
        args.putString ("vehicle_id", trim_id);
        args.putString ("vehicle_year", yr);
        args.putString ("vehicle_make", mk);
        args.putString("vehicle_model", md);
        args.putString ("vehicle_trim_name", trim_name);
        args.putByteArray("imageData", imageData);


        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
    }

    @Override
    public void OnReselectClick (byte[] raw_photo, String yr, String mk, String md) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", yr);
        args.putString("vehicle_make", mk);
        args.putString("vehicle_model", md);

        args.putByteArray("imageData", raw_photo);
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "styleChooserOverlay");

    }

    @Override
    public void onRecognitionResult (byte[] imageData, String year, String make, String model) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", year);
        args.putString("vehicle_make", make);
        args.putString("vehicle_model", model);
        args.putByteArray("imageData", imageData);

        if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }

        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show(fm, "styleChooserOverlay");

    }

    /* TODO:
        1. Query by geolocation
        2. Query by Edmund's API/other api
     */
    @Override
    public void onCreate (Bundle savedBundleinst) {
        super.onCreate(savedBundleinst);

        getLayoutInflater().inflate(R.layout.activity_nearby, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Nearby");

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
//                final TaggedVehicle taggedVehicle = (TaggedVehicle) nearby_vehicles_gridview.getItemAtPosition(position);
                /* show a dialog here, very simple */
            }
        });
    }
}
