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
    public String make;
    @JsonProperty
    public String model;
    @JsonProperty
    public String gen_name;
    @JsonProperty
    public String trim;
    @JsonProperty
    public String drivenWheels;

    @JsonProperty
    public List<Integer> years;
    @JsonProperty
    public List<DataEntryFloat> dimensions;
    @JsonProperty
    public List<DataEntry> airbags;
    @JsonProperty
    public List<DataEntryFloat> avgTco;
    @JsonProperty
    public List<DataEntryFloat> avgPrice;
    @JsonProperty
    public List<DataEntryFloat> avgRating;
    @JsonProperty
    public List<Integer> styleIds;

    @JsonProperty
    public Engine engine;
    @JsonProperty
    public Transmission transmission;
    @JsonProperty
    public Mpg mpg;
    @JsonProperty
    public ImageUrls images;
    @JsonProperty
    public Reviews reviews;

    @JsonProperty
    public Integer min_msrp;
    @JsonProperty
    public Integer max_msrp;
    @JsonProperty
    public Integer min_used_tmv;
    @JsonProperty
    public Integer max_used_tmv;

    public TrimData() {
    }

    public List<DataEntryFloat> getDimensions() {
        return dimensions;
    }

    public List<Integer> getStyleIds() {
        return styleIds;
    }

    public void setStyleIds(List<Integer> styleIds) {
        this.styleIds = styleIds;
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

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getDrivenWheels() {
        return drivenWheels;
    }

    public void setDrivenWheels(String drivenWheels) {
        this.drivenWheels = drivenWheels;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<DataEntry> getAirbags() {
        return airbags;
    }

    public void setAirbags(List<DataEntry> airbags) {
        this.airbags = airbags;
    }

    public void setDimensions(List<DataEntryFloat> dimensions) {
        this.dimensions = dimensions;
    }

    public List<DataEntryFloat> getAvgTco() {
        return avgTco;
    }

    public void setAvgTco(List<DataEntryFloat> avgTco) {
        this.avgTco = avgTco;
    }

    public List<DataEntryFloat> getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(List<DataEntryFloat> avgPrice) {
        this.avgPrice = avgPrice;
    }

    public List<DataEntryFloat> getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(List<DataEntryFloat> avgRating) {
        this.avgRating = avgRating;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Mpg getMpg() {
        return mpg;
    }

    public void setMpg(Mpg mpg) {
        this.mpg = mpg;
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

    public Integer getMin_msrp() {
        return min_msrp;
    }

    public void setMin_msrp(Integer min_msrp) {
        this.min_msrp = min_msrp;
    }

    public Integer getMax_msrp() {
        return max_msrp;
    }

    public void setMax_msrp(Integer max_msrp) {
        this.max_msrp = max_msrp;
    }

    public Integer getMin_used_tmv() {
        return min_used_tmv;
    }

    public void setMin_used_tmv(Integer min_used_tmv) {
        this.min_used_tmv = min_used_tmv;
    }

    public Integer getMax_used_tmv() {
        return max_used_tmv;
    }

    public void setMax_used_tmv(Integer max_used_tmv) {
        this.max_used_tmv = max_used_tmv;
    }
}
