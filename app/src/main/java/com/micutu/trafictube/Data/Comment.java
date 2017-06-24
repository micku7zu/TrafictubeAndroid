package com.micutu.trafictube.Data;

import java.io.Serializable;

public class Comment implements Serializable{
    private Integer id = null;
    private User user = null;
    private String date = null;
    private String content = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
