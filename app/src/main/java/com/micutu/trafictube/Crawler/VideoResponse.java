package com.micutu.trafictube.Crawler;

import com.micutu.trafictube.Data.Video;

import java.util.Map;

public interface VideoResponse {
    public void onResponse(Video video, Map<String, Object> extra);
}
