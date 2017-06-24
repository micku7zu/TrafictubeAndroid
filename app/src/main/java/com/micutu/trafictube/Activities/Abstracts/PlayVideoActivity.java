package com.micutu.trafictube.Activities.Abstracts;

import android.content.Intent;

import com.micutu.trafictube.Activities.PostActivity;
import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Fragments.PlayVideoFragmentDialog;

public abstract class PlayVideoActivity extends ThemeActivity {
    private PlayVideoFragmentDialog playVideoFragmentDialog = null;

    public PlayVideoActivity() {
        this.playVideoFragmentDialog = null;
    }

    public void showVideoDialog(Post post) {
        this.playVideoFragmentDialog = new PlayVideoFragmentDialog();
        this.playVideoFragmentDialog.show(getSupportFragmentManager(), null);
        this.playVideoFragmentDialog.play(post);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.playVideoFragmentDialog == null) {
            return;
        }

        if (this.playVideoFragmentDialog.isDeepLink() || this.playVideoFragmentDialog.isInstallFragmentVisible()) {
            this.playVideoFragmentDialog.dismiss();
        }
    }

    public boolean onBackPressedContinue() {
        if (this.playVideoFragmentDialog != null && this.playVideoFragmentDialog.isFullscreen()) {
            this.playVideoFragmentDialog.setFullscreen(false);
            return false;
        } else {
            return true;
        }
    }
}
