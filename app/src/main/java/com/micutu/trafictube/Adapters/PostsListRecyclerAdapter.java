package com.micutu.trafictube.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.micutu.trafictube.Adapters.ViewHolders.LoadingListViewHolder;
import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.R;

import java.util.List;

public class PostsListRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final Integer VIDEO_TYPE = 0;
    private static final Integer LOADING_TYPE = 1;

    private List<Post> posts = null;
    private Context context = null;
    private OnScrollEndListener onScrollEndListener = null;
    private PostsListViewHolder.ViewUserPostsListener viewUserPostsListener = null;

    public PostsListRecyclerAdapter(PostsListViewHolder.ViewUserPostsListener viewUserPostsListener, Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
        this.onScrollEndListener = null;
        this.viewUserPostsListener = viewUserPostsListener;
    }

    public void setOnScrollEndListener(OnScrollEndListener onScrollEndListener) {
        this.onScrollEndListener = onScrollEndListener;
    }

    public void addPosts(List<Post> posts) {
        Integer initialCount = this.getItemCount();
        Integer newListSize = posts.size();
        this.posts.addAll(posts);
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

        return new PostsListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false), this.viewUserPostsListener);
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

        PostsListViewHolder postsListViewHolder = (PostsListViewHolder) holder;
        Post post = posts.get(position);
        postsListViewHolder.setTitle(String.valueOf(Html.fromHtml(post.getTitle())));
        postsListViewHolder.setMore(this.getMoreText(post));
        postsListViewHolder.setImage(this.context, post.getImage());
        postsListViewHolder.setUser(post.getUser());
        postsListViewHolder.setContext(context);

        if (this.onScrollEndListener != null && (position >= getItemCount() - 2)) {
            this.onScrollEndListener.loadPosts();
        }
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
    public int getItemCount() {
        if (posts == null) {
            return 0;
        }

        return posts.size() + 1;
    }

    public interface OnScrollEndListener {
        public void loadPosts();
    }
}