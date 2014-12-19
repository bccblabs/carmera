package veme.cario.com.CARmera.model.APIModels;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EdmundsReview {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public List<EdmundsRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<EdmundsRating> ratings) {
        this.ratings = ratings;
    }

    @JsonProperty
    private String date;

    @JsonProperty
    private String grade;

    @JsonProperty
    private String summary;

    @JsonProperty
    private List<EdmundsRating> ratings;
}
