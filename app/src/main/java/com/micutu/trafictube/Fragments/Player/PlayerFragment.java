package com.micutu.trafictube.Fragments.Player;

public interface PlayerFragment {
    void initialization(InitializationListener initializationListener);

    void playVideo(String id);

    void setFullscreen(Boolean fullscreen);

    Boolean isFullscreen();

    Boolean isDeepLink();

    interface InitializationListener {
        enum InitializationResponse {
            SUCCESS,
            NOT_INSTALLED,
            FAIL
        }

        void onInitialization(InitializationResponse response);
    }
}
