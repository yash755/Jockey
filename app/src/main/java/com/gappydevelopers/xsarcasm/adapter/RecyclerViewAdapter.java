/*
 * Copyright (C) 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gappydevelopers.xsarcasm.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.gappydevelopers.xsarcasm.activity.ImagePreview;
import com.gappydevelopers.xsarcasm.activity.MainScreen;
import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;
import com.gappydevelopers.xsarcasm.helper.MasterData;
import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.sharedprefrence.UserLocalStore;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;


import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


/**
 * The {@link RecyclerViewAdapter} class.
 * <p>The adapter provides access to the items in the {@link MenuItemViewHolder}
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    // An Activity's Context.
    DataBaseHelper dataBaseHelper;
    AlertDialog dialog;
    private List<String> imageItems = new ArrayList<String>();
    UserLocalStore userLocalStore;
    private final Context mContext;
    Integer flag = -1;

    // The list of Native ads and menu items.
    private final List<Object> mRecyclerViewItems;



    public RecyclerViewAdapter(Context context, List<Object> recyclerViewItems, List x, Integer flag) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
        dataBaseHelper = new DataBaseHelper(context);
        userLocalStore = new UserLocalStore(context);
        this.imageItems = x;
        this.flag = flag;

    }

    /**
     * The {@link MenuItemViewHolder} class.
     * Provides a reference to each view in the menu item view.
     */
    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuItemImage, dBookmark, dInfo;

        MenuItemViewHolder(View view) {
            super(view);
            menuItemImage = (ImageView) view.findViewById(R.id.menu_item_image);
            dBookmark = (ImageView) view.findViewById(R.id.d_bookmark);
            dInfo = (ImageView) view.findViewById(R.id.d_info);

        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mRecyclerViewItems.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return MENU_ITEM_VIEW_TYPE;
    }

    /**
     * Creates a new view for a menu item view or a Native ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                View unifiedNativeLayoutView = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.ad_unified,
                        viewGroup, false);
                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case MENU_ITEM_VIEW_TYPE:
                // Fall through.
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.menu_item_container, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
        }
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * Native ad view. This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                UnifiedNativeAd nativeAd = (UnifiedNativeAd) mRecyclerViewItems.get(position);
                populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
                break;
            case MENU_ITEM_VIEW_TYPE:
                // fall through
            default:
                final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
                final MasterData masterData = (MasterData) mRecyclerViewItems.get(position);

                // Get the menu item image resource ID.
                String imageName = masterData.getImageName();
                int imageResID = mContext.getResources().getIdentifier(imageName, "drawable",
                        mContext.getPackageName());

                // Add the menu item details to the menu item view.

                Picasso.get()
                        .load(masterData.getImageName())
                        .error( R.drawable.error )
                        .placeholder( R.drawable.progress_animation )
                        .into(menuItemHolder.menuItemImage);


                menuItemHolder.dBookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor cr = dataBaseHelper.getParticularImage(masterData.getImageName());
                        if (cr.getCount() == 0) {
                            dataBaseHelper.insertImage(masterData.getImageName());
                            Toast.makeText(mContext , "Added to favourite list.", Toast.LENGTH_SHORT).show();
                        } else {
                            dataBaseHelper.deleteImage(masterData.getImageName());
                            Toast.makeText(mContext, "Removed from favourtie list.", Toast.LENGTH_SHORT).show();

                            if (flag == 1)
                            update();


                        }
                    }

                });






                menuItemHolder.dInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImagePreview.class);
                        intent.putExtra("position",String.valueOf(position));
                        intent.putExtra("imagelist", (ArrayList<String>) imageItems);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });


        }
    }


    private void update() {
        mRecyclerViewItems.clear();
        Cursor cr = dataBaseHelper.getImage();

        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            imageItems.add(cr.getString(cr.getColumnIndex("url")));
            mRecyclerViewItems.add(new MasterData(cr.getString(cr.getColumnIndex("url"))));
            cr.moveToNext();
        }
        cr.close();
        notifyDataSetChanged();

    }


    private void populateNativeAdView(UnifiedNativeAd nativeAd,
                                      UnifiedNativeAdView adView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }
}
