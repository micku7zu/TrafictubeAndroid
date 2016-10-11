package com.micutu.trafictube.Fragments.Player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.micutu.trafictube.Views.Player.CustomVideoPlayer;


public class VimeoPlayerFragment extends Fragment implements PlayerFragment {

    private CustomVideoPlayer customVideoPlayer = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        customVideoPlayer = new CustomVideoPlayer(getContext());
        return customVideoPlayer;
    }

    @Override
    public void initialization(InitializationListener initializationListener) {
        this.customVideoPlayer.playVideoUrl("https://06-lvl3-pdl.vimeocdn.com/01/651/1/28259893/63220624.mp4?expires=1476213207&token=067d202372b80379979cb");
    }

    @Override
    public void playVideo(String id) {

    }

    @Override
    public void setFullscreen(Boolean fullscreen) {

    }

    @Override
    public Boolean isFullscreen() {
        return null;
    }

}
