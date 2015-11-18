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
    public List<Integer> cylinders = new ArrayList<>();
    @JsonProperty
    public List<String> compressors = new ArrayList<>();
    @JsonProperty
    public List<String> drivetrains = new ArrayList<>();
    @JsonProperty
    public List<String> conditions = new ArrayList<>();
    @JsonProperty
    public List<String> extColors = new ArrayList<>();
    @JsonProperty
    public List<String> intColors = new ArrayList<>();
    @JsonProperty
    public List<String> equipments = new ArrayList<>();
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
    public List<List<String>> sortBy;

    public CarQuery() {
    }

    public List<List<String>> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<List<String>> sortBy) {
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

    public List<Integer> getCylinders() {
        return cylinders;
    }

    public void setCylinders(List<Integer> cylinders) {
        this.cylinders = cylinders;
    }

    public List<String> getCompressors() {
        return compressors;
    }

    public void setCompressors(List<String> compressors) {
        this.compressors = compressors;
    }

    public List<String> getDrivetrains() {
        return drivetrains;
    }

    public void setDrivetrains(List<String> drivetrains) {
        this.drivetrains = drivetrains;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public List<String> getExtColors() {
        return extColors;
    }

    public void setExtColors(List<String> extColors) {
        this.extColors = extColors;
    }

    public List<String> getIntColors() {
        return intColors;
    }

    public void setIntColors(List<String> intColors) {
        this.intColors = intColors;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
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

}
