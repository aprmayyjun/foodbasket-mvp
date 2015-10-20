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
import teamrenaissance.foodbasket.user.InventoryListActivity;

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
        String username;

        public RetrieveEntries (List<NameValuePair> p, Context c, String username, int op) {
            super();
            this.params = p;
            this.context = c;
            this.username = username;
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

            // Check success tag
            try {
                // shld be: success=1, message="success"
                Log.d("jResp123", jResp.toString());
                isSuccess = jResp.getInt("success");
            } catch (JSONException e) {
                Log.d("ERROR", "E");
                e.printStackTrace();
            }
            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            if (isSuccess == 1 && jResp != null) {

                if (this.option == 0) {
                    // Send JSONobj to HomeuListActivity.class
                    Intent toHListAct = new Intent (this.context, HomeuListActivity.class);
                    toHListAct.putExtra("username", username);
                    toHListAct.putExtra("json", jResp.toString());
                    this.context.startActivity(toHListAct);

                } else if (0 < this.option && this.option < 4) {
                    // Send JSONobj to EmployeeListActivity.class
                    Intent toEListAct = new Intent (this.context, EmployeeListActivity.class);
                    toEListAct.putExtra("username", username);
                    toEListAct.putExtra("json", jResp.toString());
                    toEListAct.putExtra("option", this.option);
                    this.context.startActivity(toEListAct);

                } else if (3 < this.option && this.option < 7) {
                    // Send JSONobj to EmployeeGraphActivity.class
                    Intent toGraphAct = new Intent (this.context, EmployeeGraphActivity.class);
                    toGraphAct.putExtra("username", username);
                    toGraphAct.putExtra("json", jResp.toString());
                    toGraphAct.putExtra("option", this.option);
                    this.context.startActivity(toGraphAct);

                } else {
                    Intent toLogIn = new Intent(this.context, LoginActivity.class);
                    this.context.startActivity(toLogIn);

                    // show log-in failure message in Toast if fail
                    String text = "Error occurred. Please try again...";
                    Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
                }
            } else
                try {
                    if (isSuccess == 0 && jResp!= null && jResp.getString("message").equals("Error: Query did not return a result")) {
                        // no posts for this user
                        String text = "No existing posts";
                        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

                        if (this.option == 0) {
                            // Send JSONobj to HomeuListActivity.class
                            Intent toHListAct = new Intent (this.context, HomeuListActivity.class);
                            toHListAct.putExtra("username", username);
                            toHListAct.putExtra("newuser", "newuser");
                            toHListAct.putExtra("json", jResp.toString());
                            this.context.startActivity(toHListAct);
                        }

                    }
                    else {
                        Intent toLogIn = new Intent(this.context, LoginActivity.class);
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
}
