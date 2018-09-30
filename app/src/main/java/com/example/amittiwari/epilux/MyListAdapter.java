package com.example.amittiwari.epilux;

/**
 * Created by Amit Tiwari on 30-09-2018.
 */

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> problemName;
    private final List<Boolean> visited;

    public MyListAdapter(Activity context, List<String> problemName, List<Boolean> visited) {
        super(context, R.layout.mylist, problemName);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.problemName=problemName;
        this.visited=visited;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);

        titleText.setText(problemName.get(position));
        if(visited.get(position))
            rowView.findViewById(R.id.problemVisited).setVisibility(View.VISIBLE);

        return rowView;

    };
}