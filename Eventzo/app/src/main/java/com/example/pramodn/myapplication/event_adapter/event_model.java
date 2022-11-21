package com.example.pramodn.myapplication.event_adapter;

public class event_model {

    private String event_id;
    private String event_name;
    private String event_synopsis;
    private String event_image;
    private int likes;
    private int numberofcomments;
    private boolean is_checked;

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public event_model(String event_id, String event_name, String event_synopsis, String event_image, int likes, boolean is_checked,int numberofcomments) {

        this.event_id = event_id;
        this.event_name = event_name;
        this.event_synopsis = event_synopsis;
        this.event_image = event_image;
        this.likes=likes;
        this.is_checked=is_checked;
        this.numberofcomments=numberofcomments;


    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_synopsis() {
        return event_synopsis;
    }

    public String getEvent_image() {
        return event_image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getnumberofcomments(){return numberofcomments;}

}
