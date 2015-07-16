package carmera.io.carmera.models;

import org.parceler.Parcel;

/**
 * Created by bski on 7/15/15.
 */
@Parcel
public class VehicleQuery {
    public String trim;
    public String label;
    public int zipcode;
    public int radius;
    public int pagenum;
    public int pagesize;

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public VehicleQuery() {}

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String get_query_params () {
        return String.format ("?trim=%s&zipcode=%d&radius=%d&pagenum=%d&pagesize=%d", getTrim(), getZipcode(), getRadius(), getPagenum(), getPagesize());
    }

}
