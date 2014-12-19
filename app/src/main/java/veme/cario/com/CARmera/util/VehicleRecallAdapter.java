package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.CustomerReview;
import veme.cario.com.CARmera.model.Json.Recall;

/**
 * Created by bski on 12/19/14.
 */
public class VehicleRecallAdapter extends ArrayAdapter<Recall> {

    private LayoutInflater inflater;
    private static class ViewHolder {
        TextView componentDesc;
        TextView manufacturerRecallNumber;
        TextView manufacturedFrom;
        TextView manufacturedTo;
        TextView numberOfVehiclesAffected;
        TextView defectConsequence;
        TextView defectCorrectiveAction;
        TextView defectDescription;
    }
    public VehicleRecallAdapter (Context cxt) {
        super (cxt, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_review, parent, false);
            holder = new ViewHolder();
            holder.componentDesc = (TextView) view.findViewById(R.id.recall_component_desc_tv);
            holder.manufacturedFrom = (TextView) view.findViewById(R.id.recall_from_tv);
            holder.manufacturedTo = (TextView) view.findViewById(R.id.recall_to_tv);
            holder.numberOfVehiclesAffected = (TextView) view.findViewById(R.id.recall_num_affected_tv);
            holder.defectConsequence = (TextView) view.findViewById(R.id.recall_consequence_tv);
            holder.defectCorrectiveAction = (TextView) view.findViewById(R.id.recall_corrective_tv);
            holder.defectDescription = (TextView) view.findViewById(R.id.recall_desc_tv);
            holder.manufacturerRecallNumber = (TextView) view.findViewById(R.id.recall_number_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Recall review = getItem(position);
        holder.componentDesc.setText(review.getComponentDescription());
        holder.manufacturedFrom.setText(review.getManufacturedFrom());
        holder.manufacturedTo.setText(review.getManufacturedTo());
        holder.numberOfVehiclesAffected.setText(review.getNumberOfVehiclesAffected());
        holder.defectConsequence.setText(review.getDefectConsequence());
        holder.defectCorrectiveAction.setText(review.getDefectCorrectiveAction());
        holder.defectDescription.setText(review.getDefectDescription());
        holder.manufacturerRecallNumber.setText(review.getManufacturerRecallNumber());
        return view;
    }
}