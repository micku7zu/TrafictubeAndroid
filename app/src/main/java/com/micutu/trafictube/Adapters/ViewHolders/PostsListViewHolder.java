package com.micutu.trafictube.Adapters.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.micutu.trafictube.Crawler.VolleySingleton;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.R;
import com.micutu.trafictube.Views.AppCompatImageButtonWithTooltip;

public class PostsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    /* views */
    private TextView title = null;
    private TextView more = null;
    private NetworkImageView image = null;

    /* extra data */
    private PostsActionsListener postsActionsListener = null;

    /* actual data */
    private Post post = null;

    public PostsListViewHolder(final View itemView, PostsActionsListener postsActionsListener) {
        super(itemView);
        this.postsActionsListener = postsActionsListener;

        this.title = (TextView) itemView.findViewById(R.id.title);
        this.more = (TextView) itemView.findViewById(R.id.more);
        this.image = (NetworkImageView) itemView.findViewById(R.id.image);

        this.title.setOnClickListener(this);
        this.image.setOnClickListener(this);
        ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.thumbs_up)).setOnClickListener(this);
        ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.play_button)).setOnClickListener(this);

        if (this.postsActionsListener.showUsersPostsButton() == false) {
            ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.view_user)).setVisibility(View.GONE);
        } else {
            ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.view_user)).setOnClickListener(this);
        }
    }

    public void setPost(Context context, Post post) {
        this.post = post;

        this.setTitle(String.valueOf(Html.fromHtml(this.post.getTitle())));
        this.setMore(this.getMoreText(this.post));
        this.setImage(context, this.post.getImage());

    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setMore(String text) {
        this.more.setText(text);
    }

    public void setImage(Context context, String url) {
        this.image.setImageUrl(url, VolleySingleton.getImageLoader(context));
    }

    private String getMoreText(Post post) {
        String more = "";
        if (post.getUser() != null && post.getUser().getName() != null) {
            more += post.getUser().getName() + " \u2022 ";
        }

        if (post.getVotes() != null) {
            more += post.getVotes() + " voturi \u2022 ";
        }

        if (post.getTimeAgo() != null) {
            more += post.getTimeAgo() + " \u2022 ";
        }

        return more.substring(0, more.length() - 2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_user:
                onViewUserPressed(view);
                break;
            case R.id.thumbs_up:
                onThumbsUpPressed(view);
                break;
            case R.id.play_button:
                onPlayButtonPressed(view);
                break;
            case R.id.title:
            case R.id.image:
                onPostClick(view);
                break;
        }
    }

    public void onPostClick(View view) {
        this.postsActionsListener.showPost(this.post);
    }

    public void onViewUserPressed(View view) {
        this.postsActionsListener.showUserPosts(this.post.getUser());
    }

    public void onThumbsUpPressed(View view) {

    }

    public void onPlayButtonPressed(View view) {
        this.postsActionsListener.showVideoDialog(this.post);
    }


    public interface PostsActionsListener {
        public void showPost(Post post);

        public boolean showUsersPostsButton();

        public void showUserPosts(User user);

        public void showVideoDialog(Post post);
    }
}