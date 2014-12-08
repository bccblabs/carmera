package veme.cario.com.CARmera.model.UserModels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * The set of tagged_vehicles that have been starred in the app.
 */

public class MentionedVehicleList {
    /**
     * A listener to notify other parts of the app when tagged_vehicles have been starred
     * or un-starred.
     */
    public static interface Listener {
        void onMentionedVehicleAdded(TaggedVehicle tagged_vehicle);

        void onMentionedVehicleRemoved(TaggedVehicle tagged_vehicle);
    }

    // This class is a Singleton, since there's only one set of favorites for
    // the installation.
    private static MentionedVehicleList instance = new MentionedVehicleList();

    public static MentionedVehicleList get() {
        return instance;
    }

    private List<TaggedVehicle> mentioned_vehicle_list = new ArrayList<TaggedVehicle>();

    // The set of objectIds for the tagged_vehicles that have been added to favorites.
    private HashSet<String> mentioned_vehicle_ids = new HashSet<String>();

    // Listeners to notify when the set changes.
    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    private MentionedVehicleList() {
        fetchMentionedFromParse();
    }

    public List<TaggedVehicle> getMentionedVehicles() {
        return mentioned_vehicle_list;
    }

    /**
     * Returns true if this tagged_vehicle has been added to favorites.
     */
    public boolean contains(TaggedVehicle mentioned_vehicle) {
        return mentioned_vehicle_ids.contains(mentioned_vehicle.getObjectId());
    }

    /**
     * Adds a tagged_vehicle to the set of favorites.
     */
    public void add(TaggedVehicle tagged_vehicle) {
        // For now, just add the favorite to this list; we will save it to the
        // relation later
        mentioned_vehicle_list.add(tagged_vehicle);
        mentioned_vehicle_ids.add(tagged_vehicle.getObjectId());
        /* Get Relation will add a new relation if not exists previously */
        ParseUser.getCurrentUser().getRelation("mentionedVehicles").add(tagged_vehicle);
        for (Listener listener : listeners) {
            listener.onMentionedVehicleAdded(tagged_vehicle);
        }
    }

    /**
     * Removes a tagged_vehicle from the set of favorites.
     */
    public void remove(TaggedVehicle tagged_vehicle) {
        mentioned_vehicle_list.remove(tagged_vehicle);
        mentioned_vehicle_ids.remove(tagged_vehicle.getObjectId());
        ParseUser.getCurrentUser().getRelation("mentionedVehicles").remove(tagged_vehicle);
        for (Listener listener : listeners) {
            listener.onMentionedVehicleRemoved(tagged_vehicle);
        }
    }

    /**
     * Adds a listener to be notified when the set of favorites changes.
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener.
     */
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    /**
     * Populates the set of favorites from its JSON representation, as returned
     * from toJSON.
     */
    private void setJSON(JSONObject json) {
        JSONArray favorites = json.optJSONArray("mentionedVehicles");
        if (favorites == null) {
            favorites = new JSONArray();
        }

        ArrayList<TaggedVehicle> toRemove = new ArrayList<TaggedVehicle>();
        for (String objectId : mentioned_vehicle_ids) {
            TaggedVehicle pointer = TaggedVehicle.createWithoutData(TaggedVehicle.class, objectId);
            toRemove.add(pointer);
        }
        for (TaggedVehicle tagged_vehicle : toRemove) {
            remove(tagged_vehicle);
        }

        for (int i = 0; i < favorites.length(); ++i) {
            String objectId = favorites.optString(i);
            TaggedVehicle pointer = TaggedVehicle.createWithoutData(TaggedVehicle.class, objectId);
            add(pointer);
        }
    }

    /**
     * Returns a JSON representation of the set of favorited tagged_vehicles. The format
     * is something like:
     * <code>{ "favorites": [ "tagged_vehicleObjectId1", "tagged_vehicleObjectId2", "tagged_vehicleObjectId3" ] }</code>
     */
    private JSONObject toJSON() {
        JSONArray favorites = new JSONArray();
        for (String objectId : mentioned_vehicle_ids) {
            favorites.put(objectId);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("mentionedVehicles", favorites);
        } catch (JSONException e) {
            // This can't happen.
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * Saves the current set of favorites to a SharedPreferences file. This
     * method returns quickly, while the saving runs asynchronously.
     */
    private void saveLocally(final Context context) {
        final JSONObject json = toJSON();

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... unused) {
                try {
                    String jsonString = json.toString();
                    SharedPreferences prefs = context.getSharedPreferences(
                            "mentionedVehicles.json", Context.MODE_PRIVATE);
                    prefs.edit().putString("json", jsonString).commit();
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception error) {
                if (error != null) {
                    Toast toast = Toast.makeText(context, error.getMessage(),
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }.execute();
    }

    /**
     * Saves the current set of favorites to Parse, so that we can push to
     * people based on what tagged_vehicles they have favorited, and also to measure which
     * tagged_vehicles were the most favorited.
     */
    private void saveToParse() {
        // Save the new relation on the user object
        ParseUser.getCurrentUser().saveInBackground();
    }

    /**
     * Loads the set of favorites from the SharedPreferences file, calling the
     * listeners for all the favorites that get added.
     */
    public void findLocally(final Context context) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... unused) {
                SharedPreferences prefs = context.getSharedPreferences(
                        "mentionedVehicles.json", Context.MODE_PRIVATE);
                String jsonString = prefs.getString("json", "{}");
                try {
                    return new JSONObject(jsonString);
                } catch (JSONException json) {
                    // Just ignore malformed json.
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                if (json != null) {
                    setJSON(json);
                }
            }
        }.execute();
    }

    /**
     * Saves the current set of favorites both to the local disk and to Parse.
     * This returns immediately, which the saves run asynchronously.
     */
    public void save(final Context context) {
        saveLocally(context);
        saveToParse();
    }

    private void fetchMentionedFromParse() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseRelation<TaggedVehicle> relation = currentUser.getRelation("mentionedVehicles");
        ParseQuery<TaggedVehicle> favoriteTaggedVehiclesQuery = relation.getQuery();
        favoriteTaggedVehiclesQuery.findInBackground(new FindCallback<TaggedVehicle>() {

            @Override
            public void done(List<TaggedVehicle> objects, ParseException e) {
                if ((objects != null) && (!objects.isEmpty())) {
                    for (TaggedVehicle tagged_vehicle : objects) {
                        mentioned_vehicle_list.add(tagged_vehicle);
                        mentioned_vehicle_ids.add(tagged_vehicle.getObjectId());
                    }
                }
            }

        });
    }
}
