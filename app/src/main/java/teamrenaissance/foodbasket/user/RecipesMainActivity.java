package teamrenaissance.foodbasket.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import teamrenaissance.foodbasket.R;
import teamrenaissance.foodbasket.data.GetRecipeUtil;

public class RecipesMainActivity extends AppCompatActivity {

    private String householdID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_main);

        householdID = getIntent().getExtras().getString("householdID");

        setRecipeButton_1() ;
    }

    private void setRecipeButton_1() {
        Button btn = (Button)findViewById(R.id.btn_Recipes_1);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent().setClass(RecipesMainActivity.this, RecipesListActivity.class);
//                intent.putExtras(getIntent().getExtras());

                initialise();
            }
        });
    }

    private void initialise() {

        // add the data collected to params variable to be sent to server
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("household_id", householdID));
        new GetRecipeUtil.RetrieveRecipesList(params, RecipesMainActivity.this, householdID).execute();

    }
}
