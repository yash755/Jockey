package com.gappydevelopers.xsarcasm.activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.gappydevelopers.xsarcasm.R;

public class SplashScreen extends AppCompatActivity {

    static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this,
                        Home.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
                handler.removeCallbacks(this);
            }
        }, SPLASH_TIME_OUT);
    }
}
