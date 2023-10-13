package com.example.wastuxam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class LevelActivity extends AppCompatActivity {

    private RecyclerView levelView;
    private Toolbar toolbar;
    private LevelAdapter adapter;
    private Dialog progressDialog;
    private TextView dialogText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle(DbQuery.g_matList.get(DbQuery.g_selected_mat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        levelView = findViewById(R.id.level_recycler_view);

        progressDialog = new Dialog(LevelActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Tunggu...");

        progressDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        levelView.setLayoutManager(layoutManager);

        //loadLevelData();

        DbQuery.loadLevelData(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                adapter = new LevelAdapter(DbQuery.g_levelList);
                levelView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(LevelActivity.this, "Gagal Login! Harap Coba Lagi!",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            LevelActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
