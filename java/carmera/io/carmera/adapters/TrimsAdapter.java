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
import carmera.io.carmera.models.Engine;
import carmera.io.carmera.models.Mpg;
import carmera.io.carmera.models.TrimData;
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
        Engine engine = trimData.getEngine();
        Mpg mpg = trimData.getMpg();
        viewHolder.gen_info.setText(String.format ("%s %s %s", trimData.make, trimData.model, trimData.trim));
        viewHolder.hp.setText(String.format("Horsepower: %d hp", engine.horsepower));
        viewHolder.tq.setText(String.format("Torque: %d lb/ft", engine.torque));
        viewHolder.city_mpg.setText(String.format("City MPG: %d ", mpg.city));
        viewHolder.hwy_mpg.setText(String.format("Highway MPG: %d ", mpg.highway));
        viewHolder.msrp.setText(String.format("MSRP: $%d - %d", trimData.min_msrp, trimData.max_msrp));
        viewHolder.tmv.setText(String.format("Used: $%d - %d", trimData.min_used_tmv, trimData.max_used_tmv));
        viewHolder.drivetrain.setText(String.format("Available as %s", trimData.drivenWheels));

        String base_url = this.cxt.getResources().getString(R.string.edmunds_baseurl);
        if (trimData.getImages().getExterior().size() > 0) {
            Picasso.with(cxt)
                    .load(base_url + trimData.getImages().getExterior().get(0))
                    .placeholder(R.drawable.placeholder) //
                    .error(R.drawable.error) //
                    .centerCrop()
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