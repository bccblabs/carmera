package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

import carmera.io.carmera.models.car_data_subdocuments.Complaints;
import carmera.io.carmera.models.car_data_subdocuments.Costs;
import carmera.io.carmera.models.car_data_subdocuments.DataEntryFloat;
import carmera.io.carmera.models.car_data_subdocuments.Incentives;
import carmera.io.carmera.models.car_data_subdocuments.Powertrain;
import carmera.io.carmera.models.car_data_subdocuments.Prices;
import carmera.io.carmera.models.car_data_subdocuments.Ratings;
import carmera.io.carmera.models.car_data_subdocuments.Recalls;
import carmera.io.carmera.models.car_data_subdocuments.Safety;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class StyleData {
    @JsonProperty
    public List<String> good_tags;

    @JsonProperty
    public List<String> tags;

    @JsonProperty
    public Complaints complaints;

    @JsonProperty
    public Recalls recalls;

    @JsonProperty
    public Safety safety;

    @JsonProperty
    public List<String> favorites;

    @JsonProperty
    public List<String> improvements;

    @JsonProperty
    public List<String> reviews;

    @JsonProperty
    public Ratings ratings;

    @JsonProperty
    public Prices prices;

    @JsonProperty
    public Costs costs;

    @JsonProperty
    public List<DataEntryFloat> dimensions;

    @JsonProperty
    public List<String> images;

    @JsonProperty
    public Powertrain powertrain;

    @JsonProperty
    public Float estimated_annual_fuel_cost;

    @JsonProperty
    public String compact_label;

    @JsonProperty
    public String submodel;

    @JsonProperty
    public Integer year;

    @JsonProperty
    public Integer yearId;

    @JsonProperty
    public Integer styleId;

    @JsonProperty
    public String bodyType;

    @JsonProperty
    public String model;

    @JsonProperty
    public String make;

    @JsonProperty
    public Incentives incentives;

    public List<String> getGood_tags() {
        return good_tags;
    }

    public void setGood_tags(List<String> good_tags) {
        this.good_tags = good_tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Complaints getComplaints() {
        return complaints;
    }

    public void setComplaints(Complaints complaints) {
        this.complaints = complaints;
    }

    public Recalls getRecalls() {
        return recalls;
    }

    public void setRecalls(Recalls recalls) {
        this.recalls = recalls;
    }

    public Safety getSafety() {
        return safety;
    }

    public void setSafety(Safety safety) {
        this.safety = safety;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public Costs getCosts() {
        return costs;
    }

    public void setCosts(Costs costs) {
        this.costs = costs;
    }

    public List<DataEntryFloat> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DataEntryFloat> dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Powertrain getPowertrain() {
        return powertrain;
    }

    public void setPowertrain(Powertrain powertrain) {
        this.powertrain = powertrain;
    }

    public Float getEstimated_annual_fuel_cost() {
        return estimated_annual_fuel_cost;
    }

    public void setEstimated_annual_fuel_cost(Float estimated_annual_fuel_cost) {
        this.estimated_annual_fuel_cost = estimated_annual_fuel_cost;
    }

    public String getCompact_label() {
        return compact_label;
    }

    public void setCompact_label(String compact_label) {
        this.compact_label = compact_label;
    }

    public String getSubmodel() {
        return submodel;
    }

    public void setSubmodel(String submodel) {
        this.submodel = submodel;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Incentives getIncentives() {
        return incentives;
    }

    public void setIncentives(Incentives incentives) {
        this.incentives = incentives;
    }
}
