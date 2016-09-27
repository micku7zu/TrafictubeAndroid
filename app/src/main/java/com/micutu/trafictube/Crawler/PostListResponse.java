package com.micutu.trafictube.Crawler;

import com.micutu.trafictube.Data.Post;

import java.util.Map;

public interface PostListResponse {
    public void onResponse(Post post, Map<String, Object> extra);
}
