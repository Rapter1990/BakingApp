package company.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.data.RecipeStep;
import company.example.android.bakingapp.fragments.RecipeStepsDetailFragment;
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
    private RecipeStepsDetailFragment stepDetailFragment;

    // TODO 263 ) Defining recipe step detail information as a container step_detail_container
    //@BindView(R.id.step_detail_container)
    //FrameLayout stepDetailContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // TODO 264 ) Defining ButterKnife
        //ButterKnife.bind(this);

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
        else{ // TODO 262 ) Checking whether the saved object is in the widget or not and defining twoPane
            if(findViewById(R.id.step_detail_container) != null){ // TODO 265 ) Checking whether step detail FrameLayout is null or not
                twoPane = true;
                selectedStep = recipe.getSteps().get(0); // TODO 266 ) Getting first step of selected recipe
            }else{
                twoPane = false;
            }
        }

        // TODO 267 ) Checking whether the layout is two panel structure or not.
        if(twoPane){
            // TODO 268 ) Checking whether recipe step as savedInstanceState is null or not according to whether step was saved before or not
            if(savedInstanceState == null){
                stepDetailFragment = new RecipeStepsDetailFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_container,stepDetailFragment,"Step Video Not Saved Before");
            }else{
                stepDetailFragment = new RecipeStepsDetailFragment();
                stepDetailFragment = (RecipeStepsDetailFragment)
                        getSupportFragmentManager().getFragment(savedInstanceState,"Step Video Saved Before");
            }
        }

    }


    // TODO 170 ) Defining onSaveInstanceState to put step and two pane informaation
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

        // TODO 272 ) Checking whether the layout is designed for twopanel as widget or normal screen then calling new Activity
        if(twoPane){
            RecipeStepsDetailFragment stepDetailFragment = new RecipeStepsDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }
        else{
            Bundle extras = new Bundle();
            extras.putParcelable("CURRENT_RECIPE_STEP", currentStep);
            Intent recipeStepDetailActivityIntent = new Intent(this, RecipeStepDetailActivity.class);
            recipeStepDetailActivityIntent.putExtras(extras);
            startActivity(recipeStepDetailActivityIntent);
        }
    }

    // TODO 183 ) Defining  getCurrentStep for getting RecipeStep from Stepdetail
    public RecipeStep getCurrentStep() {
        return selectedStep;
    }


    // TODO 198 ) Getting current step of recipe
    public static RecipeStep navigateStep(String stepId) {

        RecipeStep recipeStep = null;

        // TODO 199 ) Getting all steps for current recipe
        ArrayList<RecipeStep> currentRecipeSteps = recipe.getSteps();


        for(int stepIndex = 0;stepIndex<currentRecipeSteps.size();stepIndex++){

            // TODO 206 ) Checking whether stepId equals to stepId of last recipe step
            if(stepId.equals(String.valueOf(currentRecipeSteps.size()-1))){
                recipeStep = null;
            }
            // TODO 200 ) Checking whether stepId equals to stepId of current recipe step
            else if(stepId.equals(currentRecipeSteps.get(stepIndex).getStepId())){
                recipeStep = currentRecipeSteps.get(stepIndex);

            }
            // TODO 201 ) Because some stepId is missing from the JSON file , assigning stepId to stepId of current recipe step
            else if(Integer.parseInt(stepId) < stepIndex){
                recipeStep = currentRecipeSteps.get(stepIndex);
                break;
            }

        }

        // TODO 202 ) Returning the current step
        return recipeStep;
    }


}
