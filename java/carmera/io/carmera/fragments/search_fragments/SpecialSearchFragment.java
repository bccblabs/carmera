package carmera.io.carmera.fragments.search_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.MakesSearchActivity;
import carmera.io.carmera.R;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class SpecialSearchFragment extends Fragment {
    public String TAG = getClass().getCanonicalName();
    @Bind (R.id.incentivized_search) View incentivized_search;
    @Bind (R.id.super_sport_search) View super_sport_search;
    @Bind (R.id.green_search) View green_search;
    @Bind (R.id.four_wheel_drive_search) View four_wheel_drive_search;
    @Bind (R.id.boosted_search) View boosted_search;
    @Bind (R.id.reliable_search) View reliable_search;
    @Bind (R.id.top_safety_search) View top_safety_search;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.special_search, container, false);
        ButterKnife.bind(this, v);
        incentivized_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.add("Has Incentives");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        super_sport_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.minHp = 400;
                listingsQuery.car.bodyTypes.add("Coupe");
                listingsQuery.car.bodyTypes.add("Convertible");
                listingsQuery.car.bodyTypes.add("Sedan");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        green_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.minMpg = 40;
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        four_wheel_drive_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.drivenWheels.add("four wheel drive");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        boosted_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.compressors.add("Turbo");
                listingsQuery.car.compressors.add("Supercharger");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        reliable_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.add("No Recalls");
                listingsQuery.car.tags.add("No Major Complaints");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });


        top_safety_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.add("Top Safety");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        return v;
    }

    public static SpecialSearchFragment newInstance () {
        return new SpecialSearchFragment();
    }

    @Override
    public void onViewCreated (View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
