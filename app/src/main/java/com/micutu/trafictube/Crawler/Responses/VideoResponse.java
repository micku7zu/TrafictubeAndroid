package com.micutu.trafictube.Crawler.Responses;

import com.micutu.trafictube.Data.Video;

import java.util.Map;

public interface VideoResponse {
    void onResponse(Video video, Map<String, Object> extra);
}
