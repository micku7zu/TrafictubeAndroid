package com.micutu.trafictube.Crawler;

import com.micutu.trafictube.Data.Post;

import java.util.Map;

interface PostResponse {
    void onResponse(Post post, Map<String, Object> extra);
}
