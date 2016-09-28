package com.micutu.trafictube.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.micutu.trafictube.Adapters.PostsListRecyclerAdapter;
import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder;
import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Crawler.NormalPosts;
import com.micutu.trafictube.Crawler.TopPostsSingleton;
import com.micutu.trafictube.Crawler.Responses.PostsListResponse;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.R;
import com.micutu.trafictube.Views.HidingScrollListener;

import java.util.List;
import java.util.Map;

public class PostsListFragment extends Fragment implements PostsListResponse {
    private final static String TAG = PostsListFragment.class.getSimpleName();
    public static final String MENU_ID = "menu_id";
    public static final String SEARCH = "search";
    public static final String USERNAME = "username";

    private View root;
    private Context context;
    private Toolbar toolbar;
    private NormalPosts normalPosts = null;
    private Integer menuId = null;

    public PostsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = inflater.getContext();
        this.menuId = getArguments().getInt(MENU_ID);
        root = inflater.inflate(R.layout.posts_list_fragment, container, false);
        loadPosts();
        return root;
    }

    private void loadPosts() {
        normalPosts = null;
        switch (menuId) {
            case R.id.latest:
                normalPosts = new NormalPosts();
                normalPosts.getPosts(context, this);
                break;
            case R.id.search:
                normalPosts = new NormalPosts(NormalPosts.MODE_SEARCH, getArguments().getString(SEARCH));
                normalPosts.getPosts(context, this);
                break;
            case R.id.user_posts:
                normalPosts = new NormalPosts(NormalPosts.MODE_USER, getArguments().getString(USERNAME));
                normalPosts.getPosts(context, this);
                break;
            case R.id.top_general:
                TopPostsSingleton.getGeneralTopPosts(context, this);
                break;
            case R.id.top_two_days:
                TopPostsSingleton.getTwoDaysTopPosts(context, this);
                break;
            case R.id.top_weekly:
                TopPostsSingleton.getWeeklyTopPosts(context, this);
                break;
        }
    }

    @Override
    public void onResponse(List<Post> posts, Map<String, Object> extra) {
        //hide the loading even if it's an error
        root.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        //if we get no posts, show the error
        if (posts == null) {
            this.showError();
            return;
        }

        /* log the error */
        if (extra.containsKey("error")) {
            Log.d(TAG, " ERROR onResponse: " + extra.get("error"));
        }

        if (posts.size() == 0) {
            showNoPosts();
            return;
        }

        showPosts(posts, extra);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideToolbar();
            }

            @Override
            public void onShow() {
                showToolbar();
            }
        });

        showToolbar();
    }

    private void hideToolbar() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showToolbar() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    private void showPosts(List<Post> posts, Map<String, Object> extra) {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        PostsActionsListener onViewUserPostsListener = null;
        if(getActivity() instanceof PostsActionsListener) {
            onViewUserPostsListener = (PostsActionsListener) getActivity();
        }

        final PostsListRecyclerAdapter adapter = new PostsListRecyclerAdapter(onViewUserPostsListener, context, posts);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        if (!extra.containsKey("haveNextPage") || !((Boolean) extra.get("haveNextPage"))) {
            return;
        }

        adapter.setOnScrollEndListener(new PostsListRecyclerAdapter.OnScrollEndListener() {
            @Override
            public void loadPosts() {
                normalPosts.getPosts(context, new PostsListResponse() {
                    @Override
                    public void onResponse(List<Post> posts, Map<String, Object> extra) {
                        Boolean haveNextPage = true;
                        if (extra.containsKey("haveNextPage")) {
                            haveNextPage = (Boolean) extra.get("haveNextPage");
                        }

                        if (!haveNextPage) {
                            adapter.setOnScrollEndListener(null);
                        }

                        if (posts == null) {
                            return;
                        }

                        adapter.addPosts(posts);
                    }
                });
            }
        });
    }

    private void showNoPosts() {
        root.findViewById(R.id.search_no_results).setVisibility(View.VISIBLE);
        root.findViewById(R.id.search_again_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnSearchDialogShow) getActivity()).showSearchDialog();
            }
        });
    }

    public void showError() {
        root.findViewById(R.id.error_container).setVisibility(View.VISIBLE);
        root.findViewById(R.id.retry_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.findViewById(R.id.error_container).setVisibility(View.GONE);
                root.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                loadPosts();
            }
        });
    }

    public interface OnSearchDialogShow {
        void showSearchDialog();
    }

}
