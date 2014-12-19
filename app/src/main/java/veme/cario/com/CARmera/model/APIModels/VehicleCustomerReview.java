package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.CustomerReview;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleCustomerReview {

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getReviewCount() {
        return reviewsCount;
    }

    public void setReviewCount(String reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public List<CustomerReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<CustomerReview> reviews) {
        this.reviews = reviews;
    }

    @JsonProperty
    private String averageRating;

    @JsonProperty
    private String reviewsCount;

    @JsonProperty
    private List<CustomerReview> reviews;
}
