package carmera.io.carmera.models;

import com.google.gson.Gson;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bski on 7/21/15.
 */
@Parcel
public class GenQuery {
    public List<String> makes = new ArrayList<>();
    public List<String> models = new ArrayList<>();
    public List<String> bodyTypes = new ArrayList<>();
    public List<Integer> styleIds = new ArrayList<>();

    public List<String> transmissionTypes = new ArrayList<>();
    public List<Integer> cylinders = new ArrayList<>();
    public List<String> compressors = new ArrayList<>();
    public List<String> drivetrains = new ArrayList<>();

    public Integer max_price = Integer.MAX_VALUE;
    public Integer min_price = 0;
    public Integer max_mileage = Integer.MAX_VALUE;
    public Integer min_hp = 0;
    public Integer min_tq = 0;
    public String min_mpg = "";
    public Integer zipcode;

    public Integer max_dist = Integer.MAX_VALUE;
    public Integer pagenum;
    public Integer pagesize;
    public List<String> sortby;

    public List<String> conditions = new ArrayList<>();
    public List<String> ext_colors = new ArrayList<>();
    public List<String> int_colors = new ArrayList<>();
    public List<String> equipments = new ArrayList<>();
    public List<Double> coordinates = new ArrayList<>();
    public List<String> labels = new ArrayList<>();
    public List<Integer> years = new ArrayList<>();

    public GenQuery() {}

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Integer> getStyleIds() {
        return styleIds;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public List<String> getSortby() {
        return sortby;
    }

    public void setSortby(List<String> sortby) {
        this.sortby = sortby;
    }

    public void setStyleIds(List<Integer> styleIds) {
        this.styleIds = styleIds;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
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

    public String getMin_mpg() {
        return min_mpg;
    }

    public void setMin_mpg(String min_mpg) {
        this.min_mpg = min_mpg;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getMax_dist() {
        return max_dist;
    }

    public void setMax_dist(Integer max_dist) {
        this.max_dist = max_dist;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public List<String> getExt_colors() {
        return ext_colors;
    }

    public void setExt_colors(List<String> ext_colors) {
        this.ext_colors = ext_colors;
    }

    public List<String> getInt_colors() {
        return int_colors;
    }

    public void setInt_colors(List<String> int_colors) {
        this.int_colors = int_colors;
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
}
