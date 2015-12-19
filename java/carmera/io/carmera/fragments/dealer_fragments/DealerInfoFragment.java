package carmera.io.carmera.fragments.dealer_fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.cards.CarInfoCard;
import carmera.io.carmera.cards.CompositeHeader;import carmera.io.carmera.requests.DealershipRequest;
import carmera.io.carmera.utils.Constants;
/**
 * Created by bski on 11/30/15.
 */
public class DealerInfoFragment extends Fragment {

    private SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);
    private Context cxt;
    public final String TAG = getClass().getCanonicalName();
    @Bind (R.id.content_container) public View content_container;
    @Bind (R.id.dealer_name_card) public it.gmariotti.cardslib.library.view.CardView dealer_name_card;
    @Bind (R.id.dealer_operations_card) public it.gmariotti.cardslib.library.view.CardView dealer_ops_card;
    @Bind (R.id.loading_container) public View loadingContainer;
    @Bind (R.id.call_dealer) public ButtonRectangle call_dealer;

    private final class DealerInfoListener implements RequestListener<String> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(String res) {
            try {
                Log.i (TAG, res.toString());
                final JsonObject dealerInfo = new Gson().fromJson (res, JsonElement.class).getAsJsonObject();
                if (DealerInfoFragment.this.isAdded()) {
                    loadingContainer.setVisibility(View.GONE);
                    content_container.setVisibility(View.VISIBLE);
                    String address = String.format("%s, %s, %s, %s",
                            dealerInfo.getAsJsonObject("address").get("street").getAsString(),
                            dealerInfo.getAsJsonObject("address").get("city").getAsString(),
                            dealerInfo.getAsJsonObject("address").get("stateName").getAsString(),
                            dealerInfo.getAsJsonObject("address").get("zipcode").getAsString()
                    );
                    CarInfoCard contactCard = new CarInfoCard(cxt,
                            null,
                            dealerInfo.get("name").getAsString(),
                            address,
                            R.drawable.card_bgd1
                    );

                    dealer_name_card.setCard(contactCard);

                    String operations = String.format("Monday:\t%s\nTuesday:\t%s\nWednesday:\t%s\nThursday:\t%s\nFriday:\t%s\nSaturday:\t%s\nSunday:\t%s\n",
                            dealerInfo.getAsJsonObject("operations").get("Monday").getAsString(),
                            dealerInfo.getAsJsonObject("operations").get("Tuesday").getAsString(),
                            dealerInfo.getAsJsonObject("operations").get("Wednesday").getAsString(),
                            dealerInfo.getAsJsonObject("operations").get("Thursday").getAsString(),
                            dealerInfo.getAsJsonObject("operations").get("Friday").getAsString(),
                            dealerInfo.getAsJsonObject("operations").get("Saturday").getAsString(),
                            dealerInfo.getAsJsonObject("operations").get("Sunday").getAsString()
                    );

                    CarInfoCard operationsCard = new CarInfoCard(cxt, null, null, operations, R.drawable.card_bgd2);

                    dealer_ops_card.setCard(operationsCard);


                    call_dealer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String number = "tel:" + dealerInfo.getAsJsonObject("contactInfo").get("phone").getAsString();
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                            startActivity(callIntent);
                        }
                    });

                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

    }

    public static DealerInfoFragment newInstance () {
        return new DealerInfoFragment();
    }

    @Override
    public void onCreate (Bundle savedBundle) {
        super.onCreate(savedBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate (R.layout.dealer_info, container, false);
        ButterKnife.bind(this, v);
        cxt = getActivity();
        return v;

    }


    @Override
    public void onStart () {
        super.onStart();
        if (!spiceManager.isStarted())
            spiceManager.start(cxt);
        String query_data = getArguments().getString(Constants.EXTRA_DEALERID);
        if (query_data != null) {
            spiceManager.execute(new DealershipRequest(query_data),
                    new DealerInfoListener());
        }
    }

    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
