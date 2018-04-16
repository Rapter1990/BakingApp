package company.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.data.RecipeStep;
import timber.log.Timber;

// TODO 178 ) Creating RecipeStepDetailActivity for displaying detail step with its related video
public class RecipeStepDetailActivity extends AppCompatActivity {

    // TODO 179 ) Defining LOG_TAG variable
    private static final String LOG_TAG = RecipeStepDetailActivity.class.getSimpleName();

    // TODO 180 ) Defining button and step detail description
    @BindView(R.id.step_description)
    TextView stepDetailDescription;

    @BindView(R.id.step_button)
    Button stepButton;

    // TODO 182 ) Defining currentStep as RecipeStep
    private RecipeStep currentStep;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        // TODO 181 ) Defining ButterKnife to perceive textview and button.
        ButterKnife.bind(this);

        // TODO 194 ) Getting current step from RecipeDetailActivity via getParcelable with its key value
        Bundle extras = getIntent().getExtras();
        currentStep = extras.getParcelable("CURRENT_RECIPE_STEP");

        // TODO 269 ) Defining orientation of layout which is widget or not.
        int currentOrientation = getResources().getConfiguration().orientation;

        // TODO 270 ) Checking whether landspace is shown on the right side of layout in FrameLayout in the widget and
        // covering the description and video of recipe at first
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            stepDetailDescription.setVisibility(View.GONE);
            stepButton.setVisibility(View.GONE);
        }
        // TODO 271 ) After covering these attribute, show each step detail and video while pressing each one related with short description of recipe.
        else {
            // TODO 196 ) Defining button to click next and show related step and video
            stepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // TODO 197 ) Getting current the id of step
                    String stepId = String.valueOf(Integer.parseInt(currentStep.getStepId()));

                    Timber.i(LOG_TAG + "/ Step Id : " + stepId);

                    // TODO 203 ) Getting the recipe step according to the step id displayed on the screen
                    RecipeStep stepNavigation = RecipeDetailActivity.navigateStep(stepId);

                    // TODO 204 ) Checking whether stepNavigation is null or not
                    if (stepNavigation != null) {

                        // TODO 205 ) Starting with a new activity in terms of the step of recipe
                        Bundle extras = new Bundle();
                        extras.putParcelable("CURRENT_RECIPE_STEP", stepNavigation);
                        Intent stepDetailActivityIntent = new Intent(RecipeStepDetailActivity.this,
                                RecipeStepDetailActivity.class);
                        stepDetailActivityIntent.putExtras(extras);
                        startActivity(stepDetailActivityIntent);
                    } else {
                        Toast.makeText(RecipeStepDetailActivity.this,
                                getString(R.string.noavailablestep), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    // TODO 195 ) Getting current step from RecipeDetailActivity via getParcelable with its key value
    public RecipeStep getCurrentStep() {
        return getIntent().getExtras().getParcelable("CURRENT_RECIPE_STEP");
    }



}