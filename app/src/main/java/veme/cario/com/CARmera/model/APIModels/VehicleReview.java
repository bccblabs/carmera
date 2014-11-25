package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Rating;

/**
 * Created by bski on 11/14/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class VehicleReview {

    @JsonProperty
    private String grade;

    @JsonProperty
    private String summary;

    @JsonProperty
    private List<Rating> ratings;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
