package teamrenaissance.foodbasket.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.Entries;
import teamrenaissance.foodbasket.data.Entry;
import teamrenaissance.foodbasket.data.ImageUtil;
import teamrenaissance.foodbasket.data.ManageEntryUtil;
import teamrenaissance.foodbasket.data.Product;

public class ProductInfoActivity extends AppCompatActivity {

    Bundle extras;
    String householdID = null;
    Product product;
    String dateStr = "20151210";

    private TextView pnameView;
    private TextView categoryView;
    private TextView capacityView;
    private ImageView imgView;
    private ProgressBar spinner;
    private ImageUtil.DownloadImageTask ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        extras = getIntent().getExtras();
        householdID = extras.getString("householdID");

        initialise();
        populateInfo();

        setUpEditbutton();
        setUpSubmitButton();

    }


    private void initialise() {

        JSONObject jResp;
        try {
            //Log.d("JSON RESPONSE", extras.getString("json"));
            jResp = new JSONObject(extras.getString("json"));
            // Creates a Product object with the JSON response from server
            product = new Product (jResp);
            Log.d("product name!", product.getPName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void populateInfo() {

        pnameView = (TextView) findViewById(R.id.productName);
        pnameView.setText(product.getPName());
        categoryView = (TextView) findViewById(R.id.productCategory);
        categoryView.setText(product.getCategory());
        capacityView = (TextView) findViewById(R.id.productCapacity);
        capacityView.setText(product.getCapacity());
        imgView = (ImageView) findViewById(R.id.productImage);
        spinner = (ProgressBar) findViewById(R.id.progressBar2);
        Log.d("IMAGE URL!!!", product.getImageUrl());
        ref = new ImageUtil.DownloadImageTask(imgView, product.getImageUrl(), spinner);
        ref.execute();

    }


    public void setUpEditbutton(){
//        Button editBtn=(Button)findViewById(R.id.btn_EditProduct);
//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toEditEntry = new Intent(ProductInfoActivity.this, EditEntryActivity.class);
//
//                toEditEntry.putExtra("householdID", householdID);
//                toEditEntry.putExtra("pname", pname);
//                toEditEntry.putExtra("category", category);
//                toEditEntry.putExtra("capacity", capacity);
//                toEditEntry.putExtra("capacityUnits", capacityUnits);
//                toEditEntry.putExtra("imageUrl", image);
//                toEditEntry.putExtra("expiryDate", expiryDate);
//                toEditEntry.putExtra("inventoryID", inventoryID);
//
//                startActivity(toEditEntry);
//            }
//        });
    }


    public void setUpSubmitButton(){
        Button deleteBtn=(Button)findViewById(R.id.btn_SubmitProduct);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // add the data collected to params variable to be sent to server
                // array('household_id', 'product_id', 'capacity', 'capacity_units', 'expiry_date');
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("household_id", householdID));
                params.add(new BasicNameValuePair("product_id", product.getProductID()));
                params.add(new BasicNameValuePair("capacity", product.getCapacityFloatStr()));
                params.add(new BasicNameValuePair("capacity_units", product.getCapacityUnits()));
                params.add(new BasicNameValuePair("expiry_date", dateStr));

                new ManageEntryUtil.CreateEntryWithoutPicture(params, ProductInfoActivity.this, householdID).execute();


            }
        });
    }

}
