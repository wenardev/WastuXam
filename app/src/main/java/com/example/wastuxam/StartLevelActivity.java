package com.example.wastuxam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.wastuxam.DbQuery.g_matList;
import static com.example.wastuxam.DbQuery.loadlatihan;


public class StartLevelActivity extends AppCompatActivity {

    private TextView matName, levelNo, totalQ, bestScore, time;
    private Button startLevelB;
    private ImageView backB;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_level);

        init();

        progressDialog = new Dialog(StartLevelActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Tunggu...");

        progressDialog.show();

        loadlatihan(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                setData();

                progressDialog.dismiss();

            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(StartLevelActivity.this, "Gagal Login! Harap Coba Lagi!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init()
    {
        matName = findViewById(R.id.st_mat_name);
        levelNo = findViewById(R.id.st_level_no);
        totalQ = findViewById(R.id.st_total_ques);
        bestScore = findViewById(R.id.st_best_score);
        time = findViewById(R.id.st_time);
        startLevelB = findViewById(R.id.start_levelB);
        backB = findViewById(R.id.st_backB);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartLevelActivity.this.finish();
            }
        });

        startLevelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartLevelActivity.this, LatihanActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setData()
    {
        matName.setText(g_matList.get(DbQuery.g_selected_mat_index).getName());
        levelNo.setText("Level No: " + String.valueOf(DbQuery.g_selected_level_index + 1));
        totalQ.setText(String.valueOf(DbQuery.g_latihanList.size()));
        bestScore.setText(String.valueOf(DbQuery.g_levelList.get(DbQuery.g_selected_level_index).getTopScore()));
        time.setText(String.valueOf(DbQuery.g_levelList.get(DbQuery.g_selected_level_index).getTime()));
    }

}

