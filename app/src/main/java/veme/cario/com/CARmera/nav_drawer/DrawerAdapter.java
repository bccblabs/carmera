package veme.cario.com.CARmera.nav_drawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 12/30/14.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {


    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public DrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

            drawerHolder.headerLayout = (LinearLayout) view
                    .findViewById(R.id.headerLayout);
            drawerHolder.itemLayout = (LinearLayout) view
                    .findViewById(R.id.itemLayout);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = this.drawerItemList.get(position);

        if (dItem.getItemTitle() != null) {
            Typeface fa = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.ItemName.setTypeface(fa);
            drawerHolder.title.setText(dItem.getItemTitle());
            drawerHolder.title.setTypeface(fa);
        } else {
            Typeface fa = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
            drawerHolder.headerLayout.setVisibility(LinearLayout.GONE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

            drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                    dItem.getImgId()));
            drawerHolder.ItemName.setText(dItem.getItemName());
            drawerHolder.ItemName.setTypeface(fa);
        }
        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName, title;
        ImageView icon;
        LinearLayout headerLayout, itemLayout;
    }


}
