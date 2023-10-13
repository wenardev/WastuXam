package com.example.wastuxam;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import static com.example.wastuxam.DbQuery.ANSWERED;
import static com.example.wastuxam.DbQuery.NOT_VISITED;
import static com.example.wastuxam.DbQuery.REVIEW;
import static com.example.wastuxam.DbQuery.UNANSWERED;

public class LatihanGridAdapter extends BaseAdapter {

    private int numOfLati;
    private Context context;

    public LatihanGridAdapter(Context context, int numOfLati) {
        this.numOfLati = numOfLati;
        this.context = context;
    }

    @Override
    public int getCount() {
        return numOfLati;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View myview;

        if (view == null)
        {
            myview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lati_grid_item,viewGroup,false);
        }
        else
        {
            myview = view;
        }

        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context instanceof LatihanActivity)
                    ((LatihanActivity)context).goToLatihan(i);

            }
        });

        TextView latiTV = myview.findViewById(R.id.lati_num);
        latiTV.setText(String.valueOf(i+1));

        Log.d("LOGGGGGGGGGGGG", String.valueOf(DbQuery.g_latihanList.get(i).getStatus()));
        switch (DbQuery.g_latihanList.get(i).getStatus())
        {
            case NOT_VISITED :
                latiTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(), R.color.grey)));
                break;
            case UNANSWERED :
                latiTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(), R.color.red2)));
                break;
            case ANSWERED :
                latiTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(), R.color.green2)));
                break;
            case REVIEW :
                latiTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(), R.color.blue)));
                break;

                default:
                    break;
        }

        return myview;
    }
}
