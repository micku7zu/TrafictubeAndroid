package com.micutu.trafictube.Fragments.Player;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.vimeo.android.deeplink.VimeoDeeplink;


public class VimeoPlayerFragment extends Fragment implements PlayerFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initialization(InitializationListener initializationListener) {
        boolean vimeoIsOk = VimeoDeeplink.isVimeoAppInstalled(getContext()) && VimeoDeeplink.canHandleVideoDeeplink(getContext());
        initializationListener.onInitialization((vimeoIsOk) ? InitializationListener.InitializationResponse.SUCCESS : InitializationListener.InitializationResponse.NOT_INSTALLED);
    }

    @Override
    public void playVideo(String id) {
        VimeoDeeplink.showVideoWithUri(getContext(), "/videos/" + id);
    }

    @Override
    public void setFullscreen(Boolean fullscreen) {

    }

    @Override
    public Boolean isFullscreen() {
        return null;
    }

    @Override
    public Boolean isDeepLink() {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
