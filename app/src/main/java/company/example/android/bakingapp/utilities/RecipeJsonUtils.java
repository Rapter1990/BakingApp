package company.example.android.bakingapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.data.RecipeStep;
import timber.log.Timber;

/**
 * Created by user on 2.04.2018.
 */

// TODO 23 ) Creating RecipeJsonUtils for getting information from Json style file
public class RecipeJsonUtils {

    // TODO 42 ) Defining LOG attribute to determine whether the issue is thrown or not
    private static final String LOG_TAG = RecipeJsonUtils.class.getSimpleName();

    // TODO 24 ) Defining a file name getting information
    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    // TODO 25 ) Defining Recipe "id" key
    public static final String RECIPE_ID = "id";

    // TODO 26 ) Defining Recipe "name" key
    public static final String RECIPE_NAME = "name";

    // TODO 27 ) Defining Recipe "servings" key
    public static final String RECIPE_SERVINGS = "name";

    // TODO 28 ) Defining Recipe "image" key
    public static final String RECIPE_IMAGE = "image";

    // TODO 29 ) Defining Recipe "ingredients" key
    public static final String RECIPE_INGREDIENTS = "ingredients";

    // TODO 30 ) Defining Recipe "quantity" key of "ingredients"
    public static final String RECIPE_INGREDIENTS_QUANTITY = "quantity";

    // TODO 31 ) Defining Recipe "measure" key of "ingredients"
    public static final String RECIPE_INGREDIENTS_MEASURE = "measure";

    // TODO 32 ) Defining Recipe "ingredient" key of "ingredients"
    public static final String RECIPE_INGREDIENTS_INGREDIENT = "ingredient";

    // TODO 33 ) Defining Recipe "steps" key
    public static final String RECIPE_STEPS = "steps";

    // TODO 34 ) Defining Recipe "id" key of "steps"
    public static final String RECIPE_STEPS_ID = "id";

    // TODO 35 ) Defining Recipe "shortDescription" key of "steps"
    public static final String RECIPE_STEPS_SHORTDESCRIPTON = "shortDescription";

    // TODO 36 ) Defining Recipe "description" key of "steps"
    public static final String RECIPE_STEPS_DESCRIPTON = "description";

    // TODO 37 ) Defining Recipe "videoURL" key of "steps"
    public static final String RECIPE_STEPS_VIDEOURL = "videoURL";

    // TODO 38 ) Defining Recipe "thumbnailURL" key of "steps"
    public static final String RECIPE_STEPS_THUMBNAILURL = "thumbnailURL";


