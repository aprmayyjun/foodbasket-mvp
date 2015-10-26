package teamrenaissance.foodbasket.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


/**
 * Entity class. The object that represents an array of multiple journal entries.
 *
 */
public class Recipes {

    public ArrayList<Recipe> recipesArray;

    /**
     * Constructor that accepts a JSONObject
     * @param jResp: a JSONObject containing the strings of the entrie
     */
    public Recipes (JSONObject jResp) {

        Recipe recipe;
        JSONObject jObject;
        String recipeID, ingredientsStr, imageURL, recipeName;
        int timeinS;
        float rating;

        try {
            JSONArray jArray = jResp.getJSONArray("json");
            recipesArray = new ArrayList<Recipe>();

            // jArray contains an array of journalEntry JSONObjects
            for (int i=0; i<jArray.length(); i++) {

                jObject = jArray.getJSONObject(i);

                recipeID = jObject.getString("id");
                ingredientsStr = jObject.getString("ingredients");
                imageURL = jObject.getString("image");
                recipeName = jObject.getString("name");
                timeinS = jObject.getInt("totalTimeInSeconds");
                rating = (float) jObject.getDouble("rating");

                // initialize a Recipe object for each entry read from DB
                recipe = new Recipe (recipeID, recipeName, ingredientsStr, timeinS, rating, imageURL);
                // add each Entry to the array of entries
                recipesArray.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
