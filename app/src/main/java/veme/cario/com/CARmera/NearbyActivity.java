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
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.VehicleGridAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 11/22/14.
 */
public class NearbyActivity extends BaseActivity {


    private VehicleInfoDialog vehicleInfoDialog = null;
    private GridView nearby_vehicles_gridview;
    private LinearLayout no_nearby_vehicles_layout;
    private VehicleGridAdapter vehicleGridAdapter;


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
}
