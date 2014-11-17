package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bski on 11/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPG {
    public String getHighway() {
        return highway;
    }

    public void setHighway(String highway) {
        this.highway = highway;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String highway;
    private String city;
}
