package com.pappydevelopers.extremesarcasm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pappydevelopers.extremesarcasm.R;
import com.pappydevelopers.extremesarcasm.utils.Utils;

public class Privacy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        Intent i = new Intent(Privacy.this, HomeScreen.class);
        startActivity(i);
    }
}
