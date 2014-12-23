package veme.cario.com.CARmera.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.SimpleVehicleListAdapter;

/**
 * Created by bski on 12/10/14.
 */
public class SimpleTaggedVehicleDialog extends DialogFragment {

    /* TODO:
            1. Show Dialog, with empty view
            2. From data saved on parse, get the list
     */

    private final static String TAG = "SIMPLE_TAGGED_VEHICLE_DIALOG";
    private final int LIST_ITEM_COUNT = 5;
    private ListView tagged_vehicle_lv;
    private LinearLayout no_vehicles_view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OnSimpleTagSelectedListener onSimpleTagSelectedCallback;
    private SimpleVehicleListAdapter simpleVehicleListAdapter;
    private static int page = 0;

    public interface OnSimpleTagSelectedListener {
        public void onSimpleTagSelected (byte[] imageData, String year, String make, String model);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.onSimpleTagSelectedCallback = (OnSimpleTagSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnListingSelectedListener interface");
        }
    }

    /* onCreateDialog: use alternatively to onCreateView if needs a complete custom view */
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        Drawable semi_transparent = new ColorDrawable(Color.BLACK);
        semi_transparent.setAlpha(180);

        dialog.getWindow().setBackgroundDrawable(semi_transparent);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout(width, height);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_simple_tagged_vehicle_list, container);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.simple_tagged_swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        tagged_vehicle_lv = (ListView) view.findViewById(R.id.simple_tagged_vehicles_list_view);
        no_vehicles_view = (LinearLayout) view.findViewById(R.id.no_tagged_vehicle_view);
        simpleVehicleListAdapter = new SimpleVehicleListAdapter(getActivity());
        return view;
    }

    private void doQuery() {

        /* TODO: next iteration, go fetch thumbnail versions of the image */
        simpleVehicleListAdapter.clear();
        ParseQuery<TaggedVehicle> query = ParseQuery.getQuery("TaggedVehicle");
        query.setLimit(LIST_ITEM_COUNT);
        query.setSkip(page * LIST_ITEM_COUNT);
        page ++;
        List<String> fields = new ArrayList<String>(Arrays.asList("year", "make", "model", "createdAt", "photo"));
        query.selectKeys(fields);
        query.findInBackground(new FindCallback<TaggedVehicle>() {
            @Override
            public void done(List<TaggedVehicle> taggedVehicles, ParseException e) {
                for (TaggedVehicle vehicle : taggedVehicles) {
                    simpleVehicleListAdapter.add(vehicle);
                }
            }
        });
        simpleVehicleListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedBundleInstance) {

        super.onViewCreated(view, savedBundleInstance);
        tagged_vehicle_lv.setAdapter(simpleVehicleListAdapter);

        doQuery();
        tagged_vehicle_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) tagged_vehicle_lv.getItemAtPosition(position);
                try {
                    onSimpleTagSelectedCallback.onSimpleTagSelected(taggedVehicle.getTagPhoto().getData(),
                                                                    taggedVehicle.getYear(),
                                                                    taggedVehicle.getMake(),
                                                                    taggedVehicle.getModel());
                } catch (ParseException e) {
                    Log.e (TAG, "image data conversion prob...");
                }
            }
        });
        tagged_vehicle_lv.setEmptyView(no_vehicles_view);
        getDialog().setTitle("Tagged Cars");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
                doQuery();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}