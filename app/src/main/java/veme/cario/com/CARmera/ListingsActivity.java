package veme.cario.com.CARmera;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import java.util.List;

import veme.cario.com.CARmera.fragment.ActivityFragment.ListingsDetailFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.ListingsAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class ListingsActivity extends BaseActivity
                              implements ImageFragment.UploadListener {

    private ListView listings_view;
    private TextView no_listings_view;
    private ListingsAdapter listingsAdapter;
    private VehicleInfoDialog vehicleInfoDialog;
    private EditText search_name;

    private String TAG = ListingsActivity.class.toString();

    private FloatingActionButton edit_search_btn, save_search_btn, saved_cars_btn, saved_search_btn;

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

    @Override
    public void onSearchCreated (SavedSearch savedSearch) {
        /* re-populate query */
    }

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        getLayoutInflater().inflate(R.layout.activity_listings, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Dis.car.vr");

        /* sets adapter and loads data */
        listings_view = (ListView) findViewById(R.id.listings_view);
        no_listings_view = (TextView) findViewById(R.id.no_listings_textview);
        listings_view.setEmptyView(no_listings_view);

        listingsAdapter = new ListingsAdapter(ListingsActivity.this);
        listings_view.setAdapter(listingsAdapter);

        edit_search_btn = (FloatingActionButton) findViewById(R.id.listing_activity_edit_srch_btn);
        saved_search_btn = (FloatingActionButton) findViewById(R.id.listing_activity_saved_searches_btn);
        save_search_btn = (FloatingActionButton) findViewById(R.id.listing_activity_save_srch_btn);
        saved_cars_btn = (FloatingActionButton) findViewById(R.id.listing_activity_saved_cars_btn);

        /* Button listeners: each opens a fragment with information */
        edit_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("dialog_type", "create_search");
                if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
                    vehicleInfoDialog.dismiss();
                    vehicleInfoDialog = null;
                }
                FragmentManager fm = getSupportFragmentManager();
                vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show(fm, "createSearchOverlay");
            }
        });

        saved_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("dialog_type", "saved_search");
                /* get stored searches from the server, gets the count for each */
                if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
                    vehicleInfoDialog.dismiss();
                    vehicleInfoDialog = null;
                }
                FragmentManager fm = getSupportFragmentManager();
                vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show(fm, "savedSearchOverlay");
            }
        });

        saved_cars_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("dialog_type", "saved_listings");
                /* get stored searches from the server, gets the count for each */
                if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
                    vehicleInfoDialog.dismiss();
                    vehicleInfoDialog = null;
                }
                FragmentManager fm = getSupportFragmentManager();
                vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show(fm, "savedSearchOverlay");
            }
        });

        /* shows a toast */
        save_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog save_search_dialog = new MaterialDialog.Builder(ListingsActivity.this)
                                  .title("Save Search")
                                  .customView(R.layout.dialog_save_search)
                                  .positiveText("Save")
                                  .negativeText("Cancel")
                                  .callback(new MaterialDialog.ButtonCallback() {
                                      @Override
                                      public void onPositive(MaterialDialog dialog) {
                                          super.onPositive(dialog);
                                          SavedSearch savedSearch = new SavedSearch();
                                          savedSearch.setSearchName(search_name.getText().toString());
                                          savedSearch.saveInBackground();
                                          Toast.makeText(ListingsActivity.this, "\"" + search_name.getText().toString() +
                                                  "\" saved! ", Toast.LENGTH_SHORT).show();
                                      }
                                  })
                                  .build();
                search_name = (EditText) save_search_dialog.getCustomView().findViewById(R.id.search_name_edittext);
                save_search_dialog.show();
            }
        });

        /* load data from parse query */

        ParseQuery<TaggedVehicle> query = ParseQuery.getQuery("TaggedVehicle");
        query.findInBackground(new FindCallback<TaggedVehicle>() {
            @Override
            public void done(List<TaggedVehicle> taggedVehicles, ParseException e) {
                for (TaggedVehicle vehicle : taggedVehicles) {
                    listingsAdapter.add (vehicle);
                }
            }
        });
        listingsAdapter.notifyDataSetChanged();
    }
}
