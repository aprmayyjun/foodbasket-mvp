package teamrenaissance.foodbasket.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.ImageUtil;
import teamrenaissance.foodbasket.data.ManageEntryUtil;
import teamrenaissance.foodbasket.data.Recipe;
import teamrenaissance.foodbasket.data.Recipes;

public class DetailedRecipeActivity extends AppCompatActivity {

//    private String recipeID = null;
//    private String recipeName = null;
//    private String ingredientsStr = null;
//    private String imageUrl = null;
//    private float rating = 0;
//    private String ratingStr = null;
//    private int timeinS = 0;
//    private String timeStr = null;

    private String nameStr = null;
    private String sourceUrl = null;
    private String ingredientLines = null;
    private String imageUrlLarge = null;
    private String yieldStr = null;
    private String totalTimeStr = null;
    private float rating = 0;

    private TextView rnameView;
    private TextView ingredientsView;
    private TextView ratingView;
    private TextView recipeTimeView;
    private TextView yieldView;
    private ImageView imgView;
    private ProgressBar spinner;
    private ImageUtil.DownloadImageTask ref;

    private Bundle extras;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        extras = getIntent().getExtras();

        JSONObject jResp;
        try {
            //Log.d("JSON RESPONSE", extras.getString("json"));
            jResp = new JSONObject(extras.getString("json_recipe"));
            this.recipe = new Recipe (jResp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        recipeID = getIntent().getExtras().getString("recipeID");
//        recipeName = getIntent().getExtras().getString("recipeName");
//        ingredientsStr = getIntent().getExtras().getString("ingredientsStr");
//        imageUrl = getIntent().getExtras().getString("imageUrl");
//        rating = Float.parseFloat(getIntent().getExtras().getString("rating"));
//        ratingStr = getIntent().getExtras().getString("rating");
//        timeinS = Integer.parseInt(getIntent().getExtras().getString("timeinS"));
//        timeStr = getIntent().getExtras().getString("timeinS");

//        recipeName = getIntent().getExtras().getString("recipeName");
//        ingredientsStr = getIntent().getExtras().getString("ingredientsStr");
//        imageUrl = getIntent().getExtras().getString("imageUrl");
//        rating = Float.parseFloat(getIntent().getExtras().getString("rating"));
//        ratingStr = getIntent().getExtras().getString("rating");
//        timeinS = Integer.parseInt(getIntent().getExtras().getString("timeinS"));
//        timeStr = getIntent().getExtras().getString("timeinS");


        nameStr = this.recipe.getNameStr();
        ingredientLines = this.recipe.getIngredientLines();
        rating = this.recipe.getRating();
        totalTimeStr = this.recipe.getTotalTimeStr();
        yieldStr = this.recipe.getYieldStr();
        sourceUrl = this.recipe.getSourceUrl();
        imageUrlLarge = this.recipe.getImageUrlLarge();

        rnameView = (TextView) findViewById(R.id.viewRName);
        if (nameStr != null)
            rnameView.setText(nameStr);
        else
            rnameView.setText("Not available");

        ingredientsView = (TextView) findViewById(R.id.viewRIngredients);
        if (ingredientLines != null)
            ingredientsView.setText(ingredientLines);
        else
            ingredientsView.setText("Not available");

        ratingView = (TextView) findViewById(R.id.viewRRating);
        if (rating != 0)
            ratingView.setText(rating + "/5");
        else
            ratingView.setText("Not available");

        recipeTimeView = (TextView) findViewById(R.id.viewRTime);
        if (totalTimeStr != null)
            recipeTimeView.setText(totalTimeStr);
        else
            recipeTimeView.setText("Not available");

        yieldView = (TextView) findViewById(R.id.viewRYield);
        if (yieldStr != null)
            yieldView.setText(yieldStr);
        else
            yieldView.setText("Not available");

        imgView = (ImageView) findViewById(R.id.recipeImage);
        spinner = (ProgressBar) findViewById(R.id.progressBar_recipe);
        if (imageUrlLarge != null) {
            ref = new ImageUtil.DownloadImageTask(imgView, imageUrlLarge, spinner);
            ref.execute();
        }

        setRecipeSourceButton(sourceUrl);

        //editbutton();
        //deleteButton();

    }

    public void setRecipeSourceButton(final String url){
        Button btn = (Button)findViewById(R.id.btn_Recipe_Source);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

/*
    public void editbutton(){
        Button editBtn=(Button)findViewById(R.id.btn_Edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEditEntry = new Intent(DetailedEntryActivity.this, EditEntryActivity.class);

                toEditEntry.putExtra("householdID", householdID);
                toEditEntry.putExtra("pname", pname);
                toEditEntry.putExtra("category", category);
                toEditEntry.putExtra("capacity", capacity);
                toEditEntry.putExtra("capacityUnits", capacityUnits);
                toEditEntry.putExtra("imageUrl", image);
                toEditEntry.putExtra("expiryDate", expiryDate);
                toEditEntry.putExtra("inventoryID", inventoryID);

                startActivity(toEditEntry);
            }
        });
    }


    public void deleteButton(){
        Button deleteBtn=(Button)findViewById(R.id.btn_Delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // add the data collected to params variable to be sent to server
                // 6: action (edit/delete), id, pname, description, rating, imageUrl
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("action", "delete"));
                params.add(new BasicNameValuePair("inventory_id", inventoryID));

                new ManageEntryUtil.DeleteEntry(params, DetailedEntryActivity.this, householdID).execute();


            }
        });
    }
*/
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}