package company.example.android.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import company.example.android.bakingapp.R;
import company.example.android.bakingapp.RecipeDetailActivity;
import company.example.android.bakingapp.data.Recipe;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    // TODO 251 ) Defining recipe's name and its ingredient as null
    private static String recipeName = null;
    private static String recipeIngredient = null;

    // TODO 252 ) Defining LOG_TAG
    private static final String LOG_TAG = RecipeWidgetProvider.class.getSimpleName();

    // TODO 253 ) Defining divider
    private static final String DIVIDER = "/";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // TODO 249 ) Getting available recipe from widget config
        Recipe recipeAvailable =
                ConfigRecipeWidget.getCurrentRecipePreference(context,appWidgetId);

        // TODO 250 ) Getting details of recipe from widget config
        String recipeWidgetDetails =
                ConfigRecipeWidget.getRecipeDetailPreference(context, appWidgetId);

        // TODO 250 ) Checking whether recipeWidgetDetails is null or not
        if(recipeWidgetDetails == null) {
            return;
        }

        // TODO 254 ) Determining recipeWidgetDetails has a divider to split recipe's name and its ingredient or not
        if (recipeWidgetDetails.contains(DIVIDER)) {
            // Split it.
            String[] parts = recipeWidgetDetails.split(DIVIDER);
            recipeName = parts[0];
            recipeIngredient = parts[1];
        } else {
            Timber.d(LOG_TAG +" " + recipeWidgetDetails + " does not contain " + DIVIDER);
        }


        // TODO 255 ) Defining recipe widget layout as RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        // TODO 256 ) Setting recipe's name and its ingredient with its picture to textview and imageview in RemoteViews in widget
        views.setTextViewText(R.id.widget_recipe_name_texview, recipeName);
        views.setTextViewText(R.id.widget_recipe_ingredient_list, recipeIngredient);
        String imageDrawableResourceName = (recipeName.replaceAll("\\s+", "")).toLowerCase();
        int imageResourceId = context.getResources().getIdentifier(
                imageDrawableResourceName,
                "drawable",
                context.getPackageName());
        views.setImageViewResource(R.id.widget_recipe_image, imageResourceId);

        // TODO 257 ) Sending recipe to its detail activity
        Bundle extras = new Bundle();
        extras.putParcelable("RECIPE_DETAIL_INFORMATION", recipeAvailable);
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtras(extras);

        // TODO 258 ) Giving notification feature to intent according to last process as defining FLAG_UPDATE_CURRENT
        PendingIntent pending =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // TODO 259 ) Giving onClick feature to pending intent
        views.setOnClickPendingIntent(R.id.widget_recipe_card, pending);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO 260 ) Deleting recipe in the widget via deletetRecipePreference.
        for (int appWidgetId : appWidgetIds) {
            ConfigRecipeWidget.deletetRecipePreference(context, appWidgetId);
        }
    }
}

