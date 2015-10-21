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
        String householdID, pname, cat, units, img, dateStr;
        float cap;
        int inventory_id;
        JSONObject jObject;

        try {
            JSONArray jArray = jResp.getJSONArray("entries");
            entriesArray = new ArrayList<Entry>();

            // jArray contains an array of journalEntry JSONObjects
            for (int i=0; i<jArray.length(); i++) {

                jObject = jArray.getJSONObject(i);

                householdID = jObject.getString("household_id");
                pname = jObject.getString("product_name");
                cat = jObject.getString("category");
                cap = (float) jObject.getDouble("capacity");
                units = jObject.getString("capacity_units");
                img = jObject.getString("picture");
                dateStr = jObject.getString("expiry_date");
                inventory_id = jObject.getInt("inventory_id");

                // initialize an Entry object for each entry read from DB
                entry = new Entry (householdID, pname, cat, cap, units, img, dateStr, inventory_id);
                // add each Entry to the array of entries
                entriesArray.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
