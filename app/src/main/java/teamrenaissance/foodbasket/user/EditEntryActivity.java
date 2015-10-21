package teamrenaissance.foodbasket.user;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class EditEntryActivity extends Activity{
    private String editName = "";
    private String editExp = "";
    private String rating = "";
    private int ratingBuffer;
    private String householdID = "";
    private String image = "";
    private String id = "";

    EditText editNameET;
    EditText editExpET;
    RatingBar editRateRB;

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

        editNameET = (EditText)findViewById(R.id.editName);
        editExpET = (EditText)findViewById(R.id.editExp);
        editRateRB = (RatingBar)findViewById(R.id.editRate);

        if (getIntent().getExtras()==null)
            Log.d("null!", "null!");
        householdID = getIntent().getExtras().getString("householdID");

        editName = getIntent().getExtras().getString("pname");
        editExp = getIntent().getExtras().getString("description");
        rating = getIntent().getExtras().getString("rating");
        image = getIntent().getExtras().getString("imageUrl");
        id = getIntent().getExtras().getString("id");

        button();

        editNameET.setText(editName);
        editExpET.setText(editExp);

        float ratingScore = Float.parseFloat(rating);
        editRateRB.setRating(ratingScore/2);

    }

    public void button() {

        Button editButton=(Button)findViewById(R.id.bth_editSubmit);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String editedName = editNameET.getText().toString();
                String editedExp = editExpET.getText().toString();

                ratingBuffer =((int) (2*(editRateRB.getRating())));
                String editedRating = String.valueOf(ratingBuffer);

                if (!(StringUtilities.isAlphaNumericPunctuation(editedName))&&!(StringUtilities.isAlphaNumericPunctuation(editedExp))){
                    Toast.makeText(EditEntryActivity.this, "Error: Invalid Entry in field.", Toast.LENGTH_SHORT).show();
                } else if (editedName.equalsIgnoreCase("") || editedExp.equalsIgnoreCase("") ) {
                    Toast.makeText(EditEntryActivity.this, "Error: All fields required.", Toast.LENGTH_SHORT).show();
                } else if (editedName.equals(editName)&&editedExp.equals(editExp)&&editedRating.equals(rating)){
                    Toast.makeText(EditEntryActivity.this, "Error: No changes made.", Toast.LENGTH_SHORT).show();
                }
                else {

                    // add the data collected to params variable to be sent to server
                    // 6: action (edit/delete), id, pname, description, rating, imageUrl

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("action", "edit"));
                    params.add(new BasicNameValuePair("id", id));
                    params.add(new BasicNameValuePair("pname", editedName));
                    params.add(new BasicNameValuePair("description", editedExp));
                    params.add(new BasicNameValuePair("rating", editedRating));
                    params.add(new BasicNameValuePair("imageUrl", image));


                    Log.d("id", id+"");
                    Log.d("pname", editedName);
                    Log.d("description", editedExp);
                    Log.d("rating", editedRating);
                    Log.d("imageUrl", image);


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