package veme.cario.com.CARmera.fragment.ListingWizard;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;

//import io.searchbox.client.JestClient;
//import io.searchbox.client.JestClientFactory;
//import io.searchbox.client.config.HttpClientConfig;
//import io.searchbox.core.Search;

/**
 * Created by bski on 3/17/15.
 */
public class ListingsSearchWizard extends BasicWizardLayout {

//    @ContextVariable
//    private JestClient jest_client;
//
//    @ContextVariable
//    private Search listings_query;

    public ListingsSearchWizard () {}

    @Override
    public WizardFlow onSetup () {
        /* initialize the jest client */
//        JestClientFactory factory = new JestClientFactory();
//        factory.setHttpClientConfig(
//                new HttpClientConfig
//                        .Builder("'http://429cab6e1c887ea7d28923ebd5a56704-us-east-1.foundcluster.com:9200")
//                        .multiThreaded(true)
//                        .build());
//
//        jest_client = factory.getObject();
        return new WizardFlow.Builder()
                .addStep(ListingsSearchInputFragment.class, true)
                .addStep (VehicleFilterFragment.class)
                .create();
    }

    @Override
    public void onWizardComplete () {
        super.onWizardComplete();
    }
}
