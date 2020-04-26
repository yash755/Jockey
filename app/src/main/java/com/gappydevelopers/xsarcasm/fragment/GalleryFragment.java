package com.gappydevelopers.xsarcasm.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;
import com.gappydevelopers.xsarcasm.helper.MasterData;

import com.gappydevelopers.xsarcasm.adapter.RecyclerViewAdapter;
import com.gappydevelopers.xsarcasm.helper.GetJSONArray;
import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.utils.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    // Store instance variables
    private List<Object> mRecyclerViewItems;
    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 3;
    RecyclerView mRecyclerView;
    AlertDialog dialog;
    Context context;
    private List<String> imageItems;



    // The AdLoader used to load ads.
    private AdLoader adLoader;

    // List of MenuItems and native ads that populate the RecyclerView.

    RecyclerView.Adapter adapter;

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();


    // newInstance constructor for creating fragment with arguments
    public static GalleryFragment newInstance(int page, String title) {
        GalleryFragment fragmentFirst = new GalleryFragment();
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
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.demo);
        mRecyclerViewItems = new ArrayList<>();
        imageItems = new ArrayList<>();

        dialog = new SpotsDialog.Builder().setContext(context).setTheme(R.style.Custom).build();
        dialog.show();
        new Utils().getAllList(getContext(), new GetJSONArray() {
            @Override
            public void done(JSONArray jsonArray) {
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String image = "https://gappydevelopers.com/thumbnail/" + jsonObject1.getString("pic_url");
                            image = image.replace("\\", "");
                            mRecyclerViewItems.add(new MasterData(image));
                            imageItems.add(image);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

/*                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerViewAdapter(context, mRecyclerViewItems, imageItems);
                mRecyclerView.setAdapter(adapter);
                dialog.dismiss();*/
                loadNativeAds();


            }
        });




        return view;
    }


    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(context, getString(R.string.ad_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                           // insertAdsInMenuItems();

                            if (mNativeAds.size() > 0) {


                                int offset = (mRecyclerViewItems.size() / mNativeAds.size()) + 1;
                                int index = 2;
                                for (UnifiedNativeAd ad : mNativeAds) {
                                    mRecyclerViewItems.add(index, ad);
                                    index = index + offset;

                                }
          /*                      adapter.notifyItemInserted(index);*/
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                mRecyclerView.setLayoutManager(layoutManager);


                                adapter = new RecyclerViewAdapter(context, mRecyclerViewItems, imageItems, 0);
                                mRecyclerView.setAdapter(adapter);
                                dialog.dismiss();
                            }


                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                            //insertAdsInMenuItems();
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(layoutManager);


                            adapter = new RecyclerViewAdapter(context, mRecyclerViewItems, imageItems, 0);
                            mRecyclerView.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }

}