package teamrenaissance.foodbasket.data;


import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import teamrenaissance.foodbasket.admin.LoginRegisterActivity;
import teamrenaissance.foodbasket.admin.PHPConnector;
import teamrenaissance.foodbasket.admin.PHPConnectorInterface;
import teamrenaissance.foodbasket.admin.TabHostActivity;
import teamrenaissance.foodbasket.user.CreateEntryActivity;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.user.ProductInfoActivity;
import teamrenaissance.foodbasket.user.ScannerActivity;

/**
 * Control class to handle retrieving of entries.
 * Accesses the DAO interface (PHPConnectorInterface) that encapsulates the back end.
 *
 */
public class GetEntryUtil {

    // Background Async Task to retrieve entries stored in DB
    public static class RetrieveEntries extends AsyncTask<Void, Void, Void> {

        // 0: h-list-date
        // 1: e-list-date
        // 2: e-list-age
        // 3: e-list-rating
        // 4: e-graph-date
        // 5: e-graph-age
        // 6: e-graph-rating
        int option;

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        Entries entriesList;
        int isSuccess = 0;
        String householdID;

        public RetrieveEntries (List<NameValuePair> p, Context c, String householdID, int op) {
            super();
            this.params = p;
            this.context = c;
            this.householdID = householdID;
            this.option = op;
        }

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.context);
            pDialog.setMessage("Retrieving entries. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Ask for entries from PHP server given username
        protected Void doInBackground(Void... arg) {
            Log.d("Reached getentryutil", "ad");
            // Send username to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.getEntriesFromDB(this.params);
            Log.d("ENTER HERE!!!", jResp.toString());

            if (jResp != null) {
                // Check success tag
                try {
                    // shld be: success=1, message="success"
                    Log.d("jResp123", jResp.toString());
                    isSuccess = jResp.getInt("success");
                } catch (JSONException e) {
                    Log.d("ERROR", "E");
                    e.printStackTrace();
                }
            }
            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            if (isSuccess == 1 && jResp != null) {
                // Send JSONobj to InventoryListActivity.class
//                Intent toListAct = new Intent (this.context, InventoryListActivity.class);

                Intent toListAct = new Intent (this.context, TabHostActivity.class);
                toListAct.putExtra("householdID", householdID);
                toListAct.putExtra("json", jResp.toString());
                this.context.startActivity(toListAct);
            } else
                try {
                    if (isSuccess == 0 && jResp!= null && jResp.getString("message").equals("Error: Query did not return a result")) {
                        // no posts for this user
                        String text = "No existing posts";
                        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

                        // Send JSONobj to InventoryListActivity.class
                        Intent toListAct = new Intent (this.context, TabHostActivity.class);
                        toListAct.putExtra("householdID", householdID);
                        toListAct.putExtra("newuser", "newuser");
                        toListAct.putExtra("json", jResp.toString());
                        this.context.startActivity(toListAct);

                    }
                    else {
                        Intent toLogIn = new Intent(this.context, LoginRegisterActivity.class);
                        this.context.startActivity(toLogIn);

                        // show log-in failure message in Toast if fail
                        String text = "Error occurred. Please try again...";
                        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }





    // Background Async Task to retrieve product stored in DB
    public static class RetrieveProduct extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        int isSuccess = 0;
        String householdID;

        public RetrieveProduct (List<NameValuePair> p, Context c, String householdID) {
            super();
            this.params = p;
            this.context = c;
            this.householdID = householdID;
        }

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.context);
            pDialog.setMessage("Retrieving product. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Ask for entries from PHP server given username
        protected Void doInBackground(Void... arg) {
            Log.d("Reached getProduct", "ad");
            // Send username to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.getProductFromDB(this.params);

            if (jResp != null) {
                // Check success tag
                try {
                    // shld be: success=1, message="success"
                    Log.d("jResp1234567890", jResp.toString());
                    isSuccess = jResp.getInt("success");
                } catch (JSONException e) {
                    Log.d("ERROR", "E");
                    e.printStackTrace();
                }
            }
            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            if (isSuccess == 1 && jResp != null) {
                Intent toProductInfoAct = new Intent (this.context, ProductInfoActivity.class);
                toProductInfoAct.putExtra("householdID", householdID);
                toProductInfoAct.putExtra("json", jResp.toString());
                this.context.startActivity(toProductInfoAct);
            } else
                try {
                    if (isSuccess == 0 && jResp!= null && jResp.getString("message").equals("Error: Query did not return a result")) {
                        // no product found in DB
                        String text = "No existing product. Please input manually...";
                        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

                        // Send to CreateEntryActivity.class
                        Intent toCreateEntryAct = new Intent (this.context, CreateEntryActivity.class);
                        toCreateEntryAct.putExtra("householdID", householdID);
                        toCreateEntryAct.putExtra("json", jResp.toString());
                        this.context.startActivity(toCreateEntryAct);

                    }
                    else {
                        Intent toScannerActivity = new Intent(this.context, ScannerActivity.class);
                        toScannerActivity.putExtra("householdID", householdID);
                        this.context.startActivity(toScannerActivity);

                        // show RetrieveProduct failure message in Toast if fail
                        String text = "Error occurred. Please try again...";
                        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
