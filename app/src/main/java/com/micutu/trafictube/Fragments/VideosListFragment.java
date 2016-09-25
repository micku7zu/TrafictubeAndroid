package com.micutu.trafictube.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.micutu.trafictube.Adapters.VideosListRecyclerAdapter;
import com.micutu.trafictube.Crawler.NormalVideos;
import com.micutu.trafictube.Crawler.TopVideosSingleton;
import com.micutu.trafictube.Crawler.VideosListResponse;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;
import com.micutu.trafictube.Views.HidingScrollListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideosListFragment extends Fragment implements VideosListResponse {
    private final static String TAG = VideosListFragment.class.getSimpleName();
    public static final String MENU_ID = "menu_id";

    private View root;
    private Context context;
    private Toolbar toolbar;
    private NormalVideos normalVideos = null;

    public VideosListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = inflater.getContext();
        root = inflater.inflate(R.layout.videos_list_fragment, container, false);
        loadVideos(getArguments().getInt(MENU_ID));
        return root;
    }

    private void loadVideos(int menuId) {
        normalVideos = null;
        switch (menuId) {
            case R.id.latest:
                normalVideos = new NormalVideos();
                normalVideos.getVideos(context, this);
                break;
            case R.id.search:
                normalVideos = new NormalVideos("Bihor");
                normalVideos.getVideos(context, this);
                break;
            case R.id.top_general:
                TopVideosSingleton.getGeneralTopVideos(context, this);
                break;
            case R.id.top_two_days:
                TopVideosSingleton.getTwoDaysTopVideos(context, this);
                break;
            case R.id.top_weekly:
                TopVideosSingleton.getWeeklyTopVideos(context, this);
                break;
        }
    }

    @Override
    public void onResponse(List<Video> videos, Map<String, Object> extra) {
        //hide the loading even if it's an error
        root.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        //if we get no videos, show the error
        if (videos == null) {
            this.showError();
        }

        /* log the error */
        if (extra.containsKey("error")) {
            Log.d(TAG, " ERROR onResponse: " + extra.get("error"));
        }

        showVideos(videos);
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

    private void showVideos(List<Video> videos) {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final VideosListRecyclerAdapter adapter = new VideosListRecyclerAdapter(context, videos);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.VISIBLE);

        if (normalVideos == null) {
            return;
        }

        adapter.setOnScrollEndListener(new VideosListRecyclerAdapter.OnScrollEndListener() {
            @Override
            public void loadVideos() {
                normalVideos.getVideos(context, new VideosListResponse() {
                    @Override
                    public void onResponse(List<Video> videos, Map<String, Object> extra) {
                        Boolean haveNextPage = false;
                        if(extra.containsKey("haveNextPage")) {
                            haveNextPage = (Boolean) extra.get("haveNextPage");
                        }
                        if(haveNextPage == false) {
                            adapter.setOnScrollEndListener(null);
                        }

                        if (videos == null) {
                            return;
                        }

                        adapter.addVideos(videos);
                    }
                });
            }
        });
    }

    public void showError() {
        root.findViewById(R.id.error_container).setVisibility(View.VISIBLE);
    }

}
