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
import company.example.android.bakingapp.data.RecipeStep;

/**
 * Created by user on 2.04.2018.
 */
// TODO 95 ) Creating StepsAdapter to show attribute of steps of each Recipe
// extending RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder>
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder>{

    // TODO 96 ) Defining Arraylist as a Steps , onClinkHandler , Context and LOG_TAG
    private static final String LOG_TAG = StepsAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<RecipeStep> stepList;
    private StepsAdapterOnClickStepListener stepsAdapterOnClickHandler;

    // TODO 97 ) Defining a constructor with Context list of steps and onClickHandler
    public StepsAdapter(Context context, ArrayList<RecipeStep> recipeStepList,
                        StepsAdapterOnClickStepListener listener) {
        mContext = context;
        stepList = recipeStepList;
        stepsAdapterOnClickHandler = listener;
    }

    // TODO 98 ) Defining onCreateViewHolder method for
    // inflating recipe_step.xml and return as a StepsViewHolder object
    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        int layoutId = R.layout.recipe_step_item;
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);

        return new StepsViewHolder(view);
    }

    // TODO 99 ) Defining onBindViewHolder to get step number and its information
    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        RecipeStep currentStep = stepList.get(position);

        holder.stepId.setText("Step " + (position + 1));
        holder.stepDescription.setText(currentStep.getShortDescription());

    }

    // TODO 100 ) Defining the size of step and return it
    @Override
    public int getItemCount() {
        if(stepList == null) {
            return 0;
        }
        return stepList.size();
    }

    // TODO 101 ) Defining StepsAdapterOnClickStepListener as Interface to add clickable function
    public interface StepsAdapterOnClickStepListener{
        void onClick(RecipeStep currentStep);
    }

    // TODO 102 ) Creating StepsViewHolder to show attributes as the id and description of step and add onClick feature
    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_id)
        TextView stepId;

        @BindView(R.id.step_defination)
        TextView stepDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            RecipeStep recipeStep = stepList.get(position);
            stepsAdapterOnClickHandler.onClick(recipeStep);
        }
    }
}
