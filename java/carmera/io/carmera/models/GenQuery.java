package carmera.io.carmera.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 7/21/15.
 */
@Parcel
public class GenQuery {
    public List<String> makes;
    public List<String> models;
    public List<String> bodyTypes;
    public List<String> conditions;
    public String max_dist;

    public List<String> transmissionTypes;
    public List<Integer> cylinders;
    public Integer min_hp;
    public Integer min_tq;
    public Integer min_mpg;
    public List<String> colors;
    public List<String> equipments;

    public GenQuery() {}

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

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public String getMax_dist() {
        return max_dist;
    }

    public void setMax_dist(String max_dist) {
        this.max_dist = max_dist;
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

    public Integer getMin_hp() {
        return min_hp;
    }

    public void setMin_hp(Integer min_hp) {
        this.min_hp = min_hp;
    }

    public Integer getMin_tq() {
        return min_tq;
    }

    public void setMin_tq(Integer min_tq) {
        this.min_tq = min_tq;
    }

    public Integer getMin_mpg() {
        return min_mpg;
    }

    public void setMin_mpg(Integer min_mpg) {
        this.min_mpg = min_mpg;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }
}
