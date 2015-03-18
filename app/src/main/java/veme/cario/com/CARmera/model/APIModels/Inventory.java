package veme.cario.com.CARmera.model.APIModels;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Listing;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory {

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<Listing> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<Listing> resultsList) {
        this.resultsList = resultsList;
    }

    @JsonProperty
    private String totalCount;

    @JsonProperty
    private List<Listing> resultsList;
}
