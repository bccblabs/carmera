package carmera.io.carmera.listeners;

import java.util.List;

import carmera.io.carmera.models.queries.ModelQuery;

/**
 * Created by bski on 1/11/16.
 */
public interface OnSeeAllModelsListener {
    public void OnSeeAllModelsCallback (String make, List<ModelQuery> query);
}
