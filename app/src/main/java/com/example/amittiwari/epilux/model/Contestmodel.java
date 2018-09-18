package com.example.amittiwari.epilux.model;

public class Contestmodel {

   String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    String code;
    String sdate;
    String edate;
    public Contestmodel(String name,String code,String sdate,String edate)
    {
        this.name = name;
        this.code = code;
        this.sdate = sdate;
        this.edate = edate;
    }
}
