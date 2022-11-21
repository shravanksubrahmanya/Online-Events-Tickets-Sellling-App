package com.example.pramodn.myapplication.event_adapter;

public class sold_model {

    private String event_id;
    private String event_name;
    private String bid;
    private String tickettype;
    private String transactionid;
    private int numberoftickets;
    private int totalamount;
    private String user_name;
    private String mobileno;
    private String email;

    public sold_model(String event_id, String event_name, String bid, String transactionid, String tickettype, int numberoftickets, int totalamount, String user_name, String mobileno, String email) {

        this.event_id = event_id;
        this.event_name = event_name;
        this.bid = bid;
        this.transactionid = transactionid;
        this.tickettype=tickettype;
        this.numberoftickets = numberoftickets;
        this.totalamount =totalamount;
        this.user_name=user_name;
        this.mobileno=mobileno;
        this.email=email;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getBid() {
        return bid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public String getTickettype() {
        return tickettype;
    }

    public int getNumberoftickets() {
        return numberoftickets;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getEmail() {
        return email;
    }

}