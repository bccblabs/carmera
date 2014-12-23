package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import veme.cario.com.CARmera.R;


public class ReviewListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    public static class ReviewItem {
        public String name;
        public String title;
        public String rating;
        public List<ReviewChildItem> items = new ArrayList<ReviewChildItem>();
    }

    public static class ReviewHolder {
        public TextView name_tv;
        public TextView title_tv;
        public TextView rating_tv;
    }

    public static class ReviewChildItem {
       public String text;
       public String favorite_features;
       public String suggested_improvements;
    }

    public static class ReviewChildHolder {
        public TextView text_tv;
        public TextView favorite_feature_tv;
        public TextView suggested_imp_tv;
    }

    private LayoutInflater inflater;
    private List<ReviewItem> items ;

    public ReviewListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        items = new ArrayList<ReviewItem>();
    }

    public void setData(List<ReviewItem> items) {
        this.items = items;
    }

    @Override
    public ReviewChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ReviewChildHolder holder;
        ReviewChildItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ReviewChildHolder();
            convertView = inflater.inflate(R.layout.list_item_review, parent, false);
            holder.text_tv = (TextView) convertView.findViewById(R.id.review_text_textview);
            holder.favorite_feature_tv = (TextView) convertView.findViewById(R.id.review_favorite_features);
            holder.suggested_imp_tv = (TextView) convertView.findViewById(R.id.review_suggested_improvements);
            convertView.setTag(holder);
        } else {
            holder = (ReviewChildHolder) convertView.getTag();
        }

        holder.text_tv.setText(item.text);
        holder.suggested_imp_tv.setText(item.suggested_improvements);
        holder.favorite_feature_tv.setText(item.favorite_features);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).items.size();
    }

    @Override
    public ReviewItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ReviewHolder holder;
        ReviewItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new ReviewHolder();
            convertView = inflater.inflate(R.layout.review_item, parent, false);
            holder.name_tv = (TextView) convertView.findViewById(R.id.reviewer_name_textview);
            holder.title_tv = (TextView) convertView.findViewById(R.id.review_title_textview);
            holder.rating_tv = (TextView) convertView.findViewById(R.id.review_average_rating);
            convertView.setTag(holder);
        } else {
            holder = (ReviewHolder) convertView.getTag();
        }

        holder.name_tv.setText("Reviewed By: " + item.name);
        holder.title_tv.setText(item.title);
        holder.rating_tv.setText(item.rating + " out of 5");

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}
