package carmera.io.carmera.fragments.search_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import org.parceler.Parcels;
import org.parceler.guava.collect.Lists;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.listeners.OnResearchListener;
import carmera.io.carmera.models.ListingsQuery;
import carmera.io.carmera.utils.Constants;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 11/17/15.
 */
public class SortFragment extends DialogFragment {

    private ListingsQuery listingsQuery;

    private OnResearchListener callback = null;

    @Bind(R.id.sort_btn) ButtonFlat sort_btn;

    @Bind (R.id.price_sort_spinner)
    MultiSpinner price_sort_spinner;

    @Bind (R.id.mileage_sort_spinner)
    MultiSpinner mileage_sort_spinner;

    @Bind (R.id.year_sort_spinner)
    MultiSpinner year_sort_spinner;

    @Bind (R.id.mpg_sort_spinner)
    MultiSpinner mpg_sort_spinner;

    @Bind (R.id.hp_sort_spinner)
    MultiSpinner hp_sort_spinner;

    @Bind (R.id.tq_sort_spinner)
    MultiSpinner tq_sort_spinner;

    @Bind (R.id.complaints_sort_spinner)
    MultiSpinner complaints_sort_spinner;

    @Bind (R.id.recalls_sort_spinner)
    MultiSpinner recalls_sort_spinner;

    @OnClick(R.id.sort_btn)
    void onSort () {
        callback.onResearchCallback(listingsQuery);
        SortFragment.this.dismiss();
    }

    public static SortFragment newInstance() {
        return new SortFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (OnResearchListener) getTargetFragment();
        if (callback == null) {
            callback = (OnResearchListener) getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.sort_fragment, null);
        this.listingsQuery = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_LISTING_QUERY));
        builder.setView(view);
        ButterKnife.bind(this, view);
        init_spinners();
        return builder.create();
    }


    private void init_spinners () {
        price_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.PRICE_ASC, Constants.PRICE_DESC), true),
                "Sort By Price",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                            for (int i = 0; i < items.size(); i++) {
                                if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.PRICE_ASC)) {
                                    listingsQuery.sortBy = Constants.PRICE_ASC;
                                } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.PRICE_DESC)) {
                                    listingsQuery.sortBy = Constants.PRICE_DESC;

                                }
                            }
                        }
                    });

        mileage_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.MILEAGE_ASC, Constants.MILEAGE_DESC), true),
                        "Sort By Mileage",
                        -1,
                        new MultiSpinner.MultiSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<KeyPairBoolData> items) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.MILEAGE_ASC)) {
                                        listingsQuery.sortBy = Constants.MILEAGE_ASC;
                                    } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.MILEAGE_DESC)) {
                                        listingsQuery.sortBy = Constants.MILEAGE_DESC;
                                    }
                                }
                            }
                        });

        year_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.YEAR_ASC, Constants.YEAR_DESC), true),
                "Sort By Year",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.YEAR_ASC)) {
                                listingsQuery.sortBy = Constants.YEAR_ASC;
                            } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.YEAR_DESC)) {
                                listingsQuery.sortBy = Constants.YEAR_DESC;
                            }
                        }
                    }
                });

        mpg_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.MPG_ASC, Constants.MPG_DESC), true),
                "Sort By MPG",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.MPG_ASC)) {
                                listingsQuery.sortBy = Constants.MPG_ASC;
                            } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.MPG_DESC)) {
                                listingsQuery.sortBy = Constants.MPG_DESC;
                            }
                        }
                    }
                });


        hp_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.HP_ASC, Constants.HP_DESC), true),
                "Sort By Horsepower",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.HP_ASC)) {
                                listingsQuery.sortBy = Constants.HP_ASC;
                            } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.HP_DESC)) {
                                listingsQuery.sortBy = Constants.HP_DESC;
                            }
                        }
                    }
                });

        tq_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.TQ_ASC, Constants.TQ_DESC), true),
                "Sort By Torque",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.TQ_ASC)) {
                                listingsQuery.sortBy = Constants.TQ_ASC;
                            } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.TQ_DESC)) {
                                listingsQuery.sortBy = Constants.TQ_DESC;
                            }
                        }
                    }
                });

        complaints_sort_spinner.setItems(Util.getSpinnerValues(
                        Lists.newArrayList(Constants.CMPL_ASC, Constants.CMPL_DESC), true),
                "Sort By Complaints",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.CMPL_ASC)) {
                                listingsQuery.sortBy = Constants.CMPL_ASC;
                            } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.CMPL_DESC)) {
                                listingsQuery.sortBy = Constants.CMPL_DESC;
                            }
                        }
                    }
                });

        final List<String> list = Arrays.asList(Constants.RECALLS_ASC, Constants.RECALLS_DESC);
        Log.i(getClass().getCanonicalName(), "" + list.size());
        recalls_sort_spinner.setItems(Util.getSpinnerValues(list, true),
                "Sort By Recalls",
                -1,
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.RECALLS_ASC)) {
                                listingsQuery.sortBy = Constants.RECALLS_ASC;
                            } else if (items.get(i).isSelected() && items.get(i).getName().equals(Constants.RECALLS_DESC)) {
                                listingsQuery.sortBy = Constants.RECALLS_DESC;
                            }
                        }
                    }
                });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
