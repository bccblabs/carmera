package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.CarQuery;

/**
 * Created by bski on 11/7/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class ListingsQuery {


    @JsonProperty
    public String userId;

    @JsonProperty
    public String time_stamp;


    @JsonProperty
    public Integer max_price;

    @JsonProperty
    public Integer min_price;

    @JsonProperty
    public Integer max_mileage;

    @JsonProperty
    public String sortBy;

    @JsonProperty
    public ApiQuery api = new ApiQuery() ;

    @JsonProperty
    public CarQuery car = new CarQuery();

    public ListingsQuery() {
    }

    public Integer getMax_price() {
        return max_price;
    }

    public void setMax_price(Integer max_price) {
        this.max_price = max_price;
    }

    public Integer getMin_price() {
        return min_price;
    }

    public void setMin_price(Integer min_price) {
        this.min_price = min_price;
    }

    public Integer getMax_mileage() {
        return max_mileage;
    }

    public void setMax_mileage(Integer max_mileage) {
        this.max_mileage = max_mileage;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public ApiQuery getApi() {
        return api;
    }

    public void setApi(ApiQuery api) {
        this.api = api;
    }

    public CarQuery getCar() {
        return car;
    }

    public void setCar(CarQuery car) {
        this.car = car;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

}
