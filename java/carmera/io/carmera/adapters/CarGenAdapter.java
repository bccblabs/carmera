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

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.Snapshot;
import carmera.io.carmera.utils.Util;
import carmera.io.carmera.widgets.SquareImageView;

/**
 * Created by bski on 7/13/15.
 */
public class CarGenAdapter extends BetterRecyclerAdapter<GenerationData, CarGenAdapter.ViewHolder> {

    public String TAG = getClass().getCanonicalName();
    private Context cxt;
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.gen_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        try {
            Snapshot snapshot = getItem(i).snapshot;
            viewHolder.gen_info.setText(snapshot.make + " " + snapshot.model + " " + snapshot.gen_name);
            viewHolder.hp.setText(Util.getRangeText(snapshot.hp_min, snapshot.hp_max) + " HP");
            viewHolder.tq.setText(Util.getRangeText(snapshot.tq_min, snapshot.tq_max) + " LB/FT");
            viewHolder.city_mpg.setText(Util.getRangeText(snapshot.city_mpg_min, snapshot.city_mpg_max) + " MPG");
            viewHolder.hwy_mpg.setText(Util.getRangeText(snapshot.hwy_mpg_min, snapshot.hwy_mpg_max) + " MPG");
            viewHolder.msrp.setText((Util.getRangeText(snapshot.msrp_min, snapshot.msrp_max)));
            viewHolder.tmv.setText(Util.getRangeText(snapshot.used_tmv_min, snapshot.used_tmv_max));
            viewHolder.drivetrain.setText(String.format(Util.joinStrings(snapshot.getDrivenWheelsList(), ", ").toUpperCase()));

            String base_url = this.cxt.getResources().getString(R.string.edmunds_baseurl);
            Picasso.with(cxt)
                    .load(base_url + snapshot.image_holder)
                    .placeholder(R.drawable.placeholder) //
                    .error(R.drawable.error) //
                    .centerCrop()
                    .fit()
                    .into(viewHolder.photo);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gen_info)
        public TextView gen_info;
        @Bind(R.id.photo)
        public ImageView photo;
        @Bind(R.id.hp)
        public TextView hp;
        @Bind(R.id.tq)
        public TextView tq;
        @Bind(R.id.city_mpg)
        public TextView city_mpg;
        @Bind(R.id.hwy_mpg)
        public TextView hwy_mpg;
        @Bind(R.id.msrp)
        public TextView msrp;
        @Bind(R.id.tmv)
        public TextView tmv;
        @Bind(R.id.drivetrain)
        public TextView drivetrain;

        public ViewHolder (View itemView) {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }
}
