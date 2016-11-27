package com.micutu.trafictube.Fragments.Player;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.micutu.trafictube.Crawler.VimeoCrawler;
import com.micutu.trafictube.Views.Player.CustomVideoPlayer;


public class VimeoPlayerFragment extends Fragment implements PlayerFragment, CustomVideoPlayer.RotateButtonListener {

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
        initializationListener.onInitialization(true);
    }

    @Override
    public void playVideo(String id) {
        VimeoCrawler.getVimeoVideoDirectUrlYoutubeDl(getContext(), id, new VimeoCrawler.VimeoResponse() {
            @Override
            public void onResponse(final String vimeoDirectUrl) {
                if (getContext() == null) {
                    return;
                }

                if (vimeoDirectUrl.length() < 5) {
                    Toast.makeText(getContext(), "Eroare :(", Toast.LENGTH_SHORT).show();
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        VimeoPlayerFragment.this.customVideoPlayer.setRotateButtonListener(VimeoPlayerFragment.this);
                        VimeoPlayerFragment.this.customVideoPlayer.playVideoUrl(vimeoDirectUrl);
                    }
                });
            }
        });
    }

    @Override
    public void setFullscreen(Boolean fullscreen) {

    }

    @Override
    public Boolean isFullscreen() {
        return null;
    }

    @Override
    public void onRotateButtonClick() {
        Boolean landscape = (getResources().getDisplayMetrics().widthPixels > getResources().getDisplayMetrics().heightPixels);
        getActivity().setRequestedOrientation((landscape) ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
