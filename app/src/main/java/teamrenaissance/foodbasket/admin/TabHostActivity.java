package teamrenaissance.foodbasket.admin;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.GetRecipeUtil;
import teamrenaissance.foodbasket.data.Recipe;
import teamrenaissance.foodbasket.data.Recipes;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.user.RecipesListActivity;
import teamrenaissance.foodbasket.user.RecipesMainActivity;
import teamrenaissance.foodbasket.user.SettingsActivity;

public class TabHostActivity extends TabActivity {

    Resources res; // Resource object to get Drawables
    TabHost tabHost; // The activity TabHost
    TabHost.TabSpec spec; // Reusable TabSpec for each tab
    Intent intent; // Reusable Intent for each tab


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);

        res = getResources();
        tabHost = getTabHost();

        setUpInventoryTab();
        setUpCookbookTab();
        setUpSettingsTab();

        //set tab which one you want open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);
    }


    private void setUpInventoryTab () {

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, InventoryListActivity.class);
        // Pass arguments from previous activity to next activity
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("inventory")
//                .setIndicator("INVENTORY", getResource(R.drawable.app_icon))
                .setIndicator("INVENTORY")
                .setContent(intent);
        tabHost.addTab(spec);
    }


    private void setUpCookbookTab () {

        //intent = new Intent().setClass(this, RecipesListActivity.class);
        intent = new Intent().setClass(this, RecipesMainActivity.class);


        // Pass arguments from previous activity to next activity
        String id = getIntent().getExtras().getString("householdID");
        String json_recipes = getIntent().getExtras().getString("json_recipes");



        intent.putExtra("householdID", id);
        intent.putExtra("json_recipes", json_recipes);

//        if (json_recipes != null)
//            intent.putExtra("json_recipes", json_recipes);
//        else {
//            intent.putExtra("json_recipes", "[{'id':'Homemade-Pumpkin-Pudding-1345616','ingredients':['corn starch','sugar','milk','eggs','pumpkin puree','vanilla','cinnamon','salt','nutmeg'],'image':'https://lh3.googleusercontent.com/J26tyqd0UJ1zb9Dacqh7RrFdiLI2bzUwgkdTQe7tUCqe_ACX6a3VD-aJKjgofYQCHuOgOImwgpsx74NB2Pi78YQ=s90','name':'Homemade Pumpkin Pudding','totalTimeInSeconds':1800,'rating':3},{'id':'Puff-Pastry-Apple-Hand-Pies-1349465','ingredients':['puff pastry','apples','all-purpose flour','cinnamon','ground cloves','nutmeg','allspice','eggs','turbinado'],'image':'https://lh3.googleusercontent.com/Yq-P27pV2gz85YxUquydIFrc8tDVy5xEBRz-9MaLaJpMp8Wup5NmTwUFZfhgNMpVLsx0wZl4nVz01WGX88jMpQ=s90','name':'Puff Pastry Apple Hand Pies','totalTimeInSeconds':1800,'rating':4},{'id':'Brussels-Sprouts-Gratin-1350920','ingredients':['brussels sprouts','butter','flour','milk','salt','pepper','grated nutmeg','grated parmesan cheese','panko breadcrumbs'],'image':'https://lh3.googleusercontent.com/jpmOMGUo3FGES1Bg_Xhes4hgxKM-J3qXQBljUCkKRQw_54_xBc2AoJVkO6wSTThWiqHETEzf6zu8dvbw3RRknmE=s90','name':'Brussels Sprouts Gratin','totalTimeInSeconds':2700,'rating':4},{'id':'Pumpkin-Bread-1349503','ingredients':['solid pack pumpkin','sugar','water','vegetable oil','eggs','all-purpose flour','baking soda','cinnamon','salt','baking powder','nutmeg','ground cloves'],'image':'https://lh3.googleusercontent.com/-C13Yu8sZlsmThTlcgwyFI2HRI1YeCdyIr2xeUbsriuGRH3T_K1Q5FpBHO82rjYc53hjU82m9tsQiDseRAyHCMU=s90','name':'Pumpkin Bread','totalTimeInSeconds':5400,'rating':4},{'id':'The-Best-Chewy-Oatmeal-Cookies-1345649','ingredients':['butter','sugar','brown sugar','vanilla','eggs','flour','cinnamon','ground nutmeg','salt','baking soda','rolled oats','raisins','chopped pecans'],'image':'https://lh3.googleusercontent.com/i75gXFB-62kZMBk5gN3N-NRLpl5k8i0CX3NcypJ5G4tr_rRMOl7_WtUrwqfiSZpxdNrJRk7UwLC6mAfuSgXq_Q=s90','name':'The Best Chewy Oatmeal Cookies','totalTimeInSeconds':1500,'rating':4},{'id':'Cinnamon-Toast---The-RIGHT-Way-1323409','ingredients':['bread','salted butter','sugar','ground cinnamon','vanilla extract','ground nutmeg'],'image':'http://lh3.googleusercontent.com/uG422Reg2mZe_GbCMbG-Y1ppsjMBywqXUjYWUvYquba-OpbAVaTl8g5gxiQ4jFeH6t_midzrY4pzVJlz0ux7qg=s90','name':'Cinnamon Toast - The RIGHT Way','totalTimeInSeconds':1800,'rating':4},{'id':'Pumpkin-Cheesecake-Dip-_with-10-Pumpkin-Alternatives__-1347957','ingredients':['cream cheese','sugar','vanilla','pumpkin puree','cinnamon','ground ginger','ground nutmeg','ground cloves','ground allspice','heavy cream','powdered sugar'],'image':'https://lh3.googleusercontent.com/rtEDt_wFERaWDHlxs0C20KHLCTw-g9VIUR9GmP-EByP4m1BeamQZrn0-It23KZn2NwR9DWj4aOX5vLO4-K37=s90','name':'Pumpkin Cheesecake Dip (with 10 Pumpkin Alternatives!)','totalTimeInSeconds':900,'rating':3},{'id':'Dutch-Apple-Pie-Bars-1346077','ingredients':['flour','sugar','butter','ground cinnamon','ground nutmeg','tart apples'],'image':'https://lh3.googleusercontent.com/BGG1tqm9w0KlLbB3Qyd4LW3qzpeQI11UngohXK5R01FpPXmXSk6ezJi5DOLGDwPsQiAQT3qOfIGCnEjKmuPUZA=s90','name':'Dutch Apple Pie Bars','totalTimeInSeconds':3600,'rating':4},{'id':'Downeast-Maine-Pumpkin-Bread-1349108','ingredients':['pumpkin puree','eggs','vegetable oil','water','white sugar','all-purpose flour','baking soda','salt','ground cinnamon','ground nutmeg','ground cloves','ground ginger'],'image':'https://lh3.googleusercontent.com/sfWbnDh7SSfXrqIyUYHI8V0RglBZNmHEzHWZH1z08Ea1eP6y_Ca29SvbeuJybpBWdhnRSzdLqFsS1f5LMk9XIsU=s90','name':'Downeast Maine Pumpkin Bread','totalTimeInSeconds':4500,'rating':4},{'id':'Creamed-Spinach-_-Mushrooms-1347183','ingredients':['olive oil','onions','mushrooms','baby spinach','nutmeg','flour','fat free milk','colby jack cheese'],'image':'https://lh3.googleusercontent.com/oyWR7dzHpYn1IR2JTUkk6_RJoUUjTCLcQnq6eRykpJnBoy8nAbgxsbav5CSNsa-ZR9k8tI75anRmjgq8SPJenQ=s90','name':'Creamed Spinach & Mushrooms','totalTimeInSeconds':1200,'rating':4}]");
//        }

        spec = tabHost.newTabSpec("cookbook")
                .setIndicator("COOKBOOK")
                .setContent(intent);
        tabHost.addTab(spec);
    }


    private  void setUpSettingsTab () {

        intent = new Intent().setClass(this, SettingsActivity.class);
        // Pass arguments from previous activity to next activity
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("settings")
                .setIndicator("SETTINGS")
                .setContent(intent);
        tabHost.addTab(spec);

    }


/*
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
            this.recipesList = new Recipes(jResp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/

}

//Read more: http://www.androidhub4you.com/2013/04/android-tabactivity-tab-layout-demo-tab.html#ixzz3pEPQsGxP
