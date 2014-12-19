package veme.cario.com.CARmera.model.Json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomerReview {

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSuggestedImprovements() {
        return suggestedImprovements;
    }

    public void setSuggestedImprovements(String suggestedImprovements) {
        this.suggestedImprovements = suggestedImprovements;
    }

    public String getFavoriteFeatures() {
        return favoriteFeatures;
    }

    public void setFavoriteFeatures(String favoriteFeatures) {
        this.favoriteFeatures = favoriteFeatures;
    }
    @JsonProperty
    private String title;

    @JsonProperty
    private Author author;

    @JsonProperty
    private String text;

    @JsonProperty
    private String created;

    @JsonProperty
    private String suggestedImprovements;

    @JsonProperty
    private String favoriteFeatures;

}
