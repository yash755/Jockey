package com.gappydevelopers.xsarcasm.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gappydevelopers.xsarcasm.R;

public class About extends AppCompatActivity {

    TextView link, contact;
    ImageView fav, share, backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backPress = (ImageView) toolbar.findViewById(R.id.about_back_press);
        contact = findViewById(R.id.contact);

        link = findViewById(R.id.link);

        link.setMovementMethod(LinkMovementMethod.getInstance());

        contact.setText(Html.fromHtml("<u>contact@gappydevelopers.com</u>"));


        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //   finish();
            }
        });


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"contact@gappydevelopers.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Write you name or contact details.");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Your valuable suggestions.");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

    }


    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
