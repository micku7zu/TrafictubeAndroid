package com.micutu.trafictube.Crawler.Responses;

import com.micutu.trafictube.Data.Post;
import java.util.List;
import java.util.Map;

public interface PostsListResponse {
    void onResponse(List<Post> posts, Map<String, Object> extra);
}
