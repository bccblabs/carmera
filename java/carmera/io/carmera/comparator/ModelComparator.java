package carmera.io.carmera.comparator;

import java.util.Comparator;

import carmera.io.carmera.models.listings_subdocuments.Model;
import carmera.io.carmera.models.queries.ModelQuery;

/**
 * Created by bski on 12/22/15.
 */
public class ModelComparator implements Comparator<ModelQuery> {
    @Override
    public int compare (ModelQuery m0, ModelQuery m1) {
        return m0.getModel().compareTo(m1.getModel());
    }
}
