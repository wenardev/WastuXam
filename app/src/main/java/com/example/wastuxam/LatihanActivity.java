package com.example.wastuxam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static com.example.wastuxam.DbQuery.ANSWERED;
import static com.example.wastuxam.DbQuery.NOT_VISITED;
import static com.example.wastuxam.DbQuery.REVIEW;
import static com.example.wastuxam.DbQuery.UNANSWERED;
import static com.example.wastuxam.DbQuery.g_latihanList;
import static com.example.wastuxam.DbQuery.g_levelList;
import static com.example.wastuxam.DbQuery.g_matList;
import static com.example.wastuxam.DbQuery.g_selected_level_index;
import static com.example.wastuxam.DbQuery.g_selected_mat_index;

public class LatihanActivity extends AppCompatActivity {

    private RecyclerView latihanView;
    private TextView tvLatiID, timerTV, matNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevLatiB, nextLatiB;
    private ImageView latiListB;
    private int latiID;
    LatihanAdapter latihanAdapter;
    private DrawerLayout drawer;
    private ImageButton drawerCloseB;
    private GridView latiListGV;
    private ImageView markImage;
    private LatihanGridAdapter gridAdapter;
    private CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latihan_list_layout);

        init();

        latihanAdapter = new LatihanAdapter(g_latihanList);
        latihanView.setAdapter(latihanAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        latihanView.setLayoutManager(layoutManager);

        gridAdapter = new LatihanGridAdapter(this, g_latihanList.size());
        latiListGV.setAdapter(gridAdapter);

        setSnapHelper();

        setClickListeners();

        startTimer();

    }

    private void init()
    {
        latihanView = findViewById(R.id.latihan_view);
        tvLatiID = findViewById(R.id.tv_latiID);
        timerTV = findViewById(R.id.tv_timer);
        matNameTV = findViewById(R.id.la_matName);
        submitB = findViewById(R.id.submitB);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clear_selB);
        prevLatiB = findViewById(R.id.prev_latiB);
        nextLatiB = findViewById(R.id.next_latiB);
        latiListB = findViewById(R.id.lati_list_gridB);
        drawer = findViewById(R.id.drawer_layout);
        markImage = findViewById(R.id.mark_image);
        latiListGV =findViewById(R.id.lati_list_gv);

        drawerCloseB = findViewById(R.id.drawerCloseB);

        latiID = 0;

        tvLatiID.setText("1/" + String.valueOf(g_latihanList.size()));
        matNameTV.setText(g_matList.get(g_selected_mat_index).getName());

        g_latihanList.get(0).setStatus(UNANSWERED);
    }

    private void setSnapHelper()
    {
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(latihanView);

        latihanView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                latiID = recyclerView.getLayoutManager().getPosition(view);

                if (g_latihanList.get(latiID).getStatus() == NOT_VISITED)
                    g_latihanList.get(latiID).setStatus(UNANSWERED);

                if (g_latihanList.get(latiID).getStatus() == REVIEW)
                {
                    markImage.setVisibility(View.VISIBLE);
                }
                else
                {
                    markImage.setVisibility(View.GONE);
                }

                tvLatiID.setText(String.valueOf(latiID + 1) + "/" + String.valueOf(g_latihanList.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void setClickListeners()
    {

        prevLatiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (latiID > 0)
                {
                    latihanView.smoothScrollToPosition(latiID - 1);
                }

            }
        });


        nextLatiB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (latiID < g_latihanList.size() - 1)
                {
                    latihanView.smoothScrollToPosition(latiID + 1);
                }

            }
        });

        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                g_latihanList.get(latiID).setSelectedAns(-1);
                g_latihanList.get(latiID).setStatus(UNANSWERED);
                markImage.setVisibility(View.GONE);
                latihanAdapter.notifyDataSetChanged();

            }
        });

        latiListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! drawer.isDrawerOpen(GravityCompat.END))
                {
                    gridAdapter.notifyDataSetChanged();
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        drawerCloseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });

        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (markImage.getVisibility() != View.VISIBLE)
                {
                    markImage.setVisibility(View.VISIBLE);

                    g_latihanList.get(latiID).setStatus(REVIEW);
                }
                else
                {
                    markImage.setVisibility(View.GONE);

                    if (g_latihanList.get(latiID).getSelectedAns() != -1)
                    {
                        g_latihanList.get(latiID).setStatus(ANSWERED);
                    }
                    else
                    {
                        g_latihanList.get(latiID).setStatus(UNANSWERED);
                    }

                }

            }
        });

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitLevel();
            }
        });

    }

    private void submitLevel()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LatihanActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);

        Button cancelB = view.findViewById(R.id.cacelB);
        Button confirmB = view.findViewById(R.id.confirmB);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timer.cancel();
                alertDialog.dismiss();

                Intent intent = new Intent(LatihanActivity.this, SkorActivity.class);
                startActivity(intent);
                LatihanActivity.this.finish();
            }
        });

        alertDialog.show();

    }

    public void goToLatihan(int position)
    {

        latihanView.smoothScrollToPosition(position);

        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);

    }

    private void startTimer()
    {
        long totalTime = g_levelList.get(g_selected_level_index).getTime()*60*1000;

        timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remainingTime) {

                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))

                );

                timerTV.setText(time);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(LatihanActivity.this, SkorActivity.class);
                startActivity(intent);
                LatihanActivity.this.finish();
            }
        };

        timer.start();

    }

}
