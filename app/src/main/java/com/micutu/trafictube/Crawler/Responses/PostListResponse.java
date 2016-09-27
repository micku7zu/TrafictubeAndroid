package com.micutu.trafictube.Crawler.Responses;

import com.micutu.trafictube.Data.Post;

import java.util.Map;

public interface PostListResponse {
    void onResponse(Post post, Map<String, Object> extra);
}
