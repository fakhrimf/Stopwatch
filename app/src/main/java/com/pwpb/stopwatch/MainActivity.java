package com.pwpb.stopwatch;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    Button btnMulai, btnBerhenti, btnReset, btnLap;
    long msTime, startTime, timeBuff, updateTime = 0L;
    Handler h;
    int seconds, minutes, ms;
    ListView lv;
    String[] listElements = new String[]{};
    List<String> lEArrayList;
    ArrayAdapter<String> adapter;
    int i = 0;

    public void initUI() {
        tvTime = findViewById(R.id.tvTime);
        btnMulai = findViewById(R.id.btnMulai);
        btnBerhenti = findViewById(R.id.btnBerhenti);
        btnReset = findViewById(R.id.btnReset);
        btnLap = findViewById(R.id.btnLap);
        h = new Handler();
        lv = findViewById(R.id.lv);
        lEArrayList = new ArrayList<>(Arrays.asList(listElements));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lEArrayList);
        lv.setAdapter(adapter);
    }

    public void initBtn() {
        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                h.postDelayed(r, 0);

                btnReset.setEnabled(false);
//                btnReset.setBackgroundColor(Color.rgb(10,10,10));
            }
        });
        btnBerhenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBuff += msTime;
                h.removeCallbacks(r);
                btnReset.setEnabled(true);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msTime = 0L;
                timeBuff = 0L;
                startTime = 0L;
                updateTime = 0L;
                seconds = 0;
                minutes = 0;
                ms = 0;
                i = 0;

                tvTime.setText("00:00:000");
                lEArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                lEArrayList.add(i+". "+tvTime.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable r = new Runnable() {
        @Override
        public void run() {
            msTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + msTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            ms = (int) (updateTime % 1000);

            tvTime.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", ms));
            h.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initBtn();
    }
}
