package teamrenaissance.foodbasket.user;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import teamrenaissance.foodbasket.admin.LoginRegisterActivity;
import teamrenaissance.foodbasket.admin.StringUtilities;
import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.ManageEntryUtil.EditEntry;;

/**
 * For the Home User.
 * Boundary class to allow editing of a journal entry.
 * Collects the input from the user and passes the information to the corresponding control class.
 *
 */

public class EditEntryActivity extends AppCompatActivity {

    private String householdID = "";
    private String editName = "";
    private String editCategory = "";
    private String editCapacity = "";
    private String editCapacityUnits = "";
    private String image = "";
    private String editExpiryDate = "";
    private String inventoryID = "";

    TextView editNameTV;
    TextView editCategoryTV;
    EditText editCapacityET;
    EditText editCapacityUnitsET;
    EditText editExpiryDateET;

    // form verification!
    // size of the following String fields not to exceed:
    // householdID- 20 char
    // pname- 50 char
    // description- 255 char
    // imageurl- 50 char

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_entry);
        setupUI(findViewById(R.id.editScreen));

        editNameTV = (TextView)findViewById(R.id.editName);
        editCategoryTV = (TextView)findViewById(R.id.editCategory);
        editCapacityET = (EditText)findViewById(R.id.editCapacity);
        editCapacityUnitsET = (EditText)findViewById(R.id.editCapacityUnits);
        editExpiryDateET = (EditText)findViewById(R.id.editExpiryDate);

        if (getIntent().getExtras()==null)
            Log.d("null!", "null!");

        householdID = getIntent().getExtras().getString("householdID");
        editName = getIntent().getExtras().getString("pname");
        editCategory = getIntent().getExtras().getString("category");
        editCapacity = getIntent().getExtras().getString("capacity");
        editCapacityUnits = getIntent().getExtras().getString("capacityUnits");
        image = getIntent().getExtras().getString("imageUrl");
        editExpiryDate = getIntent().getExtras().getString("expiryDate");
        inventoryID = getIntent().getExtras().getString("inventoryID");

        button();

        editNameTV.setText(editName);
        editCategoryTV.setText(editCategory);
        editCapacityET.setText(editCapacity);
        editCapacityUnitsET.setText(editCapacityUnits);
        editExpiryDateET.setText(editExpiryDate);

    }

    public void button() {

        Button editButton=(Button)findViewById(R.id.bth_editSubmit);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("@@ EDIT. INVENTORY ID", inventoryID + "");

                String editedName = editNameTV.getText().toString();
                String editedCategory = editCategoryTV.getText().toString();
                String editedCapacity = editCapacityET.getText().toString();
                String editedCapacityUnits = editCapacityUnitsET.getText().toString();
                String editedExpiryDate = editExpiryDateET.getText().toString();

                Log.d("@@@@@@@@@ EDIT EXPIRY", editedExpiryDate + "");

                if (!(StringUtilities.isAlphaNumericPunctuation(editedName))&&!(StringUtilities.isAlphaNumericPunctuation(editedCategory))){
                    Toast.makeText(EditEntryActivity.this, "Error: Invalid Entry in field.", Toast.LENGTH_SHORT).show();
                } else if (editedName.equalsIgnoreCase("") || editedCategory.equalsIgnoreCase("") ) {
                    Toast.makeText(EditEntryActivity.this, "Error: All fields required.", Toast.LENGTH_SHORT).show();
                } else if (editedName.equals(editName)&&editedCategory.equals(editCategory)&&editedCapacity.equals(editCapacity)&&editedCapacityUnits.equals(editCapacityUnits)&&editedExpiryDate.equals(editExpiryDate)){
                    Toast.makeText(EditEntryActivity.this, "Error: No changes made.", Toast.LENGTH_SHORT).show();
                }
                else {

                    // add the data collected to params variable to be sent to server
                    // 6: action (edit/delete), id, pname, description, rating, imageUrl

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("action", "edit"));
                    params.add(new BasicNameValuePair("inventory_id", inventoryID));
                    params.add(new BasicNameValuePair("product_name", editedName));
                    params.add(new BasicNameValuePair("category", editedCategory));
                    params.add(new BasicNameValuePair("capacity", editedCapacity));
                    params.add(new BasicNameValuePair("capacity_units", editedCapacityUnits));
                    params.add(new BasicNameValuePair("picture", image));
                    params.add(new BasicNameValuePair("expiry_date", editedExpiryDate));


                    Log.d("id", inventoryID + "");
                    Log.d("pname", editedName);
                    Log.d("editedCategory", editedCategory);
                    Log.d("editedCapacity", editedCapacity);
                    Log.d("editedCapacityUnits", editedCapacityUnits);
                    Log.d("imageUrl", image);
                    Log.d("editedExpiryDate", editedExpiryDate);


                    new EditEntry(params, EditEntryActivity.this, householdID).execute();

                    //startActivity(new Intent(HomeuEditEntryActivity.this,HomeuListActivity.class));
                }
            }
        });

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
                    hideSoftKeyboard(EditEntryActivity.this);
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