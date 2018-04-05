package company.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by user on 2.04.2018.
 */

// TODO 6 ) Creating Recipe Class to define its attributes and implemeting Parcelable
public class Recipe implements Parcelable{

    // TODO 7 ) Defining id of Recipe
    private String id;

    // TODO 8 ) Defining name of Recipe
    private String name;

    // TODO 9 ) Defining servings of Recipe
    private String servings;

    // TODO 10 ) Defining image of Recipe
    private String image;

    // TODO 11 ) Defining a list of ingredient for Recipe as an ArrayList
    private ArrayList<String> ingredients;

    // TODO 12 ) Defining a list of steps of Recipe as an Arraylist
    private ArrayList<RecipeStep> steps;

    // TODO 67 ) Defining empty constructor of Recipe
    public Recipe(){
        super();
    }


    // TODO 21 ) Defining a constructor of Recipe
    public Recipe(String id, String name, String servings, String image, ArrayList<String> ingredients, ArrayList<RecipeStep> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // TODO 23 ) Overriding and calling methods for Parcelable
    protected Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
        ingredients = in.createStringArrayList();
        steps = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
        dest.writeStringList(ingredients);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // TODO 22 ) Defining getter and setter method of each attribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }

}
