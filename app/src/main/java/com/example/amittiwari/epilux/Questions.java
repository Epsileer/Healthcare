package com.example.amittiwari.epilux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Questions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Questions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Questions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView name,code,date,question;
    Session session;
    JSONObject jb;
    JSONArray jsonArray;
    ProgressBar pb;
    WebView wb;
    TextView sourceSizeLimit,maxTimeLimit,challengeType,author,successfulSubmissions,totalSubmissions,partialSubmissions,tags;
    Button ques;
    public String body;
    HListView hv;
    List<String> tag;

    private OnFragmentInteractionListener mListener;

    public Questions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Questions.
     */
    // TODO: Rename and change types and number of parameters
    public static Questions newInstance(String param1, String param2) {
        Questions fragment = new Questions();
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
        View v = inflater.inflate(R.layout.fragment_questions, container, false);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar1);
        toolbar.setTitle("Question");

        sourceSizeLimit = v.findViewById(R.id.sourceSizeLimit);
        maxTimeLimit = v.findViewById(R.id.maxTimeLimit);
        challengeType = v.findViewById(R.id.challengeType);
        author = v.findViewById(R.id.author);
        successfulSubmissions = v.findViewById(R.id.successfulSubmissions);
        totalSubmissions = v.findViewById(R.id.totalSubmissions);
        partialSubmissions = v.findViewById(R.id.partialSubmissions);
        tags = v.findViewById(R.id.tags);
        ques = v.findViewById(R.id.ques);
        ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragment(new QuestionWebView(),body);
            }
        });
   tag = new ArrayList<>();


        name = v.findViewById(R.id.questionname);
        code = v.findViewById(R.id.questioncode);
        pb = getActivity().findViewById(R.id.loadbar);
        pb.setVisibility(View.VISIBLE);
        session = new Session(getContext());
        getActivity().findViewById(R.id.search1).setVisibility(View.GONE);
        Bundle b = getArguments();
        hv = v.findViewById(R.id.hlist1);
        Call(b.getString("code"),b.getString("ccode"));
        hv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new Tagquestions();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("tag",tag.get(position));
                fragment.setArguments(b);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });


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
    public void Call(final String qcode,String ccode)
    {
        User user = session.getUser();

        final String auth = user.getAcct();

        String url = "https://api.codechef.com/contests/"+ccode+"/problems/"+qcode;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("epsileer","this is inner error "+session.getUser().getReft());
                        try {
                            if(response.get("status").equals("OK")){
                                //jsonArray = (JSONArray) response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getJSONArray("contestList");

                             jb =  response.getJSONObject("result").getJSONObject("data").getJSONObject("content");
                              name.setText(jb.getString("problemName"));
                              code.setText(jb.getString("problemCode"));

                              sourceSizeLimit.setText(jb.getString("sourceSizeLimit"));
                              maxTimeLimit.setText(jb.getString("maxTimeLimit"));
                              challengeType.setText(jb.getString("challengeType"));
                              successfulSubmissions.setText(jb.getString("successfulSubmissions"));
                              totalSubmissions.setText(jb.getString("totalSubmissions"));
                              partialSubmissions.setText(jb.getString("partialSubmissions"));
                              tags.setText(jb.getString("tags"));
                              JSONArray t = jb.getJSONArray("tags");
                              for(int i=0;i<t.length();i++)
                              {
                                  tag.add(t.getString(i));
                              }

                              ArrayAdapter a = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,tag);
                              hv.setAdapter(a);

                              body = jb.getString("body");

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("epsileer","this is inner error 2.1");

                        }
                        pb.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("epsileer","this is inner error 2.2");
                        pb.setVisibility(View.GONE);
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

    public void swapFragment(Fragment fragment,String body) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        Bundle b = new Bundle();
        b.putString("body",body);
        fragment.setArguments(b);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
