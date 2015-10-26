package teamrenaissance.foodbasket.admin;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.GetRecipeUtil;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.user.RecipesListActivity;
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

        intent = new Intent().setClass(this, RecipesListActivity.class);
        // Pass arguments from previous activity to next activity
        intent.putExtras(getIntent().getExtras());

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

}

//Read more: http://www.androidhub4you.com/2013/04/android-tabactivity-tab-layout-demo-tab.html#ixzz3pEPQsGxP
