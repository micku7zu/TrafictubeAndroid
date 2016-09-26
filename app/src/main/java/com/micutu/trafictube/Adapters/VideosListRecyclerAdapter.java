package com.micutu.trafictube.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.micutu.trafictube.Adapters.ViewHolders.LoadingListViewHolder;
import com.micutu.trafictube.Adapters.ViewHolders.VideosListViewHolder;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;

import java.util.List;

public class VideosListRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final Integer VIDEO_TYPE = 0;
    private static final Integer LOADING_TYPE = 1;

    private List<Video> videos = null;
    private Context context = null;
    private OnScrollEndListener onScrollEndListener = null;

    public VideosListRecyclerAdapter(Context context, List<Video> videos) {
        this.videos = videos;
        this.context = context;
        this.onScrollEndListener = null;
    }

    public void setOnScrollEndListener(OnScrollEndListener onScrollEndListener) {
        this.onScrollEndListener = onScrollEndListener;
    }

    public void addVideos(List<Video> videos) {
        Integer initialCount = this.getItemCount();
        Integer newListSize = videos.size();
        this.videos.addAll(videos);
        this.notifyItemRangeInserted(initialCount, newListSize);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == this.getItemCount() - 1) {
            return LOADING_TYPE;
        }

        return VIDEO_TYPE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_TYPE) {
            return new LoadingListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_list_view_holder, parent, false));
        }

        return new VideosListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == LOADING_TYPE) {
            if (this.onScrollEndListener == null) {
                holder.itemView.findViewById(R.id.loading_list_progress_bar).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.finishedText).setVisibility(View.VISIBLE);
            }
            return;
        }

        VideosListViewHolder videosListViewHolder = (VideosListViewHolder) holder;
        Video video = videos.get(position);
        videosListViewHolder.setTitle(String.valueOf(Html.fromHtml(video.getTitle())));
        videosListViewHolder.setMore(this.getMoreText(video));
        videosListViewHolder.setImage(this.context, video.getImage());

        if (this.onScrollEndListener != null && (position >= getItemCount() - 2)) {
            this.onScrollEndListener.loadVideos();
        }
    }

    private String getMoreText(Video video) {
        String more = "";
        if (video.getUser() != null && video.getUser().getName() != null) {
            more += video.getUser().getName() + " \u2022 ";
        }

        if (video.getVotes() != null) {
            more += video.getVotes() + " voturi \u2022 ";
        }

        if (video.getTimeAgo() != null) {
            more += video.getTimeAgo() + " \u2022 ";
        }

        return more.substring(0, more.length() - 2);
    }

    @Override
    public int getItemCount() {
        if (videos == null) {
            return 0;
        }

        return videos.size() + 1;
    }

    public interface OnScrollEndListener {
        public void loadVideos();
    }
}