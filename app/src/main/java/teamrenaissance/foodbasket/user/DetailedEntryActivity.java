package teamrenaissance.foodbasket.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.ImageUtil;
import teamrenaissance.foodbasket.data.ManageEntryUtil.DeleteEntry;

/**
 * For the Home User.
 * Boundary class to view the contents of an existing journal entry.
 * Provides additional funtions for the user to choose from:
 * Editing, Sharing, Deleting
 *
 */

public class DetailedEntryActivity extends Activity {

    private String householdID = "";
    private String pname = "";
    private String category = "";
    private String capacity = "";
    private String capacityUnits = "";
    private String image = "";
    private String expiryDate = "";
    private String inventoryID = "";

    private TextView pnameView;
    private TextView categoryView;
    private TextView capacityView;
    private TextView expDateView;
    private ImageView imgView;
    private ProgressBar spinner;
    private ImageUtil.DownloadImageTask ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_entry);

        householdID = getIntent().getExtras().getString("householdID");
        pname = getIntent().getExtras().getString("pname");
        category = getIntent().getExtras().getString("category");
        capacity = getIntent().getExtras().getString("capacity");
        capacityUnits = getIntent().getExtras().getString("capacityUnits");
        image = getIntent().getExtras().getString("imageUrl");
        expiryDate = getIntent().getExtras().getString("expiryDate");
        inventoryID = getIntent().getExtras().getString("id");

        pnameView = (TextView) findViewById(R.id.viewName);
        pnameView.setText(pname);
        categoryView = (TextView) findViewById(R.id.viewCategory);
        categoryView.setText(category);
        capacityView = (TextView) findViewById(R.id.viewCapacity);
        capacityView.setText(capacity + " " + capacityUnits);
        expDateView = (TextView) findViewById(R.id.viewExpiryDate);
        expDateView.setText(expiryDate);
        imgView = (ImageView) findViewById(R.id.productImage);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        ref = new ImageUtil.DownloadImageTask(imgView, image, spinner);
        ref.execute();

        editbutton();
        deleteButton();
    }


    public void editbutton(){
        Button editBtn=(Button)findViewById(R.id.btn_Edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEditEntry = new Intent(DetailedEntryActivity.this, EditEntryActivity.class);

                toEditEntry.putExtra("householdID", householdID);
                toEditEntry.putExtra("pname", pname);
                toEditEntry.putExtra("category", category);
                toEditEntry.putExtra("capacity", capacity);
                toEditEntry.putExtra("capacityUnits", capacityUnits);
                toEditEntry.putExtra("imageUrl", image);
                toEditEntry.putExtra("expiryDate", expiryDate);
                toEditEntry.putExtra("inventoryID", inventoryID);

                startActivity(toEditEntry);
            }
        });
    }


    public void deleteButton(){
        Button deleteBtn=(Button)findViewById(R.id.btn_Delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // add the data collected to params variable to be sent to server
                // 6: action (edit/delete), id, pname, description, rating, imageUrl
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("action", "delete"));
                params.add(new BasicNameValuePair("inventory_id", inventoryID));

                new DeleteEntry(params, DetailedEntryActivity.this, householdID).execute();


            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
