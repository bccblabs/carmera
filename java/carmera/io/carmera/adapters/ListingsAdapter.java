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
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.Listing;
import carmera.io.carmera.models.listings_subdocuments.Link;
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
        viewHolder.car_info.setText(String.format("%d %s %s",   listing.getYear().getYear(),
                                                                listing.getMake().getName(),
                                                                listing.getModel().getName()));

        viewHolder.trim_info.setText(listing.getStyle().getTrim());
        if (listing.getPrices() != null)
            viewHolder.price.setText(String.format("$%.0f", listing.getMin_price()));
        else
            viewHolder.price.setVisibility(View.GONE);
        viewHolder.mileage.setText(String.format("%d Miles", listing.getMileage()));

        try {
            List<Link> links = listing.getMedia().getPhotos().getLarge().getLinks();
            if (links.size() > 0) {
                Picasso.with(cxt).load (links.get(0).getHref()).into(viewHolder.listingImage);
                Log.i(this.getClass().getCanonicalName(), " image url: " + links.get(0).getHref());
            }
        } catch (Exception e) {
            Picasso.with(cxt).load (R.drawable.carmera_small).into(viewHolder.listingImage);
            Log.e(this.getClass().getCanonicalName(), e.getMessage());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.car_info) public TextView car_info;
        @Bind(R.id.trim_info) public TextView trim_info;
        @Bind(R.id.listing_photo) public ImageView listingImage;
        @Bind(R.id.price) public TextView price;
        @Bind(R.id.mileage) public TextView mileage;
        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}