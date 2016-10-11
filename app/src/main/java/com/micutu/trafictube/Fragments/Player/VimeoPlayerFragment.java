package com.micutu.trafictube.Fragments.Player;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.micutu.trafictube.R;
import com.micutu.trafictube.Views.CustomMediaController;


public class VimeoPlayerFragment extends Fragment implements PlayerFragment {

    private View root = null;
    private RelativeLayout relativeLayout = null;
    private VideoView videoView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.vimeo_player_dialog_layout, container, false);
        relativeLayout = (RelativeLayout) root.findViewById(R.id.relative_layout_vimeo);
        videoView = (VideoView) root.findViewById(R.id.video_view_vimeo);
        return root;
    }

    @Override
    public void initialization(InitializationListener initializationListener) {
        videoView.setVideoURI(Uri.parse("https://06-lvl3-pdl.vimeocdn.com/01/651/1/28259893/63220624.mp4?expires=1476206727&token=028630613b6e3c086ca09"));
        final CustomMediaController customMediaController = (CustomMediaController) root.findViewById(R.id.custom_media_controller_vimeo);
        customMediaController.setMediaPlayer(videoView);
        customMediaController.setEnabled(true);
        
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setOnInfoListener(new OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                        if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            videoView.setBackgroundResource(android.R.color.transparent);
                            customMediaController.show(0);
                        }
                        return false;
                    }
                });
            }
        });

        videoView.start();
        customMediaController.initialization();
    }

    @JavascriptInterface
    void receiveString(String value) {
        System.out.println("JAVASCRIPT:" + value);
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
