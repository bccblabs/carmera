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
import carmera.io.carmera.models.TrimData;
import carmera.io.carmera.utils.Util;
import carmera.io.carmera.widgets.SquareImageView;

/**
 * Created by bski on 7/13/15.
 */
public class TrimsAdapter extends BetterRecyclerAdapter<TrimData, TrimsAdapter.ViewHolder>{

    private Context cxt;
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_gen_item, parent, false);
        this.cxt = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        TrimData trimData = getItem(i);
        viewHolder.gen_info.setText(String.format ("%s %s %s", trimData.getMake(), trimData.getModel(), trimData.getGen_name()));
        viewHolder.hp.setText(String.format("Horsepower: %d - %d hp", trimData.getMin_hp(), trimData.getMax_hp()));
        viewHolder.tq.setText(String.format("Torque: %d - %d lb/ft", trimData.getMin_tq(), trimData.getMax_tq()));
        viewHolder.city_mpg.setText(String.format("City MPG: %s - %s", trimData.getMin_city_mpg(), trimData.getMax_city_mpg()));
        viewHolder.hwy_mpg.setText(String.format("Highway MPG: %s - %s", trimData.getMin_hwy_mpg(), trimData.getMax_hwy_mpg()));
        viewHolder.msrp.setText(String.format("MSRP: $%d - %d", trimData.getMin_msrp(), trimData.getMax_msrp()));
        viewHolder.tmv.setText(String.format("Used: $%d - %d", trimData.getMin_used_tmv(), trimData.getMax_used_tmv()));
        viewHolder.drivetrain.setText(String.format("Available in %s", Util.joinStrings(trimData.getDrivenWheelsList(), ",") ) );

        String base_url = this.cxt.getResources().getString(R.string.edmunds_baseurl);
        if (trimData.getImages().getExterior().size() > 0) {
            Picasso.with(cxt)
                    .load(base_url + trimData.getImages().getExterior().get(0))
                    .placeholder(R.drawable.placeholder) //
                    .error(R.drawable.error) //
                    .fit()
                    .into(viewHolder.photo);
        } else {
            viewHolder.photo.setImageResource(R.drawable.carmera);
        }
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
            ButterKnife.bind(this, itemView);
        }
    }
}
