package com.example.wastuxam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MatakuliahAdapter extends BaseAdapter {

    private List<MatakuliahModel> mat_list;

    public MatakuliahAdapter(List<MatakuliahModel> mat_list) {
        this.mat_list = mat_list;
    }

    @Override
    public int getCount() {
        return mat_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View myView;

        if (view == null)
        {
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mat_item_layout,viewGroup,false);
        }
        else
        {
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DbQuery.g_selected_mat_index = i;

                Intent intent = new Intent(view.getContext(),LevelActivity.class);

                view.getContext().startActivity(intent);

            }
        });

        TextView matName = myView.findViewById(R.id.matName);
        TextView noOfLevels = myView.findViewById(R.id.no_of_levels);

        matName.setText(mat_list.get(i).getName());
        noOfLevels.setText(String.valueOf( mat_list.get(i).getNoOfLevels()) + " Levels");

        return myView;
    }
}
