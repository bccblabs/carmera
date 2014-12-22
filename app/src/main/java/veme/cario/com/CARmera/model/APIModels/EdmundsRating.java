package veme.cario.com.CARmera.model.APIModels;



import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.EdmundsSubRating;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EdmundsRating {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<EdmundsSubRating> getSubRatings() {
        return subRatings;
    }

    public void setSubRatings(List<EdmundsSubRating> subRatings) {
        this.subRatings = subRatings;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @JsonProperty
    private String title;

    @JsonProperty
    private String grade;

    @JsonProperty
    private String summary;

    @JsonProperty
    private String score;

    @JsonProperty
    private List<EdmundsSubRating> subRatings;

}