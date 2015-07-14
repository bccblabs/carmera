package carmera.io.carmera.models;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.parceler.Parcel;

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Engine {
    @JsonProperty
    public String code;

    @JsonProperty
    public String compressionRatio;

    @JsonProperty
    public String compressorType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompressionRatio() {
        return compressionRatio;
    }

    public void setCompressionRatio(String compressionRatio) {
        this.compressionRatio = compressionRatio;
    }

    public String getCompressorType() {
        return compressorType;
    }

    public void setCompressorType(String compressorType) {
        this.compressorType = compressorType;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public int getCylinder() {
        return cylinder;
    }

    public void setCylinder(int cylinder) {
        this.cylinder = cylinder;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public int getTotalValves() {
        return totalValves;
    }

    public void setTotalValves(int totalValves) {
        this.totalValves = totalValves;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty
    public String configuration;

    public Engine() {
    }

    @JsonProperty
    public int cylinder;

    @JsonProperty
    public String fuelType;

    @JsonProperty
    public int horsepower;

    @JsonProperty
    public String name;

    @JsonProperty
    public float size;

    @JsonProperty
    public int torque;

    @JsonProperty
    public int totalValves;

    @JsonProperty
    public String type;
}
