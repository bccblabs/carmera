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
public class MakeQuery {
    @JsonProperty
    public String make;
    @JsonProperty
    public Integer numModels;
    @JsonProperty
    public String imageUrl;
    @JsonProperty
    public List<ModelQuery> models;

    public MakeQuery() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Integer getNumModels() {
        return numModels;
    }

    public void setNumModels(Integer numModels) {
        this.numModels = numModels;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ModelQuery> getModels() {
        return models;
    }

    public void setModels(List<ModelQuery> models) {
        this.models = models;
    }
}
