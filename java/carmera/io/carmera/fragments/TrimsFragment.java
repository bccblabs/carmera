package carmera.io.carmera.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.TrimsViewerContainer;
import carmera.io.carmera.adapters.BetterRecyclerAdapter;
import carmera.io.carmera.adapters.TrimsAdapter;
import carmera.io.carmera.models.TrimData;

public class TrimsFragment extends Fragment {
    public static final String EXTRA_TRIMS_DATA = "extra_trims_data";

    public static TrimsFragment newInstance() {
        return new TrimsFragment();
    }

    @Bind(R.id.trims_recycler)
    RecyclerView trims_recycler;

    private TrimsAdapter trimsAdapter;

    Parcelable trimsDataListParcelable;
    List<TrimData> trimData;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle args = getArguments();
        this.trimsDataListParcelable = args.getParcelable(EXTRA_TRIMS_DATA);
        this.trimData = Parcels.unwrap(this.trimsDataListParcelable);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.trims, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        trimsAdapter = new TrimsAdapter();
        trimsAdapter.addAll(trimData);
        trimsAdapter.notifyDataSetChanged();
        trimsAdapter.setOnItemClickListener(new BetterRecyclerAdapter.OnItemClickListener<TrimData>() {
            @Override
            public void onItemClick(View v, TrimData item, int position) {
                Intent i = new Intent(getActivity(), TrimsViewerContainer.class);
                Bundle args = new Bundle();
                Parcelable trim_data = Parcels.wrap(item);
                args.putParcelable(TrimDetailsFragment.EXTRA_TRIM_DATA, trim_data);
                i.putExtras(args);
                startActivity(i);
            }
        });

        trims_recycler.setAdapter(trimsAdapter);
        trims_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        trims_recycler.setHasFixedSize(true);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), trims_recycler, null);
        if(savedInstanceState != null)
            trimsDataListParcelable = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_TRIMS_DATA));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_TRIMS_DATA, trimsDataListParcelable);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
