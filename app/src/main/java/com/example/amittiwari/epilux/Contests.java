package com.example.amittiwari.epilux;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.amittiwari.epilux.model.Contestmodel;
import com.example.amittiwari.epilux.services.MySingleton;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contests.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contests#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contests extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bundle b;
    ListView lv;
    ArrayAdapter ab;
    JSONArray k;
    List<String> cont;
    String lo;
    ArrayList<Contestmodel> contestmodels;

    private OnFragmentInteractionListener mListener;

    public Contests() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contests.
     */
    // TODO: Rename and change types and number of parameters
    public static Contests newInstance(String param1, String param2) {
        Contests fragment = new Contests();
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
        View v = inflater.inflate(R.layout.fragment_contests, container, false);


        cont = new ArrayList<>();
        contestmodels = new ArrayList<Contestmodel>();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar1);
        toolbar.setTitle("Contest");
        getActivity().findViewById(R.id.notify1).setVisibility(View.GONE);
        MaterialSearchBar materialSearchBar = getActivity().findViewById(R.id.search1);
        materialSearchBar.setVisibility(View.VISIBLE);


       lv = v.findViewById(R.id.cnlist);
      b = getActivity().getIntent().getExtras();
        try {
             k = new JSONArray(b.getString("Value"));
        } catch (JSONException e) {
             k = MySingleton.getInstance(getContext()).getJsonArray();
            e.printStackTrace();
        }
        k = MySingleton.getInstance(getContext()).getJsonArray();

        for(int i=0;i<k.length();i++)
   {
       try {
           cont.add(k.getJSONObject(i).getString("name"));
           contestmodels.add(new Contestmodel(k.getJSONObject(i).getString("name"),k.getJSONObject(i).getString("code"),k.getJSONObject(i).getString("startDate"),k.getJSONObject(i).getString("endDate")));
       } catch (JSONException e) {
           e.printStackTrace();
       }
   }

  final Contestlistadapter a = new Contestlistadapter(getActivity(),contestmodels);
        ab = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,cont);
        lv.setAdapter(a);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                             a.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                swapFragment(new ContestDetail(),contestmodels.get(position).getCode());
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
    public void swapFragment(Fragment fragment,String name) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        Bundle b = new Bundle();
        b.putString("cname",name);
        fragment.setArguments(b);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
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
}
