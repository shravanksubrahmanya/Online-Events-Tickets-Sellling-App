package com.example.pramodn.myapplication.event_adapter;

public class notify_model {
    private String grpname;
    private String event_id;
    private String event_name;
    private String event_image;
    private String synopsis;

    public notify_model(String grpname_str, String event_id, String event_name, String event_image, String synopsis) {
        this.grpname=grpname_str;
        this.event_id=event_id;
        this.event_name=event_name;
        this.event_image=event_image;
        this.synopsis=synopsis;
    }

    public String getGrpname() {
        return grpname;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_image() {
        return event_image;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
