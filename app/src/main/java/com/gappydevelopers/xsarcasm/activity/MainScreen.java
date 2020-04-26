package com.gappydevelopers.xsarcasm.activity;

import android.os.Bundle;

import com.gappydevelopers.xsarcasm.fragment.GalleryFragment;
import com.gappydevelopers.xsarcasm.fragment.FavouriteFragment;
import com.gappydevelopers.xsarcasm.fragment.AboutFragment;
import com.gappydevelopers.xsarcasm.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.LinearLayout;

import com.gappydevelopers.xsarcasm.R;

public class MainScreen extends AppCompatActivity implements View.OnClickListener{


    LinearLayout bottomBarGallery = null, bottomBarFavourite = null, bottomBarAbout = null;
    CoordinatorLayout mainScreenLayout;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        mainScreenLayout = findViewById(R.id.main_screen_layout);

        bottomBarGallery = findViewById(R.id.gallery);
        bottomBarFavourite = findViewById(R.id.favourite);
        bottomBarAbout = findViewById(R.id.about);

        bottomBarGallery.setOnClickListener(this);
        bottomBarFavourite.setOnClickListener(this);
        bottomBarAbout.setOnClickListener(this);



            mAdView = findViewById(R.id.banner);
            MobileAds.initialize(getApplicationContext(),
                    getString(R.string.ad_id));
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

    }


    private void initUI() {
        Fragment newFragment = new GalleryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment_container, newFragment);
        transaction.commit();

        bottomBarGallery.setSelected(true);
        bottomBarFavourite.setSelected(false);
        bottomBarAbout.setSelected(false);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.gallery) {
            bottomBarGallery.setSelected(true);
            bottomBarFavourite.setSelected(false);
            bottomBarAbout.setSelected(false);

            Fragment newFragment = new GalleryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment_container, newFragment);
            transaction.commit();

        } else if (view.getId() == R.id.favourite) {
            bottomBarGallery.setSelected(false);
            bottomBarFavourite.setSelected(true);
            bottomBarAbout.setSelected(false);

            Fragment newFragment = new FavouriteFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment_container, newFragment);
            transaction.commit();

        } else if (view.getId() == R.id.about) {
            bottomBarGallery.setSelected(false);
            bottomBarFavourite.setSelected(false);
            bottomBarAbout.setSelected(true);

            Fragment newFragment = new AboutFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment_container, newFragment);
            transaction.commit();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if (!new Utils().check_connection(MainScreen.this)) {
            Utils.showSnackBar(mainScreenLayout, getString(R.string.internet_error));
        } else {
            initUI();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
