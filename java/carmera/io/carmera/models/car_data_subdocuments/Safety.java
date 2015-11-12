package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class Safety {
    @JsonProperty
    public List<String> equipments;

    @JsonProperty
    public Nhtsa nhtsa;

    @JsonProperty
    public List<CategoryValuePair> iihs;

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }

    public Nhtsa getNhtsa() {
        return nhtsa;
    }

    public void setNhtsa(Nhtsa nhtsa) {
        this.nhtsa = nhtsa;
    }
}
