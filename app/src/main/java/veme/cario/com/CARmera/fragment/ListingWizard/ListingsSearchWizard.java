package veme.cario.com.CARmera.fragment.ListingWizard;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;
import org.codepond.wizardroid.persistence.ContextVariable;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolFilterBuilder;

import java.util.List;

import io.searchbox.client.JestClient;

public class ListingsSearchWizard extends BasicWizardLayout {

    @ContextVariable
    private JestClient jestClient;

    @ContextVariable
    private SearchResponse searchResponse;

    @ContextVariable
    private BoolFilterBuilder boolFilterBuilder;

    @ContextVariable
    private List<FilterBuilder> filterBuilders_list;

    public ListingsSearchWizard () {
        super();
    }

    @Override
    public WizardFlow onSetup () {

        return new WizardFlow.Builder()
                .addStep(ListingsSearchInputFragment.class)
                .addStep (VehicleFilterFragment.class)
                .create();
    }


    @Override
    public void onWizardComplete () {
        super.onWizardComplete();
        getActivity().finish();
    }


}
