package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.Json.EdmundsSubRating;

/**
 * Created by bski on 12/19/14.
 */
public class EdmundsSubRatingAdapter extends ArrayAdapter<EdmundsSubRating> {

    private LayoutInflater inflater;
    private static class ViewHolder {
        TextView title_textview;
        TextView grade_textview;
        TextView score_textview;
        TextView summary_textview;
    }
    public EdmundsSubRatingAdapter (Context cxt) {
        super (cxt, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_edmunds_subrating, parent, false);
            holder = new ViewHolder();
            holder.title_textview = (TextView) view.findViewById(R.id.subratings_title_textview);
            holder.grade_textview = (TextView) view.findViewById(R.id.subratings_grade_textview);
            holder.score_textview = (TextView) view.findViewById(R.id.subratings_score_textview);
            holder.summary_textview = (TextView) view.findViewById(R.id.subratings_summary_textview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final EdmundsSubRating edmundsSubRating = getItem(position);
        holder.title_textview.setText(edmundsSubRating.getTitle());
        holder.grade_textview.setText(edmundsSubRating.getGrade());
        holder.score_textview.setText(edmundsSubRating.getScore());
        holder.summary_textview.setText(edmundsSubRating.getSummary());
        return view;
    }
}
