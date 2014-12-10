package veme.cario.com.CARmera.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

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
    private ListView tagged_vehicle_lv;
    private LinearLayout no_vehicles_view;
    private OnSimpleTagSelectedListener onSimpleTagSelectedCallback;

    public interface OnSimpleTagSelectedListener {
        public void onSimpleTagSelected (String year, String make, String model);
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_simple_tagged_vehicle_list, container);
        tagged_vehicle_lv = (ListView) view.findViewById(R.id.simple_tagged_vehicles_list_view);
        no_vehicles_view = (LinearLayout) view.findViewById(R.id.no_tagged_vehicle_view);
        final SimpleVehicleListAdapter simpleVehicleListAdapter = new SimpleVehicleListAdapter(getActivity());
        tagged_vehicle_lv.setAdapter(simpleVehicleListAdapter);
        tagged_vehicle_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle taggedVehicle = (TaggedVehicle) tagged_vehicle_lv.getItemAtPosition(position);
                onSimpleTagSelectedCallback.onSimpleTagSelected(taggedVehicle.getYear(),
                                                    taggedVehicle.getMake(), taggedVehicle.getModel());
            }
        });

        tagged_vehicle_lv.setEmptyView(no_vehicles_view);
        getDialog().setTitle("Tagged Cars");
        return view;
    }
}