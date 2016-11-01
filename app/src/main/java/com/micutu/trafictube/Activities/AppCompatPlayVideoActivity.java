package com.micutu.trafictube.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Fragments.PlayVideoFragmentDialog;

public abstract class AppCompatPlayVideoActivity extends ThemeAppCompatActivity implements PostsActionsListener {
    private PlayVideoFragmentDialog playVideoFragmentDialog = null;

    public AppCompatPlayVideoActivity() {
        this.playVideoFragmentDialog = null;
    }

    @Override
    public void showPost(Post post) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.BUNDLE_ID, post.getId());
        startActivity(intent);
    }

    @Override
    public void showVideoDialog(Post post) {
        this.playVideoFragmentDialog = new PlayVideoFragmentDialog();
        this.playVideoFragmentDialog.show(getSupportFragmentManager(), null);
        this.playVideoFragmentDialog.play(post);
    }

    public boolean onBackPressedContinue() {
        if (this.playVideoFragmentDialog != null && this.playVideoFragmentDialog.isFullscreen() == true) {
            this.playVideoFragmentDialog.setFullscreen(false);
            return false;
        } else {
            return true;
        }
    }
}
