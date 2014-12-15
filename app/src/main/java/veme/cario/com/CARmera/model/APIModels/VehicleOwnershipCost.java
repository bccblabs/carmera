package veme.cario.com.CARmera.model.APIModels;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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

    @JsonProperty
    private FuelCost fuel;

    @JsonProperty
    private InsuranceCost insurance;

    @JsonProperty
    private MaintenanceCost maintenance;

    @JsonProperty
    private RepairCost repairs;

    public DeprCost getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(DeprCost depreciation) {
        this.depreciation = depreciation;
    }

    public FuelCost getFuel() {
        return fuel;
    }

    public void setFuel(FuelCost fuel) {
        this.fuel = fuel;
    }

    public InsuranceCost getInsurance() {
        return insurance;
    }

    public void setInsurance(InsuranceCost insurance) {
        this.insurance = insurance;
    }

    public MaintenanceCost getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(MaintenanceCost maintenance) {
        this.maintenance = maintenance;
    }

    public RepairCost getRepairs() {
        return repairs;
    }

    public void setRepairs(RepairCost repairs) {
        this.repairs = repairs;
    }

    @JsonProperty
    private DeprCost depreciation;
}
