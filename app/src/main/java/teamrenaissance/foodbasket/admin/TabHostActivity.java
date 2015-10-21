package teamrenaissance.foodbasket.admin;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.user.RecipeListActivity;
import teamrenaissance.foodbasket.user.ScannerActivity;

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
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("inventory")
//                .setIndicator("INVENTORY", res.getDrawable(R.drawable.ic_tab_home))
                .setIndicator("INVENTORY")
                .setContent(intent);
        tabHost.addTab(spec);


        // Do the same for the other tabs

        intent = new Intent().setClass(this, ScannerActivity.class);
        intent.putExtras(getIntent().getExtras());
        spec = tabHost.newTabSpec("scan")
                .setIndicator("SCAN")
                .setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, RecipeListActivity.class);
        intent.putExtras(getIntent().getExtras());
        spec = tabHost
                .newTabSpec("recipes")
                .setIndicator("RECIPES")
                .setContent(intent);
        tabHost.addTab(spec);

        //set tab which one you want open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);
    }

}

//Read more: http://www.androidhub4you.com/2013/04/android-tabactivity-tab-layout-demo-tab.html#ixzz3pEPQsGxP
