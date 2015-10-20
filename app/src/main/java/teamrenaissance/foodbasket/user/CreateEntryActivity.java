package teamrenaissance.foodbasket.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
//import teamrenaissance.foodbasket.data.ImageUtil;
import teamrenaissance.foodbasket.data.ManageEntryUtil.CreateEntryWithoutPicture;
import teamrenaissance.foodbasket.data.ManageEntryUtil.CreateEntryWithPicture;
import teamrenaissance.foodbasket.admin.LoginRegisterActivity;
import teamrenaissance.foodbasket.admin.StringUtilities;
import teamrenaissance.foodbasket.R;


/**
 * For the Home User.
 * Boundary class to allow creation of a journal entry.
 * Collects the input from the user and passes the information to the corresponding control class.
 *
 */
public class CreateEntryActivity extends Activity{
    private String newName;
    private String newExp;
    private String rating;
    private int ratingBuffer;
    private String username;
    private Date date;
    private String dateString;

    private EditText newnameET;
    private EditText newexpET;
    private RatingBar rateRB;
    private ImageView img;
    private Button submitButton;

    private static final int SELECT_PICTURE = 1;
    private Uri imageUri = null;
    private String imagePath = "";
    private Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_user_create_entry);
        setupUI(findViewById(R.id.createScreen));

        username = getIntent().getExtras().getString("username");

        newnameET = (EditText)findViewById(R.id.newName);
        newexpET = (EditText)findViewById(R.id.newExp);
        rateRB = (RatingBar)findViewById(R.id.newRate);
        submitButton = (Button) findViewById(R.id.btn_newSubmit);
        img = (ImageView) findViewById(R.id.productImageView);

        setButtons();
        setPictureButton();
    }

    public void setButtons () {

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                newName = newnameET.getText().toString();
                newExp = newexpET.getText().toString();
                ratingBuffer = ((int) (2*(rateRB.getRating())));
                rating = String.valueOf(ratingBuffer);
                date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                dateString = sdf.format(date);

                // Check for validity of input
                if (!(StringUtilities.isAlphaNumericPunctuation(newName))&&!(StringUtilities.isAlphaNumericPunctuation(newExp))){
                    Toast.makeText(HomeuCreateEntryActivity.this, "Error: Invalid Entry into field", Toast.LENGTH_SHORT).show();
                } else if (newName.equalsIgnoreCase("") || newExp.equalsIgnoreCase("") ) {
                    Toast.makeText(HomeuCreateEntryActivity.this, "Error: All fields required", Toast.LENGTH_SHORT).show();
                } else {

                    // add the data collected to params variable to be sent to server
                    // 6: username, pname, description, rating, imageUrl, date;
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("username", username));
                    params.add(new BasicNameValuePair("pname", newName));
                    params.add(new BasicNameValuePair("description", newExp));
                    params.add(new BasicNameValuePair("rating", rating));
                    params.add(new BasicNameValuePair("date", dateString));

                    if (bitmap == null)
                        new CreateEntryWithoutPicture(params, HomeuCreateEntryActivity.this, username).execute();
                    else
                        new CreateEntryWithPicture(params, HomeuCreateEntryActivity.this, username).execute(bitmap);
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
                imagePath = ImageUtil.getPath(HomeuCreateEntryActivity.this, imageUri);
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
                    hideSoftKeyboard(HomeuCreateEntryActivity.this);
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