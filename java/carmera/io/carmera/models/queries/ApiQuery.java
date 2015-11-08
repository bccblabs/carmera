package carmera.io.carmera.models.queries;

import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */

@Parcel
public class ApiQuery {
    public Integer zipcode;
    public Integer pagenum;
    public Integer pagesize;
    public Integer radius;
}
