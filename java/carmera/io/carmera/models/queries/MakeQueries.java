package carmera.io.carmera.models.queries;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by bski on 12/18/15.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
@Parcel
public class MakeQueries {
    @JsonProperty
    public List<MakeQuery> makes;

    @JsonProperty
    public Integer makesCount;

    public MakeQueries() {
    }

    public List<MakeQuery> getMakes() {
        return makes;
    }

    public void setMakes(List<MakeQuery> makes) {
        this.makes = makes;
    }

    public Integer getMakesCount() {
        return makesCount;
    }

    public void setMakesCount(Integer makesCount) {
        this.makesCount = makesCount;
    }
}
