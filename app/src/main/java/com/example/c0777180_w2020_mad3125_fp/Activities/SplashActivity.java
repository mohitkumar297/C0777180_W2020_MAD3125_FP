package com.example.c0777180_w2020_mad3125_fp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.c0777180_w2020_mad3125_fp.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity((LoginActivity.class))
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#000000"))
                .withAfterLogoText("\n\n\nMohit Kumar\n\n  C0777180")
                .withLogo(R.drawable.cost);

        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextSize(20);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

    }
}