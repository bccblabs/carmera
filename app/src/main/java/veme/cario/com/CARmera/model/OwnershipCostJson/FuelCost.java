package veme.cario.com.CARmera.model.OwnershipCostJson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by bski on 11/16/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelCost {
    private List<Integer> values;
    private String total;
}
