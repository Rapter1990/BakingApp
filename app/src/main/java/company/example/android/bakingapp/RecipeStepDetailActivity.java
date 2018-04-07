package company.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.data.RecipeStep;

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

        // TODO 194 ) Get current step from RecipeDetailActivity via getParcelable with its key value
        Bundle extras = getIntent().getExtras();
        currentStep = extras.getParcelable("CURRENT_RECIPE_STEP");


    }


}