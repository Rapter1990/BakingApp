package company.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.not;

/**
 * Created by user on 16.04.2018.
 */

// TODO 280 ) Implementing testing class as AndroidJUnit4
@RunWith(AndroidJUnit4.class)
// TODO 281 ) Creating MainActivityIntentTest class to provide passing right recipe name via intent
public class MainActivityIntentTest {

    // TODO 282 ) Defining Rule
    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);

    // TODO 283 ) Blocking all external intents before testing
    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    // TODO 284 ) Defining checkIntent_RecipeName_DetailActivity method to check whether the right intent is open or not.
    @Test
    public void checkIntent_RecipeName_DetailActivity() {

        // TODO 285 ) Defining the position of recipe name for checking
        onView(ViewMatchers.withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(1,click()));

        // TODO 286 ) Getting the intent name of recipe while opening an intent
        intended(hasExtra("id",2));

    }
}
