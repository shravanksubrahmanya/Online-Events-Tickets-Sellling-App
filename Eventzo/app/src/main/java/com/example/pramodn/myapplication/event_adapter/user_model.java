package com.example.pramodn.myapplication.event_adapter;

public class user_model {

    private String fname_str;
    private String image;
    private String dob;
    private String gender;
    private String moreinfo;
    private String uid;
    private String lname_str;
    private boolean is_checked;

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public user_model(String fname_str,String lname_str, String image, String dob, String gender,String moreinfo,String uid) {

        this.fname_str = fname_str;
        this.lname_str=lname_str;
        this.image = image;
        this.dob = dob;
        this.gender = gender;
        this.moreinfo=moreinfo;
        this.uid=uid;
    }

    public String getFname_str() {
        return fname_str;
    }

    public String getLname_str() {
        return lname_str;
    }

    public String getImage() {
        return image;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getMoreinfo() {
        return moreinfo;
    }

    public String getUid() {
        return uid;
    }


}
