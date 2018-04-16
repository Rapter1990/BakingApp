package company.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.data.RecipeStep;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by user on 16.04.2018.
 */

// TODO 287 ) Implementing testing class as AndroidJUnit4
@RunWith(AndroidJUnit4.class)
// TODO 288 ) Creating RecipeDetailActivityStepDescriptionTest class to get right step description.
public class RecipeDetailActivityStepDescriptionTest {

    // TODO 289 ) Defining sample step
    private RecipeStep step1 = new RecipeStep("1", "Intent step", "step Description",
            null,null);

    // TODO 290 ) Defining sample ingredient
    private String ingredient = "Ingredient Step";

    // TODO 291 ) Defining stepList
    private ArrayList<RecipeStep> stepList = new ArrayList<RecipeStep>(Arrays.asList(step1));

    // TODO 292 ) Defining ingredientList
    private ArrayList<String> ingredientList = new ArrayList<String>(Arrays.asList(ingredient));

    // TODO 293 ) Defining recipe by using samples defined above
    private Recipe recipe = new Recipe("1", "Brownies", null,null,ingredientList,stepList);


    // TODO 294 ) Defining rule to get step and ingredient information of recipe in detail activity
    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule
            = new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent =
                    new Intent(InstrumentationRegistry.getContext(),RecipeDetailActivity.class);
            Bundle intentBundle = new Bundle();
            intentBundle.putParcelable("RECIPE_DETAIL_INFORMATION", recipe);
            intent.putExtras(intentBundle);
            return intent;
        }
    };


    // TODO 295 ) Determining whether right step description is displayed or not.
    @Test
    public void clickRecipeStep_displayStepDescription() {

        onView(withId(R.id.recipe_steps_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_description))
                .check(matches(withText("step Description")));

    }


}
