package carmera.io.carmera.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;

/**
 * Created by bski on 8/2/15.
 */
public class ReviewAdapter extends BetterRecyclerAdapter<String, ReviewAdapter.ViewHolder> {

    private Context context;

    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_text_item, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        String review = getItem(i);
        viewHolder.review_text.setText(String.format("\"%s\"\n\n", review));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.review_text)
        public TextView review_text;
        public ViewHolder (View itemView) {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }
}
