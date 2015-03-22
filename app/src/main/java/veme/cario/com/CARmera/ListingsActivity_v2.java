package veme.cario.com.CARmera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import veme.cario.com.CARmera.fragment.ListingWizard.ListingsSearchInputFragment;
import veme.cario.com.CARmera.fragment.ListingWizard.VehicleFilterFragment;


public class ListingsActivity_v2 extends BaseActivity implements ListingsSearchInputFragment.OnSearchBtnClickedListener, VehicleFilterFragment.OnFilterPressedListener{

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate (savedBundleInst);

        getLayoutInflater().inflate(R.layout.activity_listings_v2, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Discarvr");


        Fragment searchfragment = new ListingsSearchInputFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add (R.id.fragment_container, searchfragment);
        ft.commit();
    }

    @Override
    public void OnFilterPressed (String query_string) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment search_result_fragment = new ListingsSearchInputFragment();
        Bundle arg = new Bundle();
        arg.putString ("query_string", query_string);
        search_result_fragment.setArguments(arg);
        ft.replace (R.id.fragment_container, search_result_fragment);
        ft.commit();
    }

    @Override
    public void OnSearchButtonClicked (String str, String query_str) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment search_result_fragment = new VehicleFilterFragment();
        Bundle arg = new Bundle();
        arg.putString ("res_string", str);
        arg.putString ("query_string", query_str);
        search_result_fragment.setArguments(arg);
        ft.replace (R.id.fragment_container, search_result_fragment);
        ft.commit();
    }

}

