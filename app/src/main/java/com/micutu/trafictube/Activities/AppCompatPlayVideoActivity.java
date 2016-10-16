package com.micutu.trafictube.Activities;

import android.support.v7.app.AppCompatActivity;

import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Fragments.PlayVideoFragmentDialog;

public abstract class AppCompatPlayVideoActivity extends ThemeAppCompatActivity implements PostsActionsListener {
    private PlayVideoFragmentDialog playVideoFragmentDialog = null;

    public AppCompatPlayVideoActivity() {
        this.playVideoFragmentDialog = null;
    }

    @Override
    public void showVideoDialog(Post post) {
        this.playVideoFragmentDialog = new PlayVideoFragmentDialog();
        this.playVideoFragmentDialog.show(getSupportFragmentManager(), null);
        this.playVideoFragmentDialog.play(post);
    }

    @Override
    public void onBackPressed() {
        if (this.playVideoFragmentDialog != null && this.playVideoFragmentDialog.isFullscreen() == true) {
            this.playVideoFragmentDialog.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }
}
