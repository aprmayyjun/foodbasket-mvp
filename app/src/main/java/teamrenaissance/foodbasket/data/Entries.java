package teamrenaissance.foodbasket.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Entity class. The object that represents an array of multiple journal entries.
 *
 */
public class Entries {

    public ArrayList<Entry> entriesArray;

    // public List<String> entryNames;
    // private int entryHash;

    /**
     * Constructor that accepts a JSONObject
     * @param jResp: a JSONObject containing the strings of the entrie
     */
    public Entries (JSONObject jResp) {

        Entry entry;
        String uname, pname, desc, img, dateStr, rating;
        int yob, id;
        JSONObject jObject;

        try {
            JSONArray jArray = jResp.getJSONArray("entries");
            entriesArray = new ArrayList<Entry>();

            // jArray contains an array of journalEntry JSONObjects
            for (int i=0; i<jArray.length(); i++) {

                jObject = jArray.getJSONObject(i);

                uname = jObject.getString("username");
                yob = jObject.getInt("yob");
                pname = jObject.getString("pname");
                desc = jObject.getString("description");
                rating = jObject.getString("rating");
                img = jObject.getString("imageUrl");
                dateStr = jObject.getString("date");
                id = jObject.getInt("id");

                // initialize an Entry object for each entry read from DB
                entry = new Entry (uname, yob, pname, desc, rating, img, dateStr, id);
                // add each Entry to the array of entries
                entriesArray.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
