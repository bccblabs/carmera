package veme.cario.com.CARmera.model.APIModels;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Review;

/**
 * Created by bski on 11/16/14.
 */
// // @JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleCustomerReview {
    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    private String averageRating;
    private String reviewCount;
    private List<Review> reviews;
}
