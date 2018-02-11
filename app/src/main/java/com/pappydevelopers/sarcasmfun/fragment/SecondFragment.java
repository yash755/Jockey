package com.pappydevelopers.sarcasmfun.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.pappydevelopers.sarcasmfun.R;
import com.pappydevelopers.sarcasmfun.adapter.GridAdapter;
import com.pappydevelopers.sarcasmfun.databasehelper.DataBaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;
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
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.error);
        frameLayout.setVisibility(View.INVISIBLE);
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
        } else {
            frameLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }


}