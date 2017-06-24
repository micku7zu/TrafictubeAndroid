package com.micutu.trafictube.Fragments.Player;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.micutu.trafictube.Activities.MainActivity;
import com.micutu.trafictube.R;

public class YoutubePlayerFragment extends YouTubePlayerSupportFragment implements PlayerFragment, YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private InitializationListener initializationListener = null;
    private YouTubePlayer youTubePlayer = null;
    private Boolean fullscreen = false;

    @Override
    public void initialization(InitializationListener initializationListener) {
        this.initializationListener = initializationListener;
        this.youTubePlayer = null;
        this.fullscreen = false;
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
    public Boolean isDeepLink() {
        return false;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayer.setOnFullscreenListener(this);
        this.initializationListener.onInitialization(InitializationListener.InitializationResponse.SUCCESS);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.initializationListener.onInitialization(InitializationListener.InitializationResponse.NOT_INSTALLED);
    }

    @Override
    public void onFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }
}
