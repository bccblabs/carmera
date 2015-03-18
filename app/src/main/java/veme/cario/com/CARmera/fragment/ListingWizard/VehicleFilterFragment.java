package veme.cario.com.CARmera.fragment.ListingWizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.List;

import veme.cario.com.CARmera.R;

/**
 * Created by bski on 3/17/15.
 */
public class VehicleFilterFragment extends WizardStep {

    public VehicleFilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.layout_vehicle_filter, container, false);

        return v;
    }
}
