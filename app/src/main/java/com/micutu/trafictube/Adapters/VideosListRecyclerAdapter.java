package com.micutu.trafictube.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.micutu.trafictube.Adapters.ViewHolders.VideosListViewHolder;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;

import java.net.URLEncoder;
import java.util.List;

public class VideosListRecyclerAdapter extends RecyclerView.Adapter<VideosListViewHolder> {
    private List<Video> videos;
    private Context context;

    public VideosListRecyclerAdapter(Context context, List<Video> videos) {
        this.videos = videos;
        this.context = context;
    }

    public void addMore(List<Video> videos) {
        this.videos.addAll(videos);
    }

    @Override
    public VideosListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return a new VideosListViewHolder from layoutinflater
        return new VideosListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(VideosListViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.setTitle(String.valueOf(Html.fromHtml(video.getTitle())));
        holder.setMore(this.getMoreText(video));
        holder.setImage(this.context, video.getImage());
    }

    private String getMoreText(Video video) {
        String more = "";
        if(video.getUser() != null && video.getUser().getName() != null) {
            more += video.getUser().getName() + " \u2022 ";
        }

        if(video.getVotes() != null) {
            more += video.getVotes() + "\1F44D \u2022 ";
        }

        if(video.getTimeAgo() != null) {
            more += video.getTimeAgo() + " \u2022 ";
        }

        return more.substring(0, more.length() - 2);
    }

    @Override
    public int getItemCount() {
        if(videos == null) {
            return 0;
        }

        return videos.size();
    }
}