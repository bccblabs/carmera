package veme.cario.com.CARmera.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.widget.FacebookDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.SavedSearch;
import veme.cario.com.CARmera.model.UserModels.UserActivity;

/**
 * Created by bski on 1/8/15.
 */
public class SavedSearchAdapter extends ArrayAdapter<SavedSearch> {
    private LayoutInflater inflater;

    private static class ViewHolder {
        TextView search_name_tv;
        TextView match_cnt_tv;
        TextView last_update_tv;
        TextView radius_tv;
        FloatingActionButton fb_btn;
    }

    public SavedSearchAdapter(Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_saved_search, parent, false);
            holder = new ViewHolder();
            holder.last_update_tv = (TextView) view.findViewById(R.id.search_last_update_date_tv);
            holder.search_name_tv = (TextView) view.findViewById(R.id.saved_search_name_tv);
            holder.match_cnt_tv = (TextView) view.findViewById(R.id.match_search_cnt_tv);
            holder.radius_tv = (TextView) view.findViewById(R.id.search_radius_tv);
            holder.fb_btn = (FloatingActionButton) view.findViewById(R.id.saved_search_share_btn);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        final SavedSearch savedSearch = getItem(position);
        holder.last_update_tv.setText(savedSearch.getUpdatedAt().toString());
        holder.search_name_tv.setText(savedSearch.getSearchName());
        holder.match_cnt_tv.setText("20+");
        holder.radius_tv.setText(" in " + 50 + " mile radius.");
        holder.fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FacebookDialog.canPresentMessageDialog(getContext())) {
                    FacebookDialog.MessageDialogBuilder builder = new FacebookDialog.MessageDialogBuilder((Activity) getContext());
                    builder.setLink("http://www.carmera.io")
                            .setName(savedSearch.getSearchName());
                    builder.build().present();
                } else {
                    // The user doesn't have the Facebook Messenger app for Android app installed.
                }
                UserActivity userActivity = new UserActivity();
                userActivity.setSrcName("I");
                userActivity.setDataId(savedSearch.getObjectId());
                userActivity.setDestName("Kai");
                userActivity.setType("savedSearch");
                userActivity.saveInBackground();
            }
        });


        return view;
    }

}
