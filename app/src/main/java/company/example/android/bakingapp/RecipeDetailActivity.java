package company.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Creating RecipeDetailActivity class to show detail information about recipe
public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
    }

}
