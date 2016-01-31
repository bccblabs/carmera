package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import carmera.io.carmera.R;
import carmera.io.carmera.cards.StaggeredSearchFragmentCard;
import carmera.io.carmera.listeners.OnEditCompressors;
import carmera.io.carmera.listeners.OnEditCylinders;
import carmera.io.carmera.listeners.OnEditDriveTrain;
import carmera.io.carmera.listeners.OnEditHp;
import carmera.io.carmera.listeners.OnEditMakes;
import carmera.io.carmera.listeners.OnEditMpg;
import carmera.io.carmera.listeners.OnEditSort;
import carmera.io.carmera.listeners.OnEditTags;
import carmera.io.carmera.listeners.OnEditTransmission;
import carmera.io.carmera.listeners.OnSearchFragmentVisible;
import it.gmariotti.cardslib.library.extra.staggeredgrid.internal.CardGridStaggeredArrayAdapter;
import it.gmariotti.cardslib.library.extra.staggeredgrid.view.CardGridStaggeredView;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by bski on 1/13/16.
 */
public class StaggeredSearchFragment extends Fragment {

    private Context cxt;
    private OnSearchFragmentVisible onSearchFragmentVisible;

    private OnEditTags onEditTagsListener;
    private OnEditMakes onEditMakes;
    private OnEditDriveTrain onEditDriveTrain;
    private OnEditHp onEditHp;
    private OnEditMpg onEditMpg;
    private OnEditCompressors onEditCompressors;
    private OnEditTransmission onEditTransmission;
    private OnEditCylinders onEditCylinders;

    private CardGridStaggeredArrayAdapter cardGridStaggeredArrayAdapter;
    private CardGridStaggeredView cardGridStaggeredView;
    private StaggeredSearchFragmentCard staggeredSearchFragmentCard;

    public static StaggeredSearchFragment newInstance () {
        return new StaggeredSearchFragment();
    }

    private void showDialog () {
        Toast.makeText(getActivity(), "added to filters!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_initial_search_grid, container, false);
        cxt = getActivity();


        List<Card> cards;
        /* Cost Saver */
        /* incentives */
        cards = new ArrayList<>();
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.incentivized_search), R.drawable.staggered_grid_incentives);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditTagsListener.OnEditTagCallback("has incentives");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* 40+ mpg */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.efficient), R.drawable.staggered_grid_40mpg);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditMpg.OnEditMpgCallback(40);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* low insurance */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.low_insurance), R.drawable.staggered_grid_lowinsurance);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] tags = cxt.getResources().getStringArray(R.array.cheap_insurance_tags);
                onEditTagsListener.OnEditTagsCallback(tags);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* electric */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.electric_search), R.drawable.staggered_grid_electric);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditTagsListener.OnEditTagCallback("electric");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* low repairs */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.low_repairs_search), R.drawable.staggered_grid_lowrepairs);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] tags = getResources().getStringArray(R.array.cheap_repairs_tags);
                onEditTagsListener.OnEditTagsCallback(tags);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* low depreciation */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.low_depreciation), R.drawable.staggered_grid_lowdepreciation);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] tags = cxt.getResources().getStringArray(R.array.cheap_depreciation_tags);
                onEditTagsListener.OnEditTagsCallback(tags);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        cardGridStaggeredView = (CardGridStaggeredView) v.findViewById(R.id.low_cost_grid);
        cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(cxt, cards);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();

        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }

        /* Imports */
        /* british */
        cards = new ArrayList<>();
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.british_search), R.drawable.staggered_grid_eng);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] europeanMakes = cxt.getResources().getStringArray(R.array.british_makes);
                onEditMakes.OnEditMakesCallback(europeanMakes);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* german */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.german_search), R.drawable.staggered_grid_ger);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] europeanMakes = cxt.getResources().getStringArray(R.array.german_makes);
                onEditMakes.OnEditMakesCallback(europeanMakes);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* italian */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.italian_search), R.drawable.staggered_grid_ita);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] europeanMakes = cxt.getResources().getStringArray(R.array.italian_makes);
                onEditMakes.OnEditMakesCallback(europeanMakes);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* japanese */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.japanese_search), R.drawable.staggered_grid_jap);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] japaneseMakes = cxt.getResources().getStringArray(R.array.japanese_makes);
                onEditMakes.OnEditMakesCallback(japaneseMakes);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        cardGridStaggeredView = (CardGridStaggeredView) v.findViewById(R.id.imports_grid);
        cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(cxt, cards);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }

        /* Powertrain */
        /* awd */
        cards = new ArrayList<>();
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.four_wheel_drive_search), R.drawable.staggered_grid_awd);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditDriveTrain.OnEditDriveTrainCallback("all wheel drive");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* rear wheel drive */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.rear_wheel_drive_search), R.drawable.staggered_grid_rwd);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditDriveTrain.OnEditDriveTrainCallback("rear wheel drive");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* 300+ hp */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.seven_seats), R.drawable.staggered_grid_seats);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditTagsListener.OnEditTagCallback("seven seats");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* 500+ hp */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.high_hp), R.drawable.staggered_search_highhp);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditHp.OnEditHpCallback(400);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* supercharger */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.supercharger_search), R.drawable.staggered_grid_supercharged);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditCompressors.OnEditCompressorCallback("supercharger");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* turbo */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.turbo_search), R.drawable.staggered_grid_turbo);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditCompressors.OnEditCompressorCallback("Turbo");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* manual */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.manual), R.drawable.staggered_grid_manual);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditTransmission.addTransmissionType("manual");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* cylinders */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.many_cylinders), R.drawable.staggered_grid_8cyl);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditCylinders.onSetMinCylinders(8);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        cardGridStaggeredView = (CardGridStaggeredView) v.findViewById(R.id.powertrain_grid);
        cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(cxt, cards);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }




        /* Reliable & Safe */
        /* no recalls */
        cards = new ArrayList<>();
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.no_recalls_search), R.drawable.staggered_grid_norecalls);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                String[] reliableTags = cxt.getResources().getStringArray(R.array.no_recalls_tags);
                onEditTagsListener.OnEditTagsCallback(reliableTags);
            }
        });
        cards.add(staggeredSearchFragmentCard);
        /* top safety */
        staggeredSearchFragmentCard = new StaggeredSearchFragmentCard(cxt, cxt.getResources().getString(R.string.top_safety_search), R.drawable.staggered_grid_topsafety);
        staggeredSearchFragmentCard.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                showDialog();
                onEditTagsListener.OnEditTagCallback("top safety");
            }
        });
        cards.add(staggeredSearchFragmentCard);
        cardGridStaggeredView = (CardGridStaggeredView) v.findViewById(R.id.reliability_grid);
        cardGridStaggeredArrayAdapter = new CardGridStaggeredArrayAdapter(cxt, cards);
        cardGridStaggeredArrayAdapter.notifyDataSetChanged();
        if (cardGridStaggeredView != null) {
            cardGridStaggeredView.setAdapter(cardGridStaggeredArrayAdapter);
        }

        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onSearchFragmentVisible.SetFabVisible();
        }

    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        try {
            cxt = getContext();
            onSearchFragmentVisible = (OnSearchFragmentVisible) activity;
            onEditMakes = (OnEditMakes) activity;
            onEditTagsListener = (OnEditTags) activity;
            onEditDriveTrain = (OnEditDriveTrain) activity;
            onEditHp = (OnEditHp) activity;
            onEditMpg = (OnEditMpg) activity;
            onEditCompressors = (OnEditCompressors) activity;
            onEditTransmission = (OnEditTransmission) activity;
            onEditCylinders = (OnEditCylinders) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

}
