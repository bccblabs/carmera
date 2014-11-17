package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import veme.cario.com.CARmera.model.OwnershipCostJson.DeprCost;
import veme.cario.com.CARmera.model.OwnershipCostJson.FuelCost;
import veme.cario.com.CARmera.model.OwnershipCostJson.InsuranceCost;
import veme.cario.com.CARmera.model.OwnershipCostJson.MaintenanceCost;
import veme.cario.com.CARmera.model.OwnershipCostJson.RepairCost;

/**
 * Created by bski on 11/14/14.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class VehicleOwnershipCost {
    private FuelCost fuelCost;
    private InsuranceCost insuranceCost;
    private MaintenanceCost maintenanceCost;
    private RepairCost repairCost;
    private DeprCost deprCost;
}
