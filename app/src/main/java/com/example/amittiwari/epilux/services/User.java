package com.example.amittiwari.epilux.services;

/**
 * Created by Amit Tiwari on 06-09-2018.
 */

public class User {

    public String getName() {
        return name;
    }

    String name;

    public void setUsername(String username) {
        this.username = username;
    }

    String username;
    boolean logged_in = false;

    public String getCountry() {
        return country;
    }



    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    String country;
    String email;
    String phone;

    public String getAcct() {
        return acct;
    }

    public String getReft() {
        return reft;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public void setReft(String reft) {
        this.reft = reft;
    }

    String acct;
    String reft;

  public User()
   {

   }
   public User(String acct,String reft,String username)
    {
        this.acct = acct;
        this.reft = reft;
        this.username = username;
    }

  public   User(String acct,String reft)
    {
       this.acct = acct;
       this.reft = reft;
    }


    User(String acct,String reft,String name,String username,String country)
    {
        this.name = name;
        this.name = username;
        this.country = country;
        this.acct = acct;
        this.reft = reft;
    }

    User(String name,String username,String country, String email)
    {
        this.name = name;
        this.name = username;
        this.country = country;

        this.email = email;
        //this.phone = phone;

    }

    void setEmail(String Email)
    {
        this.email = Email;
    }

    void setPhone(String phone)
    {
        this.phone = phone;
    }

   public String getUsername()
    {
        return username;
    }
}
