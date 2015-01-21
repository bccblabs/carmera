package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.UserActivity;

public class SharedItemAdapter extends ArrayAdapter<UserActivity> {
    public static class ViewHolder {
        TextView item_tv;
        ImageView btn;
    }

    private LayoutInflater inflater;

    public SharedItemAdapter (Context context) {
        super (context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView (int pos, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_shared, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_tv = (TextView) view.findViewById(R.id.item_desc_tv);
            viewHolder.btn = (ImageView) view.findViewById(R.id.share_item_btn);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final UserActivity userActivity = getItem(pos);
        if (userActivity.getType().equals("taggedVehicle"))
            viewHolder.btn.setImageResource(R.drawable.ic_action_car);
        else
            viewHolder.btn.setImageResource(R.drawable.ic_action_search_green);
        viewHolder.item_tv.setText(userActivity.toString());
        return view;
    }
}
