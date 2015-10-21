package teamrenaissance.foodbasket.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import teamrenaissance.foodbasket.toDelete.AddItemActivity;
import teamrenaissance.foodbasket.toDelete.JSONParser;


// Data format: PRODUCTS_TABLE (id, product_id, product_name, category, capacity, capacity_units, picture)

public class SearchProduct {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    int productID;
    String productName;
    String category;
    float capacity;
    String capacityUnits;
    String pictureURL;


    // TODO: enter url to PHP file
    // url to create new product
    private static String url_create_product = "http://api.androidhive.info/android_connect/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    // returns productID as result when product is found
    public int SearchProductByName (String name, float cap, String units) {

        this.productName = name;
        this.capacity = cap;
        this.capacityUnits = units;

        new SearchDatabase().execute();
    }

    /**
     * Background Async Task to Create new product
     * */
    class SearchDatabase extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddItemActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            String price = inputPrice.getText().toString();
            String description = inputDesc.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("product_name", name));
            // TODO: UPDATE based on backend logic
//            params.add(new BasicNameValuePair("price", price));
//            params.add(new BasicNameValuePair("description", description));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}