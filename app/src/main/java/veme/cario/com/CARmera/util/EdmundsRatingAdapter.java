package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.EdmundsRating;
import veme.cario.com.CARmera.model.Json.EdmundsSubRating;

/**
 * Created by bski on 12/19/14.
 */
public class EdmundsRatingAdapter extends ArrayAdapter<EdmundsRating> {

    private LayoutInflater inflater;
    private static class ViewHolder {
        TextView title_textview;
        TextView grade_textview;
        TextView score_textview;
        TextView summary_textview;
        ListView subratings_listview;
    }

    private EdmundsSubRatingAdapter edmundsSubRatingAdapter;

    public EdmundsRatingAdapter (Context cxt) {
        super (cxt, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_edmunds_rating, parent, false);
            holder = new ViewHolder();
            holder.title_textview = (TextView) view.findViewById(R.id.edmunds_ratings_title_textview);
            holder.grade_textview = (TextView) view.findViewById(R.id.edmunds_ratings_grade_textview);
            holder.score_textview = (TextView) view.findViewById(R.id.edmunds_ratings_score_textview);
            holder.summary_textview = (TextView) view.findViewById(R.id.edmunds_ratings_summary_textview);
            holder.subratings_listview = (ListView) view.findViewById(R.id.edmunds_ratings_subratings_listview);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final EdmundsRating edmundsRating = getItem(position);
        holder.title_textview.setText(edmundsRating.getTitle());
        holder.grade_textview.setText(edmundsRating.getGrade());
        holder.score_textview.setText(edmundsRating.getScore());
        holder.summary_textview.setText(edmundsRating.getSummary());

        edmundsSubRatingAdapter = new EdmundsSubRatingAdapter(getContext());
        holder.subratings_listview.setAdapter(edmundsSubRatingAdapter);
        for (EdmundsSubRating edmundsSubRating : edmundsRating.getSubRatings()) {
            edmundsSubRatingAdapter.add(edmundsSubRating);
        }
        edmundsSubRatingAdapter.notifyDataSetChanged();
        return view;
    }
}
