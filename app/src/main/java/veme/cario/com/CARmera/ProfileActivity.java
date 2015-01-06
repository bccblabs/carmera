package veme.cario.com.CARmera;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.gc.materialdesign.views.ButtonFloatSmall;

import veme.cario.com.CARmera.fragment.ActivityFragment.ChatFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.TaggedVehicleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class ProfileActivity extends BaseActivity
                             implements TaggedVehicleFragment.OnSeeListingsSelectedListener,
                                        SavedListingsFragment.OnSavedListingSelectedListener,
                                        ChatFragment.OnMentionedListingSelectedListener,
                                        SelectStyleFragment.SelectResultListener,
                                        CarInfoFragment.OnReselectClickListener,
                                        TaggedVehicleFragment.OnVehicleSelectedListener,
                                        ImageFragment.ImageResultListener {

    private VehicleInfoDialog vehicleInfoDialog = null;
    private ButtonFloatSmall my_tags, saved_vehicles, saved_search, shared_vehicles;
    @Override
    public void OnSeeListingsSelected(String year, String make, String model) {
        Bundle args = new Bundle();
        args.putString("listings_year", year);
        args.putString("listings_make", make);
        args.putString("listings_model", model);
        Intent i = new Intent (this, NearbyActivity.class);
        i.putExtras(args);
        startActivity(i);
    }

    @Override
    public void OnSavedListingSelected (int pos) {
    }

    @Override
    public void OnMentionedListingSelected (int pos) {
    }

    @Override
    public void onStyleSelected (byte[] imageData, String trim_id, String trim_name, String yr, String mk, String md) {
        Bundle args = new Bundle();
        args.putString ("dialog_type", "vehicle_info");
        args.putString ("vehicle_id", trim_id);
        args.putString ("vehicle_year", yr);
        args.putString ("vehicle_make", mk);
        args.putString ("vehicle_model", md);
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

    @Override
    public void OnVehicleSelected (byte[] imageData, String year, String make, String model) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "choose_style");
        args.putString("vehicle_year", year);
        args.putString("vehicle_make", make);
        args.putString("vehicle_model", model);
        args.putByteArray("imageData", imageData);

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
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        getLayoutInflater().inflate(R.layout.activity_profile, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("My Tags");
    }
}
