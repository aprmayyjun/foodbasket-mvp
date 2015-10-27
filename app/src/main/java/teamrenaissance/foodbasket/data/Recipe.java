package teamrenaissance.foodbasket.data;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Entity class. The object that represents a recipe.
 *
 */
public class Recipe {

    String recipeID, ingredientsStr, imageUrlSmall, recipeName;
    int timeinS;
    float rating;
    String[] ingredientArray = null;

    String sourceUrl, ingredientLines, imageUrlLarge, nameStr, yieldStr, totalTimeStr;


    public Recipe (JSONObject jResp) {

        JSONObject jObject;

        try {
            jObject = jResp.getJSONObject("json");

            this.sourceUrl = jObject.getString("url");
            this.ingredientLines = jObject.getString("ingredientLines");
            this.imageUrlLarge = jObject.getString("image");
            this.nameStr = jObject.getString("name");
            this.yieldStr = jObject.getString("yield");
            this.totalTimeStr = jObject.getString("totalTime");
            this.rating = (float) jObject.getDouble("rating");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.ingredientArray =  generateIngredientArray(this.ingredientLines);
    }


    // constructor for recipes read from database for viewing purposes
    public Recipe (String id, String name, String ingre, int time, float r, String img) {

        this.recipeID = id;
        this.recipeName = name;
        this.ingredientsStr = ingre;
        this.ingredientArray =  generateIngredientArray(ingre);

        this.imageUrlSmall = img;
        this.timeinS = time;
        this.rating = r;

    }

    private String[] generateIngredientArray (String ingredientsStr) {
        Log.d ("@@@ ingredientsStr", ingredientsStr);

        String[] myStringArray = null;
        JSONArray jArray;
        int i;

        try {
            jArray = new JSONArray(ingredientsStr);

            if (jArray != null) {
                myStringArray = new String[jArray.length()];

                for (i = 0; i < jArray.length(); i++) {
                    myStringArray[i] = jArray.get(i).toString();
                    Log.d("@@@ FOR LOOP", i + ", " + myStringArray[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myStringArray;
    }

    /**
     * Getters and Setters
     */
    public String getRecipeID() {
        return this.recipeID;
    }

    public void setRecipeID(String id) {
        this.recipeID = id;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public String getIngredientsStr() {
        return this.ingredientsStr;
    }

    public void setIngredientsStr(String str) {
        this.ingredientsStr = str;
    }

    public String[] getIngredientsArray() {
        return (this.ingredientArray);
    }

    public void setIngredientArray(String[] array) {this.ingredientArray = array;
    }

    public String getImageUrlSmall() {
        return this.imageUrlSmall;
    }

    public void setImageUrlSmall (String img) {
        this.imageUrlSmall = img;
    }

    public int getTimeinS() {
        return (this.timeinS);
    }

    public void setTimeinS(int time) {
        this.timeinS = time;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating (float rating) {
        this.rating = rating;
    }



    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl (String str) {
        this.sourceUrl = str;
    }

    public String getIngredientLines () {
        return this.ingredientLines;
    }

    public void setIngredientLines (String str) {
        this.ingredientLines = str;
    }

    public String getImageUrlLarge () {
        return this.imageUrlLarge;
    }

    public void setImageUrlLarge (String str) {
        this.imageUrlLarge = str;
    }

    public String getNameStr () {
        return this.nameStr;
    }

    public void setNameStr (String str) {
        this.nameStr = str;
    }

    public String getYieldStr() {
        return this.yieldStr;
    }

    public void setYieldStr (String str) {
        this.yieldStr = str;
    }

    public String getTotalTimeStr() {
        return this.totalTimeStr;
    }

    public void setTotalTimeStr (String str) {
        this.totalTimeStr = str;
    }


    /*
     * @see java.lang.Object#toString()
     * Override toString so that this is displayed in ListViews instead of the default implementation
     */
    public String toString() {
        return this.recipeName;
    }

}
