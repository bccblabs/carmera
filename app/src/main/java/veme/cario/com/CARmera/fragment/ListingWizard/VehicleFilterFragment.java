package veme.cario.com.CARmera.fragment.ListingWizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.lucene.queryparser.xml.FilterBuilder;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolFilterBuilder;

import java.util.List;

import io.searchbox.client.JestClient;
import veme.cario.com.CARmera.R;

public class VehicleFilterFragment extends WizardStep {

    /* populate the vehicles from query */
    /* map vehicles as aggregation results */
    /* selectable listview */
    @ContextVariable
    private JestClient jestClient;

    @ContextVariable
    private SearchResponse searchResponse;

    @ContextVariable
    private BoolFilterBuilder boolFilterBuilder;

    @ContextVariable
    private List<FilterBuilder> filterBuilders_list;

    public VehicleFilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.layout_vehicle_filter, container, false);
        return v;
    }
}
