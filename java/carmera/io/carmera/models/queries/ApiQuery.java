package carmera.io.carmera.models.queries;

import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */

@Parcel
public class ApiQuery {
    public String zipcode;
    public Integer pagenum;
    public String pagesize;
    public String radius;
}
