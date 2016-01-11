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
import carmera.io.carmera.listeners.OnMakeSelectedListener;
import carmera.io.carmera.listeners.OnSeeAllModelsListener;
import carmera.io.carmera.models.queries.MakeQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class MakesAdapter extends BetterRecyclerAdapter<MakeQuery, MakesAdapter.ViewHolder> {
    private Context cxt;
    private OnMakeSelectedListener onMakeSelectedListener = null;
    private OnSeeAllModelsListener onSeeAllModelsListener = null;
    public MakesAdapter (OnMakeSelectedListener listener, OnSeeAllModelsListener l1) {
        onMakeSelectedListener = listener;
        onSeeAllModelsListener = l1;

    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.make_query_item, parent, false);
        this.cxt = parent.getContext();
        return new MakesAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        final MakeQuery makeQuery = getItem(i);
        viewHolder.make_name.setText(makeQuery.make);
        viewHolder.make_subtitle.setText(String.format("%d Models Found", makeQuery.numModels));
        viewHolder.add_make_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMakeSelectedListener.OnMakeSelected(makeQuery.make);
            }
        });
        viewHolder.see_all_make_models.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeeAllModelsListener.OnSeeAllModelsCallback(makeQuery.make, makeQuery.models);
            }
        });
        try {
            Picasso.with(cxt).load (Constants.EdmundsMedia + makeQuery.imageUrl.replace("_150.", "_300.")).into(viewHolder.make_image_holder);
        } catch (Exception e) {
            Picasso.with(cxt).load (R.drawable.carmera_small).into(viewHolder.make_image_holder);
            Log.e(this.getClass().getCanonicalName(), e.getMessage());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.make_name) public TextView make_name;
        @Bind(R.id.make_subtitle) public TextView make_subtitle;
        @Bind(R.id.make_image_holder) public ImageView make_image_holder;
        @Bind(R.id.add_make_to_search) public ButtonFlat add_make_to_search;
        @Bind(R.id.see_all_make_models) public ButtonFlat see_all_make_models;
        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
