package com.cyberllama.mujtabaashfaq.materialtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/*
    Sets last highlighted item on app creation.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    //Get saved values.
    private SharedPreferences savedVals = getContext().getSharedPreferences("SavedVals", 0);
    //Set last list view selected from saved values.
    private int lastListView = savedVals.getInt("viewPos", 0);


    public CustomAdapter(Context context, int resource, String[] data) {
        super(context, resource, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        //Highlight correct view.
        if(position == lastListView) {
            v.setBackgroundColor(Color.parseColor("#ffbdbdbd"));
        }

        return v;
    }
}
