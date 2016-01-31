package carmera.io.carmera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.listings_subdocuments.Link;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 7/15/15.
 */
public class ListingsAdapter extends BetterRecyclerAdapter<Listing, ListingsAdapter.ViewHolder> {

    private Context cxt;
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_listing_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        final Listing listing = getItem(i);
        NumberFormat milefmt = NumberFormat.getIntegerInstance(Locale.US);
        viewHolder.car_info.setText(String.format("%d %s %s\n%s", listing.getYear().getYear(),
                listing.getMake().getName(),
                listing.getModel().getName(),
                listing.getStyle().getTrim()));

        if (listing.getPrices() != null) {
            Util.setText(viewHolder.price, Util.formatCurrency(listing.getMin_price()));
        }
        if (listing.getMileage() > 0) {
            Util.setText(viewHolder.mileage, String.format("%s miles", milefmt.format(listing.getMileage())));
        }

        if (listing.getListedSince() != null) {
            String desc = "Listed Since " + listing.listedSince;
            viewHolder.listed_since.setText(desc);
        }
        else
            viewHolder.listed_since.setVisibility(View.GONE);



        try {
            List<Link> links = listing.getMedia().getPhotos().getLarge().getLinks();
            if (links.size() > 0) {
                Picasso.with(cxt).load (links.get(0).getHref()).fit().into(viewHolder.listingImage);
            }
        } catch (Exception e) {
            viewHolder.image_layout.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.listed_since) public TextView listed_since;
        @Bind(R.id.car_info_trim) public TextView car_info;
        @Bind(R.id.listing_photo) public ImageView listingImage;
        @Bind(R.id.listing_price) public TextView price;
        @Bind(R.id.listing_mileage) public TextView mileage;
        @Bind(R.id.image_layout) public View image_layout;
        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}