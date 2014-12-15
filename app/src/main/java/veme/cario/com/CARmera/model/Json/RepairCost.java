package veme.cario.com.CARmera.model.Json;

// import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by bski on 11/16/14.
 */
// @JsonIgnoreProperties(ignoreUnknown = true)
public class RepairCost {
    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public String getTotal () {
        return total;
    }

    public void setTotal (String total) {
        this.total = total;
    }

    private List<Integer> values;
    private String total;
}
