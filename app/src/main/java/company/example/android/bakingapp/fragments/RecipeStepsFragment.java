package company.example.android.bakingapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.R;
import company.example.android.bakingapp.adapter.IngredientsAdapter;
import company.example.android.bakingapp.adapter.StepsAdapter;
import company.example.android.bakingapp.data.Recipe;
import company.example.android.bakingapp.data.RecipeStep;

/**
 * Created by user on 4.04.2018.
 */

// TODO 124 ) Creating RecipeStepsFragment extending fragment with implementing StepAdapter
public class RecipeStepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickStepListener {

    // TODO 125 ) Defining LOG_TAG, selectedListener interface and Recipe object
    private static final String LOG_TAG = RecipeStepsFragment.class.getSimpleName();
    private OnStepSelectedListener selectedListener;
    private Recipe recipe;

    // TODO 130 ) Giving  a reference for the RecyclerViews with recipes' ingredients and steps in the recipe_steps_fragment
    @BindView(R.id.recipe_ingredients_recyclerview)
    RecyclerView recipeIngredientsRecyclerView;

    @BindView(R.id.recipe_steps_recyclerview)
    RecyclerView recipeStepsRecyclerView;

    // TODO 131 ) Giving a reference for TextView with recipe's name
    @BindView(R.id.recipe_name)
    TextView recipeNameTextView;

    // TODO 174 ) Giving a reference for TextView with Ingredient Heading
    @BindView(R.id.recipe_ingredients_heading)
    TextView recipeHeading;

    // TODO 175 ) Giving a reference for TextView with Step Heading
    @BindView(R.id.recipe_steps_heading)
    TextView stepHeading;

    // TODO 176 ) Giving a reference for TextView with Step Sub Heading
    @BindView(R.id.recipe_steps_sub_heading)
    TextView stepSubHeading;

    // TODO 138 ) Defining adapters for ingredient and step
    IngredientsAdapter ingredientsAdapter;
    StepsAdapter stepsAdapter;

    // TODO 125 ) Creating OnStepSelectedListener to call each step when it' clicked
    public interface OnStepSelectedListener {
        void onStepSelected(RecipeStep currentStep);
    }

    // TODO 126 ) Creating empty constructor
    public RecipeStepsFragment() {

    }

    // TODO 127 ) Creating onCreateView to design the fragment involving ingredients and steps related with recipe
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // TODO 128 ) Determing the layout used to show fragment
        int layoutFragment = R.layout.recipe_steps_fragment;

        // TODO 129 ) Creating a view to inflate that fragment
        final View rootView = inflater.inflate(layoutFragment, container, false);

        // TODO 140 ) Defining butterknife to perceive each elements defined by bindView
        ButterKnife.bind(this, rootView);

        // TODO 132 ) Getting recipe object from main activity via bundle
        Bundle recipeBundle = getActivity().getIntent().getExtras();
        recipe = recipeBundle.getParcelable("RECIPE_DETAIL_INFORMATION");

        // TODO 133 ) Assigning name to textview
        recipeNameTextView.setText(recipe.getName());

        // TODO 177 ) Assigning headings of ingredient and step and subheading of step to textview
        recipeHeading.setText(R.string.recipe_ingredients_heading);
        stepHeading.setText(R.string.recipe_steps_heading);
        stepSubHeading.setText(R.string.recipe_steps_sub_heading);

        // TODO 134 ) Getting ingredient and steps list and assign them to arraylist
        ArrayList<String> ingredientList = recipe.getIngredients();
        ArrayList<RecipeStep> recipeStepsList = recipe.getSteps();

        // TODO 135 ) Creating adapter for ingredient and steps
        ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);
        stepsAdapter = new StepsAdapter(getContext(), recipeStepsList, this);


        // TODO 136 ) Designing layout for RecyclerViews with recipes' ingredients and steps
        recipeIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO 137 ) Setting the adapters to these RecyclerViews
        recipeIngredientsRecyclerView.setAdapter(ingredientsAdapter);
        recipeStepsRecyclerView.setAdapter(stepsAdapter);

        // TODO 139 ) Returning the root view
        return rootView;
    }

    // TODO 127 ) Calling step clicked while clicking it.
    @Override
    public void onClick(RecipeStep currentStep) {
        selectedListener.onStepSelected(currentStep);
    }


    // TODO 192 ) Implementing selectedListener in onAttach method
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context  instanceof OnStepSelectedListener)
            selectedListener = (OnStepSelectedListener) getActivity();
    }
}
