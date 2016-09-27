package com.micutu.trafictube.Crawler.Responses;

import com.micutu.trafictube.Data.Post;

import java.util.Map;

public interface PostResponse {
    void onResponse(Post post, Map<String, Object> extra);
}
