package com.pappydevelopers.sarcasmfun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pappydevelopers.sarcasmfun.R;

/**
 * Created by yash on 26/1/18.
 */

public class ThirdFragment extends Fragment {
    // Store instance variables
    private String title, base_url;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
        ThirdFragment fragmentFirst = new ThirdFragment();
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
        View view = inflater.inflate(R.layout.item_list, container, false);
        return view;
    }


}