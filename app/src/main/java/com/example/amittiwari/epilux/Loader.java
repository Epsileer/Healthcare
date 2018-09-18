package com.example.amittiwari.epilux;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.amittiwari.epilux.model.Apiurls;
import com.example.amittiwari.epilux.services.ApiCalls;
import com.example.amittiwari.epilux.services.MySingleton;
import com.example.amittiwari.epilux.services.Session;
import com.example.amittiwari.epilux.services.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Loader extends AppCompatActivity {
    Session session;
    ProgressBar pb;
    Context context;
    ApiCalls apiCalls;
    JSONArray s = new JSONArray();

    public Loader() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        pb = findViewById(R.id.pb);
        context = this;
        session = new Session(this);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(session.getLoggedin())
                        {
                            /*
                            User user = session.getUser();
                            final String acct = user.getAcct();
                            apiCalls = new ApiCalls(getApplication());
                            apiCalls.Call(acct);
                            while(apiCalls.getJsonArray() == null){

                            }
                            Intent intent = new Intent(context, MainActivity.class);
                            Bundle b = new Bundle();
                            b.putString("Value",apiCalls.getJsonArray().toString());
                            intent.putExtras(b);
                            startActivity(intent);  */
                            Log.d("access token",session.getUser().getAcct());
                            Log.d("fcm",session.getDeviceToken());

                            try {
                                CallForNewToken();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {

                            Intent i = new Intent(context, Login.class);
                            startActivity(i);
                            finish();

                        }
                    }
                }).start();
            }
        }, 2000 );




    }
    public void Call()
    {
        User user = session.getUser();

        final String auth = user.getAcct();

        String url = Apiurls.allcontest;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("epsileer","this is inner error "+session.getUser().getReft());
                        try {
                            if(response.get("status").equals("OK")){
                                //jsonArray = (JSONArray) response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getJSONArray("contestList");
                                User user = session.getUser();
                                // apiCalls = new ApiCalls(getApplication());
                                String acc = user.getAcct();

                                Intent intent = new Intent(context, MainActivity.class);
                                //apiCalls.Call(acc);
                                JSONArray j = (JSONArray) response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getJSONArray("contestList");
                                MySingleton.getInstance(getApplicationContext()).setJsonArray(j);
                                MySingleton.getInstance(getApplicationContext()).setCurrentTime( response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getLong("currentTime"));
                                Bundle b = new Bundle();
                                Log.d("error  :" ,j.toString());
                                b.putString("Value",j.toString());
                                intent.putExtras(b);

                                startActivity(intent);
                                finish();
                            }
                            else if(response.get("status").equals("error")){
                                CallForNewToken();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("epsileer","this is inner error 2.1");
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("epsileer","this is inner error 2.2");
                    }
                }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization","Bearer "+auth);
                return params;
            }


        };
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    public void swapFragment(Fragment fragment,JSONArray Code) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        Bundle b = new Bundle();
        b.putString("Value",Code.toString());
        fragment.setArguments(b);
        transaction.replace(R.id.cont, fragment);
        transaction.commit();
    }

    public  void CallForNewToken() throws JSONException {
        String reft = session.getUser().getReft();
        Log.d("hastable","this is for new token");
        String url = "https://api.codechef.com/oauth/token";
        JSONObject params = new JSONObject();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token",reft);
        params.put("client_id",Apiurls.clientid);
        params.put("client_secret",Apiurls.clientscret);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("status").equals("OK")){
                                JSONObject jsonObject;
                                jsonObject = response.getJSONObject("result").getJSONObject("data");
                                session = new Session(context);

                                session.setCodes(jsonObject);
                                Log.d("new acc",session.getUser().getAcct());
                                Call();
                            }
                            else if(response.get("status").equals("error")){
}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Intent i = new Intent(context, Login.class);
                        startActivity(i);
                        finish();
                    }
                }){

        };
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
