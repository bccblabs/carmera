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
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import veme.cario.com.CARmera.fragment.ActivityFragment.CreateSearchFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SavedListingsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.SharedTagsFragment;
import veme.cario.com.CARmera.fragment.ActivityFragment.TaggedVehicleFragment;
import veme.cario.com.CARmera.fragment.SavedSearchFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.CarInfoFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.SelectStyleFragment;
import veme.cario.com.CARmera.fragment.VehicleInfoFragment.TaggedPostFragment;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

public class ProfileActivity extends BaseActivity implements SavedListingsFragment.OnSavedListingSelectedListener {

    private FloatingActionButton my_tags_btn, saved_vehicles_btn, saved_search_btn, shared_vehicles_btn;
    private ProfilePictureView profilePictureView;
    private TextView name_view;
    private static final String TAG = ProfileActivity.class.getSimpleName();

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
        i.putExtra ("search_obj", savedSearch);
        startActivity(i);
    }


    @Override
    public void OnSavedListingSelected (int pos) {}

}
