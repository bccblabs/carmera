package veme.cario.com.CARmera.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import veme.cario.com.CARmera.model.UserModels.CarChat;

/**
 * Created by bski on 12/18/14.
 */
public class ChatListAdapter extends ArrayAdapter<CarChat> {
    /* adding, but not removing vehicles */
    /* render vehicle with a detailed dialog listener */
    /* configuration, how to implement ? */

    private LayoutInflater inflater;
    public ChatListAdapter (Context context) {
        super(context, 0);
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        return view;
    }
}
