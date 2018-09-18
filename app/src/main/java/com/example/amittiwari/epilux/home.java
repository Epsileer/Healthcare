package com.example.amittiwari.epilux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WebView vb;
    WebViewClient vc;
    ApiCalls apiCalls;
    Session session;
  String s = Apiurls.login;
    private OnFragmentInteractionListener mListener;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
       vb = v.findViewById(R.id.webscreen);
       vb.loadUrl(s);
       vb.setInitialScale(1);
       vb.getSettings().setJavaScriptEnabled(true);
       vb.getSettings().setLoadWithOverviewMode(true);
       vb.getSettings().setUseWideViewPort(true);
       vb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
       vb.setScrollbarFadingEnabled(false);
       vb.setWebViewClient(new MyBrowser());
       vb.getSettings().setBuiltInZoomControls(true);
       vb.getSettings().setDisplayZoomControls(false);
       vb.clearCache(true);
       apiCalls = new ApiCalls(getContext());
       return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if(url.indexOf("http://149.129.135.60/epilux/welcome.php") > -1 )
            {
                String Url = url;
                int l = Url.indexOf('&') - Url.indexOf('=') -1;
                char code[] = new char[l];
                Url.getChars(Url.indexOf('=')+1,Url.indexOf('&'),code,0);
                String url_code = String.valueOf(code);
                try {
                    CallForToken(url_code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                return false;
            }
            return true;
        }
        public void onPageStarted(WebView view, String url) {

            if(url.indexOf("http://149.129.135.60/epilux/welcome.php") > -1 )
            {
                Toast.makeText(getContext(),url,Toast.LENGTH_LONG).show();
            }
        }
        public void onPageFinished(WebView view, String url) {
            getActivity().findViewById(R.id.loadbar).setVisibility(View.GONE);
        }

    }
    public void swapFragment(Fragment fragment,String Code) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle b = new Bundle();
        b.putString("Code",Code);
        fragment.setArguments(b);
        transaction.replace(R.id.cont, fragment);
        transaction.commit();
    }

    public void CallForToken(String code) throws JSONException {
        String url = "https://api.codechef.com/oauth/token";
        JSONObject params = new JSONObject();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("client_id","ee24c470a38e6580f3968c7cf3f8c8bd");
        params.put("client_secret","219880d4827a19d453b074d618f972e3");
        params.put("redirect_uri",Apiurls.redirecturl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("status").equals("OK")){
                                JSONObject jsonObject = response.getJSONObject("result").getJSONObject("data");
                                session = new Session(getContext());
                                session.setCodes(jsonObject);

                                Call();


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
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


    }
    public void Call()
    {
        final Session session1 = new Session(getContext());
        final User user = session.getUser();

        final String auth = user.getAcct();

        String url = "https://api.codechef.com/users/me";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("status").equals("OK")){
                                String acc = user.getAcct();
                                JSONObject j = (JSONObject) response.getJSONObject("result").getJSONObject("data").getJSONObject("content");
                                Log.d("name&device","jbjhgfjyt");
                                 session.setUsername(j.getString("username").toString());
                                sendUserData(session.getDeviceToken(),j.getString("username").toString(),j.getString("fullname").toString());
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
                params.put("Authorization","Bearer "+auth);
                return params;
            }


        };
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }
    public void sendUserData(final String device, final String username, final String fullname)
    {
        final Session session = new Session(getContext());
        final User user = session.getUser();

        final String auth = user.getAcct();

        Log.d("usernam&fullname&device",username+" "+fullname+" "+device);

        String url = "http://149.129.135.60/server/RegisterDevice.php?username="+username+"&device="+device;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("error").equals(0)){
                                Log.d("faltu",username+" "+fullname+" "+device);
                                Intent intent = new Intent(getContext(),Loader.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            Intent intent = new Intent(getContext(),Loader.class);
                            startActivity(intent);
                            getActivity().finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("errorOnServer",error.getMessage());
                    }
                }){


        };
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }
}
