package veme.cario.com.CARmera;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import veme.cario.com.CARmera.fragment.ActivityFragment.CreateSearchFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SharedTagsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.TaggedVehicleFragment;
import veme.cario.com.CARmera.fragment.RecognitionResultFragment;
import veme.cario.com.CARmera.fragment.SavedSearchFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.ImageFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.TaggedPostFragment;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class ProfileActivity extends BaseActivity
                                implements SavedListingsFragment.OnSavedListingSelectedListener,
                                           ImageFragment.UploadListener,
                                           RecognitionResultFragment.RecognitionResultCallback {

    private FloatingActionButton my_tags_btn, saved_vehicles_btn, saved_search_btn, shared_vehicles_btn;
    private ProfilePictureView profilePictureView;
    private TextView name_view;
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private VehicleInfoDialog vehicleInfoDialog;

    @Override
    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        getLayoutInflater().inflate(R.layout.activity_profile, frame_layout);

        Typeface fa = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        profilePictureView = (ProfilePictureView) findViewById(R.id.fb_profile_pic_view);
        name_view = (TextView) findViewById(R.id.username_view);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.has("profile")) {
            JSONObject userProfile = currentUser.getJSONObject("profile");
            try {
                if (userProfile.has("facebookId")) {
                    profilePictureView.setProfileId(userProfile.getString("facebookId"));
                } else {
                    profilePictureView.setProfileId(null);
                }
                String userName = userProfile.getString("name");
                CARmeraApp.userName = userName;
                name_view.setText(userProfile.getString("name"));
                name_view.setTypeface(fa);
            } catch (JSONException e) {
                Log.d(TAG, "Error parsing saved user data.");
            }
        }

        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("My Tags");
        my_tags_btn = (FloatingActionButton) findViewById(R.id.my_tags_btn);
        saved_search_btn = (FloatingActionButton) findViewById(R.id.saved_search_btn);
        shared_vehicles_btn = (FloatingActionButton) findViewById(R.id.shared_tags_btn);
        saved_vehicles_btn = (FloatingActionButton) findViewById(R.id.saved_listings_btn);

        my_tags_btn.setIcon(R.drawable.ic_action_tags_green);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        TaggedVehicleFragment taggedVehicleFragment = new TaggedVehicleFragment();
        fragmentTransaction.add(R.id.profile_fragment_holder, taggedVehicleFragment);
        fragmentTransaction.commit();

        my_tags_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_tags_btn.setIcon(R.drawable.ic_action_tags_green);
                saved_vehicles_btn.setIcon(R.drawable.ic_action_star_10);
                shared_vehicles_btn.setIcon(R.drawable.ic_action_share_blue);
                saved_search_btn.setIcon(R.drawable.ic_action_search_purple);

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
                my_tags_btn.setIcon(R.drawable.ic_action_tags_purple);
                saved_vehicles_btn.setIcon(R.drawable.ic_action_star_10_green);
                shared_vehicles_btn.setIcon(R.drawable.ic_action_share_blue);
                saved_search_btn.setIcon(R.drawable.ic_action_search_purple);

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
                my_tags_btn.setIcon(R.drawable.ic_action_tags_purple);
                saved_vehicles_btn.setIcon(R.drawable.ic_action_star_10);
                shared_vehicles_btn.setIcon(R.drawable.ic_action_share_green);
                saved_search_btn.setIcon(R.drawable.ic_action_search_purple);

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
                my_tags_btn.setIcon(R.drawable.ic_action_tags_purple);
                saved_vehicles_btn.setIcon(R.drawable.ic_action_star_10);
                shared_vehicles_btn.setIcon(R.drawable.ic_action_share_blue);
                saved_search_btn.setIcon(R.drawable.ic_action_search_green);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                SavedSearchFragment savedSearchFragment = new SavedSearchFragment();
                fragmentTransaction.replace(R.id.profile_fragment_holder, savedSearchFragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onSearchCreated (SavedSearch savedSearch) {
        /* directs to listings activity to retrieve listings */
        Intent i = new Intent(ProfileActivity.this, ListingsActivity.class);
        i.putExtra ("search_obj", savedSearch.getObjectId());
        startActivity(i);
    }

    @Override
    public void OnSavedListingSelected (int pos) {}

    @Override
    public void onUploadResult (String tagged_vehicle_id) {
        if (vehicleInfoDialog != null && vehicleInfoDialog.isVisible()) {
            vehicleInfoDialog.dismiss();
            vehicleInfoDialog = null;
        }
        if (tagged_vehicle_id != null) {
            Log.i (TAG, "Tagged Vehicle id: " + tagged_vehicle_id );
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
