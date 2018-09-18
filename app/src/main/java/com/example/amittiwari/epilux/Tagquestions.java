package com.example.amittiwari.epilux;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.amittiwari.epilux.model.Apiurls;
import com.example.amittiwari.epilux.model.MyNotificationManager;
import com.example.amittiwari.epilux.services.MySingleton;
import com.example.amittiwari.epilux.services.Session;
import com.example.amittiwari.epilux.services.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tagquestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tagquestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tagquestions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<String> programname;
    ProgressBar pb;
    Session session;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView lv;


    private OnFragmentInteractionListener mListener;

    public Tagquestions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tagquestions.
     */
    // TODO: Rename and change types and number of parameters
    public static Tagquestions newInstance(String param1, String param2) {
        Tagquestions fragment = new Tagquestions();
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
        View v =  inflater.inflate(R.layout.fragment_tagquestions, container, false);
        lv = v.findViewById(R.id.taglistp);
        Toolbar toolbar =  getActivity().findViewById(R.id.toolbar1);
        toolbar.setTitle("Tags");
        pb = getActivity().findViewById(R.id.loadbar);
        programname = new ArrayList<>();
        session = new Session(getContext());
        Bundle b = getArguments();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                swapFragment(new Questions(),programname.get(i));
            }
        });
        Log.d("tags123",b.getString("tag"));
        Call(b.getString("tag"));


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


    public void Call(String type)
    {  pb.setVisibility(View.VISIBLE);
        User user = session.getUser();
        programname.clear();

        final String auth = user.getAcct();

        String url = Apiurls.tagsearch+type+"&limit=50";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("epsileer","this is inner error "+session.getUser().getReft());
                        try {
                            if(response.get("status").equals("OK")){
                                //jsonArray = (JSONArray) response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getJSONArray("contestList");
                               jsonObject = response.getJSONObject("result").getJSONObject("data").getJSONObject("content");

                                for(Iterator<String> keys = jsonObject.keys(); keys.hasNext();) {
                                    programname.add(jsonObject.getJSONObject(keys.next()).getString("code"));
                                   // Log.d("tagsvalue",jsonObject.getJSONObject(keys.next()).getString("code"));
                                }

                                ArrayAdapter ab = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,programname);
                                lv.setAdapter(ab);
                                ab.notifyDataSetChanged();



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
    public void swapFragment(Fragment fragment,String name) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        Bundle b = new Bundle();
        b.putString("code",name);
        b.putString("ccode","PRACTICE");
        fragment.setArguments(b);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
