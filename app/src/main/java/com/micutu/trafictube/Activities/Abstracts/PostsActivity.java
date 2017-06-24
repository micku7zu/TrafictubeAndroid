package com.micutu.trafictube.Activities.Abstracts;

import android.content.Intent;

import com.micutu.trafictube.Activities.PostActivity;
import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Fragments.PlayVideoFragmentDialog;

public abstract class PostsActivity extends PlayVideoActivity implements PostsActionsListener {

    @Override
    public void showPost(Post post) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.BUNDLE_POST, post);
        startActivity(intent);
    }

    @Override
    public void showVideoDialog(Post post) {
        super.showVideoDialog(post);
    }

}
