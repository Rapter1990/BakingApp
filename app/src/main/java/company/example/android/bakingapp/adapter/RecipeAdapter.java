package company.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.R;
import company.example.android.bakingapp.data.Recipe;
import timber.log.Timber;

/**
 * Created by user on 2.04.2018.
 */
// TODO 73 ) Creating RecipeAdapter class to list recipe on the screen as a visual side and
// extending RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    // TODO 77 ) Defining Arraylist as a Recipe , onClinkHandler , Context and LOG_TAG
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();

    private ArrayList<Recipe> recipeArrayList;
    private RecipeAdapterOnClickHandler recipeAdapterOnClickHandler;
    private Context mContext;

    // TODO 78 ) Defining RecipeAdapterOnClickHander as Interface to add clickable function
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    // TODO 79 ) Defining a Constructor to set context and onclickHandler
    public RecipeAdapter(Context context, RecipeAdapterOnClickHandler handler) {
        this.mContext = context;
        this.recipeAdapterOnClickHandler = handler;
    }

    // TODO 81 ) Defining onCreateViewHolder method to inflate recipe.item.xml and return as a RecipeAdapterViewHolder object
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemId = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(listItemId, parent, shouldAttachToParentImmediately);

        return new RecipeAdapterViewHolder(view);

    }

    // TODO 82 ) Defining onBindViewHolder to get name and image of Recipe and show image via Picasso
    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeAdapterViewHolder holder, int position) {

        String recipeName = recipeArrayList.get(position).getName();
        String recipeImageUrl = recipeArrayList.get(position).getImage();

        holder.recipeNameTextview.setText(recipeName);

        if(recipeImageUrl.isEmpty()){
            // Like "Nutella Pie" -> "nutellepie"
            String imageDrawableResourceName = recipeName.replaceAll("\\s+", "").toLowerCase();
            int imageResourceId = mContext.getResources().getIdentifier(
                    imageDrawableResourceName,
                    "drawable",
                    mContext.getPackageName());
            Timber.i(LOG_TAG + "/ ImageResourceId : " + imageResourceId);

            Picasso.with(mContext)
                    .load(imageResourceId)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.recipeImageview);
        } else {
            Picasso.with(mContext)
                    .load(recipeImageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.recipeImageview);
        }


    }

    // TODO 83 ) Defining the size of Recipe and return it
    @Override
    public int getItemCount() {
        if (recipeArrayList == null) {
            return 0;
        }
        return recipeArrayList.size();
    }


    // TODO 80 ) Creating RecipeAdapterViewHolder to show attributes as a list and add onClick feature
    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_imageview)
        ImageView recipeImageview;

        @BindView(R.id.recipe_image_name_textview)
        TextView recipeNameTextview;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Recipe recipeObject = recipeArrayList.get(position);
            recipeAdapterOnClickHandler.onClick(recipeObject);
        }
    }

    // TODO 87 ) Setting and updating data of Recipe
    public void setRecipeData(ArrayList<Recipe> recipeData) {
        recipeArrayList = recipeData;
        notifyDataSetChanged();
    }
}
