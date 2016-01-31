package carmera.io.carmera.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import carmera.io.carmera.R;

/**
 * Created by bski on 7/20/15.
 */
public class MultiSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<KeyPairBoolData> items;
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;

    private MyAdapter adapter;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    @Override // same
    public void onCancel(DialogInterface dialog) {
//        StringBuffer spinnerBuffer = new StringBuffer();
//        int num_selected = 0;
//        for (int i = 0; i < items.size(); i++) {
//            if (items.get(i).isSelected() ) {
//                spinnerBuffer.append(items.get(i).getName());
//                num_selected ++;
//                spinnerBuffer.append(", ");
//            }
//        }
//
        String spinnerText = "";
//        spinnerText = spinnerBuffer.toString();
//        if (spinnerText.length() <= 3 && spi)
//            spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
//        else if (num_selected > 3)
//            spinnerText = defaultText;
//        else
            spinnerText = defaultText;

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
                R.layout.view_spinner_text,
                new String[] { spinnerText });
        setAdapter(adapterSpinner);
        if(adapter != null)
            adapter.notifyDataSetChanged();
        listener.onItemsSelected(items);


    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate(R.layout.dialog_listview_search, null),
                title_view = inflater.inflate(R.layout.view_spinner_title, null);
        View search_view = view.findViewById(R.id.alertSearchEditText);
        search_view.setVisibility(View.GONE);
        builder.setView(view);
        builder.setCustomTitle(title_view);

        final ListView listView = (ListView) view.findViewById(R.id.alertSearchListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setCacheColorHint(getResources().getColor(R.color.background_floating_material_dark));
        listView.setFastScrollEnabled(false);
        adapter = new MyAdapter(getContext(), items);
        listView.setAdapter(adapter);


//        builder.setMultiChoiceItems(
//                items.toArray(new CharSequence[items.size()]), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    // same
    public void setItems(List<KeyPairBoolData> items, String allText, int position,
                         MultiSpinnerListener listener) {
        this.items = items;
        this.defaultText = allText;
        this.listener = listener;

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
                R.layout.view_spinner_text, new String[] { allText });
        setAdapter(adapterSpinner);

        if(position != -1)
        {
            items.get(position).setSelected(true);
            onCancel(null);
        }
    }


    public class MyAdapter extends BaseAdapter {

        List<KeyPairBoolData> arrayList;
        List<KeyPairBoolData> mOriginalValues; // Original Values
        LayoutInflater inflater;

        public MyAdapter(Context context, List<KeyPairBoolData> arrayList) {
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView textView;
            CheckBox checkBox;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.dialog_listview_search_subview, null);
                holder.textView = (TextView) convertView.findViewById(R.id.alertTextView);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.alertCheckbox);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final KeyPairBoolData data = arrayList.get(position);

            holder.textView.setText(data.getName());
            holder.checkBox.setChecked(data.isSelected());

            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ViewHolder temp = (ViewHolder) v.getTag();
                    temp.checkBox.setChecked(!temp.checkBox.isChecked());

                    int len = arrayList.size();
                    for (int i = 0; i < len; i++) {
                        if (i == position) {
                            data.setSelected(!data.isSelected());
                            Log.i("TAG", "On Click Selected : " + data.getName() + " : " + data.isSelected());
                            break;
                        }
                    }
                }
            });
            holder.checkBox.setTag(holder);
            return convertView;
        }
    }
    public interface MultiSpinnerListener {
        public void onItemsSelected(List<KeyPairBoolData> items);
    }

}