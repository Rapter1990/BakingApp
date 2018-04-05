package company.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;


// TODO 12 ) Creating a RecipeStep class for defining each step of the recipe and implemeting Parcelable
public class RecipeStep implements Parcelable{

    // TODO 13 ) Defining a step id of Recipe
    private String stepId;

    // TODO 14 ) Defining a shortDescription id of Recipe
    private String shortDescription;

    // TODO 15 ) Defining a description id of Recipe
    private String description;

    // TODO 16 ) Defining a videoURL id of Recipe
    private String videoURL;

    // TODO 17 ) Defining a thumbnailURL id of Recipe
    private String thumbnailURL;

    // TODO 68 ) Defining empty constructor of RecipeStep
    public RecipeStep(){
        super();
    }

    // TODO 18 ) Defining a constructor of Recipe Step
    public RecipeStep(String stepId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    // TODO 20 ) Overriding and calling methods for Parcelable
    protected RecipeStep(Parcel in) {
        stepId = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };


    // TODO 19 ) Defining getter and setter method of each attribute
    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

}
