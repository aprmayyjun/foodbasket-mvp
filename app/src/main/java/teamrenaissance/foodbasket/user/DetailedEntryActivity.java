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
import teamrenaissance.foodbasket.data.ManageEntryUtil.DeleteEntry;
//import teamrenaissance.foodbasket.data.ImageUtil;
//import teamrenaissance.foodbasket.data.ImageUtil.DownloadImageTask;

/**
 * For the Home User.
 * Boundary class to view the contents of an existing journal entry.
 * Provides additional funtions for the user to choose from:
 * Editing, Sharing, Deleting
 *
 */

public class DetailedEntryActivity extends Activity {

    private String pname = "";
    private String description = "";
    private String rating = "";
    private String householdID = "";
    private String image = "";
    private String id = "";
    private String shareText = "";
    //private URI shareUri = null;

    private TextView pnameView;
    private TextView descView;
    private RatingBar editRateRB;
    private ImageView img;
    private ProgressBar spinner;
    private DownloadImageTask ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_entry);

        householdID = getIntent().getExtras().getString("householdID");
        pname = getIntent().getExtras().getString("pname");
        description = getIntent().getExtras().getString("description");
        rating = getIntent().getExtras().getString("rating");
        image = getIntent().getExtras().getString("imageUrl");
        id = getIntent().getExtras().getString("id");

        pnameView = (TextView) findViewById(R.id.viewName);
        pnameView.setText(pname);
        descView = (TextView) findViewById(R.id.viewExp);
        descView.setText(description);
        editRateRB = (RatingBar) findViewById(R.id.viewRate);
        float ratingScore = Float.parseFloat(rating);
        editRateRB.setRating(ratingScore/2);
        img = (ImageView) findViewById(R.id.productImage);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        ref = new DownloadImageTask(img, image, spinner);
        ref.execute();

        shareText = "Review for " + pname + ": " + description;

        editbutton();
        deleteButton();
        shareButton(shareText, image);
    }


    public void editbutton(){
        Button editBtn=(Button)findViewById(R.id.btn_Edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHEditEntry = new Intent(DetailedEntryActivity.this, EditEntryActivity.class);

                toHEditEntry.putExtra("householdID", householdID);
                toHEditEntry.putExtra("pname", pname);
                toHEditEntry.putExtra("description", description);
                toHEditEntry.putExtra("rating", rating);
                toHEditEntry.putExtra("imageUrl", image);
                toHEditEntry.putExtra("id", id);

                startActivity(toHEditEntry);
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
                params.add(new BasicNameValuePair("id", id));

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

    public void shareButton (final String shareText, final String imageUrl){
        Button shareBtn = (Button) findViewById(R.id.btn_Share);
        shareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bitmap bm = ref.getBitmap();

                if (bm != null) {
                    Uri uri= getImageUri(DetailedEntryActivity.this,bm);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("image/*");
                    sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(sendIntent, "Share via"));
                }

                else {
                    Uri uri= getImageUri(DetailedEntryActivity.this,bm);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(sendIntent, "Share via"));
                }
            }
        });

    }
}
