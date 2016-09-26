package com.micutu.trafictube.Crawler;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.micutu.trafictube.Data.Site;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Data.Video;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NormalVideos {
    private final static String TAG = NormalVideos.class.getSimpleName();

    public static final int MODE_NORMAL = 0;
    public static final int MODE_SEARCH = 1;
    public static final int MODE_USER = 2;

    private Integer mode = null;
    private String value = null;
    private Integer page = null;
    private Boolean haveNextPage = null;

    public NormalVideos() {
        this(1, MODE_NORMAL, null);
    }

    public NormalVideos(Integer mode, String value) {
        this(1, mode, value);
    }

    public NormalVideos(Integer page, Integer mode, String value) {
        this.page = page;
        this.haveNextPage = true;
        this.mode = mode;
        this.value = value;
    }

    public void getVideos(Context context, final VideosListResponse listener) {
        getVideos(context, getPage(), getMode(), getValue(), listener);
        nextPage();
    }

    public void getVideos(Context context, Integer page, Integer mode, String value, final VideosListResponse listener) {
        if (listener == null) {
            return;
        }

        String link = getPageUrl(page, mode, value);
        Log.d(TAG, "Request, page:" + page + " - link:" + link);

        StringRequest request = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Video> videos = getLatestVideosFromPageContent(response);

                            NormalVideos.this.haveNextPage = false;
                            if (videos.size() >= 20) { //only if we have more than 20 videos we need to chck if we have more pages
                                NormalVideos.this.haveNextPage = haveMorePageFromPageContent(response);
                            }
                            Log.d("TEST", "HAVE NEXT PAGE1: " + videos.size() +  " - " + NormalVideos.this.haveNextPage);

                            listener.onResponse(videos, (new HashMap<String, Object>() {{
                                put("haveNextPage", NormalVideos.this.haveNextPage);
                            }}));
                        } catch (final Exception e) {
                            listener.onResponse(null, (new HashMap<String, Object>() {{
                                put("error", Log.getStackTraceString(e));
                            }}));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                listener.onResponse(null, (new HashMap<String, Object>() {{
                    put("error", error.getMessage());
                }}));
            }
        });

        VolleySingleton.makeRequest(context, request);
    }

    private static String getPageUrl(int page) {
        if (page == 1) {
            return Site.link;
        }

        return Site.link + "/page/" + page;
    }

    private static String getPageUrl(int page, int mode, String value) {
        if (value == null || value.length() == 0) {
            return getPageUrl(page);
        }

        String encoded = value;

        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
        }

        if (mode == MODE_SEARCH) {
            return Site.link + "/page/" + page + "/?s=" + encoded;
        } else if (mode == MODE_USER) {
            return Site.link + "/author/" + value + "/page/" + page + "/";
        }

        return "";
    }

    public static List<Video> getLatestVideosFromPageContent(String content) {
        List<Video> videos = new ArrayList<Video>();

        if (content.contains("page-description\">Niciun video")) {
            return videos;
        }

        try {
            content = content.split("class=\"latest-clips\">")[1].split("<footer")[0];
        } catch (Exception e) {
            content = content.split("class=\"video-listing\">")[1].split("<footer")[0];
        }

        String temps[] = content.split("the-video");

        for (int i = 1; i < temps.length; i++) {
            Video video = new Video();
            User user = new User();

            /* ugly crawler */
            video.setTitle(temps[i].split("<h3")[1].split("</a>")[0].split("\">")[2]);
            video.setLink(temps[i].split("href=\"")[1].split("\"")[0]);
            user.setName(temps[i].split("\"author\">")[1].split("</a>")[0].split("\">")[1]);
            user.setUsername(temps[i].split("\"author\">")[1].split("/\" tit")[0].split("author/")[1]);
            video.setUser(user);
            video.setImage(temps[i].split("background-image: url\\(")[1].split("\\);\">")[0].replaceAll("\\s+", ""));
            video.setTimeAgo(temps[i].split("class=\"post-date\">")[1].split("</span>")[0]);

            videos.add(video);
        }


        return videos;
    }

    public static Boolean haveMorePageFromPageContent(String content) {
        try {
            return content.contains(">&raquo;</a></span>");
        } catch (Exception e) {
            return true;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void nextPage() {
        this.setPage(this.getPage() + 1);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }
}
