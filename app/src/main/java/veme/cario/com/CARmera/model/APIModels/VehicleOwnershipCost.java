package veme.cario.com.CARmera.model.APIModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import veme.cario.com.CARmera.model.Json.DeprCost;
import veme.cario.com.CARmera.model.Json.FuelCost;
import veme.cario.com.CARmera.model.Json.InsuranceCost;
import veme.cario.com.CARmera.model.Json.MaintenanceCost;
import veme.cario.com.CARmera.model.Json.RepairCost;

/**
 * Created by bski on 11/14/14.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class VehicleOwnershipCost {

    public FuelCost getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(FuelCost fuelCost) {
        this.fuelCost = fuelCost;
    }

    public InsuranceCost getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(InsuranceCost insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public MaintenanceCost getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(MaintenanceCost maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public RepairCost getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(RepairCost repairCost) {
        this.repairCost = repairCost;
    }

    public DeprCost getDeprCost() {
        return deprCost;
    }

    public void setDeprCost(DeprCost deprCost) {
        this.deprCost = deprCost;
    }

    @JsonProperty
    private FuelCost fuelCost;

    @JsonProperty
    private InsuranceCost insuranceCost;

    @JsonProperty
    private MaintenanceCost maintenanceCost;

    @JsonProperty
    private RepairCost repairCost;

    @JsonProperty
    private DeprCost deprCost;
}
