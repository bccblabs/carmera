package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 1/30/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class DealerRatings {
    @JsonProperty
    public Float overallRating;
    @JsonProperty
    public Integer count;
    @JsonProperty
    public Integer recommendedCount;
    @JsonProperty
    public Integer notRecommendedCount;

    public DealerRatings() {
    }

    public Float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Float overallRating) {
        this.overallRating = overallRating;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRecommendedCount() {
        return recommendedCount;
    }

    public void setRecommendedCount(Integer recommendedCount) {
        this.recommendedCount = recommendedCount;
    }

    public Integer getNotRecommendedCount() {
        return notRecommendedCount;
    }

    public void setNotRecommendedCount(Integer notRecommendedCount) {
        this.notRecommendedCount = notRecommendedCount;
    }
}
