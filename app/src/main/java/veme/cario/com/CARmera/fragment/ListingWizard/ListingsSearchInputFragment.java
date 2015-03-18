package veme.cario.com.CARmera.fragment.ListingWizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;
//
//import io.searchbox.client.JestClient;
//import io.searchbox.client.JestClientFactory;
//import io.searchbox.core.Search;
import java.util.List;

import veme.cario.com.CARmera.R;
import veme.cario.com.CARmera.util.MultiSelectSpinner;

/**
 * Created by bski on 3/17/15.
 */
public class ListingsSearchInputFragment extends WizardStep {

//    @ContextVariable
//    private Search listings_search;
    private Spinner vehicle_state_spnr;
    private MultiSelectSpinner make_spnr, model_spnr, transmission_spnr, drivetrain_spnr, ext_spnr,
            int_spnr;

    public ListingsSearchInputFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.layout_create_search, container, false);

        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.car_state_array,
                R.layout.spinner_item);
        year_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        vehicle_state_spnr = (Spinner) v.findViewById(R.id.car_state_spinner);


        return v;
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
            break;
            case WizardStep.EXIT_PREVIOUS:
            break;
        }
    }

    private void bindDataFields() {
        /* populate listings_search variable */
    }
}
