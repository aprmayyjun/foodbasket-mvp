package teamrenaissance.foodbasket.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
public class InventoryListActivity extends Activity {


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

        setButtonEntry();
        setButtonLogout();

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

                Date date1 = e1.getDate();
                Date date2 = e2.getDate();

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
                Intent toHDetailed = new Intent(InventoryListActivity.this, DetailedEntryActivity.class);
                toHDetailed.putExtra("householdID", clickedEntry.getUsername());
                toHDetailed.putExtra("pname", clickedEntry.getPName());
                toHDetailed.putExtra("description", clickedEntry.getDescription());
                toHDetailed.putExtra("rating", clickedEntry.getRating());
                toHDetailed.putExtra("imageUrl", clickedEntry.getImageUrl());
                toHDetailed.putExtra("id", clickedEntry.getId());
                startActivity(toHDetailed);

            }
        });
    }

    public void setButtonEntry(){
        Button editButton=(Button)findViewById(R.id.btn_createEntry);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHCreateEntry = new Intent(InventoryListActivity.this,CreateEntryActivity.class);
                Log.d("cf", "householdID in put extra is "+householdID);
                toHCreateEntry.putExtra("householdID", householdID);
                startActivity(toHCreateEntry);


            }
        });

    }

    public void setButtonLogout(){
        Button logoutButton = (Button)findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(InventoryListActivity.this, LoginRegisterActivity.class);
                startActivity(toLogin);
            }
        });
    }

}

