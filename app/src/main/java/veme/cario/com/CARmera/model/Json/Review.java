package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<CustomerRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<CustomerRating> ratings) {
        this.ratings = ratings;
    }

    @JsonProperty
    private String title;

    @JsonProperty
    private Author author;

    @JsonProperty
    private String text;

    @JsonProperty
    private List<CustomerRating> ratings;

}
