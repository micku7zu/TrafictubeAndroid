package com.micutu.trafictube.Views.Player;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.micutu.trafictube.R;

public class CustomVideoPlayer extends RelativeLayout {

    private final Context context;
    private VideoView videoView;
    private CustomMediaController customMediaController;

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
        inflate(this.context, R.layout.custom_video_player, this);

        this.videoView = (VideoView) this.findViewById(R.id.video_player_video_view);
        this.customMediaController = (CustomMediaController) this.findViewById(R.id.video_player_custom_media_controller);

        this.customMediaController.setMediaPlayer(this.videoView);
        this.customMediaController.setEnabled(true);
        //this.videoView.setMediaController(new MediaControllerAbstract(this.context, this.customMediaController));

    }

    public void playVideoUrl(String URL) {
        this.videoView.setVideoURI(Uri.parse(URL));

        this.videoView.start();
        this.customMediaController.initialization();

        //mai am aici de facut, chestii
    }

}
