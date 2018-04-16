package company.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by user on 16.04.2018.
 */

// TODO 274 ) Implementing testing class as AndroidJUnit4
@RunWith(AndroidJUnit4.class)
// TODO 273 ) Creating MainActivityRecipeNameTest class to determine whether the right recipe is displayed on the screen or not.
public class MainActivityRecipeNameTest {

    // TODO 275 ) Defining sample recipe
    private static final String RECIPE_NAME = "Brownies";

    // TODO 276 ) Defining Rule
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    // TODO 277 ) Defining clickRecipeCardView_clickRecipe_showText to determine whether recipe name is displayed or not.
    @Test
    public void clickRecipeCardView_clickRecipe_showText(){

        // TODO 278 ) Defining the right position of recipe name defines as variable in the class above.
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // TODO 279 ) Checking whether the defined recipe is equal to recipe name coming from URL
        onView(withId(R.id.recipe_image_name_textview)).check(matches(withText(RECIPE_NAME)));
    }

}
