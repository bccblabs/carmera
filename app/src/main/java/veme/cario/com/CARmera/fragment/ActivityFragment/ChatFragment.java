package veme.cario.com.CARmera.fragment.ActivityFragment;

import android.app.Activity;
import android.content.ServiceConnection;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.model.UserModels.MentionedVehicleList;
import veme.cario.com.CARmera.model.UserModels.TaggedVehicle;
import veme.cario.com.CARmera.util.ChatListAdapter;
import veme.cario.com.CARmera.view.VehicleInfoDialog;

/**
 * Created by bski on 12/3/14.
 */
public class ChatFragment extends Fragment {

    private static final String TAG = "SAVED_LISTINGS_FRAGMENT";


    /* do a find in background query from this guy's userinfp ? */
    /* see the "favorites implementation */
    private ChatListAdapter chatListAdapter;

    private ListView mentioned_conversation_listview;
    private LinearLayout no_mentioned_overlay;
    private OnMentionedListingSelectedListener mentionedCallback;

//    private MessageService.MessageServiceInterface messageService;
//    private ServiceConnection serviceConnection = new MyServiceConnection();

    public interface OnMentionedListingSelectedListener {
        public abstract void OnMentionedListingSelected (int pos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mentionedCallback = (OnMentionedListingSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " has to implement the OnMentionedLister interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mentioned, container, false);
        mentioned_conversation_listview = (ListView) view.findViewById(R.id.mentioned_conversation_listview);
        no_mentioned_overlay = (LinearLayout) view.findViewById(R.id.no_mentioned_overlay);
        no_mentioned_overlay.setClickable(true);
        no_mentioned_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* open dialog conversation interface */
//                Intent i = new Intent(getActivity(), NearbyActivity.class);
//                startActivity(i);
            }
        });
        mentioned_conversation_listview.setEmptyView(no_mentioned_overlay);

        chatListAdapter = new ChatListAdapter(inflater.getContext());
        mentioned_conversation_listview.setAdapter(chatListAdapter);

        /* load data from parse */

        List<TaggedVehicle> mentionedVehicles = MentionedVehicleList.get().getMentionedVehicles();
//        for (TaggedVehicle vehicle : mentionedVehicles) {
//            vehicleListAdapter.add (vehicle);
//        }
        chatListAdapter.notifyDataSetChanged();

        mentioned_conversation_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaggedVehicle mentionedVehicle = (TaggedVehicle) mentioned_conversation_listview.getItemAtPosition(position);
                Bundle args = new Bundle();
                byte[] vehicle_image = null;
                try {
                    vehicle_image = mentionedVehicle.getTagPhoto().getData();

                } catch (com.parse.ParseException e) {
                    Log.d(TAG, " - " + e.getMessage());
                }

                args.putByteArray("imageData", vehicle_image);
                args.putString("vehicle_year", mentionedVehicle.getYear());
                args.putString("vehicle_make", mentionedVehicle.getMake());
                args.putString("vehicle_model", mentionedVehicle.getModel());

                FragmentManager fm = getChildFragmentManager();
                VehicleInfoDialog vehicleInfoDialog = new VehicleInfoDialog();
                vehicleInfoDialog.setArguments(args);
                vehicleInfoDialog.show(fm, "vehicleInfoOverlay");
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedBundleInstance) {
        super.onActivityCreated(savedBundleInstance);
//        getActivity().getApplicationContext().bindService()
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.chat_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_invite_friends:
                // dialog, then show x invitation sent, then done
                return true;
            case R.id.action_new_chat:
                // create a new box to select recipient, then select few vehicles, then add to chat list adapter,
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
