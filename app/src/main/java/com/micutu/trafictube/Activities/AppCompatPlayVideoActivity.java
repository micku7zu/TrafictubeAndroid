package com.micutu.trafictube.Activities;

import android.support.v7.app.AppCompatActivity;

import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Views.PlayVideoDialog;

public abstract class AppCompatPlayVideoActivity extends AppCompatActivity implements PostsActionsListener {
    private PlayVideoDialog playVideoDialog = null;

    @Override
    public void showVideoDialog(Post post) {
        this.playVideoDialog = new PlayVideoDialog(this);
        playVideoDialog.playPost(post);
    }

    @Override
    public void onBackPressed() {
        if (playVideoDialog != null && playVideoDialog.isFullscreen() == true) {
            playVideoDialog.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }
}
