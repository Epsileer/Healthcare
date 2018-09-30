package com.example.amittiwari.epilux.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Amit Tiwari on 06-09-2018.
 */

public class Session {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor ed;
    public static final  String MyPREFERENCES  = "codechef";
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";
    Context context;
    Gson gson;
    User user;
    String acc;
    String reft;
    String username;



    public Boolean getLoggedin() {
        return loggedin;
    }

    public void setLoggedin(Boolean loggedin) {
        this.loggedin = loggedin;
        ed.putBoolean("logged",loggedin);
        ed.commit();
    }

    Boolean loggedin;

    public Session(Context context)
    {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ed = sharedpreferences.edit();
        gson = new Gson();
        loggedin = sharedpreferences.getBoolean("logged",false);
        username = null;

    }
    public User getUser()
   {
       String user = sharedpreferences.getString("user"," ");
       User u = gson.fromJson(user,User.class);
       return u;
   }

    public void setUser()
   {
       String json = gson.toJson(user);
       ed.putString("user",json);
       ed.commit();
   }
   public void setUsername(String username)
   { this.username = username;
       ed.putString("uname",username);
       ed.commit();


   }
   public String getUsername()
   {
       if(username == null)
       {
           username = sharedpreferences.getString("uname","codechef");
       }


       return username;
   }

    public void setCodes(JSONObject response) throws JSONException {
       acc = response.getString("access_token");
       reft = response.getString("refresh_token");
       user = new User(acc,reft);
       setLoggedin(true);
       setUser();
   }
   public void setnewUser(String acc,String reft,String username)
   {   user = new User(acc,reft,username);
       setUser();
   }
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.commit();
        return true;
    }
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }


}
