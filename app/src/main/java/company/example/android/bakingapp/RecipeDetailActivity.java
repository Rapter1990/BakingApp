package company.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import company.example.android.bakingapp.data.Recipe;
import timber.log.Timber;

// TODO 164 ) Creating RecipeDetailActivity class to show detail information about recipe
public class RecipeDetailActivity extends AppCompatActivity {

    // TODO 165 ) Defining a LOG_TAG
    private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();


    // TODO 166 ) Defining Recipe Object
    private static Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // TODO 167 ) Getting the Recipe from the Bundle defined in MainActivity
        Bundle extras = getIntent().getExtras();
        recipe = extras.getParcelable("RECIPE_DETAIL_INFORMATION");

        // TODO 168 ) Checking whether the inofrmation of recipe is regiht or not
        Timber.i(LOG_TAG +  "/ Recipe selected name from Detail Activity is: " + recipe.getName());
        Timber.i(LOG_TAG+ "/ Ingredients: " + recipe.getIngredients().size());
        Timber.i(LOG_TAG+ "/ Steps: " + recipe.getSteps().size());


    }

}
