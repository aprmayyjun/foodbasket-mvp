package teamrenaissance.foodbasket.data;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import teamrenaissance.foodbasket.admin.PHPConnector;
import teamrenaissance.foodbasket.admin.PHPConnectorInterface;
import teamrenaissance.foodbasket.user.CreateEntryActivity;
import teamrenaissance.foodbasket.user.DetailedEntryActivity;
import teamrenaissance.foodbasket.user.InventoryListActivity;
import teamrenaissance.foodbasket.data.GetEntryUtil.RetrieveEntries;

/**
 * Control class to handle creation, editing and deletion of entries.
 * Accesses the DAO interface (PHPConnectorInterface) that encapsulates the back end.
 *
 */
public class ManageEntryUtil {

    // Background Async Task to create an entry (without picture) to be stored in DB
    public static class CreateEntryWithoutPicture extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        int isSuccess = 0;
        String householdID;

        public CreateEntryWithoutPicture (List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Creating entry. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Attempt to create entry record in the DB
        protected Void doInBackground(Void... arg) {

            // Send entry details to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.addEntryToDB(this.params);

            if (jResp != null) {
                // Check success tag
                try {
                    // shld be: success=1, message="success"
                    isSuccess = jResp.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            // Change activity based on results
            if (isSuccess==1) {
                int option = 0;

                // add the data collected to params variable to be sent to server
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("household_id", householdID));

                new RetrieveEntries(params, this.context, householdID, option).execute();

                // show registration success message in Toast if success
                String text = "Entry successfully created.";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

            } else {
                Intent toCreateEntry = new Intent (this.context, CreateEntryActivity.class);
                toCreateEntry.putExtra("householdID", householdID);
                this.context.startActivity(toCreateEntry);

                // show registration failure message in Toast if fail
                String text = "Failed to create entry. Please try again...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
            }
        }
    }


    // Background Async Task to create an entry (with picture) to be stored in DB
    public static class CreateEntryWithPicture extends AsyncTask<Bitmap, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        int isSuccess = 0;
        String householdID;

        public CreateEntryWithPicture (List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Creating entry. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Attempt to create entry record in the DB
        protected Void doInBackground(Bitmap... bitmaps) {

            if (bitmaps[0] == null)
                return null;

            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream); //compress to which format you want.
            byte [] byte_arr = stream.toByteArray();
            String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

            params.add(new BasicNameValuePair("picture", image_str));

            // Send entry details to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.addEntryToDB(this.params);

            if (jResp != null) {
                // Check success tag
                try {
                    // shld be: success=1, message="success"
                    isSuccess = jResp.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            // Change activity based on results
            if (isSuccess==1) {
                int option = 0;

                // add the data collected to params variable to be sent to server
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("household_id", householdID));

                new RetrieveEntries(params, this.context, householdID, option).execute();

                // show registration success message in Toast if success
                String text = "Entry successfully created.";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

            } else {
                Intent toCreateEntry = new Intent (this.context, CreateEntryActivity.class);
                toCreateEntry.putExtra("householdID", householdID);
                this.context.startActivity(toCreateEntry);

                // show registration failure message in Toast if fail
                String text = "Failed to create entry. Please try again...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
            }
        }
    }


    // Background Async Task to create an entry to be stored in DB
    public static class DeleteEntry extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        int isSuccess = 0;
        String householdID;

        public DeleteEntry (List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Deleting entry. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Attempt to create entry record in the DB
        protected Void doInBackground(Void... arg) {

            // Send entry details to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.updateEntryInDB(this.params);

            if (jResp != null) {
                // Check success tag
                try {
                    Log.d("deleteEntry resp: ", jResp.toString());
                    // shld be: success=1, message="success"
                    isSuccess = jResp.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            // Change activity based on results
            if (isSuccess==1) {
                int option = 0;

                // add the data collected to params variable to be sent to server
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("household_id", householdID));

                new RetrieveEntries(params, this.context, householdID, option).execute();

                // show registration success message in Toast if success
                String text = "Entry successfully deleted.";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

            } else {
                Intent toHDetailedEntry = new Intent (this.context, DetailedEntryActivity.class);
                toHDetailedEntry.putExtra("householdID", householdID);
                this.context.startActivity(toHDetailedEntry);

                // show registration failure message in Toast if fail
                String text = "Failed to delete entry. Please try again...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
            }
        }
    }



    // Background Async Task to create an entry to be stored in DB
    public static class EditEntry extends AsyncTask<Void, Void, Void> {

        List<NameValuePair> params;
        Context context;
        ProgressDialog pDialog;
        JSONObject jResp = null;
        int isSuccess = 0;
        String householdID;

        public EditEntry (List<NameValuePair> p, Context c, String householdID) {
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
            pDialog.setMessage("Submitting edited entry. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        // Attempt to create entry record in the DB
        protected Void doInBackground(Void... arg) {

            // Send entry details to PHP server and receive response
            PHPConnectorInterface phpC = new PHPConnector ();
            jResp = phpC.updateEntryInDB(this.params);

            if (jResp != null) {
                // Check success tag
                try {
                    // shld be: success=1, message="success"
                    Log.d("jResp@@@@@@@@@@@@", jResp.toString());
                    isSuccess = jResp.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        // After completing background task Dismiss the progress dialog
        protected void onPostExecute(Void result) {
            // Dismiss the dialog once verification done
            pDialog.dismiss();

            // Change activity based on results
            if (isSuccess==1) {
                int option = 0;

                // add the data collected to params variable to be sent to server
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("household_id", householdID));

                new RetrieveEntries(params, this.context, householdID, option).execute();

                // show registration success message in Toast if success
                String text = "Entry successfully updated.";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();

            } else {
                Intent toHDetailedEntry = new Intent (this.context, DetailedEntryActivity.class);
                toHDetailedEntry.putExtra("householdID", householdID);
                this.context.startActivity(toHDetailedEntry);

                // show registration failure message in Toast if fail
                String text = "Failed to update entry. Please try again...";
                Toast.makeText(this.context, text, Toast.LENGTH_LONG).show();
            }
        }
    }
}
