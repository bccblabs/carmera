package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Costs {
    @JsonProperty
    public Float repairs;

    @JsonProperty
    public Float maintenance;

    @JsonProperty
    public Float insurance;

    @JsonProperty
    public Float depreciation;

    public Float getRepairs() {
        return repairs;
    }

    public void setRepairs(Float repairs) {
        this.repairs = repairs;
    }

    public Float getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Float maintenance) {
        this.maintenance = maintenance;
    }

    public Float getInsurance() {
        return insurance;
    }

    public void setInsurance(Float insurance) {
        this.insurance = insurance;
    }

    public Float getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(Float depreciation) {
        this.depreciation = depreciation;
    }
}
