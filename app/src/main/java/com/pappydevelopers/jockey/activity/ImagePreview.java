package com.pappydevelopers.jockey.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pappydevelopers.jockey.R;
import com.pappydevelopers.jockey.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagePreview extends AppCompatActivity {

    ImageAdapter imageAdapter;
    ViewPager viewPager;
    private List<String> imageItems;
    String position = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Jockey");

        if(getIntent().hasExtra("imagelist")) {
            imageItems =  getIntent().getStringArrayListExtra("imagelist");
            position = getIntent().getStringExtra("position");
        }

        imageAdapter = new ImageAdapter(this, imageItems);
        viewPager = (ViewPager) findViewById(R.id.image);
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(Integer.parseInt(position));

        System.out.println(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Find more than 10K active Whatsapp Groups.Very effective and amazing app.\n\n";
                shareBody = shareBody + "https://play.google.com/store/apps/details?id=com.pappydevelopers.groupsforwhatsapp \n\n";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Unlimited Whatsapp Groups List");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=com.pappydevelopers.groupsforwhatsapp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.pappydevelopers.groupsforwhatsapp")));
                }
                return true;*/
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        imageItems = new ArrayList<String>();
    }
}
