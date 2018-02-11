package com.pappydevelopers.sarcasmfun.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.pappydevelopers.sarcasmfun.helper.GetJSONObject;
import com.pappydevelopers.sarcasmfun.R;
import com.pappydevelopers.sarcasmfun.utils.Utils;
import com.pappydevelopers.sarcasmfun.adapter.GridAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title, base_url;
    private int page;
    private JSONArray image_list;
    private List<String> imageItems;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
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
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        final GridView gridView = (GridView) view.findViewById(R.id.gridview);
        imageItems = new ArrayList<String>();
        new Utils().getAllList(getContext(), new GetJSONObject() {
            @Override
            public void done(JSONObject jsonObject) {
                try {
                    base_url = jsonObject.getString("base_url");
                    image_list = jsonObject.getJSONArray("image_list");
                    for(int i=0; i<image_list.length();i++){
                        JSONObject jsonObject1 = image_list.getJSONObject(i);
                        imageItems.add("http://" + base_url + '/' +jsonObject1.getString("image_url"));
                    }
                    gridView.setAdapter(new GridAdapter(getContext(),imageItems,1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }


}