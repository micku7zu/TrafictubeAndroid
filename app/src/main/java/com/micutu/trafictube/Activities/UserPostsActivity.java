package com.micutu.trafictube.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.micutu.trafictube.Activities.Abstracts.PlayVideoActivity;
import com.micutu.trafictube.Activities.Abstracts.PostsActivity;
import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Fragments.PostsListFragment;
import com.micutu.trafictube.R;

public class UserPostsActivity extends PostsActivity implements PostsActionsListener {
    public static final String USERNAME = "username";
    public static final String USER_NAME = "name";

    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);
        init();

        Fragment fragment = new PostsListFragment();
        Bundle args = new Bundle();
        args.putInt(PostsListFragment.MENU_ID, R.id.user_posts);
        args.putString(PostsListFragment.USERNAME, getIntent().getStringExtra(USERNAME));

        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.user_posts_of) + " " + getIntent().getStringExtra(USER_NAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean showUsersPostsButton() {
        return false;
    }

    @Override
    public void showUserPosts(User user) {
        return;
    }
}
