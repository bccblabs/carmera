package carmera.io.carmera.fragments.search_fragments;

/**
 * Created by bski on 10/12/15.
 */
public abstract class SearchFragment extends android.support.v4.app.Fragment {

    public static GenQuery genQuery;

    public static GenQuery getGenQuery() {
        if (genQuery == null)
            genQuery = new GenQuery();
        return genQuery;
    }

    abstract public void init_spinners();
}
