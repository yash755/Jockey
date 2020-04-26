package com.gappydevelopers.xsarcasm.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.adapter.RecyclerViewAdapter;
import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;
import com.gappydevelopers.xsarcasm.helper.MasterData;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yash on 26/1/18.
 */
public class FavouriteFragment extends Fragment {
    // Store instance variables
    private String title, base_url;
    private int page;
    private JSONArray image_list;
    private List<String> imageItems;
    DataBaseHelper dataBaseHelper;
    private List<Object> mRecyclerViewItems;
    RecyclerView mRecyclerView;
    Context context;
    RecyclerView.Adapter adapter;

    // newInstance constructor for creating fragment with arguments
    public static FavouriteFragment newInstance(int page, String title) {
        FavouriteFragment fragmentFirst = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");*/
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.error);
        frameLayout.setVisibility(View.INVISIBLE);

        mRecyclerView = view.findViewById(R.id.favourite_recycler);
        mRecyclerViewItems = new ArrayList<>();

        dataBaseHelper = new DataBaseHelper(getContext());
        imageItems = new ArrayList<String>();
        Cursor cr = dataBaseHelper.getImage();
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                imageItems.add(cr.getString(cr.getColumnIndex("url")));
                mRecyclerViewItems.add(new MasterData(cr.getString(cr.getColumnIndex("url"))));
                cr.moveToNext();
            }
            cr.close();
           /* gridView.setAdapter(new GridAdapter(getContext(),imageItems,2));*/
        } else {
            frameLayout.setVisibility(View.VISIBLE);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(context, mRecyclerViewItems, imageItems, 1);
        mRecyclerView.setAdapter(adapter);

        if (mRecyclerViewItems.size() == 0) {
            frameLayout.setVisibility(View.VISIBLE);
        }



        return view;
    }


}