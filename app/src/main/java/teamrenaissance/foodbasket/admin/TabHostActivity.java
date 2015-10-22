package teamrenaissance.foodbasket.admin;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.user.RecipeListActivity;
import teamrenaissance.foodbasket.user.ScannerActivity;
import teamrenaissance.foodbasket.user.SettingsActivity;

public class TabHostActivity extends TabActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, InventoryListActivity.class);
        // Pass arguments from previous activity to next activity
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("inventory")
//                .setIndicator("INVENTORY", getResource(R.drawable.app_icon))
                .setIndicator("INVENTORY")
                .setContent(intent);
        tabHost.addTab(spec);


        // Do the same for the other tabs

        intent = new Intent().setClass(this, RecipeListActivity.class);
        // Pass arguments from previous activity to next activity
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("cookbook")
                .setIndicator("COOKBOOK")
                .setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, SettingsActivity.class);
        // Pass arguments from previous activity to next activity
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("settings")
                .setIndicator("SETTINGS")
                .setContent(intent);
        tabHost.addTab(spec);

        //set tab which one you want open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);
    }

}

//Read more: http://www.androidhub4you.com/2013/04/android-tabactivity-tab-layout-demo-tab.html#ixzz3pEPQsGxP
