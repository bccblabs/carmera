package carmera.io.carmera.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;

import java.text.NumberFormat;
import java.util.Locale;
import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.DealerInfo;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 1/30/16.
 */
public class DealersAdapter extends BetterRecyclerAdapter<DealerInfo, DealersAdapter.ViewHolder> {

    private Context cxt;
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_dealer_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        final DealerInfo dealerInfo = getItem(i);
        Util.setText(viewHolder.dealer_name, dealerInfo.getName());
        Util.setText(viewHolder.dealer_address, String.format("%s, %s, %s",
                dealerInfo.getAddress().getStreet(),
                dealerInfo.getAddress().getCity(),
                dealerInfo.getAddress().getStateName()));

        viewHolder.sales_ratings.setText(dealerInfo.getReviews().getSales().getOverallRating() + " / 5");
        viewHolder.service_ratings.setText(dealerInfo.getReviews().getService().getOverallRating() + " / 5");
        try {
            viewHolder.call_dealer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = "tel:" + dealerInfo.getContactInfo().getPhone();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                    String permission = "android.permission.CALL_PHONE";
                    int res = cxt.checkCallingOrSelfPermission(permission);
                    if (res == PackageManager.PERMISSION_GRANTED)
                        DealersAdapter.this.cxt.startActivity(callIntent);
                }
            });
        } catch (SecurityException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.dealer_name) public TextView dealer_name;
        @Bind(R.id.dealer_address) public TextView dealer_address;
        @Bind(R.id.sales_ratings) public TextView sales_ratings;
        @Bind(R.id.service_ratings) public TextView service_ratings;
        @Bind(R.id.call_dealer) public ButtonFlat call_dealer;
        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
