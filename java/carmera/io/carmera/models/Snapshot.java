package carmera.io.carmera.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Snapshot {
    @JsonProperty
    public String make;
    @JsonProperty
    public String model;
    @JsonProperty
    public String gen_name;
    @JsonProperty
    public List<String> drivenWheelsList;
    @JsonProperty
    public Integer hp_min;
    @JsonProperty
    public Integer hp_max;
    @JsonProperty
    public Integer tq_min;
    @JsonProperty
    public Integer tq_max;
    @JsonProperty
    public String city_mpg_min;
    @JsonProperty
    public String city_mpg_max;
    @JsonProperty
    public String hwy_mpg_min;
    @JsonProperty
    public String hwy_mpg_max;
    @JsonProperty
    public Integer msrp_min;
    @JsonProperty
    public Integer msrp_max;
    @JsonProperty
    public Integer used_tmv_min;
    @JsonProperty
    public Integer used_tmv_max;
    @JsonProperty
    public String image_holder;
    @JsonProperty
    public String trim;

    @JsonProperty
    public Integer count;

    public Snapshot() {
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGen_name() {
        return gen_name;
    }

    public void setGen_name(String gen_name) {
        this.gen_name = gen_name;
    }

    public List<String> getDrivenWheelsList() {
        return drivenWheelsList;
    }

    public void setDrivenWheelsList(List<String> drivenWheelsList) {
        this.drivenWheelsList = drivenWheelsList;
    }

    public Integer getHp_min() {
        return hp_min;
    }

    public void setHp_min(Integer hp_min) {
        this.hp_min = hp_min;
    }

    public Integer getHp_max() {
        return hp_max;
    }

    public void setHp_max(Integer hp_max) {
        this.hp_max = hp_max;
    }

    public Integer getTq_min() {
        return tq_min;
    }

    public void setTq_min(Integer tq_min) {
        this.tq_min = tq_min;
    }

    public Integer getTq_max() {
        return tq_max;
    }

    public void setTq_max(Integer tq_max) {
        this.tq_max = tq_max;
    }

    public String getCity_mpg_min() {
        return city_mpg_min;
    }

    public void setCity_mpg_min(String city_mpg_min) {
        this.city_mpg_min = city_mpg_min;
    }

    public String getCity_mpg_max() {
        return city_mpg_max;
    }

    public void setCity_mpg_max(String city_mpg_max) {
        this.city_mpg_max = city_mpg_max;
    }

    public String getHwy_mpg_min() {
        return hwy_mpg_min;
    }

    public void setHwy_mpg_min(String hwy_mpg_min) {
        this.hwy_mpg_min = hwy_mpg_min;
    }

    public String getHwy_mpg_max() {
        return hwy_mpg_max;
    }

    public void setHwy_mpg_max(String hwy_mpg_max) {
        this.hwy_mpg_max = hwy_mpg_max;
    }

    public Integer getMsrp_min() {
        return msrp_min;
    }

    public void setMsrp_min(Integer msrp_min) {
        this.msrp_min = msrp_min;
    }

    public Integer getMsrp_max() {
        return msrp_max;
    }

    public void setMsrp_max(Integer msrp_max) {
        this.msrp_max = msrp_max;
    }

    public Integer getUsed_tmv_min() {
        return used_tmv_min;
    }

    public void setUsed_tmv_min(Integer used_tmv_min) {
        this.used_tmv_min = used_tmv_min;
    }

    public Integer getUsed_tmv_max() {
        return used_tmv_max;
    }

    public void setUsed_tmv_max(Integer used_tmv_max) {
        this.used_tmv_max = used_tmv_max;
    }

    public String getImage_holder() {
        return image_holder;
    }

    public void setImage_holder(String image_holder) {
        this.image_holder = image_holder;
    }
}
