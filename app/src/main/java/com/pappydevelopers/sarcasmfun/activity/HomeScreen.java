package com.pappydevelopers.sarcasmfun.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pappydevelopers.sarcasmfun.R;
import com.pappydevelopers.sarcasmfun.adapter.HomeAdapter;
import com.pappydevelopers.sarcasmfun.utils.Utils;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class HomeScreen extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        FirebaseMessaging.getInstance().subscribeToTopic("jokey");

        initUI();
    }

    private void initUI() {

        ViewPager vpPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        adapterViewPager = new HomeAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Code goes here
             }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.icon),
                        Color.parseColor(colors[0]))
                        .title("Gallery")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.star),
                        Color.parseColor(colors[1]))
                        .title("Favourite")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.manuser),
                        Color.parseColor(colors[2]))
                        .title("About Us")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(vpPager, vpPager.getCurrentItem());
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
        if (!new Utils().check_connection(HomeScreen.this)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.homeLayout), "No internet connection!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }


}
