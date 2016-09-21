package com.micutu.trafictube.Data;

public class User {
    private String name = null;
    private String username = null;
    private String avatar = null;
    private Integer numberOfVideos = null;
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

    public Integer getNumberOfVideos() {
        return numberOfVideos;
    }

    public void setNumberOfVideos(Integer numberOfVideos) {
        this.numberOfVideos = numberOfVideos;
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
}
