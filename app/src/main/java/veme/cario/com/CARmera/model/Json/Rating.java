package veme.cario.com.CARmera.model.Json;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by bski on 11/16/14.
 */
// @JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<SubRating> getSubRatings() {
        return subRatings;
    }

    public void setSubRatings(List<SubRating> subRatings) {
        this.subRatings = subRatings;
    }

    private String title;
    private String grade;
    private String score;
    private String summary;
    private List<SubRating> subRatings;
}
