package teamrenaissance.foodbasket.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.admin.LoginRegisterActivity;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setButtonLogout() ;
    }


    public void setButtonLogout(){
        Button logoutButton = (Button)findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(SettingsActivity.this, LoginRegisterActivity.class);
                startActivity(toLogin);
            }
        });
    }
}
