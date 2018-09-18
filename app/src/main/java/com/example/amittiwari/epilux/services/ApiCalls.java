package com.example.amittiwari.epilux.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.amittiwari.epilux.Contests;
import com.example.amittiwari.epilux.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amit Tiwari on 05-09-2018.
 */

public class ApiCalls {
    MySingleton mySingleton;
    Context context;
    User user;
    Session session;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject;

  public   ApiCalls(Context context)
    {
        this.context = context;
    }

    public void CallForToken(String code) throws JSONException {
        String url = "https://api.codechef.com/oauth/token";
        JSONObject params = new JSONObject();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("client_id","ee24c470a38e6580f3968c7cf3f8c8bd");
        params.put("client_secret","219880d4827a19d453b074d618f972e3");
        params.put("redirect_uri","https://olcadmy.000webhostapp.com");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("status").equals("OK")){
                                jsonObject = response.getJSONObject("result").getJSONObject("data");
                                session = new Session(context);
                                session.setCodes(jsonObject);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                }){

        };
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }



    public void Call(final String acct)
    {

        Log.d("epsileer","this is inner error 2");
        String url = "https://api.codechef.com/contests";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("status").equals("OK")){
                                jsonArray = (JSONArray) response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getJSONArray("contestList");
                                setJsonArray(jsonArray);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization","Bearer "+acct);
                return params;
            }


        };
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }
}
