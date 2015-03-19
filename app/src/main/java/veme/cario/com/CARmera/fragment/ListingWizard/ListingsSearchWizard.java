package veme.cario.com.CARmera.fragment.ListingWizard;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

public class ListingsSearchWizard extends BasicWizardLayout {

    public ListingsSearchWizard () {}

    @Override
    public WizardFlow onSetup () {

        return new WizardFlow.Builder()
                .addStep(ListingsSearchInputFragment.class, true)
                .addStep (VehicleFilterFragment.class, true)
                .addStep (VehicleListingsFragment.class, true)
                .create();
    }


    @Override
    public void onWizardComplete () {
        super.onWizardComplete();
    }


}
