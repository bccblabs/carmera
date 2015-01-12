package veme.cario.com.CARmera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.ListingsAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 1/6/15.
 */
public class ListingsActivity extends BaseActivity
                              implements SavedListingsFragment.OnSavedListingSelectedListener {

    private ListView listings_view;
    private TextView no_listings_view;
    private ListingsAdapter listingsAdapter;
    private VehicleInfoDialog vehicleInfoDialog;
    private SavedSearch savedSearch;

    private String TAG = ListingsActivity.class.toString();

    private FloatingActionButton edit_search_btn, save_search_btn, saved_cars_btn, saved_search_btn;


    @Override
    public void OnSavedListingSelected (int pos) {}

    @Override
    public void onSearchCreated (SavedSearch savedSearch) {
        /* re-populate query */
    }

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        getLayoutInflater().inflate(R.layout.activity_listings, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Listings");

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
//                args.putParcelable("search_obj", savedSearch);
//                if ( vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
//                    vehicleInfoDialog.dismiss();
//                    vehicleInfoDialog = null;
//                }
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
//                savedSearch = new SavedSearch() ;
//                savedSearch.setFavorite(true);
//                savedSearch.saveInBackground();
                Toast.makeText(getApplicationContext(), "Search Saved!", Toast.LENGTH_LONG).show();
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
