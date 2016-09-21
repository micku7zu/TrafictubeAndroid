package com.micutu.trafictube.Crawler;

import com.micutu.trafictube.Data.Video;
import java.util.List;
import java.util.Map;

public interface VideosListResponse {
    public void onResponse(List<Video> videos, Map<String, Object> extra);
}
