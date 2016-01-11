package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.afollestad.materialdialogs.MaterialDialog;
import org.parceler.Parcels;
import java.util.Arrays;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.MakesSearchActivity;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnEditMakes;
import carmera.io.carmera.listeners.OnEditTags;
import carmera.io.carmera.listeners.OnSearchFragmentVisible;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 12/18/15.
 */
public class SpecialSearchFragment extends Fragment {
    @Bind (R.id.see_incentives) View incentivized_search;
    @Bind (R.id.see_low_depreciation) View low_depr_search;
    @Bind (R.id.see_low_insurance) View low_insurance_search;
    @Bind (R.id.see_low_repairs) View low_repairs_search;
    @Bind (R.id.see_european) View european_search;
    @Bind (R.id.see_reliable) View reliable_search;
    @Bind (R.id.see_safety) View top_safety_search;

    private Context cxt;
    private OnEditTags onEditTagsListener;
    private OnEditMakes onEditMakes;
    private OnSearchFragmentVisible onSearchFragmentVisible;

    @OnClick(R.id.add_incentives)
    void addIncentives () {
        showDialog();
        onEditTagsListener.OnEditTagCallback("Has Incentives");
    }

    @OnClick(R.id.add_low_repairs)
    void addRepairs () {
        showDialog();
        onEditTagsListener.OnEditTagsCallback(cxt.getResources().getStringArray(R.array.cheap_repairs_tags));
    }

    @OnClick(R.id.add_low_depreciation)
    void addDepreciation () {
        showDialog();
        onEditTagsListener.OnEditTagsCallback(cxt.getResources().getStringArray(R.array.cheap_depreciation_tags));
    }

    @OnClick(R.id.add_low_insurance)
    void addInsurance () {
        showDialog();
        onEditTagsListener.OnEditTagsCallback(cxt.getResources().getStringArray(R.array.cheap_insurance_tags));
    }

    @OnClick(R.id.add_european)
    void addEuropean () {
        showDialog();
        String[] europeanMakes = cxt.getResources().getStringArray(R.array.european_makes);
        onEditMakes.OnEditMakesCallback(europeanMakes);
    }


    @OnClick(R.id.add_reliable)
    void addReliable() {
        showDialog();
        String [] reliableTags = cxt.getResources().getStringArray(R.array.reliable_tags);
        onEditTagsListener.OnEditTagsCallback(reliableTags);
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onSearchFragmentVisible.SetFabVisible();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.special_search, container, false);
        ButterKnife.bind(this, v);
        incentivized_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.add("Has Incentives");
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        low_depr_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.addAll(Arrays.asList(cxt.getResources().getStringArray(R.array.cheap_depreciation_tags)));
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        low_insurance_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.addAll(Arrays.asList(cxt.getResources().getStringArray(R.array.cheap_insurance_tags)));
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        low_repairs_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.addAll(Arrays.asList(cxt.getResources().getStringArray(R.array.cheap_repairs_tags)));
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });
        european_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.makes.addAll(Arrays.asList(cxt.getResources().getStringArray(R.array.european_makes)));
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });

        reliable_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
                ListingsQuery listingsQuery = new ListingsQuery();
                listingsQuery.car.tags.addAll (Arrays.asList(cxt.getResources().getStringArray(R.array.reliable_tags)));
                i.putExtra(Constants.EXTRA_LISTING_QUERY, Parcels.wrap(ListingsQuery.class, listingsQuery));
                startActivityForResult(i, 1);
            }
        });


        top_safety_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, MakesSearchActivity.class);
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


    private void showDialog () {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Search Filters")
                .content("New Criteria Added!")
                .positiveText("OK")
                .show();
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
            onSearchFragmentVisible = (OnSearchFragmentVisible) activity;
            onEditMakes = (OnEditMakes) activity;
            onEditTagsListener = (OnEditTags) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

}
