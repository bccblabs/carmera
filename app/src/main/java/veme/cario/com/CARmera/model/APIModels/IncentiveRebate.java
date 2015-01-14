package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

import veme.cario.com.CARmera.model.Json.Incentive;

/**
 * Created by bski on 1/14/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class IncentiveRebate {
    public List<Incentive> getIncentiveHolder() {
        return incentiveHolder;
    }

    public void setIncentiveHolder(List<Incentive> incentiveHolder) {
        this.incentiveHolder = incentiveHolder;
    }

    @JsonProperty
    private List<Incentive> incentiveHolder;
}
