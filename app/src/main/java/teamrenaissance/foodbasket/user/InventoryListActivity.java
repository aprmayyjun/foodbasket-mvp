package teamrenaissance.foodbasket.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.Date;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.admin.LoginRegisterActivity;
import teamrenaissance.foodbasket.data.Entries;
import teamrenaissance.foodbasket.data.Entry;

/**
 * For the Home User.
 * Boundary class to display as a list all entries created by this user.
 * An item in the list can be selected to view that entry in detail.
 *
 */
public class InventoryListActivity extends AppCompatActivity {


    Entries entries = null;
    Bundle extras;
    Boolean newuser = false;
    String householdID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        extras = getIntent().getExtras();
        householdID = extras.getString("householdID");
        if (extras.getString("newuser")!=null) {
            newuser = true;
        }
        if (!newuser) {
            initialise();
            population();
        }
    }

    private void initialise() {

        JSONObject jResp;
        try {
            //Log.d("JSON RESPONSE", extras.getString("json"));
            jResp = new JSONObject(extras.getString("json"));
            this.entries = new Entries (jResp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void population() {

        ListView itemList = (ListView)findViewById(R.id.listView);
        ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this,R.layout.list_item, entries.entriesArray);

        adapter.sort(new Comparator<Entry>() {

            public int compare(Entry e1, Entry e2) {

                Date date1 = e1.getExpiryDate();
                Date date2 = e2.getExpiryDate();

                //latest to earliest
                return (date2.compareTo(date1));
            }
        });

        itemList.setAdapter(adapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                //TextView textview=(TextView) viewClicked;
                Entry clickedEntry = InventoryListActivity.this.entries.entriesArray.get(position);
                Intent toDetailed = new Intent(InventoryListActivity.this, DetailedEntryActivity.class);
                toDetailed.putExtra("householdID", clickedEntry.getHouseholdID());
                toDetailed.putExtra("pname", clickedEntry.getPName());
                toDetailed.putExtra("category", clickedEntry.getCategory());
                toDetailed.putExtra("capacity", (clickedEntry.getCapacityFloatStr()));
                toDetailed.putExtra("capacityUnits", clickedEntry.getCapacityUnits());
                toDetailed.putExtra("imageUrl", clickedEntry.getImageUrl());
                toDetailed.putExtra("expiryDate", clickedEntry.getExpiryDateStr());
                toDetailed.putExtra("id", clickedEntry.getId());
                startActivity(toDetailed);
            }
        });
    }


    // To set up the action bar in the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_list, menu);
        return true;
    }

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
    }

}

