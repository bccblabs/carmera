package carmera.io.carmera.fragments.search_fragments;

import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.CarQuery;

/**
 * Created by bski on 10/12/15.
 */
public abstract class SearchFragment extends android.support.v4.app.Fragment {

    public static CarQuery genQuery;
    public static ApiQuery apiQuery;

    public static CarQuery getGenQuery() {
        if (genQuery == null)
            genQuery = new CarQuery();
        return genQuery;
    }

    public static ApiQuery getApiQuery() {
        if (apiQuery == null)
            apiQuery = new ApiQuery();
        return apiQuery;
    }
    abstract public void init_spinners();
}
