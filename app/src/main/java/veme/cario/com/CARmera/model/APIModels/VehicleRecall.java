package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Recall;

/**
 * Created by bski on 12/19/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleRecall {
    @JsonProperty
    private List<Recall> recallHolder;

    public List<Recall> getRecallHolder() {
        return recallHolder;
    }

    public void setRecallHolder(List<Recall> recallHolder) {
        this.recallHolder = recallHolder;
    }
}
