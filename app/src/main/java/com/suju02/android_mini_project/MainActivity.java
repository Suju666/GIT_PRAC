package com.suju02.android_mini_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Intent intnt;
    int pb_cnt=0;
    Timer tmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View v1 = findViewById(R.id.first_circle);
        TextView tv = findViewById(R.id.app_title);
        ProgressBar pb = findViewById(R.id.progressBar);

        Animation anmtion1 = AnimationUtils.loadAnimation(this,R.anim.scale_my_circle_main_screen);
        v1.setAnimation(anmtion1);
        tv.setTextColor(Color.WHITE);

        pb.setProgress(pb_cnt); pb.setMax(100);
        tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run()
            {
                pb_cnt += 5; pb.setProgress(pb_cnt);
                if(pb.getProgress() >= 100)
                {
                    tmr.cancel();
                    intnt = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intnt);
                    finish();
                }
            }
        },1000,50);
    }
}