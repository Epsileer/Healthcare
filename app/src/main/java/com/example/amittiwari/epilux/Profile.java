package com.example.amittiwari.epilux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.amittiwari.epilux.services.MySingleton;
import com.example.amittiwari.epilux.services.Session;
import com.example.amittiwari.epilux.services.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView user_name, username, user_add, user_language, college_name, global_rank, country_rank, codechef_rating;
    Button button;
    JSONObject j;
    ProgressBar pb;
    Session session;
    Bundle b;
    private boolean swaped = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }
    public  Profile(boolean s){
        this.swaped = s;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        Call();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        pb = getActivity().findViewById(R.id.loadbar);
        pb.setVisibility(View.VISIBLE);

        user_name = (TextView) view.findViewById(R.id.user_name);
        username = (TextView) view.findViewById(R.id.username);
        user_add = (TextView) view.findViewById(R.id.user_add);
        user_language = (TextView) view.findViewById(R.id.user_language);
        global_rank = (TextView) view.findViewById(R.id.global_rank);
        country_rank = (TextView) view.findViewById(R.id.country_rank);
        college_name = (TextView) view.findViewById(R.id.college_name);
        button = (Button) view.findViewById(R.id.user_logout);
        getActivity().findViewById(R.id.search1).setVisibility(View.GONE);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar1);
        toolbar.setTitle("Profile");

//        try {
//            user_name.setText(j.getString("fullname").toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        session = new Session(getContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLoggedin(false);
                clearApplicationData();
                Intent intent = new Intent(getContext(),Loader.class);
                startActivity(intent);
            }
        });

        if(swaped) {
            b = getArguments();
            button.setVisibility(View.GONE);
            toolbar.setTitle(b.getString("name"));
        }

        return view;
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

    public void Call()
    {
        final Session session = new Session(getContext());
        final User user = session.getUser();

        final String auth = user.getAcct();

        String url = "https://api.codechef.com/users/";
        if(swaped){
            b = getArguments();
            String usercode = b.getString("usercode");
            url += usercode;
        }
        else
            url += "me";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("status").equals("OK")){
                                String acc = user.getAcct();
                                JSONObject j = (JSONObject) response.getJSONObject("result").getJSONObject("data").getJSONObject("content");
                                username.setText(j.getString("username").toString());
                                user_name.setText(j.getString("fullname").toString());
                                college_name.setText(j.getString("organization").toString());
                                global_rank.setText(j.getJSONObject("rankings").getJSONObject("allContestRanking").getString("global"));
                                country_rank.setText(j.getJSONObject("rankings").getJSONObject("allContestRanking").getString("country"));
                                user_add.setText(j.getJSONObject("city").getString("name")+", "+j.getJSONObject("state").getString("name"));
                                user_language.setText(j.getString("language").toString());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pb.setVisibility(getView().GONE);
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
    public void clearApplicationData() {
        try {
            // clearing app data
            String packageName = getContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        File cacheDirectory = getContext().getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }
    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}
