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
import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.Snapshot;
import carmera.io.carmera.utils.Util;
import carmera.io.carmera.widgets.SquareImageView;

/**
 * Created by bski on 7/13/15.
 */
public class CarGenAdapter extends BetterRecyclerAdapter<GenerationData, CarGenAdapter.ViewHolder> {

    private Context cxt;
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.car_gen_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        Snapshot snapshot = getItem(i).snapshot;
        viewHolder.gen_info.setText(snapshot.gen_name + snapshot.make + snapshot.model);
        viewHolder.hp.setText(String.format("Horsepower: %d - %d hp", snapshot.hp_min, snapshot.hp_max));
        viewHolder.tq.setText(String.format("Torque: %d - %d lb/ft", snapshot.tq_min, snapshot.tq_max));
        viewHolder.city_mpg.setText(String.format("City MPG: %s - %s", snapshot.city_mpg_min, snapshot.city_mpg_max));
        viewHolder.hwy_mpg.setText(String.format("Highway MPG: %s - %s", snapshot.hwy_mpg_min, snapshot.hwy_mpg_max));
        viewHolder.msrp.setText(String.format("MSRP: $%d - %d", snapshot.msrp_min, snapshot.msrp_max));
        viewHolder.tmv.setText(String.format("Used: $%d - %d", snapshot.used_tmv_min, snapshot.used_tmv_max));
        viewHolder.drivetrain.setText(String.format("Available in %s", Util.joinStrings(snapshot.getDrivenWheelsList(), ", ") ));
        String base_url = this.cxt.getResources().getString(R.string.edmunds_baseurl);
        Picasso.with(cxt)
                .load(base_url + snapshot.image_holder)
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit()
                .into(viewHolder.photo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gen_info)
        public TextView gen_info;
        @Bind(R.id.photo)
        public SquareImageView photo;
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
