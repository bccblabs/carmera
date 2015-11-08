package carmera.io.carmera.models.car_data_subdocuments;

/**
 * Created by bski on 7/12/15.
 */
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/15/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Mpg {

    @JsonProperty
    public String desc;

    @JsonProperty
    public Integer highway;

    @JsonProperty
    public Integer city;

    public Mpg() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getHighway() {
        return highway;
    }

    public void setHighway(Integer highway) {
        this.highway = highway;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }
}
