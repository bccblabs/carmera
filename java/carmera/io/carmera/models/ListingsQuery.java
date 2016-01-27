package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

import carmera.io.carmera.models.queries.ApiQuery;
import carmera.io.carmera.models.queries.CarQuery;
import carmera.io.carmera.utils.Constants;

/**
 * Created by bski on 11/7/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class ListingsQuery {


    @JsonProperty
    public Integer num_matching_models;

    @JsonProperty
    public Integer num_matching_listings;

    @JsonProperty
    public String max_price = "No Max";

    @JsonProperty
    public String max_mileage = "No Max";

    @JsonProperty
    public String sortBy = Constants.PRICE_ASC;

    @JsonProperty
    public ApiQuery api = new ApiQuery() ;

    @JsonProperty
    public CarQuery car = new CarQuery();

    @JsonProperty
    public Integer submodelCount;

    public ListingsQuery() {
    }

    public Integer getSubmodelCount() {
        return submodelCount;
    }

    public void setSubmodelCount(Integer submodelCount) {
        this.submodelCount = submodelCount;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getMax_mileage() {
        return max_mileage;
    }

    public void setMax_mileage(String max_mileage) {
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


    public Integer getNum_matching_models() {
        return num_matching_models;
    }

    public void setNum_matching_models(Integer num_matching_models) {
        this.num_matching_models = num_matching_models;
    }

    public Integer getNum_matching_listings() {
        return num_matching_listings;
    }

    public void setNum_matching_listings(Integer num_matching_listings) {
        this.num_matching_listings = num_matching_listings;
    }
}
