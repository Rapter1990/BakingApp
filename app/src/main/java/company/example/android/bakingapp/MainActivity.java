package company.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.adapter.RecipeAdapter;
import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.utilities.NetworkUtils;
import company.example.android.bakingapp.utilities.RecipeJsonUtils;
import timber.log.Timber;

// TODO 110 ) Implementing RecipeAdapter.RecipeAdapterOnClickHander to show Recipe in the main screen via Asyntask in Loader
public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    // TODO 111 ) Defining LOG_TAG variable
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // TODO 112 ) Defining NETWORK_RECIPE_LOADER_ID variable
    private static final int NETWORK_RECIPE_LOADER_ID = 1;

    // TODO 113 ) Creating  bindview elements and RecipeAdapter
    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipeRecyclerView;

    @BindView(R.id.error_message_display)
    TextView errorMessageTextView;

    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    RecipeAdapter recipeAdapter;

    // TODO 114 ) Defining a loader to show recipes
    LoaderManager.LoaderCallbacks<ArrayList<Recipe>> recipeLoader =
            new LoaderManager.LoaderCallbacks<ArrayList<Recipe>>() {

                @Override
                public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
                    return new AsyncTaskLoader<ArrayList<Recipe>>(MainActivity.this) {

                        ArrayList<Recipe> recipeList;

                        @Override
                        protected void onStartLoading() {
                            if (recipeList != null) {
                                deliverResult(recipeList);
                            } else {
                                forceLoad();
                            }
                        }

                        @Override
                        public ArrayList<Recipe> loadInBackground() {
                            ArrayList<Recipe> listOfRecipes = null;

                            try {
                                if (NetworkUtils.isNetworkConnected(getContext())) {
                                    listOfRecipes = RecipeJsonUtils
                                            .getRecipes(RecipeJsonUtils.RECIPE_URL);
                                }
                            } catch (JSONException e) {
                                Timber.e(LOG_TAG + " / Fetching data from network JSON Exception " + e);
                            }
                            return listOfRecipes;
                        }

                        @Override
                        public void deliverResult(ArrayList<Recipe> data) {
                            recipeList = data;
                            super.deliverResult(data);
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    recipeAdapter.setRecipeData(data);
                    if (data == null) {
                        showErrorMessage();
                    } else {
                        showRecipes();
                    }
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // TODO 117 ) Defining ButtleKnife to perceive @BindView
        ButterKnife.bind(this);

        // TODO 118 ) Designing GridLayoutManager with its attributes
        int orientation = GridLayout.VERTICAL;
        int span = getResources().getInteger(R.integer.main_gridlayout_span_integer);
        boolean reverseLayout = false;
        GridLayoutManager layoutManager = new GridLayoutManager(this, span, orientation, reverseLayout);

        // TODO 119 ) Setting fixed size and layout to recyleview
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setLayoutManager(layoutManager);

        // TODO 120 ) Defining RecipeAdapter and assign it into recyleview
        recipeAdapter = new RecipeAdapter(this,this);
        recipeRecyclerView.setAdapter(recipeAdapter);

        // TODO 121 ) Initializing loader
        getSupportLoaderManager().initLoader(NETWORK_RECIPE_LOADER_ID,null,recipeLoader);


    }

    @Override
    public void onClick(Recipe recipe) {
        // TODO 122 ) Starting intent including detail information about recipe according to recipe position
        Intent recipeInformationDetailActivityIntent = new Intent(this, RecipeDetailActivity.class);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable("RECIPE_DETAIL_INFORMATION",recipeBundle);
        recipeInformationDetailActivityIntent.putExtras(recipeBundle);
        startActivity(recipeInformationDetailActivityIntent);
    }

    // TODO 115 ) Defining showErrorMessage
    private void showErrorMessage() {
        recipeRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }

    // TODO 116 ) Defining showErrorMessage
    private void showRecipes() {
        recipeRecyclerView.setVisibility(View.VISIBLE);
        errorMessageTextView.setVisibility(View.INVISIBLE);
    }


}
