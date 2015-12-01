package carmera.io.carmera.fragments.dealer_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.R;
import carmera.io.carmera.messaging.Message;
import carmera.io.carmera.messaging.MessageAdapter;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/30/15.
 */
public class DealerChatFragment extends Fragment {

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private String mUsername = Constants.STATIC_MOCK_USER;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new MessageAdapter(activity, mMessages);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });
        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (null == mUsername) return;
//                if (!mSocket.connected()) return;
//
//                if (!mTyping) {
//                    mTyping = true;
//                    mSocket.emit("typing");
//                }
//
//                mTypingHandler.removeCallbacks(onTypingTimeout);
//                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                attemptSend();
            }
        });

        String inquire_car_info = getArguments().getString(Constants.EXTRA_LISTINGS_CHAT_INFO);
        addLog(String.format("Starting chatting!\n%s", inquire_car_info));
    }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
//            if (Activity.RESULT_OK != resultCode) {
//                getActivity().finish();
//                return;
//            }

//            mUsername = data.getStringExtra("username");
//            int numUsers = data.getIntExtra("numUsers", 1);
//
//            addLog(getResources().getString(R.string.message_welcome));
//            addParticipantsLog(numUsers);
        }

        private void addLog(String message) {
            mMessages.add(new Message.Builder(Message.TYPE_LOG)
                    .message(message).build());
            mAdapter.notifyItemInserted(mMessages.size() - 1);
            scrollToBottom();
        }

        private void addParticipantsLog(int numUsers) {
            addLog(getResources().getQuantityString(R.plurals.message_participants, numUsers, numUsers));
        }

        private void addMessage(String username, String message) {
            mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                    .username(username).message(message).build());
            mAdapter.notifyItemInserted(mMessages.size() - 1);
            scrollToBottom();
        }

        private void addTyping(String username) {
            mMessages.add(new Message.Builder(Message.TYPE_ACTION)
                    .username(username).build());
            mAdapter.notifyItemInserted(mMessages.size() - 1);
            scrollToBottom();
        }

        private void removeTyping(String username) {
            for (int i = mMessages.size() - 1; i >= 0; i--) {
                Message message = mMessages.get(i);
                if (message.getType() == Message.TYPE_ACTION && message.getUsername().equals(username)) {
                    mMessages.remove(i);
                    mAdapter.notifyItemRemoved(i);
                }
            }
        }

        private void attemptSend() {
//            if (null == mUsername) return;
//            if (!mSocket.connected()) return;

            mTyping = false;

            String message = mInputMessageView.getText().toString().trim();
            if (TextUtils.isEmpty(message)) {
                mInputMessageView.requestFocus();
                return;
            }

            mInputMessageView.setText("");
            addMessage(mUsername, message);

            // perform the sending message attempt.
//            mSocket.emit("new message", message);
        }

        private void startSignIn() {
//            mUsername = null;
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivityForResult(intent, REQUEST_LOGIN);
        }

        private void leave() {
//            mUsername = null;
//            mSocket.disconnect();
//            mSocket.connect();
//            startSignIn();
        }

        private void scrollToBottom() {
            mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
        }

}


