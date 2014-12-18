package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseImageView;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.Contact;

/**
 * Created by bski on 12/18/14.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;
    private static String TAG = "CONTACT_LIST_ADAPTER";
    private static class ViewHolder {
        private ParseImageView user_thumbnail;
        private TextView user_name;
    }


    public ContactListAdapter (Context cxt) {
        super (cxt, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView (int pos, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_contact, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.user_thumbnail = (ParseImageView) view
                    .findViewById(R.id.contact_thumbnail_view);
            viewHolder.user_name = (TextView) view
                    .findViewById(R.id.contact_name_textview);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Contact contact = getItem(pos);
        viewHolder.user_name.setText(contact.getParseUser().getUsername());
        viewHolder.user_thumbnail.setParseFile(contact.getUserThumbnail());
        viewHolder.user_thumbnail.loadInBackground();
        return view;
    }
}
