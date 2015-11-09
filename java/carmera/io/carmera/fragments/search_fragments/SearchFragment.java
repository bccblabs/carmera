package carmera.io.carmera.fragments.search_fragments;

import carmera.io.carmera.models.queries.CarQuery;

/**
 * Created by bski on 10/12/15.
 */
public abstract class SearchFragment extends android.support.v4.app.Fragment {

    public static CarQuery genQuery;

    public static CarQuery getGenQuery() {
        if (genQuery == null)
            genQuery = new CarQuery();
        return genQuery;
    }

    abstract public void init_spinners();
}
