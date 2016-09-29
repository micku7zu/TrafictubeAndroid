package com.micutu.trafictube.Fragments.Player;

public interface PlayerFragment {
    void initialization(InitializationListener initializationListener);

    void playVideo(String id);

    void setFullscreen(Boolean fullscreen);

    Boolean isFullscreen();

    interface InitializationListener {
        void onInitialization(Boolean success);
    }
}
