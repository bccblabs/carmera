package veme.cario.com.CARmera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.ListingsAdapter;

/**
 * Created by bski on 1/6/15.
 */
public class ListingsActivity extends BaseActivity {
    private ListView listings_view;
    private TextView no_listings_view;
    private ListingsAdapter listingsAdapter;

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
