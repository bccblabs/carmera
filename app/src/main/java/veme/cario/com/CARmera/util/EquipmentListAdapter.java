package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 1/19/15.
 */
public class EquipmentListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    public static class EquipmentItem {
        public String package_name;
        public String package_cnt;
        public List<EquipmentChildItem> items = new ArrayList<EquipmentChildItem>();
    }

    public static class EquipmentItemHolder {
        public TextView package_name_tv;
    }

    public static class EquipmentChildItem {
        public String equipment_name;
        public String equipment_desc;
        public String equipment_price;
    }

    public static class EquipmentChildHolder {
        public TextView equipment_name_tv;
        public TextView equipment_desc_tv;
        public TextView equipment_price_tv;
    }


    private LayoutInflater inflater;
    private List<EquipmentItem> items ;

    public EquipmentListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        items = new ArrayList<EquipmentItem>();
    }

    public void setData(List<EquipmentItem> items) {
        this.items = items;
    }

    @Override
    public EquipmentChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        EquipmentChildHolder holder;
        EquipmentChildItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new EquipmentChildHolder();
            convertView = inflater.inflate(R.layout.list_item_equipment, parent, false);
            holder.equipment_name_tv = (TextView) convertView.findViewById(R.id.equipment_name);
            holder.equipment_price_tv = (TextView) convertView.findViewById(R.id.equipment_price);
            holder.equipment_desc_tv = (TextView) convertView.findViewById(R.id.equipment_description);
            convertView.setTag(holder);
        } else {
            holder = (EquipmentChildHolder) convertView.getTag();
        }

        holder.equipment_name_tv.setText(item.equipment_name);
        holder.equipment_price_tv.setText(item.equipment_price);
        holder.equipment_desc_tv.setText(item.equipment_desc);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).items.size();
    }

    @Override
    public EquipmentItem getGroup(int groupPosition) {
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
        EquipmentItemHolder holder;
        EquipmentItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new EquipmentItemHolder();
            convertView = inflater.inflate(R.layout.equipment_item, parent, false);
            holder.package_name_tv = (TextView) convertView.findViewById(R.id.package_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (EquipmentItemHolder) convertView.getTag();
        }

        holder.package_name_tv.setText(item.package_name);

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
