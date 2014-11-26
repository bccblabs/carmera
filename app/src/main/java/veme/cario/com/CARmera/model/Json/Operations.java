package veme.cario.com.CARmera.model.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bski on 11/26/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Operations {

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getMondayOperations() {
        return mondayOperations;
    }

    public void setMondayOperations(String mondayOperations) {
        this.mondayOperations = mondayOperations;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getTuesdayOperations() {
        return tuesdayOperations;
    }

    public void setTuesdayOperations(String tuesdayOperations) {
        this.tuesdayOperations = tuesdayOperations;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getWednesdayOperations() {
        return wednesdayOperations;
    }

    public void setWednesdayOperations(String wednesdayOperations) {
        this.wednesdayOperations = wednesdayOperations;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getThursdayOperations() {
        return thursdayOperations;
    }

    public void setThursdayOperations(String thursdayOperations) {
        this.thursdayOperations = thursdayOperations;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getFridayOperations() {
        return fridayOperations;
    }

    public void setFridayOperations(String fridayOperations) {
        this.fridayOperations = fridayOperations;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSaturdayOperations() {
        return saturdayOperations;
    }

    public void setSaturdayOperations(String saturdayOperations) {
        this.saturdayOperations = saturdayOperations;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getSundayOperations() {
        return sundayOperations;
    }

    public void setSundayOperations(String sundayOperations) {
        this.sundayOperations = sundayOperations;
    }

    @JsonProperty
    private String monday;

    @JsonProperty
    private String mondayOperations;

    @JsonProperty
    private String tuesday;

    @JsonProperty
    private String tuesdayOperations;

    @JsonProperty
    private String wednesday;

    @JsonProperty
    private String wednesdayOperations;

    @JsonProperty
    private String thursday;

    @JsonProperty
    private String thursdayOperations;

    @JsonProperty
    private String friday;

    @JsonProperty
    private String fridayOperations;

    @JsonProperty
    private String saturday;

    @JsonProperty
    private String saturdayOperations;

    @JsonProperty
    private String sunday;

    @JsonProperty
    private String sundayOperations;

}
