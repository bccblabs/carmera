package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import veme.cario.com.CARmera.ListingsActivity;
import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.model.UserModels.UserActivity;
import veme.cario.com.CARmera.util.SharedItemAdapter;

/**
 * Created by bski on 1/5/15.
 */
public class SharedTagsFragment extends Fragment {
    private SharedItemAdapter sharedItemAdapter;
    private ListView shared_items_listview;
    private TaggedVehicleFragment.OnVehicleSelectedListener vehicleSelectedCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            vehicleSelectedCallback = (TaggedVehicleFragment.OnVehicleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_items, container, false);
        shared_items_listview = (ListView) view.findViewById(R.id.shared_items_listview);
        sharedItemAdapter = new SharedItemAdapter(inflater.getContext());
        shared_items_listview.setAdapter(sharedItemAdapter);

        ParseQuery<UserActivity> query = ParseQuery.getQuery("UserActivity");
        query.findInBackground(new FindCallback<UserActivity>() {
            @Override
            public void done(List<UserActivity> userActivities, ParseException e) {
                for (UserActivity userActivity : userActivities) {
                    sharedItemAdapter.add(userActivity);
                }
            }
        });
        sharedItemAdapter.notifyDataSetChanged();
        shared_items_listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        shared_items_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(SharedTagsFragment.class.getSimpleName(), "selected!");
                final UserActivity userActivity = (UserActivity) shared_items_listview.getItemAtPosition(position);
                ParseObject data = userActivity.getData();
                if (data instanceof TaggedVehicle) {
                    vehicleSelectedCallback.OnVehicleSelected(data.getObjectId());
                } else {
                    Bundle args = new Bundle();
                    args.putString("saved_search_id", data.getObjectId());
                    Intent listings_intent = new Intent(getActivity(), ListingsActivity.class);
                    listings_intent.putExtras(args);
                    startActivity(listings_intent);
                }
            }
        });
        return view;
    }
}
