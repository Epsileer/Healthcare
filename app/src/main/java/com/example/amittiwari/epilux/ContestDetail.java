package com.example.amittiwari.epilux;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.amittiwari.epilux.services.MySingleton;
import com.example.amittiwari.epilux.services.Session;
import com.example.amittiwari.epilux.services.User;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.Object.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContestDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContestDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContestDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Session session;
    Context context;
    ListView lv;
    TextView name,code,sdate,endate,annouc;
    ImageView im;
    JSONObject jb;
    JSONArray jsonArray;
    ProgressBar pb;
    Bundle b;
   String contestname;
   List<String> cbname;
    ImageView imageView;
    ImageButton bell;
    private OnFragmentInteractionListener mListener;

    public ContestDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContestDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static ContestDetail newInstance(String param1, String param2) {
        ContestDetail fragment = new ContestDetail();
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
        View v = inflater.inflate(R.layout.fragment_contest_detail, container, false);

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

  session = new Session(getContext());
        context = getContext();
        name = v.findViewById(R.id.contestname);
        code = v.findViewById(R.id.code);
        sdate = v.findViewById(R.id.startdate);
        annouc = v.findViewById(R.id.annouc);
        imageView = v.findViewById(R.id.banner);
        pb = getActivity().findViewById(R.id.loadbar);
        bell = getActivity().findViewById(R.id.notify1);
        getActivity().findViewById(R.id.search1).setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        b = getArguments();
        contestname = b.getString("cname");
        cbname = new ArrayList<>();
        lv = v.findViewById(R.id.problemlist);
        Call(contestname);
        if(b.getBoolean("notify"))
        {
            bell.setVisibility(View.VISIBLE);
            bell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setForContestNotification();
                }
            });

        }
 lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         swapFragment(new Questions(),cbname.get(position),code.getText().toString());
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

    public void Call(String cname)
    {
        User user = session.getUser();

        final String auth = user.getAcct();

        String url = "https://api.codechef.com/contests/"+cname;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("epsileer","this is inner error "+session.getUser().getReft());
                        try {
                            if(response.get("status").equals("OK")){
                                //jsonArray = (JSONArray) response.getJSONObject("result").getJSONObject("data").getJSONObject("content").getJSONArray("contestList");

                               jb = response.getJSONObject("result").getJSONObject("data").getJSONObject("content");
                               jsonArray = jb.getJSONArray("problemsList");
                               name.setText(jb.getString("name"));
                               code.setText(jb.getString("code"));
                               sdate.setText(jb.getString("startDate"));
                               annouc.setText(Html.fromHtml(Html.fromHtml(jb.getString("announcements")).toString()));
                               Log.d("problemlist",jsonArray.toString());
                                Log.d("image",Html.fromHtml(Html.fromHtml(jb.getString("bannerFile")).toString()).toString() );
                               for(int i=0;i<jsonArray.length();i++)
                               {
                                   cbname.add(jsonArray.getJSONObject(i).getString("problemCode"));
                               }
                                ArrayAdapter ab = new ArrayAdapter(context,android.R.layout.simple_list_item_1,cbname);

                               lv.setAdapter(ab);
                               ab.notifyDataSetChanged();

                               imageset(Html.fromHtml(Html.fromHtml(jb.getString("bannerFile")).toString()).toString());



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
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    void imageset(String url)
    {
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        name.setVisibility(View.INVISIBLE);

                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getContext(),"jbjbhjhijom",Toast.LENGTH_LONG).show();
                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
    public void swapFragment(Fragment fragment,String name,String ccode) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        Bundle b = new Bundle();
        b.putString("code",name);
        b.putString("ccode",ccode);
        fragment.setArguments(b);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    public void setForContestNotification(){
        String url = "http://149.129.135.60/server/Registerevent.php?username="+session.getUsername()+"&eventcode="+contestname;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(),"successful registered for contest notificaion "+session.getUsername(),Toast.LENGTH_LONG).show();
                        Log.d("responsecodi",response.toString());
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
