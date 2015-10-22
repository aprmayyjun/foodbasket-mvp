package teamrenaissance.foodbasket.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mirasense.scanditsdk.ScanditSDKAutoAdjustingBarcodePicker;
import com.mirasense.scanditsdk.interfaces.ScanditSDK;
import com.mirasense.scanditsdk.interfaces.ScanditSDKCode;
import com.mirasense.scanditsdk.interfaces.ScanditSDKOnScanListener;
import com.mirasense.scanditsdk.interfaces.ScanditSDKScanSession;

import teamrenaissance.foodbasket.R;

public class ScannerActivity extends AppCompatActivity implements ScanditSDKOnScanListener {

    // The main object for recognizing a displaying barcodes.
    private ScanditSDK mBarcodePicker;

    private Bundle extras;
    private Boolean newuser = false;
    private String householdID = null;

    // Enter your Scandit SDK App key here.
    public static final String sScanditSdkAppKey = "i/UIWO7JIYDxe/hF3o7HskB16fa4rFqqpUOkxRCR7RM";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        extras = getIntent().getExtras();
        householdID = extras.getString("householdID");
        if (extras.getString("newuser")!=null) {
            newuser = true;
        }

        // Switch to full screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scanner);

        // Initialize and start the bar code recognition.
        initializeAndStartBarcodeScanning();
    }


    // Initializes and starts the bar code scanning.
    public void initializeAndStartBarcodeScanning() {
        
        // We instantiate the automatically adjusting barcode picker that will
        // choose the correct picker to instantiate. Be aware that this picker
        // should only be instantiated if the picker is shown full screen as the
        // legacy picker will rotate the orientation and not properly work in
        // non-fullscreen.
        ScanditSDKAutoAdjustingBarcodePicker picker = new ScanditSDKAutoAdjustingBarcodePicker(
                this, sScanditSdkAppKey, ScanditSDKAutoAdjustingBarcodePicker.CAMERA_FACING_BACK);

        // Add both views to activity, with the scan GUI on top.
        setContentView(picker);
        mBarcodePicker = picker;

        // Register listener, in order to be notified about relevant events
        // (e.g. a successfully scanned bar code).
        mBarcodePicker.addOnScanListener(this);
    }


    @Override
    protected void onPause() {
        // When the activity is in the background immediately stop the
        // scanning to save resources and free the camera.
        mBarcodePicker.stopScanning();

        super.onPause();
    }

    @Override
    protected void onResume() {
        // Once the activity is in the foreground again, restart scanning.
        mBarcodePicker.startScanning();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        mBarcodePicker.stopScanning();
        finish();
    }


    // this callback is called whenever a barcode is decoded successfully.
    @Override
    public void didScan(ScanditSDKScanSession session) {
        String message = "";
        Toast mToast = null;

        for (ScanditSDKCode code : session.getNewlyDecodedCodes()) {
            String data = code.getData();
            // truncate code to certain length
            String cleanData = data;
            if (data.length() > 30) {
                cleanData = data.substring(0, 25) + "[...]";
            }
            if (message.length() > 0) {
                message += "\n\n\n";
            }
            message += cleanData;
            message += "\n\n(" + code.getSymbologyString() + ")";
        }
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        mToast.show();
    }



    // To set up the action bar in the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.scanner_option_1) {
            Intent toManualEntry = new Intent(ScannerActivity.this, CreateEntryActivity.class);
            toManualEntry.putExtra("householdID", householdID);
            startActivity(toManualEntry);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
