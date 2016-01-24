package carmera.io.carmera.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by bski on 1/22/16.
 */
@ParseClassName("ParseSavedSearch")
public class ParseSavedSearch extends ParseObject {


    public static ParseQuery<ParseSavedSearch> getQuery() {
        return ParseQuery.getQuery(ParseSavedSearch.class);
    }

    public void setSavedName (String name) {
        put ("saved_name", name);
    }

    public String getSavedName () {
        return getString("saved_name");
    }

    public void setUser (ParseUser val) {
        put ("user", val);
    }

    public ParseUser getUserId () {
        return getParseUser("user");
    }

    public void setMaxPrice (Integer val) {
        put ("maxPrice", val);
    }

    public Integer getMaxPrice () {
        return getInt("maxPrice");
    }

    public void setMaxMileage (Integer val) {
        put ("maxPrice", val);
    }

    public Integer getMaxMileage () {
        return getInt("maxMileage");
    }

    public void setSortBy (String val) {
        put ("sortBy", val);
    }

    public String getSortBy () {
        return getString("sortBy");
    }

    public void setMinHp(Integer val) {
        put ("minHp", val);
    }

    public Integer getMinHp () {
        return getInt("minHp");
    }

    public void setMinTq(Integer val) {
        put ("minTq", val);
    }

    public Integer getMinTq () {
        return getInt("minTq");
    }

    public void setMinMpg(Integer val) {
        put ("minMpg", val);
    }

    public Integer getMinMpg () {
        return getInt("minMpg");
    }

    public void setMinYr(Integer val) {
        put ("minYr", val);
    }

    public Integer getMinYr () {
        return getInt("minYr");
    }

    public void setMakes(List<String> val) {
        put ("makes", val);
    }

    public List<String> getMakes () {
        return getList("makes");
    }

    public void setModels(List<String> val) {
        put("models", val);
    }

    public List<String> getModels () {
        return getList("models");
    }

    public void setTags(List<String> val) {
        put("tags", val);
    }

    public List<String> getTags () {
        return getList("tags");
    }

    public void setZip(String val) {
        put("zipcode", val);
    }

    public String getZip () {
        return getString("zipcode");
    }

    public void setBodyTypes(List<String> val) {
        put("bodytypes", val);
    }

    public List<String> getBodyTypes () {
        return getList("bodytypes");
    }

    public void setCompressors(List<String> val) {
        put("compressors", val);
    }

    public List<String> getCompressors () {
        return getList("compressors");
    }

    public void setDrivetrains(List<String> val) {
        put("drivetrains", val);
    }

    public List<String> getDrivetrains () {
        return getList("drivetrains");
    }

    public void setConditions (List<String> val) {
        put ("conditions", val);
    }

    public List<String> getConditions () {
        return getList("conditions");
    }

    public void setMatchingListingsCnt (Integer val) {
        put ("matchingListingsCnt", val);
    }

    public Integer getMatchingListingsCnt () {
        return getInt("matchingListingsCnt");
    }

    public void setMatchingModelsCnt (Integer val) {
        put ("matchingModelsCnt", val);
    }

    public Integer getMatchingModelsCnt () {
        return getInt("matchingModelsCnt");
    }


}

