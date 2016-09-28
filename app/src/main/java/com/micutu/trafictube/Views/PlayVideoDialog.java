package com.micutu.trafictube.Views;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.micutu.trafictube.Activities.MainActivity;
import com.micutu.trafictube.Crawler.GetPostSingleton;
import com.micutu.trafictube.Crawler.Responses.VideoResponse;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;
import com.micutu.trafictube.Utils.DimenUtils;

import java.util.Map;

public class PlayVideoDialog extends Dialog implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer.OnInitializedListener {

    private Activity activity = null;
    private YouTubePlayerFragment youTubePlayerFragment = null;
    private YouTubePlayer youTubePlayer = null;
    private Video video = null;
    private Boolean fullscreen = false;


    public PlayVideoDialog(@NonNull Activity activity) {
        super(activity, R.style.FullScreenDialog);
        this.activity = activity;
        this.setContentView(R.layout.youtube_player_alert_dialog);
        this.setOnDismissListener(this);
        this.setOnShowListener(this);

        this.youTubePlayerFragment = (YouTubePlayerFragment) this.activity.getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.fullscreen = false;
        FragmentManager fragmentManager = this.activity.getFragmentManager();
        fragmentManager.beginTransaction().remove(this.youTubePlayerFragment).commit();
    }

    public void playPost(Post post) {
        this.show();
        GetPostSingleton.getPostVideo(this.activity, post.getLink(), new VideoResponse() {
            @Override
            public void onResponse(Video video, Map<String, Object> extra) {
                if (video.getType() != Video.TYPE_YOUTUBE) {
                    PlayVideoDialog.this.showError();
                    return;
                }
                PlayVideoDialog.this.video = video;
                PlayVideoDialog.this.youTubePlayerFragment.initialize(MainActivity.YOUTUBE_DEVELOPER_KEY, PlayVideoDialog.this);
            }
        });
    }

    public void playVideo(Video video) {
        this.show();
        this.video = video;
        this.youTubePlayerFragment.initialize(MainActivity.YOUTUBE_DEVELOPER_KEY, this);
    }

    @Override
    public void onFullscreen(boolean fullscreen) {
        Log.d("FULLSCREEN TOGGLED:", fullscreen + "");
        this.fullscreen = fullscreen;
    }

    public Boolean isFullscreen() {
        return this.fullscreen;
    }

    public void setFullscreen(Boolean fullscreen) {
        this.fullscreen = fullscreen;

        if (this.youTubePlayer != null) {
            this.youTubePlayer.setFullscreen(fullscreen);
        }
    }

    public void showError() {
        Toast.makeText(activity, "Here will be error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        if (this.video == null) {
            this.showError();
            return;
        }

        this.youTubePlayer = youTubePlayer;

        if (!restored) {
            youTubePlayer.setOnFullscreenListener(this);
            youTubePlayer.loadVideo(this.video.getId());
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        this.showError();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
    }

    /* temporary not needed
    public void setViewFullscreen(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        Point size = DimenUtils.getAppUsableScreenSize(this.activity);
        params.height = size.y - DimenUtils.getStatusBarHeight(this.activity);
        params.width = size.x;
        view.setLayoutParams(params);
    }
    */
}
