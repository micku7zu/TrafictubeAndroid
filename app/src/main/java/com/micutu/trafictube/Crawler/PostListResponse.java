package com.micutu.trafictube.Crawler;

import com.micutu.trafictube.Data.Post;

import java.util.Map;

interface PostListResponse {
    void onResponse(Post post, Map<String, Object> extra);
}
