package carmera.io.carmera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnAddModelListener;
import carmera.io.carmera.listeners.OnSeeAllModelsListener;
import carmera.io.carmera.listeners.OnSeeModelListingsListener;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.models.queries.ModelQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 12/19/15.
 */
public class ModelsAdapter extends BetterRecyclerAdapter<ModelQuery, ModelsAdapter.ViewHolder> {
    private Context cxt;
    private OnAddModelListener onAddModelListener;
    private OnSeeModelListingsListener onSeeModelListingsListener;


    public ModelsAdapter (OnAddModelListener l0, OnSeeModelListingsListener l1) {
        onAddModelListener = l0;
        onSeeModelListingsListener = l1;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_query_item, parent, false);
        this.cxt = parent.getContext();
        return new ModelsAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        final ModelQuery modelQuery = getItem(i);
        Util.setText(viewHolder.model_desc, modelQuery.model);
        Util.setText(viewHolder.years_desc, modelQuery.yearDesc);
        Util.setText(viewHolder.perf_desc, modelQuery.powerDesc);
        Util.setText(viewHolder.mpg_desc, modelQuery.mpgDesc);
        try {
            Picasso.with(cxt).load (Constants.EdmundsMedia + modelQuery.imageUrl.replace("_150.", "_300.")).into(viewHolder.model_image_holder);
        } catch (Exception e) {
            Picasso.with(cxt).load (R.drawable.carmera_small).into(viewHolder.model_image_holder);
            Log.e(this.getClass().getCanonicalName(), e.getMessage());
        }

        viewHolder.add_model_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddModelListener.onModelAddedCallback(modelQuery.model);
            }
        });

        viewHolder.see_model_listings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSeeModelListingsListener.OnSeeModels(modelQuery.styleIds);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.model_desc) public TextView model_desc;
        @Bind(R.id.years_desc) public TextView years_desc;
        @Bind(R.id.perf_desc) public TextView perf_desc;
        @Bind(R.id.mpg_desc) public TextView mpg_desc;
        @Bind(R.id.model_image_holder) public ImageView model_image_holder;

        @Bind(R.id.add_model) public ButtonFlat add_model_btn;
        @Bind(R.id.see_model_listings) public ButtonFlat see_model_listings;
        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
