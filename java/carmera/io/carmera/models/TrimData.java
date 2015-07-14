package carmera.io.carmera.models;

/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class TrimData {
    @JsonProperty
    public List<String> years;
    @JsonProperty
    public List<DataEntry> cargo_dim;
    @JsonProperty
    public List<DataEntry> airbags;
    @JsonProperty
    public String turning_diameter;
    @JsonProperty
    public String fuel_capacity;

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<DataEntry> getCargo_dim() {
        return cargo_dim;
    }

    public void setCargo_dim(List<DataEntry> cargo_dim) {
        this.cargo_dim = cargo_dim;
    }

    public List<DataEntry> getAirbags() {
        return airbags;
    }

    public void setAirbags(List<DataEntry> airbags) {
        this.airbags = airbags;
    }

    public String getTurning_diameter() {
        return turning_diameter;
    }

    public void setTurning_diameter(String turning_diameter) {
        this.turning_diameter = turning_diameter;
    }

    public String getFuel_capacity() {
        return fuel_capacity;
    }

    public void setFuel_capacity(String fuel_capacity) {
        this.fuel_capacity = fuel_capacity;
    }

    public List<DataEntry> getInterior_dim() {
        return interior_dim;
    }

    public void setInterior_dim(List<DataEntry> interior_dim) {
        this.interior_dim = interior_dim;
    }

    public List<Engine> getEnginesList() {
        return enginesList;
    }

    public void setEnginesList(List<Engine> enginesList) {
        this.enginesList = enginesList;
    }

    public List<Transmission> getTransmissionsList() {
        return transmissionsList;
    }

    public void setTransmissionsList(List<Transmission> transmissionsList) {
        this.transmissionsList = transmissionsList;
    }

    public List<String> getDrivenWheelsList() {
        return drivenWheelsList;
    }

    public void setDrivenWheelsList(List<String> drivenWheelsList) {
        this.drivenWheelsList = drivenWheelsList;
    }

    public List<Mpg> getMpg() {
        return mpg;
    }

    public void setMpg(List<Mpg> mpg) {
        this.mpg = mpg;
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

    public int getMin_hp() {
        return min_hp;
    }

    public void setMin_hp(int min_hp) {
        this.min_hp = min_hp;
    }

    public int getMax_hp() {
        return max_hp;
    }

    public void setMax_hp(int max_hp) {
        this.max_hp = max_hp;
    }

    public int getMin_tq() {
        return min_tq;
    }

    public void setMin_tq(int min_tq) {
        this.min_tq = min_tq;
    }

    public int getMax_tq() {
        return max_tq;
    }

    public void setMax_tq(int max_tq) {
        this.max_tq = max_tq;
    }

    public String getMin_city_mpg() {
        return min_city_mpg;
    }

    public void setMin_city_mpg(String min_city_mpg) {
        this.min_city_mpg = min_city_mpg;
    }

    public String getMax_city_mpg() {
        return max_city_mpg;
    }

    public void setMax_city_mpg(String max_city_mpg) {
        this.max_city_mpg = max_city_mpg;
    }

    public int getMin_msrp() {
        return min_msrp;
    }

    public void setMin_msrp(int min_msrp) {
        this.min_msrp = min_msrp;
    }

    public int getMax_msrp() {
        return max_msrp;
    }

    public void setMax_msrp(int max_msrp) {
        this.max_msrp = max_msrp;
    }

    public int getMin_used_tmv() {
        return min_used_tmv;
    }

    public void setMin_used_tmv(int min_used_tmv) {
        this.min_used_tmv = min_used_tmv;
    }

    public int getMax_used_tmv() {
        return max_used_tmv;
    }

    public void setMax_used_tmv(int max_used_tmv) {
        this.max_used_tmv = max_used_tmv;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public AverageTco getAvgTco() {
        return avgTco;
    }

    public void setAvgTco(AverageTco avgTco) {
        this.avgTco = avgTco;
    }

    public AveragePrice getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(AveragePrice avgPrice) {
        this.avgPrice = avgPrice;
    }

    public AverageRating getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(AverageRating avgRating) {
        this.avgRating = avgRating;
    }

    public ImageUrls getImages() {
        return images;
    }

    public void setImages(ImageUrls images) {
        this.images = images;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    @JsonProperty
    public List<DataEntry> interior_dim;
    @JsonProperty
    public List<Engine> enginesList;
    @JsonProperty
    public List<Transmission> transmissionsList;
    @JsonProperty
    public List<String> drivenWheelsList;
    @JsonProperty
    public List<Mpg> mpg;
    @JsonProperty
    public String make;
    @JsonProperty
    public String model;
    @JsonProperty
    public String gen_name;
    @JsonProperty
    public int min_hp;
    @JsonProperty
    public int max_hp;
    @JsonProperty
    public int min_tq;
    @JsonProperty
    public int max_tq;
    @JsonProperty
    public String min_city_mpg;
    @JsonProperty
    public String max_city_mpg;
    @JsonProperty
    public int min_msrp;
    @JsonProperty
    public int max_msrp;
    @JsonProperty
    public int min_used_tmv;
    @JsonProperty
    public int max_used_tmv;
    @JsonProperty
    public String trim;
    @JsonProperty
    public AverageTco avgTco;
    @JsonProperty
    public AveragePrice avgPrice;
    @JsonProperty
    public AverageRating avgRating;
    @JsonProperty
    public ImageUrls images;
    @JsonProperty
    public Reviews reviews;

    public TrimData() {
    }
}
