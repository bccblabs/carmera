package carmera.io.carmera.models.queries;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class ApiQuery {
    @JsonProperty
    public String zipcode;
    @JsonProperty
    public Integer pagenum;
    @JsonProperty
    public String pagesize;
    @JsonProperty
    public String radius;
    @JsonProperty
    public String extcolor;
    @JsonProperty
    public String intcolor;
    @JsonProperty
    public Integer msrpmax;
    @JsonProperty
    public Integer msrpmin;
    @JsonProperty
    public Integer lpmin;
    @JsonProperty
    public Integer lpmax;
    @JsonProperty
    public Integer max_mileage;
    @JsonProperty
    public String sortby;

    public ApiQuery() {
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getExtcolor() {
        return extcolor;
    }

    public void setExtcolor(String extcolor) {
        this.extcolor = extcolor;
    }

    public String getIntcolor() {
        return intcolor;
    }

    public void setIntcolor(String intcolor) {
        this.intcolor = intcolor;
    }

    public Integer getMsrpmax() {
        return msrpmax;
    }

    public void setMsrpmax(Integer msrpmax) {
        this.msrpmax = msrpmax;
    }

    public Integer getMsrpmin() {
        return msrpmin;
    }

    public void setMsrpmin(Integer msrpmin) {
        this.msrpmin = msrpmin;
    }

    public Integer getLpmin() {
        return lpmin;
    }

    public void setLpmin(Integer lpmin) {
        this.lpmin = lpmin;
    }

    public Integer getLpmax() {
        return lpmax;
    }

    public void setLpmax(Integer lpmax) {
        this.lpmax = lpmax;
    }

    public Integer getMax_mileage() {
        return max_mileage;
    }

    public void setMax_mileage(Integer max_mileage) {
        this.max_mileage = max_mileage;
    }

    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }
}
