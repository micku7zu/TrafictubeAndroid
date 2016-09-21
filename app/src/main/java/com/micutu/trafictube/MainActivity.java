package com.micutu.trafictube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.micutu.trafictube.Crawler.NormalVideos;
import com.micutu.trafictube.Crawler.VideosListResponse;
import com.micutu.trafictube.Data.Video;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NormalVideos videos = new NormalVideos("bihor");

        videos.getVideos(getApplicationContext(), new VideosListResponse() {
            @Override
            public void onResponse(List<Video> videos, Map<String, Object> extra) {
                for(HashMap.Entry<String, Object> info : extra.entrySet()) {
                    System.out.println(info.getKey().toString() + "- " + info.getValue().toString());
                }

                for(Video video : videos) {
                    System.out.println(video);
                }
            }
        });
    }
}
