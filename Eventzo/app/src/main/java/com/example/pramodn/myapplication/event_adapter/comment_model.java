package com.example.pramodn.myapplication.event_adapter;

public class comment_model {
    private String username;
    private String image_str;
    private String comment_str;

    public comment_model(String username, String image_str, String comment_str) {

        this.username = username;
        this.image_str = image_str;
        this.comment_str = comment_str;
    }

    public String getUsername() {
        return username;
    }

    public String getImage_str() {
        return image_str;
    }

    public String getComment_str() {
        return comment_str;
    }
}

