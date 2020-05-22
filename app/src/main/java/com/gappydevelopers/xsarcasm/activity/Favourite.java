package com.gappydevelopers.xsarcasm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.adapter.GridAdapter;
import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class Favourite extends AppCompatActivity {

    private String title, base_url;
    private int page;
    private JSONArray image_list;
    private List<String> imageItems;
    DataBaseHelper dataBaseHelper;
    ImageView back;
    GridAdapter gridAdapter;
    TextView noFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        GridView gridView = (GridView) findViewById(R.id.gridview);

        back = findViewById(R.id.fav_back_press);
        noFav = findViewById(R.id.no_fav);


        noFav.setVisibility(View.GONE);

        imageItems = new ArrayList<String>();
        dataBaseHelper = new DataBaseHelper(this);
        Cursor cr = dataBaseHelper.getImage();
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                imageItems.add(cr.getString(cr.getColumnIndex("url")));
                cr.moveToNext();
            }
            cr.close();
            gridAdapter = new GridAdapter(getApplicationContext(),imageItems);
            gridView.setAdapter(gridAdapter);
        } else {
            noFav.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //   finish();
            }

        });


    }


    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
