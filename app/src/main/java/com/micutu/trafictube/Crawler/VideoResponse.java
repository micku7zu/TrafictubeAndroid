package com.micutu.trafictube.Crawler;

import com.micutu.trafictube.Data.VideoPost;

import java.util.Map;

public interface VideoResponse {
    public void onResponse(VideoPost videoPost, Map<String, Object> extra);
}
