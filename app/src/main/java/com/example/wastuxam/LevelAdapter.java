package com.example.wastuxam;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private List<LevelModel> levelList;

    public LevelAdapter(List<LevelModel> levelList) {
        this.levelList = levelList;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        int progress = levelList.get(position).getTopScore();
        holder.setDate(position,progress);
    }

    @Override
    public int getItemCount() {
        return levelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView levelNo;
        private TextView topScore;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            levelNo = itemView.findViewById(R.id.levelNo);
            topScore = itemView.findViewById(R.id.scoretext);
            progressBar = itemView.findViewById(R.id.levelProgressbar);

        }

        private void setDate(final int pos, int progress)
        {
            levelNo.setText("Level No : " + String.valueOf(pos + 1));
            topScore.setText(String.valueOf(progress) + " %");

            progressBar.setProgress(progress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DbQuery.g_selected_level_index = pos;

                    Intent intent = new Intent(itemView.getContext(),StartLevelActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
