package company.example.android.bakingapp.utilities;

import android.content.Context;
import android.net.Uri;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by user on 3.04.2018.
 */


// TODO 103 ) Creating ExpoMediaPlayerUtils for initializePlayer method ,
// initializeMediaSession method and MySessionCallback class
public class ExpoMediaPlayerUtils {

    // TODO 104 ) Defining LOG_TAG
    private static final String LOG_TAG = ExpoMediaPlayerUtils.class.getSimpleName();

    // TODO 105 ) Defining MediaSessionCompat and PlaybackStateCompat.Builder variable for media session with its playback
    private static MediaSessionCompat mMediaSession;
    private static PlaybackStateCompat.Builder mStateBuilder;

    // TODO 106 ) Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
    //and media controller.
    public static void initializeMediaSession(Context context, SimpleExoPlayer exoPlayer) {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(context, LOG_TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback(exoPlayer));

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    // TODO 107 ) Initializes the Media Player
    public static SimpleExoPlayer  initializePlayer(Uri mediaUri, Context context, SimpleExoPlayer mExoPlayer) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);


            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }

        return mExoPlayer;
    }

    // TODO 108 ) Releasing the Media Player
    public static SimpleExoPlayer releasePlayer(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
        return exoPlayer;
    }


    // TODO 109 ) Media Session Callbacks, where all external clients control the player.
    private static class MySessionCallback extends MediaSessionCompat.Callback {

        SimpleExoPlayer mExoPlayer;

        public MySessionCallback(SimpleExoPlayer exoPlayer) {
            mExoPlayer = exoPlayer;
        }

        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }

    }
}
