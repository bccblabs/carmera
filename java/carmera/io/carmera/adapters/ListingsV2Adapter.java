package carmera.io.carmera.adapters;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.ListingV2;
import carmera.io.carmera.widgets.SquareImageView;

/**
 * Created by bski on 7/15/15.
 */
public class ListingsV2Adapter extends BetterRecyclerAdapter<ListingV2, ListingsV2Adapter.ViewHolder> {


    private Context cxt;
    public String TAG = getClass().getCanonicalName();

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        try {
            ListingV2 listing = getItem(i);
            viewHolder.car_info.setText(String.format ("%d %s %s",   listing.getYear(),
                    listing.getSnapshot().getMake(),
                    listing.getSnapshot().getModel()
                            .replace(listing.getSnapshot().getMake(), "")
                            .replace("_", " ")
            ));
            if (listing.sellingprice != null)
                viewHolder.price.setText(String.format("$ %s", NumberFormat.getNumberInstance(Locale.US).format(listing.getSellingprice().intValue())));
            else
                viewHolder.price.setVisibility(View.GONE);
            if (listing.miles != null)
                viewHolder.mileage.setText(String.format("%s miles", NumberFormat.getNumberInstance(Locale.US).format (listing.getMiles())));
            else
                viewHolder.mileage.setVisibility(View.GONE);
            if (listing.dateinstock != null)
                viewHolder.listed_since.setText(String.format("Listed Since: %s", listing.getDateinstock()));
            else
                viewHolder.listed_since.setVisibility(View.GONE);
            viewHolder.dealer_name.setText(String.format("%s", listing.getDealername()));
            viewHolder.dealer_address.setText(String.format("%s, %s, %s", listing.getDealerAddress(),
                    listing.getDealerCity(),
                    listing.getDealerState()));
            List<String> urls = listing.getImagelist();
            if (urls.size() > 0) {
                Picasso.with(cxt)
                        .load(urls.get(0))
                        .resize(320, 240)
                        .into(viewHolder.photo);
            } else {
//                viewHolder.photo.setImageResource(R.drawable.carmera);
            }
        } catch (Exception e) {
//            viewHolder.photo.setImageResource(R.drawable.carmera);
            Log.e (TAG, e.getMessage());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.car_info)
        public TextView car_info;
        @Bind(R.id.photo)
        public SquareImageView photo;
        @Bind(R.id.price)
        public TextView price;
        @Bind(R.id.mileage)
        public TextView mileage;
        @Bind(R.id.listed_since)
        public TextView listed_since;
        @Bind(R.id.dealer_name)
        public TextView dealer_name;
        @Bind(R.id.dealer_address)
        public TextView dealer_address;
        public ViewHolder (View itemView) {
            super (itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
