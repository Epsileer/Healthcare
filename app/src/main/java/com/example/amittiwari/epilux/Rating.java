package com.example.amittiwari.epilux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Rating#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rating extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Session session;
    ListView lv;
    List<String> rating;
    List<String> username;
    List<String> stringList;
    AutoCompleteTextView av;
    Spinner sap;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private AutoSuggestAdapter autoSuggestAdapter;
    private OnFragmentInteractionListener mListener;
    ArrayAdapter am;
    ImageButton b;

    public Rating() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Rating.
     */
    // TODO: Rename and change types and number of parameters
    public static Rating newInstance(String param1, String param2) {
        Rating fragment = new Rating();
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
       View view = inflater.inflate(R.layout.fragment_rating, container, false);
       session = new Session(getContext());
       lv = view.findViewById(R.id.ratinglist1);
       sap = view.findViewById(R.id.spinner);
       av = view.findViewById(R.id.searchmeet);
        autoSuggestAdapter = new AutoSuggestAdapter(getContext(),
                android.R.layout.simple_dropdown_item_1line);
        av.setThreshold(2);
        av.setAdapter(autoSuggestAdapter);
        b = view.findViewById(R.id.show);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar1);
        toolbar.setTitle("Institutions");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option2();
            }
        });
        getActivity().findViewById(R.id.search1).setVisibility(View.GONE);
        av.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  option1(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        av.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE)
                {
                    option2();
                    return true;
                }

                return false;



            }
        });

lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String usercode = username.get(position).toString();
        String name = stringList.get(position).toString();
        swapFragment(new Profile(true),usercode,name);
    }
});


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

    public class AutoSuggestAdapter extends ArrayAdapter<String> implements Filterable {
        private List<String> mlistData;

        public AutoSuggestAdapter(@NonNull Context context, int resource) {
            super(context, resource);
            mlistData = new ArrayList<>();
        }

        public void setData(List<String> list) {
            mlistData.clear();
            mlistData.addAll(list);
        }

        @Override
        public int getCount() {
            return mlistData.size();
        }


        @Override
        public String getItem(int position) {
            return mlistData.get(position);
        }

        /**
         * Used to Return the full object directly from adapter.
         *
         * @param position
         * @return
         */
        public String getObject(int position) {
            return mlistData.get(position);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            Filter dataFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        filterResults.values = mlistData;
                        filterResults.count = mlistData.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && (results.count > 0)) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return dataFilter;
        }
    }



    public void option1(CharSequence s)
    {
        User user = session.getUser();

        final String auth = user.getAcct();

        String url = "https://api.codechef.com/"+sap.getSelectedItem().toString()+"?search="+s.toString();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        List<String> stringList = new ArrayList<>();

                        Log.d("Accept ","Accept char ");
                        try {
                            if(response.get("status").equals("OK")){
                                JSONArray jsonArray = response.getJSONObject("result").getJSONObject("data").getJSONArray("content");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    stringList.add(jsonArray.getJSONObject(i).getString("institutionName"));
                                }




                            }
                            else if(response.get("status").equals("error")){

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Type ","this is inner error in type");

                        }
                        autoSuggestAdapter.setData(stringList);
                        autoSuggestAdapter.notifyDataSetChanged();
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


    public void option2( )
    {
        User user = session.getUser();

        final String auth = user.getAcct();

        String url = "https://api.codechef.com/ratings/all?"+sap.getSelectedItem().toString()+"="+av.getText();
         url = url.replace(" ","%20");
         url = url.replace(",","");
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        stringList = new ArrayList<>();
                        username = new ArrayList<>();

                        Log.d("Accept college list","Filter list");
                        try {
                            if(response.get("status").equals("OK")){
                                JSONArray jsonArray = response.getJSONObject("result").getJSONObject("data").getJSONArray("content");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    stringList.add(jsonArray.getJSONObject(i).getString("fullname"));
                                    username.add(jsonArray.getJSONObject(i).getString("username"));
                                }




                            }
                            else if(response.get("status").equals("error")){

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Accept collge list","this is inner error 2.1");

                        }
                         am =  new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,stringList);
                         lv.setAdapter(am);
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

    public void swapFragment(Fragment fragment,String usercode,String name) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        Bundle b = new Bundle();
        b.putString("usercode",usercode);
        b.putString("name",name);
        fragment.setArguments(b);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


}


