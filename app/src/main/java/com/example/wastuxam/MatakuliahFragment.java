package com.example.wastuxam;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatakuliahFragment extends Fragment {


    public MatakuliahFragment() {
        // Required empty public constructor
    }

    private GridView matView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matakuliah, container, false);

        matView = view.findViewById(R.id.mat_Grid);

        //loadMatakuliah();

        MatakuliahAdapter adapter = new MatakuliahAdapter(DbQuery.g_matList);
        matView.setAdapter(adapter);

        return view;
    }



}
