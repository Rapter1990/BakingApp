package company.example.android.bakingapp.fragments;

import android.support.v4.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.example.android.bakingapp.R;
import company.example.android.bakingapp.RecipeDetailActivity;
import company.example.android.bakingapp.RecipeStepDetailActivity;
import company.example.android.bakingapp.data.RecipeStep;
import company.example.android.bakingapp.utilities.ExpoMediaPlayerUtils;
import company.example.android.bakingapp.utilities.NetworkUtils;
import timber.log.Timber;

/**
 * Created by user on 5.04.2018.
 */

// TODO 142 )  Creating RecipeStepsDetailFragment class for connecting with recipe_steps_detail_fragment as a visual perspective
public class RecipeStepsDetailFragment extends Fragment {

    // TODO 143 ) Defining a LOG_TAG
    private static final String LOG_TAG = RecipeStepsDetailFragment.class.getSimpleName();

    // TODO 144 ) Defining SimpleExoPlayerView , TextView for step's description and button for navigation
    @BindView(R.id.step_playerView)
    SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.step_description)
    TextView stepDescriptionTextView;

    @BindView(R.id.step_button)
    Button navigationButton;

    // TODO 145 ) Defining SimpleExoPlayer to show video in the SimpleExoPlayerView
    private SimpleExoPlayer exoPlayer;

    // TODO 146 ) Defining videoPlayerCurrentPosition to determine the current position of SimpleExoPlayer
    private int videoPlayerCurrentPosition;

    // TODO 147 ) Defining RecipeStep
    private RecipeStep recipeStep = null;

    // TODO 161 ) Defining PLAYER_STATUE String variable to determine the current position of SimpleExoPlayer for Bundle
    private static final String PLAYER_STATUE = "player_current_position";
    private static final String PLAYER_READY ="player_ready";

    // TODO 185 ) Defining videoURL
    private Uri videoUri = null;

    // TODO 187 ) Defining thumbnailUrl
    String thumbnailUrl = null;


    // TODO 209 ) Defining layout of this fragment
    @BindView(R.id.recipe_step_fragment)
    LinearLayout stepDetailLayout;


    // TODO 261 ) Defining imageview
    @BindView(R.id.novideo_placeholder_iv)
    ImageView noImageAvailableImageView;


    // TODO 274 ) Defining activity name for widget
    String activityName = ""; //getActivity().getClass().getSimpleName();

    // TODO 305 ) FEEDBACK 9 ) Defining exoPlayerPlayWhenReady boolean variable;
    private static boolean exoPlayerPlayWhenReady = false;

    // TODO 148 ) Creating onCreateView to design the fragment involving steps with its video
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // TODO 149 ) Determing the layout used to show fragment
        int layoutFragment = R.layout.recipe_steps_detail_fragment;

        // TODO 150 ) Creating a view to inflate that fragment
        final View rootView = inflater.inflate(layoutFragment, container, false);

        // TODO 151 ) Defining butterknife to perceive each elements defined by bindView
        ButterKnife.bind(this, rootView);

        // TOD O 183 ) Defining  RecipeStep from detail activity
        //recipeStep = ((RecipeStepDetailActivity) getActivity()).getCurrentStep();

        // TODO 275 ) Using isAdded() to get acitivty name
        if (isAdded()) {
            activityName = getActivity().getClass().getSimpleName();
        }

        // TODO 273 ) Checking whether the current step is used for widget or used for normal screen
        if(activityName.equals(RecipeStepDetailActivity.class.getSimpleName())){
            // TODO 183 ) Defining  RecipeStep from detail activity
            recipeStep = ((RecipeStepDetailActivity) getActivity()).getCurrentStep();
            //navigationButton.setVisibility(View.GONE);
        }else{
            recipeStep = ((RecipeDetailActivity) getActivity()).getCurrentStep();
            navigationButton.setVisibility(View.GONE);
        }


        // TODO 184 ) Setting step detail description to textview
        stepDescriptionTextView.setText(recipeStep.getDescription());

        // TODO 186 ) Defining videoUri
        videoUri = Uri.parse(recipeStep.getVideoURL());

        // TODO 188 ) Defining thumbnailUrl
        thumbnailUrl = recipeStep.getThumbnailURL().trim();

        // TODO 189 ) Checking whether thumbnailUrl is empty or not
        if (String.valueOf(thumbnailUrl).equals("") && String.valueOf(videoUri).equals("")) {
            //simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    //(getResources(), R.drawable.novideoavailable));
            hideSimpleExoPlayerView();
        }else{
            // TODO 306 ) FEEDBACK 10 ) Checking whether thumbnailUrl is available from server or not
            String urlContentType = NetworkUtils.getUrlContentType(thumbnailUrl);
            if (urlContentType != null && !urlContentType.isEmpty()) {
                if (urlContentType.startsWith("image/")) {
                    simpleExoPlayerView.setDefaultArtwork(NetworkUtils.getBitmapFromURL(thumbnailUrl));
                }
            }
            else {
                hideSimpleExoPlayerView();
            }
        }

        // TODO 190 ) Initialize the Media Session.
        ExpoMediaPlayerUtils.initializeMediaSession(getActivity(), exoPlayer);
        // TODO 191 ) Initialize the player.
        if (videoUri != null) {
            exoPlayer = ExpoMediaPlayerUtils.initializePlayer(videoUri, getActivity(), exoPlayer);
            simpleExoPlayerView.setPlayer(exoPlayer);
            Timber.d(LOG_TAG + " / Player initialized");
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO 301 ) FEEDBACK 5 ) Because we wait we wait as long as possible until we grab resources Before API level 24, checking sdk then initialize ExpoPlayer
        if (Util.SDK_INT > 23) {
            initializeExpoPlayer();
        }
    }


    // TODO 152 ) Creating onResume method for initializing SimpleExoPlayer and deteriming its current position
    @Override
    public void onResume() {
        super.onResume();

        Timber.i("%s / onResume Called", LOG_TAG);
        // TODO 302 ) FEEDBACK 6 ) Because we wait we wait as long as possible until we grab resources Before API level 24, checking sdk then initialize ExpoPlayer
        if ((Util.SDK_INT <= 23 ||  exoPlayer == null)) {
            initializeExpoPlayer();
        }

    }


    // TODO 159 ) Creating onStop to release SimpleExoPlayer
    @Override
    public void onStop() {
        super.onStop();
        Timber.i("%s/ onStop", LOG_TAG);
        // TODO 303 ) FEEDBACK 7 ) Because we wait we wait as long as possible until we grab resources Before API level 24, checking sdk then release ExpoPlayer
        if (Util.SDK_INT <= 23) {
            exoPlayer = ExpoMediaPlayerUtils.releasePlayer(exoPlayer);
        }
    }


    // TODO 160 ) Creating onPause to pause SimpleExoPlayer
    @Override
    public void onPause() {
        super.onPause();
        // TODO 304 ) FEEDBACK 8 ) Because we wait we wait as long as possible until we grab resources Before API level 24, checking sdk then release ExpoPlayer
        if (Util.SDK_INT > 23) {
            if (exoPlayer != null) {
                boolean exoPlayerPlayWhenReady =exoPlayer.getPlayWhenReady();
                //exoPlayer.setPlayWhenReady(false);
                exoPlayer.setPlayWhenReady(exoPlayerPlayWhenReady);
            }
        }
        Timber.i("%s/n  onPause", LOG_TAG);
    }


    // TODO 162 ) Creating onActivityCreated to resume the exoplayer having a position while opening the Activity
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            videoPlayerCurrentPosition = savedInstanceState.getInt(PLAYER_STATUE);
        }
    }

    // TODO 163 ) Creating onSaveInstanceState for saving the current position of Exoplayer to resume it later when it's opened again.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int lastVideoPlayerCurrentPosition = (int) exoPlayer.getCurrentPosition();
        outState.putInt(PLAYER_STATUE, lastVideoPlayerCurrentPosition);

        // TODO 299 ) FEEDBACK 3 ) Checkcing whether expoplayer get Play When it's ready
        boolean exoPlayerPlayWhenReady = exoPlayer.getPlayWhenReady();
        outState.putBoolean(PLAYER_READY,exoPlayerPlayWhenReady);

    }

    // TODO 208 ) Hiding SimpleExoPlayerView to show "NO Available Image"
    public void hideSimpleExoPlayerView(){
        simpleExoPlayerView.setVisibility(View.GONE);

        //ImageView imageView = new ImageView(getActivity());
        //imageView.setImageResource(R.drawable.novideoavailable);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
        //        (LinearLayout.LayoutParams.MATCH_PARENT, 0,4);
        //imageView.setLayoutParams(layoutParams);
        //stepDetailLayout.addView(imageView);

        noImageAvailableImageView.setVisibility(View.VISIBLE);

    }

    // TODO 300 ) FEEDBACK 4 ) Creating initializeExpoPlayer method
    private void initializeExpoPlayer(){
        // TODO 153 ) Checking whether SimpleExoPlayer is null
        if (exoPlayer == null) {

            // TODO 154 )  Initialize the Media Session .
            ExpoMediaPlayerUtils.initializeMediaSession(getActivity(), exoPlayer);

            // TODO 155 ) Checking whether video step of recipe is valid or not
            if (recipeStep.getVideoURL() != null) {
                Uri videoUrl = Uri.parse(recipeStep.getVideoURL());
                exoPlayer = ExpoMediaPlayerUtils.initializePlayer(videoUrl, getActivity(), exoPlayer);
                Timber.i("%s / ExpoMediaPlayer initialized", LOG_TAG);
            } else {
                simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), R.drawable.novideoavailable));
            }

            // TODO 156 ) Setting SimpleExoPlayer to simpleExoPlayerView
            simpleExoPlayerView.setPlayer(exoPlayer);

            // TODO 157 ) Checking whether SimpleExoPlayer is not null to deteriming its current position
        } else {
            if (videoPlayerCurrentPosition != 0) {
                exoPlayer.seekTo(videoPlayerCurrentPosition);
            } else {
                exoPlayer.seekTo(0);
            }

            // TODO 158 ) Preparing SimpleExoPlayer to set Play
            //exoPlayer.setPlayWhenReady(true);
            exoPlayer.setPlayWhenReady(exoPlayerPlayWhenReady);
        }
    }

}
