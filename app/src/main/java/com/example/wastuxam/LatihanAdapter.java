package com.example.wastuxam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.wastuxam.DbQuery.ANSWERED;
import static com.example.wastuxam.DbQuery.REVIEW;
import static com.example.wastuxam.DbQuery.UNANSWERED;
import static com.example.wastuxam.DbQuery.g_latihanList;

public class LatihanAdapter extends RecyclerView.Adapter<LatihanAdapter.ViewHolder> {

    private List<LatihanModel> latihanList;

    public LatihanAdapter(List<LatihanModel> latihanList) {
        this.latihanList = latihanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.latihan_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return latihanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView lati;
        private Button optionA, optionB, optionC, optionD, prevSelectedB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lati = itemView.findViewById(R.id.tv_latihan);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);

            prevSelectedB = null;
        }

        private void setData(final int pos)
        {
            lati.setText(latihanList.get(pos).getLatihan());
            optionA.setText(latihanList.get(pos).getOptionA());
            optionB.setText(latihanList.get(pos).getOptionB());
            optionC.setText(latihanList.get(pos).getOptionC());
            optionD.setText(latihanList.get(pos).getOptionD());

            setOption(optionA, 1, pos);
            setOption(optionB, 2, pos);
            setOption(optionC, 3, pos);
            setOption(optionD, 4, pos);


            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectOption(optionA, 1, pos );
                }
            });

            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectOption(optionB, 2, pos);
                }
            });

            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectOption(optionC, 3, pos);
                }
            });

            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectOption(optionD, 4, pos);
                }
            });
        }

        private void selectOption(Button btn, int option_num, int latiID)
        {

            if (prevSelectedB == null)
            {
                btn.setBackgroundResource(R.drawable.selected_btn);
                DbQuery.g_latihanList.get(latiID).setSelectedAns(option_num);

                changeStatus(latiID, ANSWERED);
                prevSelectedB = btn;
            }
            else
            {

                if ( prevSelectedB.getId() == btn.getId())
                {
                    btn.setBackgroundResource(R.drawable.unselected_btn);
                    DbQuery.g_latihanList.get(latiID).setSelectedAns(-1);

                    changeStatus(latiID, UNANSWERED);
                    prevSelectedB= null;
                }
                else
                {

                    prevSelectedB.setBackgroundResource(R.drawable.unselected_btn);
                    btn.setBackgroundResource(R.drawable.selected_btn);

                    DbQuery.g_latihanList.get(latiID).setSelectedAns(option_num);

                    changeStatus(latiID, ANSWERED);
                    prevSelectedB = btn;
                }

            }

        }

        private void changeStatus(int id, int status)
        {
            if (g_latihanList.get(id).getStatus() != REVIEW)
            {
                g_latihanList.get(id).setStatus(status);
            }
        }

        private void setOption(Button btn, int option_num, int latiID)
        {
            if (DbQuery.g_latihanList.get(latiID).getSelectedAns() == option_num)
            {
                btn.setBackgroundResource(R.drawable.selected_btn);
            }
            else
            {
                btn.setBackgroundResource(R.drawable.unselected_btn);
            }
        }
    }

}
