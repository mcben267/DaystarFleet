package com.cliffdevops.alpha.dufleet.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cliffdevops.alpha.dufleet.R;

public class Splashscreen extends AppCompatActivity {

    private static int splashTimeOut=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ImageView img = findViewById(R.id.logo);
        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_effect);
        img.startAnimation(animZoomIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);



    }
}