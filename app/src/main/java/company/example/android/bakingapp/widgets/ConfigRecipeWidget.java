package company.example.android.bakingapp.widgets;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.R;
import company.example.android.bakingapp.RecipeDetailActivity;
import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.utilities.RecipeJsonUtils;
import timber.log.Timber;

public class ConfigRecipeWidget extends Activity {

    // TODO 209 ) Defining LOG_TAG
    private static final String LOG_TAG = ConfigRecipeWidget.class.getSimpleName();

    // TODO 210 ) Defining widget id as a sentinel value as 0
    int appwidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // TODO 211 ) Defining each attributef activity_config_recipe_widget layout file as BindView
    @BindView(R.id.spinner_recipe_name)
    Spinner recipeNameSpinner;
    @BindView(R.id.btn_add)
    Button addWidgetButton;

    // TODO 212 ) Defining recipe list
    private ArrayList<Recipe> recipeList;

    // TODO 213 ) Defining key values of prefrence to save recipe to show it in the widget, get current recipe,delete recipe
    // and get recipe detail
    private static final String PREFS_NAME = "RecipeWidget";
    private static final String PREF_PREFIX_KEY = "recipeWidget";
    private static final String PREF_RECIPE_KEY = "currentRecipe";

    // TODO 214 ) Defining empty constructor
    public ConfigRecipeWidget(){
        super();
    }

    // TODO 217 ) Defining the ingredient of recipe as a HashMap<Name,Ingredient>
    private static final HashMap<String, String> recipeDetailHashMap = new HashMap<>();

    // TODO 218 ) Defining spinner options
    private static ArrayList<String> spinnerOptions = new ArrayList<>();

    // TODO 228 ) Defining AppWidgetManager
    private static AppWidgetManager widgetManager;

