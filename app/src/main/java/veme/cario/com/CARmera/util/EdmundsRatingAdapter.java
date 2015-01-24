package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.APIModels.EdmundsRating;
import veme.cario.com.CARmera.model.Json.EdmundsSubRating;

/**
 * Created by bski on 12/19/14.
 */
public class EdmundsRatingAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    public static class RatingItem {
        public String title;
        public String grade;
        public String score;
        public String summary;
        public List<SubRatingItem> subRatings = new ArrayList<SubRatingItem>();
    }
    public static class SubRatingItem {
        public String title;
        public String grade;
        public String score;
        public String summary;
    }
    public static class SubRatingItemHolder {
        public TextView title_tv;
        public TextView grade_tv;
        public TextView score_tv;
        public TextView summary_tv;
    }
    public static class RatingItemHolder {
        public TextView title_textview;
        public TextView grade_textview;
        public TextView score_textview;
        public TextView summary_textview;
    }

    private LayoutInflater inflater;
    private List<RatingItem> ratingItems;

    public EdmundsRatingAdapter (Context cxt) {
        inflater = LayoutInflater.from(cxt);
        ratingItems = new ArrayList<RatingItem>();
    }

    public void setData(List<RatingItem> items) {
        this.ratingItems = items;
    }

    @Override
    public SubRatingItem getChild(int groupPosition, int childPosition) {
        return ratingItems.get(groupPosition).subRatings.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubRatingItemHolder holder;
        SubRatingItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new SubRatingItemHolder();
            convertView = inflater.inflate(R.layout.list_item_subrating, parent, false);
            holder.title_tv = (TextView) convertView.findViewById(R.id.subrating_title);
            holder.grade_tv = (TextView) convertView.findViewById(R.id.subrating_grade);
            holder.score_tv = (TextView) convertView.findViewById(R.id.subrating_score);
            holder.summary_tv = (TextView) convertView.findViewById(R.id.subrating_summary);
            convertView.setTag(holder);
        } else {
            holder = (SubRatingItemHolder) convertView.getTag();
        }

        holder.title_tv.setText(item.title);
        holder.grade_tv.setText(item.grade);
        holder.score_tv.setText(item.score);
        holder.summary_tv.setText(item.summary);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return ratingItems.get(groupPosition).subRatings.size();
    }

    @Override
    public RatingItem getGroup(int groupPosition) {
        return ratingItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return ratingItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        RatingItemHolder holder;
        RatingItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new RatingItemHolder();
            convertView = inflater.inflate(R.layout.list_item_edmunds_rating, parent, false);
            holder.title_textview = (TextView) convertView.findViewById(R.id.edmunds_ratings_title_textview);
            holder.grade_textview = (TextView) convertView.findViewById(R.id.edmunds_ratings_grade_textview);
            holder.score_textview = (TextView) convertView.findViewById(R.id.edmunds_ratings_score_textview);
            holder.summary_textview = (TextView) convertView.findViewById(R.id.edmunds_ratings_summary_textview);
            convertView.setTag(holder);
        } else {
            holder = (RatingItemHolder) convertView.getTag();
        }

        holder.title_textview.setText(item.title);
        holder.grade_textview.setText(item.grade);
        holder.score_textview.setText(item.score);
        holder.summary_textview.setText(item.summary);

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return false;
    }

}
