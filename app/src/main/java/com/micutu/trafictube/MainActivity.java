package com.micutu.trafictube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.micutu.trafictube.Crawler.TopVideosSingleton;
import com.micutu.trafictube.Crawler.VideoListResponse;
import com.micutu.trafictube.Crawler.VideosListResponse;
import com.micutu.trafictube.Data.Video;

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


        TopVideosSingleton.getTwoDaysTopVideos(getApplicationContext(), new VideosListResponse() {
            @Override
            public void onResponse(List<Video> videos, Map<String, Object> extra) {
                if(extra.containsKey("error")) {
                    System.out.println(extra.get("error"));
                    return;
                }

                for(Video video : videos) {
                    System.out.println(video);
                }
            }
        });
    }
}
