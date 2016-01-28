package carmera.io.carmera.models.queries;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bski on 11/7/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class CarQuery {

    @JsonProperty
    public List<String> makes = new ArrayList<>();
    @JsonProperty
    public List<String> models = new ArrayList<>();
    @JsonProperty
    public List<String> bodyTypes = new ArrayList<>();
    @JsonProperty
    public List<String> transmissionTypes = new ArrayList<>();
    @JsonProperty
    public Integer minCylinders = 0;
    @JsonProperty
    public List<String> compressors = new ArrayList<>();
    @JsonProperty
    public List<String> drivenWheels = new ArrayList<>();
    @JsonProperty
    public List<Double> coordinates = new ArrayList<>();
    @JsonProperty
    public List<String> labels = new ArrayList<>();
    @JsonProperty
    public List<Integer> years = new ArrayList<>();
    @JsonProperty
    public List<String> tags = new ArrayList<>();
    @JsonProperty
    public Integer minHp = 0;
    @JsonProperty
    public Integer minTq = 0;
    @JsonProperty
    public Integer minMpg = 0;
    @JsonProperty
    public Integer minYr = 2010;
    @JsonProperty
    public List<String> main_models = new ArrayList<>();
    @JsonProperty
    public String sortBy;
    @JsonProperty
    public List<Integer> remaining_ids;

    public CarQuery() {
    }

    public List<String> getDrivenWheels() {
        return drivenWheels;
    }

    public void setDrivenWheels(List<String> drivenWheels) {
        this.drivenWheels = drivenWheels;
    }

    public List<String> getMain_models() {
        return main_models;
    }

    public void setMain_models(List<String> main_models) {
        this.main_models = main_models;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<String> getMakes() {
        return makes;
    }

    public void setMakes(List<String> makes) {
        this.makes = makes;
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public List<String> getBodyTypes() {
        return bodyTypes;
    }

    public void setBodyTypes(List<String> bodyTypes) {
        this.bodyTypes = bodyTypes;
    }

    public List<String> getTransmissionTypes() {
        return transmissionTypes;
    }

    public void setTransmissionTypes(List<String> transmissionTypes) {
        this.transmissionTypes = transmissionTypes;
    }

    public Integer getMinCylinders() {
        return minCylinders;
    }

    public void setMinCylinders(Integer minCylinders) {
        this.minCylinders = minCylinders;
    }

    public List<String> getCompressors() {
        return compressors;
    }

    public void setCompressors(List<String> compressors) {
        this.compressors = compressors;
    }

    public List<String> getdrivenWheels() {
        return drivenWheels;
    }

    public void setdrivenWheels(List<String> drivenWheels) {
        this.drivenWheels = drivenWheels;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public Integer getMinHp() {
        return minHp;
    }

    public void setMinHp(Integer minHp) {
        this.minHp = minHp;
    }

    public Integer getMinTq() {
        return minTq;
    }

    public void setMinTq(Integer minTq) {
        this.minTq = minTq;
    }

    public Integer getMinMpg() {
        return minMpg;
    }

    public void setMinMpg(Integer minMpg) {
        this.minMpg = minMpg;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public List<Integer> getRemaining_ids() {
        return remaining_ids;
    }

    public void setRemaining_ids(List<Integer> remaining_ids) {
        this.remaining_ids = remaining_ids;
    }

    public Integer getMinYr() {
        return minYr;
    }

    public void setMinYr(Integer minYr) {
        this.minYr = minYr;
    }
}
