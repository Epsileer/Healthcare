package com.example.amittiwari.epilux;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.amittiwari.epilux.model.Contestmodel;
import com.example.amittiwari.epilux.services.MySingleton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Contestlistadapter extends ArrayAdapter {
    Activity context;
    FragmentActivity  mainactivity;
    ArrayList<Contestmodel> contestmodels;
    ArrayList<Contestmodel> cp1;
    TextView t1,t2,t3,t4;
    ArrayList<Contestmodel> cp2;
    public Contestlistadapter(@NonNull FragmentActivity context, ArrayList<Contestmodel> cp) {
        super(context, R.layout.clist1,cp);
        this.context = context;
        this.contestmodels =cp;
        this.cp1 = cp;
        this.cp2 = cp;
        mainactivity = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.clist1, null,true);
        JSONObject j;
        t1 = rowView.findViewById(R.id.cname);
        t2 = rowView.findViewById(R.id.ccode);
        t3 = rowView.findViewById(R.id.cstart);
        t4 = rowView.findViewById(R.id.cend);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// parse to a date
        Date end = null;
        Date start = null;
        try {
            end = formatter.parse(contestmodels.get(position).getEdate());
            start = formatter.parse(contestmodels.get(position).getSdate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
// get epoch millis
        long st = start.getTime();
        long en = end.getTime();
        boolean notify = false;
        long current = MySingleton.getInstance(getContext()).getCurrentTime();
        current = current*1000;
        if(current>en)
        {
            rowView.findViewById(R.id.endcontestpic).setVisibility(View.VISIBLE);
        }
        else if(current<st)
        {
            rowView.findViewById(R.id.futurecontestpic).setVisibility(View.VISIBLE);
            notify = true;
        }
        else
        {
            rowView.findViewById(R.id.presentcontest).setVisibility(View.VISIBLE);
        }

                      t1.setText(contestmodels.get(position).getName());
                      t2.setText(contestmodels.get(position).getCode());
            t4.setText(contestmodels.get(position).getEdate());

            t3.setText(contestmodels.get(position).getSdate());

        final boolean finalNotify = notify;
        rowView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             swapFragment(new ContestDetail(),contestmodels.get(position).getCode(), finalNotify);
         }
     });



        return rowView;


    }
    @Override
    public Filter getFilter()
    {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                // We implement here the filter logic
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    cp2 = cp1;
                    results.values = cp2;
                    results.count = cp1.size();
                }
                else {
                    // We perform filtering operation
                    cp2 = new ArrayList<Contestmodel>();

                    for (Contestmodel p : contestmodels) {
                        if (p.getName().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())||p.getCode().toUpperCase().startsWith(constraint.toString().toUpperCase())||p.getSdate().startsWith(constraint.toString()))
                            cp2.add(p);
                    }

                    results.values = cp2;
                    results.count = cp2.size();
                }
                return results;




            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
// Now we have to inform the adapter about the new list filtered
                if (filterResults.count == 0)
                    notifyDataSetInvalidated();
                else {
                    contestmodels = (ArrayList<Contestmodel>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
    @Override
    public int getCount()
    {

        return cp2.size();
    }
    public void swapFragment(Fragment fragment,String name,Boolean notify) {
        FragmentTransaction transaction = mainactivity.getSupportFragmentManager().beginTransaction();
        Bundle b = new Bundle();
        b.putString("cname",name);
        b.putBoolean("notify",notify);
        fragment.setArguments(b);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
