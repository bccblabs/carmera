package carmera.io.carmera.models.car_data_subdocuments;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by bski on 11/7/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Ratings {
    @JsonProperty
    public Float rating_performance;
    @JsonProperty
    public Float rating_comfort;
    @JsonProperty
    public Float rating_fun_to_drive;
    @JsonProperty
    public Float rating_int;
    @JsonProperty
    public Float rating_ext;
    @JsonProperty
    public Float rating_build_quality;
    @JsonProperty
    public Float rating_reliability;
}
