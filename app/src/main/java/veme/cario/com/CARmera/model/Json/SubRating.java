package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubRating {
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

    private String title;
    private String grade;
    private String score;
    private String summary;
}
