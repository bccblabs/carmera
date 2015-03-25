package veme.cario.com.CARmera.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.Prediction;

/**
 * Created by bski on 1/15/15.
 */
public class RecognitionResultAdapter extends ArrayAdapter<Prediction> {

    private LayoutInflater layoutInflater;
    private static class ViewHolder {
        LinearLayout recognition_overlay;
        TextView vehicle_name, f_score;
    }

    public RecognitionResultAdapter (Context context) {
        super(context, 0);
        layoutInflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView (int pos, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_recognition, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.recognition_overlay = (LinearLayout) view.findViewById(R.id.recognition_overlay);
            viewHolder.vehicle_name = (TextView) view.findViewById(R.id.vehicle_name_view);
            viewHolder.f_score = (TextView) view.findViewById(R.id.f_score_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Prediction prediction = getItem(pos);
        viewHolder.recognition_overlay.setBackgroundColor(0x4169E1);
        String name = prediction.getClass_name().replace("_", " ").toUpperCase();
        viewHolder.vehicle_name.setText(name);
        Double prob = prediction.getProb() * 100;
        DecimalFormat newFormat = new DecimalFormat("#.##");
        Double twoDecimal =  Double.valueOf(newFormat.format(prob));
        viewHolder.f_score.setText( twoDecimal.toString() + "%");
        Typeface fa = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        viewHolder.vehicle_name.setTypeface(fa);
        viewHolder.f_score.setTypeface(fa);
        return view;
    }

}
