package com.example.pramodn.myapplication.event_adapter;

public class notification_model {
    private String event_id;
    private String event_name;
    private String synopsis;
    private String image;

    public notification_model(String event_id, String event_name, String synopsis, String image) {
        this.event_id=event_id;
        this.event_name=event_name;
        this.synopsis=synopsis;
        this.image=image;
    }
    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImage() {
        return image;
    }
}