    // TODO 230 ) Defining RemoteViews for layout of widget
    private static RemoteViews remoteViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_recipe_widget);

        // TODO 215 ) Set RESULT_CANCELED for cancelling widget placement operation while pressing back button
        setResult(RESULT_CANCELED);

        // TODO 216 ) Defining ButterKnife to perceive each attribute of layout
        ButterKnife.bind(this);

        // TODO 296 ) Setting layout size of activity
        getWindow().setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // TODO 219 ) Defining ArrayAdapter with spinnerOptions and setting adapter style as dropdown structure to spinner
        final ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        recipeNameSpinner.setAdapter(spinnerAdapter);

        // TODO 220 ) Defining AsyncTask to list recipe on the widget
        new AsyncTask<Void, Void, ArrayList<Recipe>>() {


            @Override
            protected ArrayList<Recipe> doInBackground(Void... voids) {
                // TODO 221 ) Defining empty arraylist
                ArrayList<Recipe> recipeArrayList = null;
                try {
                    // TODO 222 ) Getting recipe according to recipe url defined as url
                    recipeArrayList = RecipeJsonUtils.getRecipes(RecipeJsonUtils.RECIPE_URL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // TODO 223 ) Returning recipeArrayList
                return recipeArrayList;
            }


            @Override
            protected void onPostExecute(ArrayList<Recipe> recipes) {
                // TODO 224 ) Assigning recipes getting from doInBackground to recipeList
                recipeList = recipes;

                // TODO 225 ) Adding recipelist to spinner with each recipe information
                for (Recipe recipe : recipes) {
                    String recipeIngredientList = null;
                    spinnerOptions.add(recipe.getName());
                    for (String ingredient : recipe.getIngredients()) {
                        if (recipeIngredientList == null) {
                            recipeIngredientList = ingredient;
                        } else {
                            recipeIngredientList = recipeIngredientList + ",\n" + ingredient;
                        }
                    }
                    // TODO 226 ) Adding the name of recipe with its ingredient list
                    recipeDetailHashMap.put(recipe.getName(), recipeIngredientList);
                }
                // TODO 227 ) Saving information by using notifyDataSetChanged to set it adapter
                spinnerAdapter.notifyDataSetChanged();
            }


        }.execute();


        // TODO 229 ) Initializing AppWidgetManager
        widgetManager = AppWidgetManager.getInstance(this);

        // TODO 231 ) Initializing RemoteViews
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.recipe_widget_provider);

        // TODO 232 ) Finding the id of widget from the intent with key
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appwidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // TODO 233 ) Closing Intent
        if (appwidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        // TODO 233 ) Giving button action to add selected recipe information in the remoteviews
        addWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO 234 ) Defining Recipe
                Recipe recipe = null;

                // TODO 235 ) Getting recipe from spinner as a seleted one
                String selectedRecipe = recipeNameSpinner.getSelectedItem().toString();

                // TODO 236 ) Assigning selectedRecipe to recipe
                for(Recipe r : recipeList) {
                    if (r.getName().equals(selectedRecipe)) {
                        recipe = r;
                    }
                }

                // TODO 237 ) Setting related information about recipe to remoteviews
                remoteViews.setTextViewText(R.id.widget_recipe_name_texview, selectedRecipe);
                remoteViews.setTextViewText(R.id.widget_recipe_ingredient_list, recipeDetailHashMap.get(selectedRecipe));
                String imageDrawableResourceName = (selectedRecipe.replaceAll("\\s+", "")).toLowerCase();
                int imageResourceId = getApplicationContext().getResources().getIdentifier(
                        imageDrawableResourceName,
                        "drawable",
                        getApplicationContext().getPackageName());
                remoteViews.setImageViewResource(R.id.widget_recipe_image, imageResourceId);


                // TODO 243 ) Saving selected Recipe via Shared Preference
                saveRecipePreference(getApplicationContext(),
                        appwidgetId,
                        selectedRecipe + ":" + recipeDetailHashMap.get(selectedRecipe),
                        recipe);


                // TODO 244 ) Sending recipe to its detail activity
                Bundle extras = new Bundle();
                extras.putParcelable("RECIPE_DETAIL_INFORMATION", recipe);
                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtras(extras);

                // TODO 245 ) Giving notification feature to intent according to last process as defining FLAG_UPDATE_CURRENT
                PendingIntent pending =
                        PendingIntent.getActivity(getApplicationContext(), 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                // TODO 246 ) Giving onClick feature to pending intent
                remoteViews.setOnClickPendingIntent(R.id.widget_recipe_card, pending);

                // TODO 247 ) Updating pending in the removiews in widget
                widgetManager.updateAppWidget(appwidgetId, remoteViews);

                // TODO 248 ) Showing result by using Intent to assign EXTRA_APPWIDGET_ID to widgetId and confirming it
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appwidgetId);
                setResult(RESULT_OK, resultValue);
                finish();


            }
        });

    }


    // TODO 238 ) Saving Recipe as a Preference
    public static void saveRecipePreference(Context context, int appWidgetId, String text, Recipe currentRecipe){
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        Gson gson = new Gson();
        String json = gson.toJson(currentRecipe);
        prefs.putString(PREF_RECIPE_KEY + appWidgetId, json);
        prefs.apply();
        Timber.d(LOG_TAG, "Recipe saved: " + text);
    }

    // TODO 240 ) Getting current Recipe
    public static Recipe getCurrentRecipePreference(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String json = prefs.getString(PREF_RECIPE_KEY + appWidgetId, null);
        Gson gson = new Gson();
        Recipe currentRecipe = gson.fromJson(json, Recipe.class);
        Timber.d(LOG_TAG, "Current Recipe fetched: " + json);
        return currentRecipe;
    }

    // TODO 241 ) Deleting Recipe
    public static void deleteRecipePreference(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
        Timber.d(LOG_TAG, "Recipe deleted");
    }

    // TODO 242 ) Getting recipe Detail Information
    public static String getRecipeDetailPreference(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String recipeDetails = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        Timber.d(LOG_TAG, "Recipe details : " + recipeDetails);
        return recipeDetails;
    }

}
