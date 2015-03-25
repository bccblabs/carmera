package veme.cario.com.CARmera.fragment.VehicleInfoFragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.widget.FacebookDialog;
import com.facebook.widget.ProfilePictureView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.UserActivity;

public class TaggedPostFragment extends Fragment {
    private static final String TAG = "TAGGED_POST_FRAGMENT";

    private ParseImageView tagged_photo_view;
    private ProfilePictureView fb_profile_pic_view;
    private TextView post_date_tv, user_info_tv;  /* user name, time */
    private TextView vehicle_info_tv; /* vehicle mk, yr, model */
    private TaggedVehicle taggedVehicle;
    private DetailsSelectedListener detailSelectedCallback = null;
    private ImageFragment.UploadListener uploadCallback = null;
    private SpecsFragment.OnSeeListingsClickListener onSeeListingsClickListener = null;
    private FloatingActionButton recognize_btn, details_btn, see_listings_btn;


    public interface DetailsSelectedListener {
        public abstract void onDetailsSelected (byte[] image, String yr, String mk, String model);
    }


    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            detailSelectedCallback = (DetailsSelectedListener) activity;
            onSeeListingsClickListener = (SpecsFragment.OnSeeListingsClickListener) activity;
            uploadCallback = (ImageFragment.UploadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + ": "
                    + " needs to implement the Listeners!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tagged_post, container, false);
        String post_id = getArguments().getString("tagged_post_id");
        Log.i(TAG, " vehicle post id: " + post_id);

        user_info_tv = (TextView) view.findViewById(R.id.user_info_view);
        post_date_tv = (TextView) view.findViewById(R.id.post_date_view);
        vehicle_info_tv = (TextView) view.findViewById(R.id.vehicle_info_tv);
        tagged_photo_view = (ParseImageView) view.findViewById(R.id.tagged_photo_fullview);
        fb_profile_pic_view = (ProfilePictureView) view.findViewById(R.id.fb_profile_pic_view);

        Typeface fa = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.has("profile")) {
            JSONObject userProfile = currentUser.getJSONObject("profile");
            try {
                if (userProfile.has("facebookId")) {
                    fb_profile_pic_view.setProfileId(userProfile.getString("facebookId"));
                } else {
                    fb_profile_pic_view.setProfileId(null);
                }
                user_info_tv.setText(userProfile.getString("name"));
                user_info_tv.setTypeface(fa);
            } catch (JSONException e) {
                Log.d(TAG, "Error parsing saved user data.");
            }
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TaggedVehicle");
        query.getInBackground(post_id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject taggedVehicle, ParseException e) {
                if (e == null) {
                    TaggedVehicle vehicle = (TaggedVehicle) taggedVehicle;
                    Log.d(TAG, "tagged vehicle object" + vehicle.getMake());
                    TaggedPostFragment.this.taggedVehicle = vehicle;
                    post_date_tv.setText(vehicle.getCreatedAt().toString());
                    if (vehicle.getMake() == null)
                        vehicle_info_tv.setText("Unlabeled");
                    else {
                        vehicle_info_tv.setText(vehicle.getMake() + " " + vehicle.getModel());
                    }
                    tagged_photo_view.setParseFile(vehicle.getTagPhoto());
                    tagged_photo_view.loadInBackground();
                } else {
                    Log.d (TAG, "problem getting shit");
                }
            }
        });
        recognize_btn = (FloatingActionButton) view.findViewById(R.id.try_recognize_btn);
        details_btn = (FloatingActionButton) view.findViewById(R.id.post_vehicle_details_btn);
        see_listings_btn = (FloatingActionButton) view.findViewById(R.id.post_vehicle_listings_btn);

        recognize_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCallback.onUploadResult(taggedVehicle.getObjectId());
            }
        });

        details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    byte[] imageData = taggedVehicle.getTagPhoto().getData();
                    detailSelectedCallback.onDetailsSelected(imageData, taggedVehicle.getYear(),
                            taggedVehicle.getMake(),
                            taggedVehicle.getModel());
                } catch (ParseException e) {
                    Log.i(TAG, " - getting parse file raw data err: " + e.getMessage());
                }
            }
        });

        see_listings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeeListingsClickListener.OnSeeListingsClick(taggedVehicle.getYear(),
                                                    taggedVehicle.getMake(),
                                                    taggedVehicle.getModel());
            }
        });


        return view;
    }


}
