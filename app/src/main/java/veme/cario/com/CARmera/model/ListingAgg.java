package veme.cario.com.CARmera.model;

import java.util.List;

/**
 * Created by bski on 3/23/15.
 */
public class ListingAgg {
    private float zerosixty;
    private float quartermile;

    private float avg_fuel_cost;
    private float avg_insurance_cost;
    private float avg_maintenance_cost;
    private float avg_repairs_cost;
    private float avg_depreciation;

    private float rating_performance;
    private float rating_comfort;
    private float rating_fun_to_drive;
    private float rating_build_quality;
    private float rating_reliability;

    private List<String> largePhotoUrls;

    private int dealerOfferPrice;
    private String dealerName;
    private String dealerAddress;
    private String dealerPhone;
    private String dealerSaleRating;
    private String dealerServiceRating;

    private float model_price_pct;
    private float overall_price;
    private float model_mileage_pct;
    private float overall_mileage;
    private float overall_horsepower;
    private float overall_torque;
    private float overall_safety;
    private float overall_zerosixty;
    private float overall_combined_mpg;

    public float getOverall_depr() {
        return overall_depr;
    }

    public void setOverall_depr(float overall_depr) {
        this.overall_depr = overall_depr;
    }

    public float getOverall_insurance() {
        return overall_insurance;
    }

    public void setOverall_insurance(float overall_insurance) {
        this.overall_insurance = overall_insurance;
    }

    public float getOverall_repair() {
        return overall_repair;
    }

    public void setOverall_repair(float overall_repair) {
        this.overall_repair = overall_repair;
    }

    private float overall_depr;
    private float overall_insurance;
    private float overall_repair;

    private int styleId;
    private int year;
    private String model;
    private String make;
    private String styleName;

    private String f34PhotoUrlT;
    private String f34PhotoUrlST;
    private String f34PhotoUrlE;
    private List<String> smallPhotoUrls;
    private List<String> features;

    private int mileage;

    private String automaticType;
    private String driveTrain;



    private int combinedMpg;
    private int cityMpg;
    private int hwyMpg;

    private int horsepower;
    private int torque;
    private int cylinder;

    private String engineType;
    private String compressorType;

    private String overall;
    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getF34PhotoUrlT() {
        return f34PhotoUrlT;
    }

    public void setF34PhotoUrlT(String f34PhotoUrlT) {
        this.f34PhotoUrlT = f34PhotoUrlT;
    }

    public String getF34PhotoUrlST() {
        return f34PhotoUrlST;
    }

    public void setF34PhotoUrlST(String f34PhotoUrlST) {
        this.f34PhotoUrlST = f34PhotoUrlST;
    }

    public String getF34PhotoUrlE() {
        return f34PhotoUrlE;
    }

    public void setF34PhotoUrlE(String f34PhotoUrlE) {
        this.f34PhotoUrlE = f34PhotoUrlE;
    }

    public List<String> getSmallPhotoUrls() {
        return smallPhotoUrls;
    }

    public void setSmallPhotoUrls(List<String> smallPhotoUrls) {
        this.smallPhotoUrls = smallPhotoUrls;
    }

    public String getDealerSaleRating() {
        return dealerSaleRating;
    }

    public void setDealerSaleRating(String dealerSaleRating) {
        this.dealerSaleRating = dealerSaleRating;
    }

    public String getDealerServiceRating() {
        return dealerServiceRating;
    }

