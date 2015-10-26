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
import teamrenaissance.foodbasket.user.DetailedRecipeActivity;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.user.ProductInfoActivity;
import teamrenaissance.foodbasket.user.RecipesListActivity;
import teamrenaissance.foodbasket.user.ScannerActivity;

/**
 * Control class to handle retrieving of entries.
 * Accesses the DAO interface (PHPConnectorInterface) that encapsulates the back end.
 *
 */
public class GetRecipeUtil {

    // Background Async Task to retrieve list of recipes stored in DB
    public static class RetrieveRecipesList extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        Recipes recipeList;
        int isSuccess = 0;
        String householdID;

        public RetrieveRecipesList (List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Retrieving recipes list. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Ask for entries from PHP server given username
        protected Void doInBackground(Void... arg) {
            Log.d("Reached getentryutil", "ad");
            // Send username to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.getRecipesListFromDB(this.params);
            Log.d("ENTER HERE!!!@@@###", jResp.toString());

            if (jResp != null) {
                // Check success tag
                try {
                    // shld be: success=1, message="success"
                    Log.d("jResp_maylim", jResp.toString());
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

                Intent toRecipesListAct = new Intent (this.context, RecipesListActivity.class);
                toRecipesListAct.putExtra("householdID", householdID);
                toRecipesListAct.putExtra("json_recipes", jResp.toString());
                this.context.startActivity(toRecipesListAct);
            } else
                try {
                    if (isSuccess == 0 && jResp!= null && jResp.getString("message").equals("Error: Query did not return a result")) {
                        // no posts for this user
                        String text = "No existing posts";
                        Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

                        // Send JSONobj to RecipesListActivity.class
                        Intent toRecipesListAct = new Intent (this.context, RecipesListActivity.class);
                        toRecipesListAct.putExtra("householdID", householdID);
                        toRecipesListAct.putExtra("newuser", "newuser");
                        this.context.startActivity(toRecipesListAct);

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





    // Background Async Task to retrieve detailed recipe stored in DB
    public static class RetrieveRecipeDetail extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        int isSuccess = 0;
        String householdID;

        public RetrieveRecipeDetail (List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Retrieving recipe. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Ask for entries from PHP server given username
        protected Void doInBackground(Void... arg) {
            Log.d("Reached getRecipeDetail", "ad");
            // Send username to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.getRecipeDetailFromDB(this.params);

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

                Intent toDetailed = new Intent(this.context, DetailedRecipeActivity.class);
                toDetailed.putExtra("householdID", householdID);
                toDetailed.putExtra("json_recipe", jResp.toString());
                this.context.startActivity(toDetailed);

            } else {
                // no recipe found in DB
                String text = "No existing recipe. Please select another...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

                // Send to RecipesListActivity.class
                Intent toRecipesList = new Intent(this.context, RecipesListActivity.class);
                toRecipesList.putExtra("householdID", householdID);
                this.context.startActivity(toRecipesList);
            }
        }
    }
}
