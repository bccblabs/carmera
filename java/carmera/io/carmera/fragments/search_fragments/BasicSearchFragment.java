package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.MakesSearchActivity;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnEditBodyTypes;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class BasicSearchFragment extends Fragment {

    public String TAG = getClass().getCanonicalName();
    private Context cxt;
    private OnEditBodyTypes onEditBodyTypes;

    @Bind(R.id.coupe_search) View coupe_search;
    @Bind(R.id.convertible_search) View convertible_search;
    @Bind(R.id.sedan_search) View sedan_search;
    @Bind(R.id.suv_search) View suv_search;
    @Bind(R.id.truck_search) View truck_search;
    @Bind(R.id.van_search) View van_search;
    @Bind(R.id.wagon_search) View wagon_search;

    @OnClick(R.id.add_coupe)
    void addCoupe () {
        onEditBodyTypes.OnEditBodyTypeCallback("coupe");
    }

    @OnClick(R.id.add_convertible)
    void addConvertible () {
        onEditBodyTypes.OnEditBodyTypeCallback("convertible");
    }

    @OnClick(R.id.add_sedan)
    void addSedan () {
        onEditBodyTypes.OnEditBodyTypeCallback("sedan");
    }

    @OnClick(R.id.add_suv)
    void addSuv () {
        onEditBodyTypes.OnEditBodyTypeCallback("suv");
    }

    @OnClick(R.id.add_truck)
    void addTruck () {
        onEditBodyTypes.OnEditBodyTypeCallback("truck");
    }

    @OnClick(R.id.add_van)
    void addVan () {
        onEditBodyTypes.OnEditBodyTypeCallback("van");
    }

    @OnClick(R.id.add_wagon)
    void addWagon () {
        onEditBodyTypes.OnEditBodyTypeCallback("wagon");
    }


    public static BasicSearchFragment newInstance () {
        return new BasicSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bodytype_search, container, false);
        ButterKnife.bind(this, v);
        coupe_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("Coupe");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        convertible_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("Convertible");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        sedan_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("Sedan");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        suv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("SUV");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        truck_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("Truck");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        van_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("Van");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        wagon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.bodyTypes.add("Wagon");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        return v;

    }
    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }


    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            cxt = getContext();
            onEditBodyTypes = (OnEditBodyTypes) activity;
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }
}