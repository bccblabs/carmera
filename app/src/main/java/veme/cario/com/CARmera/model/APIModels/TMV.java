package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import veme.cario.com.CARmera.model.Json.TMVDetails;

/**
 * Created by bski on 1/17/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class TMV {
    @JsonProperty
    private TMVDetails tmv;

    public TMVDetails getTmv() {
        return tmv;
    }

    public void setTmv(TMVDetails tmv) {
        this.tmv = tmv;
    }
}
