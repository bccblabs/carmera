package carmera.io.carmera.fragments.search_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import carmera.io.carmera.R;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinner;
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 10/12/15.
 */
public class ColorEquipSearchFragment extends SearchFragment {

    private Context cxt;

    @Bind(R.id.ext_colors_spinner) MultiSpinner ext_colors_spinner;

    @Bind(R.id.int_colors_spinner) MultiSpinner int_colors_spinner;

    @Bind(R.id.equipments_spinner) MultiSpinnerSearch equipments_spinner;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatanceState) {
        View v = inflater.inflate(R.layout.colors_equipments_search, container, false);
        ButterKnife.bind(this, v);
        cxt = getActivity();
        init_spinners();
        return v;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void init_spinners () {

        final List<String> ext_colors = Arrays.asList(cxt.getResources().getStringArray(R.array.color_array));
        ext_colors_spinner.setItems(Util.getSpinnerValues(ext_colors), "Exterior Color(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_ext = new ArrayList<>();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_ext.add (ext_colors.get(i));
                    }
                }
                getApiQuery().ext_colors = selected_ext;
            }
        });


        final List<String> int_colors = Arrays.asList(cxt.getResources().getStringArray(R.array.color_array));
        int_colors_spinner.setItems(Util.getSpinnerValues(int_colors), "Interior Color(s)", -1, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_int = new ArrayList<>();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_int.add (int_colors.get(i));
                    }
                }
                getApiQuery().int_colors = selected_int;
            }
        });

        final List<KeyPairBoolData> equipments = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.equipment_array)));
        equipments_spinner.setItems(equipments, "Equipments(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                List<String> selected_eqp = new ArrayList<>();
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        selected_eqp.add(items.get(i).getName());
                    }
                }
                getApiQuery().equipments = selected_eqp;
            }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
