package com.micutu.trafictube.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.micutu.trafictube.Crawler.GetPostSingleton;
import com.micutu.trafictube.Crawler.Responses.VideoResponse;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.Fragments.Player.PlayerFragment;
import com.micutu.trafictube.Fragments.Player.VimeoPlayerFragment;
import com.micutu.trafictube.Fragments.Player.YoutubePlayerFragment;
import com.micutu.trafictube.R;

import java.util.Map;

public class PlayVideoFragmentDialog extends DialogFragment implements PlayerFragment.InitializationListener {

    private PlayerFragment playerFragment = null;
    private Video video = null;

    public PlayVideoFragmentDialog() {
        this.video = null;
        this.playerFragment = null;
    }

    public void play(Video video) {
        this.video = video;
        this.createFragmentAndInit(video);
    }

    public void play(Post post) {
        GetPostSingleton.getPostVideo(getActivity(), post.getLink(), new VideoResponse() {
            @Override
            public void onResponse(Video video, Map<String, Object> extra) {
                if (video == null) {
                    PlayVideoFragmentDialog.this.showError();
                    return;
                }

                PlayVideoFragmentDialog.this.video = video;
                PlayVideoFragmentDialog.this.createFragmentAndInit(video);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_video_fragment_dialog, container);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_NoActionBar_Fullscreen);
    }

    public void hideProgressBar() {
        this.getView().findViewById(R.id.play_video_fragment_dialog_progress_bar).setVisibility(View.GONE);
    }

    public void createFragmentAndInit(Video video) {
        this.hideProgressBar();
        this.playerFragment = null;

        if (video.getType() == Video.TYPE_YOUTUBE) {
            this.playerFragment = new YoutubePlayerFragment();
        } else {
            this.playerFragment = new VimeoPlayerFragment();
        }

        this.getChildFragmentManager().beginTransaction().replace(R.id.player_fragment, (Fragment) playerFragment).commit();
        this.playerFragment.initialization(this);
    }


    @Override
    public void onInitialization(Boolean success) {
        if (!success) {
            this.showError();
            return;
        }

        this.playerFragment.playVideo(video.getId());
    }

    public Boolean isFullscreen() {
        if (this.playerFragment == null) {
            return false;
        }

        return this.playerFragment.isFullscreen();
    }

    public void setFullscreen(Boolean fullscreen) {
        if (this.playerFragment == null) {
            return;
        }

        this.playerFragment.setFullscreen(fullscreen);
    }

    public void showError() {
        Toast.makeText(getActivity(), "Eroare netratata. Ori nu ai net, ori nu e youtube.", Toast.LENGTH_SHORT).show();
        this.dismiss();
    }
}