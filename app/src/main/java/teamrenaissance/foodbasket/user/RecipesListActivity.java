package teamrenaissance.foodbasket.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.Entries;
import teamrenaissance.foodbasket.data.Entry;
import teamrenaissance.foodbasket.data.GetRecipeUtil;
import teamrenaissance.foodbasket.data.Recipe;
import teamrenaissance.foodbasket.data.Recipes;

public class RecipesListActivity extends AppCompatActivity {


    Recipes recipesList = null;
    Bundle extras;
    Boolean newuser = false;
    String householdID = null;
    String json_recipes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        extras = getIntent().getExtras();
        householdID = extras.getString("householdID");


        if (extras.getString("json_recipes")!=null) {
            json_recipes = extras.getString("json_recipes");
            prepareData();
            population();
        } else {
            if (extras.getString("newuser") != null) {
                newuser = true;
            } else {
                //initialise();
            }
        }






    }

    private void initialise() {

        // add the data collected to params variable to be sent to server
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("household_id", householdID));
        new GetRecipeUtil.RetrieveRecipesList(params, RecipesListActivity.this, householdID).execute();

    }

    private void prepareData () {

        JSONObject jResp;
        try {
            //Log.d("JSON RESPONSE", extras.getString("json"));
            jResp = new JSONObject(extras.getString("json_recipes"));
            this.recipesList = new Recipes (jResp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void population () {

        ListView itemList = (ListView)findViewById(R.id.listView_recipes);
        ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(this,R.layout.list_item, recipesList.recipesArray);

        /*
        adapter.sort(new Comparator<Entry>() {

            public int compare(Entry e1, Entry e2) {

                Date date1 = e1.getExpiryDate();
                Date date2 = e2.getExpiryDate();

                //latest to earliest
                return (date2.compareTo(date1));
            }
        });
        */

        itemList.setAdapter(adapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                //TextView textview=(TextView) viewClicked;
                Recipe clickedEntry = RecipesListActivity.this.recipesList.recipesArray.get(position);

                // add the data collected to params variable to be sent to server
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("recipe_id", clickedEntry.getRecipeID()));
                new GetRecipeUtil.RetrieveRecipeDetail(params, RecipesListActivity.this, householdID).execute();

            }
        });
    }

    /*
    // To set up the action bar in the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_list, menu);
        return true;
    }
    */

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.inventory_option_1) {
            Intent toScannerActivity = new Intent(InventoryListActivity.this, ScannerActivity.class);
            toScannerActivity.putExtra("householdID", householdID);
            startActivity(toScannerActivity);
            return true;
        }

        else if (id == R.id.inventory_option_2) {
            Intent toHCreateEntry = new Intent(InventoryListActivity.this, CreateEntryActivity.class);
            Log.d("cf", "householdID in put extra is " + householdID);
            toHCreateEntry.putExtra("householdID", householdID);
            startActivity(toHCreateEntry);
            return true;
        }

        else if (id == R.id.inventory_option_3) {
            Intent toHCreateEntry = new Intent(InventoryListActivity.this, CreateEntryActivity.class);
            Log.d("cf", "householdID in put extra is " + householdID);
            toHCreateEntry.putExtra("householdID", householdID);
            startActivity(toHCreateEntry);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }*/

}
