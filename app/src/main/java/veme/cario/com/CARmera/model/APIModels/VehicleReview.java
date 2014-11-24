package veme.cario.com.CARmera.model.APIModels;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Rating;

/**
 * Created by bski on 11/14/14.
 */
// // @JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleReview {
    private String grade;
    private String summary;
    private List<Rating> ratings;
}
