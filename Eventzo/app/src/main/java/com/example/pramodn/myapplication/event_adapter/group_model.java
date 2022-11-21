package com.example.pramodn.myapplication.event_adapter;

public class group_model {
    private String grpname;
    private String cid;
    private String cfname;
    private String clname;
    private String cimage;
    private String cgender;
    private String dateofbirth;
    private String moreinfor;

    public group_model(String grpname,String cid,String cfname,String cimage,String clname,String cgender,String cdob,String cmoreinfo) {

        this.grpname = grpname;
        this.cid=cid;
        this.cfname=cfname;
        this.clname=clname;
        this.cimage=cimage;
        this.cgender=cgender;
        dateofbirth=cdob;
        moreinfor=cmoreinfo;
    }

    public String   getGrpname() {
        return grpname;
    }

    public String getCid() {
        return cid;
    }

    public String getCfname() {
        return cfname;
    }

    public String getClname() {
        return clname;
    }

    public String getCimage() {
        return cimage;
    }

    public String getCgender() {
        return cgender;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getMoreinfor() {
        return moreinfor;
    }
}
