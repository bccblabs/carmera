package carmera.io.carmera.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.gc.materialdesign.views.ButtonRectangle;

import org.parceler.Parcels;
import java.util.Arrays;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carmera.io.carmera.R;
import carmera.io.carmera.models.GenQuery;
import carmera.io.carmera.utils.KeyPairBoolData;
import carmera.io.carmera.utils.MultiSpinnerSearch;
import carmera.io.carmera.utils.Util;

/**
 * Created by bski on 7/21/15.
 */
public class DetailsSearchFragment extends Fragment {

    @Bind(R.id.transmission_spinner)
    MultiSpinnerSearch transmission_spinner;

    @Bind(R.id.cylinders_spinner)
    MultiSpinnerSearch cylinders_spinner;

    @Bind(R.id.hp_spinner)
    MultiSpinnerSearch hp_spinner;

    @Bind(R.id.tq_spinner)
    MultiSpinnerSearch tq_spinner;

    @Bind(R.id.mpg_spinner)
    MultiSpinnerSearch mpg_spinner;

    @Bind(R.id.ext_colors_spinner)
    MultiSpinnerSearch ext_colors_spinner;

    @Bind(R.id.int_colors_spinner)
    MultiSpinnerSearch int_colors_spinner;

    @Bind(R.id.compressor_spinner)
    MultiSpinnerSearch compressors_spinner;

    @Bind(R.id.drivetrain_spinner)
    MultiSpinnerSearch drivetrain_spinner;

    @Bind(R.id.equipments_spinner)
    MultiSpinnerSearch equipments_spinner;

    @Bind(R.id.search_listings_advanced)
    ButtonRectangle search_advanced;

    @Bind(R.id.edit_base_search)
    ButtonRectangle edit_base;

    @OnClick(R.id.edit_base_search)
    public void edit_basics () {
        edit_base_callback.OnEditBaseSearch(Parcels.wrap(this.query));
    }

    @OnClick(R.id.search_listings_advanced)
    public void search_advanced () {
        search_callback.OnSearchListings(Parcels.wrap(this.query));
    }

    private Context cxt;
    private GenQuery query;

    public static DetailsSearchFragment newInstance () {
        DetailsSearchFragment fragment = new DetailsSearchFragment();
        return fragment;
    }

    public interface OnEditBaseSearchListener {
        public void OnEditBaseSearch (Parcelable query);
    }

    private OnEditBaseSearchListener edit_base_callback = null;
    private BasicSearchFragment.OnSearchVehiclesListener search_callback = null;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle args = getArguments();
        this.query = Parcels.unwrap(args.getParcelable(BasicSearchFragment.EXTRA_SEARCH_CRIT));
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.details_search, container, false);
        ButterKnife.bind(this, v);

        final List<KeyPairBoolData> transmissions = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.txn_array)));
        transmission_spinner.setItems(transmissions, "Transmission Type(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.transmissionTypes.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> compressor_types = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.compressor_array)));
        compressors_spinner.setItems(compressor_types, "Engine Compressor Type(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.compressors.add(items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> drivetrains = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.drivetrain_array)));
        drivetrain_spinner.setItems(drivetrains, "Drivetrain (s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.drivetrains.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> cylinders = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.cylinder_array)));
        cylinders_spinner.setItems(cylinders, "Cylinder Count", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.cylinders.add (Integer.parseInt(items.get(i).getName()));
                    }
                }
            }
        });

        final List<KeyPairBoolData> outputs = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.output_array)));
        hp_spinner.setItems(transmissions, "Horsepower", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.min_hp = items.get(i).getName();
                    }
                }
            }
        });
        tq_spinner.setItems(transmissions, "Torque", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.min_tq = items.get(i).getName();
                    }
                }
            }
        });

        final List<KeyPairBoolData> mpgs = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.mpg_array)));
        mpg_spinner.setItems(mpgs, "MPG", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.min_mpg = items.get(i).getName();
                    }
                }
            }
        });

        final List<KeyPairBoolData> colors = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.color_array)));
        ext_colors_spinner.setItems(colors, "Exterior Color(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.ext_colors.add (items.get(i).getName());
                    }
                }
            }
        });

        int_colors_spinner.setItems(colors, "Interior Color(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.int_colors.add (items.get(i).getName());
                    }
                }
            }
        });

        final List<KeyPairBoolData> equipments = Util.getSpinnerValues(Arrays.asList(cxt.getResources().getStringArray(R.array.equipment_array)));
        equipments_spinner.setItems(equipments, "Equipments(s)", -1, new MultiSpinnerSearch.MultiSpinnerSearchListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for(int i=0; i<items.size(); i++) {
                    if(items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        query.equipments.add (items.get(i).getName());
                    }
                }
            }
        });




        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        this.cxt = activity;
        try {
            search_callback = (BasicSearchFragment.OnSearchVehiclesListener) activity;
            edit_base_callback = (OnEditBaseSearchListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    ": needs to implement CameraResultListener" );
        }
    }

}
