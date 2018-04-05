package company.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.R;


/**
 * Created by user on 2.04.2018.
 */
//  TODO 88 ) Creating IngredientsAdapter class to list ingredient on the screen as a visual side and
// extending RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder>
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>{

    // TODO 89 ) Defining Arraylist as a Ingredient, Context and LOG_TAG
    private static final String LOG_TAG = IngredientsAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<String> ingredientList;

    // TODO 90 ) Defining a constructor with Context and list of ingredient
    public IngredientsAdapter(Context context, ArrayList<String> listOfIngredients) {
        mContext = context;
        ingredientList = listOfIngredients;
    }

    // TODO 91 ) Overridding onCreateViewHolder method for inflating recipe_ingredient.xml and return as a RecipeAdapterViewHolder object
    @Override
    public IngredientsAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        int layoutId = R.layout.recipe_ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);

        return new IngredientViewHolder(view);
    }

    // TODO 92 ) Defining onBindViewHolder to get information of ingredient
    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientViewHolder holder, int position) {
        String ingredientInfo = ingredientList.get(position);
        holder.ingredientItem.setText(ingredientInfo);
    }

    // TODO 93 ) Defining the size of Ingredient and return it
    @Override
    public int getItemCount() {
        if (ingredientList == null) {
            return 0;
        }
        return ingredientList.size();
    }

    // TODO 94 ) Creating IngredientViewHolder to show attribute of ingredient
    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_item)
        TextView ingredientItem;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
