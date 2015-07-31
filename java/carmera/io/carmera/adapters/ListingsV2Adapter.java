package carmera.io.carmera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

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

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        ListingV2 listing = getItem(i);
        viewHolder.car_info.setText(String.format ("%d %s %s %s",   listing.getYear(),
                                                                    listing.getSnapshot().getMake(),
                                                                    listing.getSnapshot().getModel(),
                                                                    listing.getSnapshot().getTrim()));

        viewHolder.price.setText(String.format("MSRP: %.0f", listing.getSellingprice()));
        viewHolder.mileage.setText(String.format("Mileage: %d", listing.getMiles()));
        viewHolder.listed_since.setText(String.format("Listed Since: %s", listing.getDateinstock()));
        viewHolder.dealer_name.setText(String.format("%s", listing.getDealername()));
        viewHolder.dealer_address.setText(String.format("%s, %s, %s", listing.getDealerAddress(),
                                                                      listing.getDealerCity(),
                                                                      listing.getDealerState()));

        List<String> urls = listing.getImagelist();
            if (urls.size() > 0) {
                Picasso.with(cxt)
                        .load(urls.get(0))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                        .centerCrop()
                        .fit()
                        .into(viewHolder.photo);
            } else {
                viewHolder.photo.setImageResource(R.drawable.carmera);
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
