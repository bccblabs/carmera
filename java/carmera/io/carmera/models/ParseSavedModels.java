package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by bski on 1/22/16.
 */
@ParseClassName("ParseSavedModels")
public class ParseSavedModels extends ParseObject{

    public static ParseQuery<ParseSavedModels> getQuery () {
        return ParseQuery.getQuery(ParseSavedModels.class);
    }

    public void setUser (String user) {
        put ("user", user);
    }

    public String getUser () {
        return getString("user");
    }
    public void setModel (String model) {
        put("model", model);
    }
    public String getModel () {
        return getString("model");
    }

    public void setRecallCnt (Integer recallCnt) {
        put("recallCnt", recallCnt);
    }
    public Integer getRecallCnt () {
        return getInt("recallCnt");
    }


    public void setStyleIds (List<Integer> styleIds) {
        put ("styleIds", styleIds);
    }

    public List<Integer> getStyleIds () {
        return getList("styleIds");
    }

    public void setPowerDesc(String powerDesc) {
        put ("power_desc", powerDesc);
    }

    public String getPowerDesc () {
        return getString("power_desc");
    }

    public void setMpgDesc(String mpgDesc) {
        put ("mpg_desc", mpgDesc);
    }

    public String getMpgDesc () {
        return getString ("mpg_desc");
    }


    public void setYearDesc (String year_desc) {
        put ("year_desc", year_desc);
    }

    public String getYearDesc () {
        return getString("year_desc");
    }

//    public void setStartYr (Integer startYr) {
//        put("startYr", startYr);
//    }
//    public Integer getStartYr () {
//        return getInt("startYr");
//    }
//
//    public void setEndYr (Integer endYr) {
//        put("endYr", endYr);
//    }
//    public Integer getEndYr () {
//        return getInt("endYr");
//    }
//
//    public void setHp (Integer startYr) {
//        put("hp", startYr);
//    }
//    public Integer getHp () {
//        return getInt("hp");
//    }
//
//    public void setTq (Integer endYr) {
//        put("tq", endYr);
//    }
//    public Integer getTq () {
//        return getInt("tq");
//    }
//
//    public void getCity (Integer startYr) {
//        put("city", startYr);
//    }
//    public Integer getCity () {
//        return getInt("city");
//    }
//
//    public void setHwy (Integer endYr) {
//        put("hwy", endYr);
//    }
//    public Integer getHwy () {
//        return getInt("hwy");
//    }
//
//    public void setMatchingListingsCnt (Integer endYr) {
//        put("matchingListingsCnt", endYr);
//    }
//    public Integer getMatchingListingsCnt () {
//        return getInt("matchingListingsCnt");
//    }
//
//    public void setBodyType (String model) {
//        put("bodytype", model);
//    }
//    public String getBodyType () {
//        return getString("bodytype");
//    }

}
