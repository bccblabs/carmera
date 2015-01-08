package veme.cario.com.CARmera;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.gc.materialdesign.views.ButtonFloatSmall;
import com.getbase.floatingactionbutton.FloatingActionButton;

import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SharedTagsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.TaggedVehicleFragment;
import veme.cario.com.CARmera.fragment.SavedSearchFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.TaggedPostFragment;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class ProfileActivity extends BaseActivity
                             implements TaggedVehicleFragment.OnSeeListingsSelectedListener,
                                        SavedListingsFragment.OnSavedListingSelectedListener,
                                        SelectStyleFragment.SelectResultListener,
                                        CarInfoFragment.OnReselectClickListener,
                                        TaggedVehicleFragment.OnVehicleSelectedListener,
                                        TaggedPostFragment.DetailsSelectedListener {

    private VehicleInfoDialog vehicleInfoDialog = null;
    private FloatingActionButton my_tags_btn, saved_vehicles_btn, saved_search_btn, shared_vehicles_btn;
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
    public void OnVehicleSelected (String post_id) {
        Bundle args = new Bundle();
        args.putString("dialog_type", "post_details");
        args.putString("tagged_post_id", post_id);

        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        FragmentManager fm = getSupportFragmentManager();
        vehicleInfoDialog = new VehicleInfoDialog();
        vehicleInfoDialog.setArguments(args);
        vehicleInfoDialog.show (fm, "postDetailsOverlay");
    }

    @Override
    public void onDetailsSelected (byte[] imageData, String year, String make, String model) {
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
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        getLayoutInflater().inflate(R.layout.activity_profile, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("My Tags");
        my_tags_btn = (FloatingActionButton) findViewById(R.id.my_tags_btn);
        saved_search_btn = (FloatingActionButton) findViewById(R.id.saved_search_btn);
        shared_vehicles_btn = (FloatingActionButton) findViewById(R.id.shared_tags_btn);
        saved_vehicles_btn = (FloatingActionButton) findViewById(R.id.saved_listings_btn);

        my_tags_btn.setColorNormalResId(R.color.light_spring_green);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        TaggedVehicleFragment taggedVehicleFragment = new TaggedVehicleFragment();
        fragmentTransaction.add (R.id.profile_fragment_holder, taggedVehicleFragment);
        fragmentTransaction.commit();

        my_tags_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_tags_btn.setColorNormalResId(R.color.light_spring_green);
                saved_vehicles_btn.setColorNormalResId(R.color.white);
                shared_vehicles_btn.setColorNormalResId(R.color.white);
                saved_search_btn.setColorNormalResId(R.color.white);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                TaggedVehicleFragment taggedVehicleFragment = new TaggedVehicleFragment();
                fragmentTransaction.replace(R.id.profile_fragment_holder, taggedVehicleFragment);
                fragmentTransaction.commit();
            }
        });

        saved_vehicles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved_vehicles_btn.setColorNormalResId(R.color.light_spring_green);
                my_tags_btn.setColorNormalResId(R.color.white);
                saved_search_btn.setColorNormalResId(R.color.white);
                shared_vehicles_btn.setColorNormalResId(R.color.white);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                SavedListingsFragment savedListingsFragment = new SavedListingsFragment();
                fragmentTransaction.replace(R.id.profile_fragment_holder, savedListingsFragment);
                fragmentTransaction.commit();
            }
        });


        shared_vehicles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_vehicles_btn.setColorNormalResId(R.color.light_spring_green);
                my_tags_btn.setColorNormalResId(R.color.white);
                saved_vehicles_btn.setColorNormalResId(R.color.white);
                saved_search_btn.setColorNormalResId(R.color.white);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                SharedTagsFragment sharedTagsFragment = new SharedTagsFragment();
                fragmentTransaction.replace(R.id.profile_fragment_holder, sharedTagsFragment);
                fragmentTransaction.commit();
            }
        });

        saved_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved_search_btn.setColorNormalResId(R.color.light_spring_green);
                my_tags_btn.setColorNormalResId(R.color.white);
                saved_vehicles_btn.setColorNormalResId(R.color.white);
                shared_vehicles_btn.setColorNormalResId(R.color.white);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                SavedSearchFragment savedSearchFragment = new SavedSearchFragment();
                fragmentTransaction.replace(R.id.profile_fragment_holder, savedSearchFragment);
                fragmentTransaction.commit();
            }
        });


    }


}
