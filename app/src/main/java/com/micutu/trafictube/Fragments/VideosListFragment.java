package com.micutu.trafictube.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.micutu.trafictube.Adapters.VideosListRecyclerAdapter;
import com.micutu.trafictube.Crawler.NormalVideos;
import com.micutu.trafictube.Crawler.TopVideosSingleton;
import com.micutu.trafictube.Crawler.VideosListResponse;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;

import java.util.List;
import java.util.Map;

public class VideosListFragment extends Fragment implements VideosListResponse {
    private final static String TAG = VideosListFragment.class.getSimpleName();
    public static final String MENU_ID = "menu_id";

    private View root;
    private Context context;

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
        switch (menuId) {
            case R.id.latest:
                (new NormalVideos()).getVideos(context, this);
                break;
            case R.id.search:
                (new NormalVideos("Bihor")).getVideos(context, this);
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

    private void showVideos(List<Video> videos) {
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        VideosListRecyclerAdapter adapter = new VideosListRecyclerAdapter(context, videos);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showError() {
        root.findViewById(R.id.error_text).setVisibility(View.VISIBLE);
    }
}
