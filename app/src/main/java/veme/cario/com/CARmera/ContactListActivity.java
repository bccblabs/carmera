package veme.cario.com.CARmera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import java.util.List;

import veme.cario.com.CARmera.model.UserModels.Contact;
import veme.cario.com.CARmera.util.ContactListAdapter;

public class ContactListActivity extends BaseActivity {
    private ListView contact_list_view;
    private ProgressDialog progress_dialog;
    private ContactListAdapter contact_list_adapter;
    private BroadcastReceiver receiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_contact_list, frame_layout);
        drawer_listview.setItemChecked(drawer_pos, true);
        setTitle("Friends");

        showSpinner();
        initUIComponents();
    }

    private void showSpinner() {
        progress_dialog = new ProgressDialog(this);
        progress_dialog.setTitle("Loading");
        progress_dialog.setMessage("Please wait...");
        progress_dialog.show();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
                progress_dialog.dismiss();
                if (!success) {
                    Toast.makeText(getApplicationContext(), "Messaging service failed to start", Toast.LENGTH_LONG).show();
                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("veme.cario.com.CARmera.ContactListActivity"));
    }

    private void initUIComponents() {

        contact_list_view = (ListView)findViewById(R.id.contact_list_view);
        contact_list_adapter = new ContactListAdapter(ContactListActivity.this);
        contact_list_view.setAdapter(contact_list_adapter);

        ParseQuery<Contact> query = ParseQuery.getQuery("Contact");
        query.findInBackground(new FindCallback<Contact>() {
            public void done(List<Contact> contact_list, com.parse.ParseException e) {
                if (e == null) {
                    for (Contact contact : contact_list) {
                        contact_list_adapter.add(contact);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error loading user list",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        contact_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Contact contact = (Contact) contact_list_view.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                /* here we use the parseuser's id to get consistent with the sample code */
                intent.putExtra("RECIPIENT_ID", contact.getParseUser().getObjectId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        initUIComponents();
        super.onResume();
    }
}