    // TODO 39 ) Defining A method to return a list of recipe after inserting each recipe with its attributes
    public static ArrayList<Recipe> getRecipes(String requestUrl) throws JSONException {

        // TODO 40 ) Defining an ArrayList for recipe object
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        // TODO 41 ) Defining url object
        URL recipeRequestUrl = null;
        try {
            recipeRequestUrl = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Timber.d(LOG_TAG + "/ Error : " +e);
        }

        // TODO 43 ) Checking whether the url is null or not. It it is null, return null by back to the callling function.
        if (recipeRequestUrl == null) {
            return null;
        }

        try {

            // TODO 44 ) Getting url from getResponseUrl method
            String urlResponse = NetworkUtils.getResponseFromUrl(recipeRequestUrl);

            // TODO 52 ) Defining A JSONArray for response Url
            JSONArray recipeJSONArray = new JSONArray(urlResponse);

            // TODO 53 ) Defining a loop to get each attributes of recipe and
            // to create a new Recipe object with adding its attrbuttes before inserting it into arraylist and then return it
            for(int i=0;i<recipeJSONArray.length();i++){

                // TODO 54 ) Defining two ArrayList for ingredients and steps for Recipe
                ArrayList<String> recipeIngredientList = new ArrayList<String>();
                ArrayList<RecipeStep> recipeStepList = new ArrayList<RecipeStep>();

                // TODO 55 ) Defining JSON object from get object from recipeJSONArray
                JSONObject recipeObject = (JSONObject) recipeJSONArray.get(i);

                // TODO 56 ) Getting information from recipeObject
                String id = recipeObject.getString(RECIPE_ID);
                String name = recipeObject.getString(RECIPE_NAME);
                String servings = recipeObject.getString(RECIPE_SERVINGS);
                String image = recipeObject.getString(RECIPE_IMAGE);
                JSONArray ingredients = recipeObject.getJSONArray(RECIPE_INGREDIENTS);
                JSONArray steps = recipeObject.getJSONArray(RECIPE_STEPS);

                // TODO 57 ) Showing log to display information via Timber
                Timber.d(LOG_TAG+ "/ Recipe id: " + id);
                Timber.d(LOG_TAG+ "/ Recipe name: " + name);
                Timber.d(LOG_TAG+ "/ Recipe servings: " + servings);
                Timber.d(LOG_TAG+ "/ Recipe image: " + image);
                Timber.d(LOG_TAG+ "/ Recipe ingredients size: " + ingredients.length());
                Timber.d(LOG_TAG+ "/ Recipe Steps size: " + steps.length());

                // TODO 58 ) Showing ingredient of Recipe Object via loop
                for(int ingredientIndex=0;ingredientIndex<ingredients.length();ingredientIndex++){

                    // TODO 59 ) Defining JSON object of Ingredient
                    JSONObject ingredientObject = (JSONObject) ingredients.get(ingredientIndex);

                    // TODO 60 ) Defining ingredient's attributes of JSON object
                    String quantity = ingredientObject.getString(RECIPE_INGREDIENTS_QUANTITY);
                    String measure =  ingredientObject.getString(RECIPE_INGREDIENTS_MEASURE);
                    String ingredient = ingredientObject.getString(RECIPE_INGREDIENTS_INGREDIENT);

                    // TODO 61 ) Adding them into one String
                    String ingredientInformation = quantity + " " + measure + " " + ingredient;

                    // TODO 62 ) Adding it into a list
                    recipeIngredientList.add(ingredientInformation);

                }

                // TODO  63 ) Showing stepIndex of Recipe Object via loop
                for(int stepIndex=0;stepIndex<steps.length();stepIndex++){

                    // TODO 64 ) Defining JSON object of step
                    JSONObject stepObject = (JSONObject) steps.get(stepIndex);

                    // TODO 65 ) Defining steps' attributes of JSON object
                    String stepID = stepObject.getString(RECIPE_STEPS_ID);
                    String stepShortDescription = stepObject.getString(RECIPE_STEPS_SHORTDESCRIPTON);
                    String stepDescription = stepObject.getString(RECIPE_STEPS_DESCRIPTON);
                    String stepVideoURL = stepObject.getString(RECIPE_STEPS_VIDEOURL);
                    String stepThumbnailURL = stepObject.getString(RECIPE_STEPS_THUMBNAILURL);

                    // TODO 66 ) Creating Recipe Step Object and add them into it via set method
                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setStepId(stepID);
                    recipeStep.setShortDescription(stepShortDescription);
                    recipeStep.setDescription(stepDescription);
                    recipeStep.setVideoURL(stepVideoURL);
                    recipeStep.setThumbnailURL(stepThumbnailURL);

                    // TODO 69 ) Adding object with its attributes into arraylist
                    recipeStepList.add(recipeStep);
                }

                // TODO 70 ) Creating Recipe object and assigning its variables
                Recipe recipe = new Recipe(id,name,servings,image,recipeIngredientList,recipeStepList);

                // TODO 71 ) Adding recipe object into arraylist
                recipes.add(recipe);

            }

        } catch (IOException e) {
            e.printStackTrace();
            Timber.e(LOG_TAG + " / Error in getRecipes : " + e);
        }

        // TODO 72 ) Returning recipe list
        return recipes;
    }

}