    public void setDealerServiceRating(String dealerServiceRating) {
        this.dealerServiceRating = dealerServiceRating;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getAutomaticType() {
        return automaticType;
    }

    public void setAutomaticType(String automaticType) {
        this.automaticType = automaticType;
    }

    public String getDriveTrain() {
        return driveTrain;
    }

    public void setDriveTrain(String driveTrain) {
        this.driveTrain = driveTrain;
    }

    public int getCombinedMpg() {
        return combinedMpg;
    }

    public void setCombinedMpg(int combinedMpg) {
        this.combinedMpg = combinedMpg;
    }

    public int getCityMpg() {
        return cityMpg;
    }

    public void setCityMpg(int cityMpg) {
        this.cityMpg = cityMpg;
    }

    public int getHwyMpg() {
        return hwyMpg;
    }

    public void setHwyMpg(int hwyMpg) {
        this.hwyMpg = hwyMpg;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public int getTorque() {
        return torque;
    }

    public void setTorque(int torque) {
        this.torque = torque;
    }

    public int getCylinder() {
        return cylinder;
    }

    public void setCylinder(int cylinder) {
        this.cylinder = cylinder;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCompressorType() {
        return compressorType;
    }

    public void setCompressorType(String compressorType) {
        this.compressorType = compressorType;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public float getZerosixty() {
        return zerosixty;
    }

    public void setZerosixty(float zerosixty) {
        this.zerosixty = zerosixty;
    }

    public float getQuartermile() {
        return quartermile;
    }

    public void setQuartermile(float quartermile) {
        this.quartermile = quartermile;
    }

    public float getAvg_fuel_cost() {
        return avg_fuel_cost;
    }

    public void setAvg_fuel_cost(float avg_fuel_cost) {
        this.avg_fuel_cost = avg_fuel_cost;
    }

    public float getAvg_insurance_cost() {
        return avg_insurance_cost;
    }

    public void setAvg_insurance_cost(float avg_insurance_cost) {
        this.avg_insurance_cost = avg_insurance_cost;
    }

    public float getAvg_maintenance_cost() {
        return avg_maintenance_cost;
    }

    public void setAvg_maintenance_cost(float avg_maintenance_cost) {
        this.avg_maintenance_cost = avg_maintenance_cost;
    }

    public float getAvg_repairs_cost() {
        return avg_repairs_cost;
    }

    public void setAvg_repairs_cost(float avg_repairs_cost) {
        this.avg_repairs_cost = avg_repairs_cost;
    }

    public float getAvg_depreciation() {
        return avg_depreciation;
    }

    public void setAvg_depreciation(float avg_depreciation) {
        this.avg_depreciation = avg_depreciation;
    }

    public float getRating_performance() {
        return rating_performance;
    }

    public void setRating_performance(float rating_performance) {
        this.rating_performance = rating_performance;
    }

    public float getRating_comfort() {
        return rating_comfort;
    }

    public void setRating_comfort(float rating_comfort) {
        this.rating_comfort = rating_comfort;
    }

    public float getRating_fun_to_drive() {
        return rating_fun_to_drive;
    }

    public void setRating_fun_to_drive(float rating_fun_to_drive) {
        this.rating_fun_to_drive = rating_fun_to_drive;
    }

    public float getRating_build_quality() {
        return rating_build_quality;
    }

    public void setRating_build_quality(float rating_build_quality) {
        this.rating_build_quality = rating_build_quality;
    }

    public float getRating_reliability() {
        return rating_reliability;
    }

    public void setRating_reliability(float rating_reliability) {
        this.rating_reliability = rating_reliability;
    }

    public List<String> getLargePhotoUrls() {
        return largePhotoUrls;
    }

    public void setLargePhotoUrls(List<String> largePhotoUrls) {
        this.largePhotoUrls = largePhotoUrls;
    }

    public int getDealerOfferPrice() {
        return dealerOfferPrice;
    }

    public void setDealerOfferPrice(int dealerOfferPrice) {
        this.dealerOfferPrice = dealerOfferPrice;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealerPhone() {
        return dealerPhone;
    }

    public void setDealerPhone(String dealerPhone) {
        this.dealerPhone = dealerPhone;
    }

    public float getModel_price_pct() {
        return model_price_pct;
    }

    public void setModel_price_pct(float model_price_pct) {
        this.model_price_pct = model_price_pct;
    }

    public float getOverall_price() {
        return overall_price;
    }

    public void setOverall_price(float overall_price) {
        this.overall_price = overall_price;
    }

    public float getModel_mileage_pct() {
        return model_mileage_pct;
    }

    public void setModel_mileage_pct(float model_mileage_pct) {
        this.model_mileage_pct = model_mileage_pct;
    }

    public float getOverall_mileage() {
        return overall_mileage;
    }

    public void setOverall_mileage(float overall_mileage) {
        this.overall_mileage = overall_mileage;
    }

    public float getOverall_horsepower() {
        return overall_horsepower;
    }

    public void setOverall_horsepower(float overall_horsepower) {
        this.overall_horsepower = overall_horsepower;
    }

    public float getOverall_torque() {
        return overall_torque;
    }

    public void setOverall_torque(float overall_torque) {
        this.overall_torque = overall_torque;
    }

    public float getOverall_safety() {
        return overall_safety;
    }

    public void setOverall_safety(float overall_safety) {
        this.overall_safety = overall_safety;
    }

    public float getOverall_zerosixty() {
        return overall_zerosixty;
    }

    public void setOverall_zerosixty(float overall_zerosixty) {
        this.overall_zerosixty = overall_zerosixty;
    }

    public float getOverall_combined_mpg() {
        return overall_combined_mpg;
    }

    public void setOverall_combined_mpg(float overall_combined_mpg) {
        this.overall_combined_mpg = overall_combined_mpg;
    }

    public String getOverall() {
        return overall;
    }
}
