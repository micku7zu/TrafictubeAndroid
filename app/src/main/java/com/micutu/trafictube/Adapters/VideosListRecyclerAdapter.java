package com.micutu.trafictube.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.micutu.trafictube.Adapters.ViewHolders.VideosListViewHolder;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;

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

        holder.setUser(video.getUser().getName());
        holder.setTitle(video.getTitle());

        if(video.getVotes() != null) {
            holder.setMore(video.getVotes() + "");
        }

        if(video.getTimeAgo() != null) {
            holder.setMore(video.getTimeAgo());
        }

        holder.setImage(this.context, video.getImage());
    }

    @Override
    public int getItemCount() {
        if(videos == null) {
            return 0;
        }

        return videos.size();
    }
}