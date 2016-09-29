package com.micutu.trafictube.Fragments.Player;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.micutu.trafictube.Activities.MainActivity;

public class YoutubePlayerFragment extends YouTubePlayerSupportFragment implements PlayerFragment, YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private InitializationListener initializationListener = null;
    private YouTubePlayer youTubePlayer = null;
    private Boolean fullscreen = null;

    @Override
    public void initialization(InitializationListener initializationListener) {
        this.initializationListener = initializationListener;
        this.youTubePlayer = null;
        this.fullscreen = null;
        this.initialize(MainActivity.YOUTUBE_DEVELOPER_KEY, this);
    }

    @Override
    public void playVideo(String id) {
        if (this.youTubePlayer == null) {
            return;
        }

        this.youTubePlayer.loadVideo(id);
        this.youTubePlayer.play();
    }

    @Override
    public void setFullscreen(Boolean fullscreen) {
        if (this.youTubePlayer == null) {
            return;
        }

        this.youTubePlayer.setFullscreen(fullscreen);
        this.fullscreen = fullscreen;
    }

    @Override
    public Boolean isFullscreen() {
        return this.fullscreen;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayer.setOnFullscreenListener(this);
        this.initializationListener.onInitialization(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.initializationListener.onInitialization(false);
    }

    @Override
    public void onFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
}
