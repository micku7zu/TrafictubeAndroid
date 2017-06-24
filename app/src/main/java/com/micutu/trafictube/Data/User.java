package com.micutu.trafictube.Data;

import java.io.Serializable;

public class User implements Serializable {
    private String name = null;
    private String username = null;
    private String avatar = null;
    private Integer numberOfPosts = null;
    private String cameraName = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }


    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getProfile() {
        if(this.getUsername() == null) {
            return null;
        }

        return Site.link + "/author/" + this.getUsername();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
