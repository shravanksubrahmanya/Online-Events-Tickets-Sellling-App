package com.example.pramodn.myapplication.event_adapter;

public class purchase_model {

    private String event_id;
    private String event_name;
    private String bid;
    private String tickettype;
    private String transactionid;
    private int numberoftickets;
    private int totalamount;
    private boolean is_checked;

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public purchase_model(String event_id, String event_name, String bid,String transactionid,String tickettype, int numberoftickets, int totalamount) {

        this.event_id = event_id;
        this.event_name = event_name;
        this.bid = bid;
        this.transactionid = transactionid;
        this.tickettype=tickettype;
        this.numberoftickets = numberoftickets;
        this.totalamount =totalamount;
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


}
