package com.gappydevelopers.xsarcasm.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.adapter.TumbnailAdapter;
import com.gappydevelopers.xsarcasm.helper.GetJSONArray;
import com.gappydevelopers.xsarcasm.helper.MasterData;
import com.gappydevelopers.xsarcasm.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity implements View.OnClickListener {

    AlertDialog dialog;
    DrawerLayout drawer;
    LinearLayout newRelease, allRelease, linearSpinner;
    ScrollView scrollLayout;

    ActionBarDrawerToggle toggle;
    TumbnailAdapter tumbnailAdapter, tumbnailAdapter1;
    private List<Object> mRecyclerViewItems, mRecyclerViewItems1;
    RecyclerView mRecyclerView, mRecyclerView1, final_list;
    private List<String> imageItems;
    AdView mAdView;
    TextView newReleaseText, allReleaseText;
    ImageView fav;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });


        toggle.setHomeAsUpIndicator(R.drawable.multimedia);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);




        mRecyclerViewItems = new ArrayList<>();
        mRecyclerViewItems1 = new ArrayList<>();
        imageItems = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.new_list);
        mRecyclerView1 = (RecyclerView) findViewById(R.id.old_list);


        linearSpinner = findViewById(R.id.linear_spinner);
        scrollLayout = findViewById(R.id.scroll_layout);


        newReleaseText = findViewById(R.id.new_release_text);
        allReleaseText = findViewById(R.id.all_release_text);

        newRelease = findViewById(R.id.new_release);
        allRelease = findViewById(R.id.all_release);


        fav = findViewById(R.id.go_to_fav);

        newReleaseText.setText(Html.fromHtml("<u>View All</u>"));
        allReleaseText.setText(Html.fromHtml("<u>View All</u>"));


        newRelease.setOnClickListener(this);
        allRelease.setOnClickListener(this);
        fav.setOnClickListener(this);


        linearSpinner.setVisibility(View.VISIBLE);
        scrollLayout.setVisibility(View.INVISIBLE);




        getData();






}


    public void loadAd() {
        mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void getData() {
        //dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
        //dialog.show();


        new Utils().getAllList(this, new GetJSONArray() {
            @Override
            public void done(JSONArray jsonArray) {
                if (jsonArray != null) {
                    System.out.println("Response" + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String image = "https://gappydevelopers.com/thumbnail/" + jsonObject1.getString("pic_url");
                            image = image.replace("\\", "");

                            if (i < 5) {
                                mRecyclerViewItems.add(new MasterData(image));
                            }

                            if (i > 5 && i <= 10) {
                                mRecyclerViewItems1.add(new MasterData(image));
                            }

                            imageItems.add(image);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    setUI();
                }


            }
        });

    }


    public void setUI() {
        //dialog.dismiss();

        linearSpinner.setVisibility(View.INVISIBLE);
        scrollLayout.setVisibility(View.VISIBLE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView1.setLayoutManager(layoutManager1);


        tumbnailAdapter = new TumbnailAdapter(this, mRecyclerViewItems);
        mRecyclerView.setAdapter(tumbnailAdapter);

        tumbnailAdapter1 = new TumbnailAdapter(this, mRecyclerViewItems1);
        mRecyclerView1.setAdapter(tumbnailAdapter1);

        loadAd();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_release ) {
            Collections.shuffle(imageItems);
            Intent intent = new Intent(this, ImagePreview.class);
            intent.putExtra("position","0");
            intent.putExtra("imagelist", (ArrayList<String>) imageItems);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        } else if (v.getId() == R.id.all_release) {
            Collections.shuffle(imageItems);
            Intent intent = new Intent(this, ImagePreview.class);
            intent.putExtra("position","0");
            intent.putExtra("imagelist", (ArrayList<String>) imageItems);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        } else if (v.getId() == R.id.go_to_fav) {
            Intent intent = new Intent(this, Favourite.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
    }


    public boolean onNavigationDrawerItemClick(View view) {
        switch (view.getId()) {

            case R.id.about_us: {
                //clickedNavItem = R.id.about_us;
                //break;
                Intent intent = new Intent(this, About.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;

              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), About.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                }, 110);
                break;*/

            }

            case R.id.share_us: {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Checkout this amazing Sarcasm jokes app.\n\n";
                shareBody = shareBody + "https://play.google.com/store/apps/details?id="+ this.getPackageName() + "\n\n";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sarcasm Fun");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            }

            case R.id.rate_us: {
                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName())));
                }
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
