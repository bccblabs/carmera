package carmera.io.carmera.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bski on 7/15/15.
 */
@Parcel
public class VehicleQueries {
    public List<VehicleQuery> queries;

    public VehicleQueries() {
    }

    public void addQueries (VehicleQuery query) {
        if (queries == null)
            queries = new ArrayList<>();
        queries.add(query);
    }

    public List<VehicleQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<VehicleQuery> queries) {
        this.queries = queries;
    }
}
