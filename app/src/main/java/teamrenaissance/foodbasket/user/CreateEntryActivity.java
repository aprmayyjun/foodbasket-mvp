package teamrenaissance.foodbasket.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.admin.StringUtilities;
import teamrenaissance.foodbasket.data.ImageUtil;
import teamrenaissance.foodbasket.data.ManageEntryUtil.CreateEntryWithPicture;
import teamrenaissance.foodbasket.data.ManageEntryUtil.CreateEntryWithoutPicture;


/**
 * For the Home User.
 * Boundary class to allow creation of a journal entry.
 * Collects the input from the user and passes the information to the corresponding control class.
 *
 */
public class CreateEntryActivity extends AppCompatActivity {


    private Bundle extras;
    private String householdID;
    private String json;

    private String newName;
    private String newCategory;
    private String tmpCapacityStr;
    private float newCapacity;
    private String newCapacityUnits;
    private Date newExpiryDate;
    private String tmpDateStr;
    private String barcode = null;

    private TextView newBarcodeTV;
    private EditText newnameET;
    private EditText newCategoryET;
    private EditText newCapacityET;
    private EditText newCapacityUnitsET;
    private EditText newExpiryDateET;
    private ImageView img;
    private Button submitButton;

    private static final int SELECT_PICTURE = 1;
    private Uri imageUri = null;
    private String imagePath = "";
    private Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_entry);
        setupUI(findViewById(R.id.createScreen));

        extras = getIntent().getExtras();
        householdID = extras.getString("householdID");
        json = extras.getString("json");

        newBarcodeTV = (TextView)findViewById(R.id.newBarcode);
        newnameET = (EditText)findViewById(R.id.newName);
        newCategoryET = (EditText)findViewById(R.id.newCategory);
        newCapacityET = (EditText)findViewById(R.id.newCapacity);
        newCapacityUnitsET = (EditText)findViewById(R.id.newCapacityUnits);
        newExpiryDateET = (EditText)findViewById(R.id.newExpiryDate);

        submitButton = (Button) findViewById(R.id.btn_newSubmit);
        img = (ImageView) findViewById(R.id.productImageView);


        if (json!=null) {
            extractAndSetBarcodeText();
        }
        setButtons();
        setPictureButton();
    }


    private void extractAndSetBarcodeText () {

        try {
            JSONObject jResp = new JSONObject(json);
            barcode = jResp.getString("barcode");

            if (newBarcodeTV != null)
                newBarcodeTV.setText(barcode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void setButtons () {

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                newName = newnameET.getText().toString();
                newCategory = newCategoryET.getText().toString();
                tmpCapacityStr = newCapacityET.getText().toString();
                newCapacityUnits = newCapacityUnitsET.getText().toString();
                tmpDateStr = newExpiryDateET.getText().toString();

                // Check for validity of input
                if (!(StringUtilities.isAlphaNumericPunctuation(newName))&&!(StringUtilities.isAlphaNumericPunctuation(newCategory))){
                    Toast.makeText(CreateEntryActivity.this, "Error: Invalid Entry into field", Toast.LENGTH_SHORT).show();
                } else if (newName.equalsIgnoreCase("") || newCategory.equalsIgnoreCase("")) {
                    Toast.makeText(CreateEntryActivity.this, "Error: All fields required", Toast.LENGTH_SHORT).show();
                } else {

                    newCapacity = new Float(tmpCapacityStr);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        newExpiryDate = sdf.parse(tmpDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // add the data collected to params variable to be sent to server
                    // 6: householdID, pname, description, rating, imageUrl, date;
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("household_id", householdID));
                    params.add(new BasicNameValuePair("product_name", newName));
                    params.add(new BasicNameValuePair("category", newCategory));
                    params.add(new BasicNameValuePair("capacity", tmpCapacityStr));
                    params.add(new BasicNameValuePair("capacity_units", newCapacityUnits));
                    params.add(new BasicNameValuePair("expiry_date", tmpDateStr));

                    Log.d("PARAMS @@@@", params.toString());
                    Log.d("1. householdID @@@@", householdID);
                    Log.d("2. newName @@@@", newName);
                    Log.d("3. newCategory @@@@", newCategory);
                    Log.d("4. tmpCapacityStr @@@@", tmpCapacityStr);
                    Log.d("5. newCapacityU @@@@", newCapacityUnits);
                    Log.d("6. tmpDateStr @@@@", tmpDateStr);

                    if (barcode != null)
                        params.add(new BasicNameValuePair("barcode", barcode));
                    else
                        params.add(new BasicNameValuePair("barcode", "0000000000000"));

                    if (bitmap == null)
                        new CreateEntryWithoutPicture(params, CreateEntryActivity.this, householdID).execute();
                    else
                        new CreateEntryWithPicture(params, CreateEntryActivity.this, householdID).execute(bitmap);
                }
            }
        });

    }


    /**
     *  Allow importing of picture from device library
     */
    public void setPictureButton() {
        Button pictureButton = (Button)findViewById(R.id.btn_newMedia);

        pictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                imageUri = data.getData();
                img.setImageURI(imageUri);
                imagePath = ImageUtil.getPath(CreateEntryActivity.this, imageUri);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
            }
        }
    }


    // UI logic to hide soft keyboard whenever user clicks out of EditText fields

    protected static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    protected void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(CreateEntryActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

}