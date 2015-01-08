package veme.cario.com.CARmera;

import android.os.Bundle;

/**
 * Created by bski on 1/6/15.
 */
public class ListingsActivity extends BaseActivity {
    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        getLayoutInflater().inflate(R.layout.activity_listings, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Listings");
    }
}
