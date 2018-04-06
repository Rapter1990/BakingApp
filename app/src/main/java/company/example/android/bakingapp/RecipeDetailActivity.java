package company.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.data.RecipeStep;
import company.example.android.bakingapp.fragments.RecipeStepsFragment;
import timber.log.Timber;

// TODO 164 ) Creating RecipeDetailActivity class to show detail information about recipe and implementing OnStepSelectedListener
public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepSelectedListener{

    // TODO 165 ) Defining a LOG_TAG
    private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();


    // TODO 166 ) Defining Recipe Object
    private static Recipe recipe;

    // TODO 169 ) Defining mTwoPane for using widget
    private static boolean twoPane;

    // TODO 169 ) Defining selectedStep for defining the steps of recipe
    private RecipeStep selectedStep;

    // TODO 173 ) Defining stepDetailFragment for designing layout
    private RecipeStepsFragment stepDetailFragment;


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

        // TODO 171 ) Getting information about selectedStep and twoPane saved before via onSaveInstanceState
        if (savedInstanceState != null) {
            selectedStep = savedInstanceState.getParcelable("selectedStep");
            twoPane = savedInstanceState.getBoolean("twoPane");
        }

        // CONTINUE STEP 192

    }


    // TODO 170 ) Defining onSaveInstanceState to put step and two pane infomration
    // because of determining whether widget works or normal screen works
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("selectedStep", selectedStep);
        outState.putBoolean("twoPane", twoPane);
    }

    // TODO 172 ) Defining onStepSelected for opening new Intent to show step with video according to widget or normal screen
    @Override
    public void onStepSelected(RecipeStep currentStep) {
        Bundle extras = new Bundle();
        extras.putParcelable("CURRENT_RECIPE_STEP", currentStep);
        Intent recipeStepDetailActivityIntent = new Intent(this, RecipeStepDetailActivity.class);
        recipeStepDetailActivityIntent.putExtras(extras);
        startActivity(recipeStepDetailActivityIntent);
    }

    // TODO 183 ) Defining  getCurrentStep for getting RecipeStep from Stepdetail
    public RecipeStep getCurrentStep() {
        return selectedStep;
    }
}
