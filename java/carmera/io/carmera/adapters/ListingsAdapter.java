package carmera.io.carmera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.listings_subdocuments.Media;
import carmera.io.carmera.models.listings_subdocuments.Photo;
import carmera.io.carmera.models.listings_subdocuments.Photos;
import carmera.io.carmera.widgets.SquareImageView;

/**
 * Created by bski on 7/15/15.
 */
public class ListingsAdapter extends BetterRecyclerAdapter<Listing, ListingsAdapter.ViewHolder> {


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
        Listing listing = getItem(i);
        viewHolder.car_info.setText(String.format ("%d %s %s %s",   listing.getYear().getYear(),
                listing.getMake().getName(),
                listing.getModel().getName(),
                listing.getStyle().getTrim()));

        if (listing.getPrices().getMsrp() > 0.0f)
            viewHolder.price.setText(String.format("MSRP: %.0f", listing.getPrices().getMsrp()));
        else
            viewHolder.price.setVisibility(View.GONE);
        viewHolder.mileage.setText(String.format("Mileage: %d", listing.getMileage()));
        if (listing.getListedSince() != null)
            viewHolder.listed_since.setText(String.format("Listed Since: %s", listing.getListedSince()));
        else
            viewHolder.listed_since.setVisibility(View.GONE);
        viewHolder.dealer_name.setText(String.format("%s", listing.getDealer().getName()));
        viewHolder.dealer_address.setText(String.format("%s, %s, %s", listing.getDealer().getAddress().getStreet(),
                listing.getDealer().getAddress().getCity(),
                listing.getDealer().getAddress().getStateName()));

        try {
            Media media = listing.getMedia();
            if (media != null) {
                Photos photos = media.getPhotos();
                if (photos != null) {
                    Photo thumbnails = photos.getLarge();
                    if (thumbnails.getCount() > 0) {
                        Picasso.with(cxt)
                                .load(thumbnails.getLinks().get(0).getHref())
                                .resize(320, 240)
                                .into(viewHolder.photo);
                    } else {
                        viewHolder.photo.setImageResource(R.drawable.carmera);
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
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