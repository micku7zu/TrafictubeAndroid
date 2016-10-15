package com.micutu.trafictube.Views.Player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.micutu.trafictube.R;

public class CustomVideoPlayer extends RelativeLayout implements MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, View.OnTouchListener {

    private final Context context;
    private VideoView videoView;
    private CustomMediaController customMediaController;
    private MediaPlayer mediaPlayer;

    public CustomVideoPlayer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        inflate(this.context, R.layout.view_custom_video_player, this);
        this.videoView = (VideoView) this.findViewById(R.id.video_player_video_view);
    }

    public void playVideoUrl(String URL) {
        this.videoView.setVideoURI(Uri.parse(URL));
        this.videoView.setOnPreparedListener(this);
        this.videoView.start();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.setOnInfoListener(this);
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            this.videoView.setBackgroundResource(android.R.color.transparent);

            this.customMediaController = (CustomMediaController) this.findViewById(R.id.video_player_custom_media_controller);
            this.customMediaController.setMediaPlayer(this.videoView);
            this.customMediaController.setEnabled(true);
            this.customMediaController.initialization();
            this.customMediaController.show();

            this.videoView.setOnTouchListener(this);
        }

        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.customMediaController.isShown()) {
            this.customMediaController.hide();
        } else {
            this.customMediaController.show();
        }
        return false;
    }
}
