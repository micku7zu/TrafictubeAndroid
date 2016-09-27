package com.micutu.trafictube.Adapters.ViewHolders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.micutu.trafictube.Activities.MainActivity;
import com.micutu.trafictube.Crawler.VolleySingleton;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.R;
import com.micutu.trafictube.Views.AppCompatImageButtonWithTooltip;


public class PostsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private User user = null;
    private TextView title = null;
    private TextView more = null;
    private NetworkImageView image = null;
    private ViewUserPostsListener viewUserPostsListener = null;
    private Context context = null;

    public PostsListViewHolder(final View itemView, ViewUserPostsListener viewUserPostsListener) {
        super(itemView);
        this.viewUserPostsListener = viewUserPostsListener;

        this.title = (TextView) itemView.findViewById(R.id.title);
        this.more = (TextView) itemView.findViewById(R.id.more);
        this.image = (NetworkImageView) itemView.findViewById(R.id.image);

        ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.thumbs_up)).setOnClickListener(this);
        ((AppCompatImageButtonWithTooltip) itemView.findViewById(R.id.play_button)).setOnClickListener(this);

        if (this.viewUserPostsListener == null) {
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
        this.viewUserPostsListener.showUserPosts(this.user);
    }

    public void onThumbsUpPressed(View view) {

    }

    public void onPlayButtonPressed(View view) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.FullScreenDialog);
        @SuppressLint("InflateParams") final View youtubeView = layoutInflater.inflate(R.layout.youtube_player_alert_dialog, null);
        builder.setView(youtubeView);
        AlertDialog dialog = builder.create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ((Activity) context).getFragmentManager().beginTransaction().
                        remove(((Activity) context).getFragmentManager().findFragmentById(R.id.youtube_fragment)).commit();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                YouTubePlayerFragment youtubePlayerFragment = (YouTubePlayerFragment) ((Activity) context).getFragmentManager().findFragmentById(R.id.youtube_fragment);
                youtubePlayerFragment.initialize(MainActivity.YOUTUBE_DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo("nCgQDjiotG0");
                        youTubePlayer.play();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
            }
        });
        dialog.show();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public interface ViewUserPostsListener {
        public void showUserPosts(User user);
    }
}