package com.micutu.trafictube.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.micutu.trafictube.Crawler.GetPostSingleton;
import com.micutu.trafictube.Crawler.Responses.VideoResponse;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.Fragments.Player.PlayerFragment;
import com.micutu.trafictube.Fragments.Player.VimeoPlayerFragment;
import com.micutu.trafictube.Fragments.Player.YoutubePlayerFragment;
import com.micutu.trafictube.R;

import java.util.Map;

public class PlayVideoFragmentDialog extends DialogFragment implements PlayerFragment.InitializationListener, View.OnClickListener {

    private PlayerFragment playerFragment = null;
    private Video video = null;
    private Post post = null;

    public PlayVideoFragmentDialog() {
        this.post = null;
        this.video = null;
        this.playerFragment = null;
    }

    public void play(Video video) {
        this.video = video;
        this.createFragmentAndInit(video);
    }

    public void play(Post post) {
        this.post = post;
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

        view.findViewById(R.id.retry_button).setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_NoActionBar_Fullscreen);

        WindowManager.LayoutParams a = getActivity().getWindow().getAttributes();
        a.dimAmount = 0;
        getActivity().getWindow().setAttributes(a);
    }

    public void hideProgressBar() {
        this.getView().findViewById(R.id.play_video_fragment_dialog_progress_bar).setVisibility(View.GONE);
    }

    public void showProgressBar() {
        this.getView().findViewById(R.id.play_video_fragment_dialog_progress_bar).setVisibility(View.VISIBLE);
    }

    public void createFragmentAndInit(Video video) {
        System.out.println(video.getId());
        this.hideProgressBar();
        this.playerFragment = null;

        if (video.getType() == Video.TYPE_YOUTUBE) {
            this.playerFragment = new YoutubePlayerFragment();
        } else {
            this.playerFragment = new VimeoPlayerFragment();
        }

        this.getChildFragmentManager().beginTransaction().replace(R.id.player_fragment, (Fragment) playerFragment).commit();
        this.getChildFragmentManager().executePendingTransactions();
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

        Boolean isFullscreen = this.playerFragment.isFullscreen();

        if (isFullscreen == null) {
            return false;
        }

        return isFullscreen;
    }

    public void setFullscreen(Boolean fullscreen) {
        if (this.playerFragment == null) {
            return;
        }

        this.playerFragment.setFullscreen(fullscreen);
    }

    public void showError() {
        this.hideProgressBar();
        this.getView().findViewById(R.id.error_container).setVisibility(View.VISIBLE);
    }

    public void hideError() {
        this.getView().findViewById(R.id.error_container).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        this.hideError();
        this.showProgressBar();

        if (this.post == null) {
            this.play(this.video);
        } else {
            this.play(this.post);
        }
    }
}