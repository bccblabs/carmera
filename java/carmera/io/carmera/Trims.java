package carmera.io.carmera;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.TrimsAdapter;
import carmera.io.carmera.models.GenerationData;
import carmera.io.carmera.models.TrimData;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 7/13/15.
 */
public class Trims extends ActionBarActivity {
    public static final String EXTRA_GEN_DATA = "extra_gen_data";

    @Bind(R.id.trims_recycler)
    RecyclerView trims_recycler;

    private TrimsAdapter trimsAdapter;

    GenerationData generationData;
    Parcelable generationDataParcelable;

    public void onCreate (Bundle savedBundleInst) {
        super.onCreate(savedBundleInst);
        setContentView (R.layout.trims);
        ButterKnife.bind(this);

        SlidrConfig config = new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)
                .touchSize(Util.dpToPx(this, 32))
                .build();

        Slidr.attach(this, config);
        this.generationDataParcelable = getIntent().getExtras().getParcelable(EXTRA_GEN_DATA);
        generationData = Parcels.unwrap(this.generationDataParcelable);

        trimsAdapter = new TrimsAdapter();
        trims_recycler.setAdapter(trimsAdapter);
        trims_recycler.setLayoutManager(new LinearLayoutManager(this));
        trims_recycler.setHasFixedSize(true);

        trimsAdapter.addAll(generationData.getTrims());
        trimsAdapter.notifyDataSetChanged();
        trimsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<TrimData>() {
            @Override
            public void onItemClick(View v, TrimData item, int position) {
                Toast.makeText(Trims.this, item.getStyleIds().toString(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent (getApplicationContext(), TrimDetailsViewer.class);
                Bundle args = new Bundle();
                Parcelable trim_data = Parcels.wrap(item);
                args.putParcelable(TrimDetailsViewer.EXTRA_TRIM_DATA, trim_data);
                i.putExtras(args);
                startActivity(i);

            }
        });


        if(savedBundleInst != null)
            generationData = Parcels.unwrap(savedBundleInst.getParcelable(EXTRA_GEN_DATA));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_GEN_DATA, generationDataParcelable);
    }
}
