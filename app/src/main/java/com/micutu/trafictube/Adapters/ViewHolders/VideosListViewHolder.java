package com.micutu.trafictube.Adapters.ViewHolders;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.micutu.trafictube.Crawler.VolleySingleton;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.R;
import com.micutu.trafictube.Views.AppCompatImageButtonWithTooltip;


public class VideosListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private User user = null;
    private TextView title = null;
    private TextView more = null;
    private NetworkImageView image = null;
    private ViewUserVideosListener viewUserVideosListener = null;

    public VideosListViewHolder(final View itemView, ViewUserVideosListener viewUserVideosListener) {
        super(itemView);
        this.viewUserVideosListener = viewUserVideosListener;

        this.title = (TextView) itemView.findViewById(R.id.title);
        this.more = (TextView) itemView.findViewById(R.id.more);
        this.image = (NetworkImageView) itemView.findViewById(R.id.image);

        ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.thumbs_up)).setOnClickListener(this);
        ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.play_button)).setOnClickListener(this);

        if (this.viewUserVideosListener == null) {
            ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.view_user)).setVisibility(View.GONE);
        } else {
            ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.view_user)).setOnClickListener(this);
        }
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setMore(String text) {
        this.more.setText(text);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setImage(Context context, String url) {
        this.image.setImageUrl(url, VolleySingleton.getImageLoader(context));
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
        }
    }

    public void onViewUserPressed(View view) {
        this.viewUserVideosListener.showUserVideos(this.user);
    }

    public void onThumbsUpPressed(View view) {

    }

    public void onPlayButtonPressed(View view) {

    }

    public interface ViewUserVideosListener {
        public void showUserVideos(User user);
    }
}