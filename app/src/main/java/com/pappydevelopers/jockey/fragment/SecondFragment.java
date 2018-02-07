package com.pappydevelopers.jockey.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.pappydevelopers.jockey.R;
import com.pappydevelopers.jockey.adapter.GridAdapter;
import com.pappydevelopers.jockey.databasehelper.DataBaseHelper;
import com.pappydevelopers.jockey.helper.GetJSONObject;
import com.pappydevelopers.jockey.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yash on 26/1/18.
 */
public class SecondFragment extends Fragment {
    // Store instance variables
    private String title, base_url;
    private int page;
    private JSONArray image_list;
    private List<String> imageItems;
    DataBaseHelper dataBaseHelper;

    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragmentFirst = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        final GridView gridView = (GridView) view.findViewById(R.id.gridview);
        dataBaseHelper = new DataBaseHelper(getContext());
        imageItems = new ArrayList<String>();
        Cursor cr = dataBaseHelper.getImage();
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                imageItems.add(cr.getString(cr.getColumnIndex("url")));
                cr.moveToNext();
            }
            cr.close();
            gridView.setAdapter(new GridAdapter(getContext(),imageItems,2));
        }
        return view;
    }


}