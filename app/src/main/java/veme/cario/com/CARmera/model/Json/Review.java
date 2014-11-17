package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    private String title;
    private Author author;
    private String text;
    private List<CustomerRating> ratings;
}
