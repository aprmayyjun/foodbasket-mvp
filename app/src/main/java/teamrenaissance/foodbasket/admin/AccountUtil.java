package teamrenaissance.foodbasket.admin;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.data.GetEntryUtil.RetrieveEntries;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Control class to handle Account related functionality.
 * Implemented using Asynchronous Task to avoid running on the main UI thread.
 *
 */
public class AccountUtil {

    // Background Async Task to register account
    static class CreateAccount extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp;
        int isSuccess = 0;

        public CreateAccount(List<NameValuePair> p, Context c) {
            super();
            this.params = p;
            this.context = c;
        }

        // Before starting background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(this.context);
            pDialog.setMessage("Creating account. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Create account record with PHP server
        protected Void doInBackground(Void... arg) {

            // Send account details to PHP server and receive responses
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.addAccountToDB(this.params);
            Log.d("register response", jResp.toString());
            // Check success tag
            try {
                // shld be: success=1, message="success"
                isSuccess = jResp.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
//			return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            // Change activity based on results
            if (isSuccess==1) {

                Intent toLogInPage = new Intent(this.context,
                        LoginRegisterActivity.class);
                Toast.makeText(this.context, "Account created successfully, please log in.", Toast.LENGTH_LONG).show();
                this.context.startActivity(toLogInPage);

            } else {
                Intent toRegister = new Intent(this.context, LoginRegisterActivity.class);
                this.context.startActivity(toRegister);
                // show register failure message in Toast if fail
                String text = "Failed to create account. Please try again...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
            }
        }
    }




    // Background Async Task to verify login
    static class VerifyLogin extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp;
        int isVerified = 0;
        String householdID;

        public VerifyLogin(List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Verifying log-in. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Verify log-in with PHP server
        protected Void doInBackground(Void... arg) {
            Log.d("verify householdID", householdID);
            Log.d("verify params", params.toString());
            // Send log-in details to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.verifyLogin(this.params);

            // Check verification tag
            try {
                // shld be: success=1, message="success"
                isVerified = jResp.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            // Change activity based on results
            if (isVerified==1) {

                Log.d("ENTER HERE!!!", jResp.toString());
                int option = 0;

                // add the data collected to params variable to be sent to server
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("household_id", householdID));

                Log.d("gga", jResp.toString());
                new RetrieveEntries(params, this.context, householdID, option).execute();

            } else {
                Intent toLogIn = new Intent(this.context, LoginRegisterActivity.class);
                this.context.startActivity(toLogIn);

                // show log-in failure message in Toast if fail
                String text = "Failed to log-in. Please try again...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
            }
        }
    }

}
