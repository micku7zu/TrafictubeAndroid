package com.micutu.trafictube.Data;

import java.io.Serializable;

public class Video implements Serializable {
    public static int TYPE_YOUTUBE = 0;
    public static int TYPE_VIMEO = 1;
    public static int TYPE_OTHER = 2;

    private Integer type = null;
    private String id = null;
    private String imageUrl = null;

    public Video() {
        this.type = null;
        this.imageUrl = null;
        this.id = null;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setType(String type) {
        if (type.equals("yt")) {
            setType(TYPE_YOUTUBE);
        } else if (type.equals("vimeo")) {
            setType(TYPE_VIMEO);
        } else {
            setType(TYPE_OTHER);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
