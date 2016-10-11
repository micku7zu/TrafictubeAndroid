package com.micutu.trafictube.Views.Player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;

public class MediaControllerAbstract extends MediaController {

    private final CustomMediaController customMediaController;

    public MediaControllerAbstract(Context context, CustomMediaController customMediaController) {
        super(context);
        this.customMediaController = customMediaController;
    }

    @Override
    public void hide() {
        return;
    }

    @Override
    public void show() {
        return;
    }

    @Override
    public void show(int timeout) {
        return;
    }

    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        return;
    }

    @Override
    public void setAnchorView(View view) {
        return;
    }

    @Override
    public void setEnabled(boolean enabled) {
        return;
    }

    @Override
    public boolean isShowing() {
        return true;
    }
}
