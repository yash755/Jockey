package com.gappydevelopers.xsarcasm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.activity.ImagePreview;
import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;
import com.gappydevelopers.xsarcasm.helper.MasterData;
import com.gappydevelopers.xsarcasm.sharedprefrence.UserLocalStore;
import com.gappydevelopers.xsarcasm.utils.CircleTransform;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class TumbnailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // A menu item view type.


    private final Context mContext;
    private final List<Object> mRecyclerViewItems;



    public TumbnailAdapter(Context context, List<Object> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;

    }

    /**
     * The {@link RecyclerViewAdapter.MenuItemViewHolder} class.
     * Provides a reference to each view in the menu item view.
     */
    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuItemImage, dBookmark, dInfo;

        MenuItemViewHolder(View view) {
            super(view);
            menuItemImage = (ImageView) view.findViewById(R.id.imageView);

        }
    }


    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }



    /**
     * Creates a new view for a menu item view or a Native ad view
     * based on the viewType. This method is invoked by the layout manager.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.thumbnail_view, viewGroup, false);
        return new MenuItemViewHolder(menuItemLayoutView);
    }

    /**
     * Replaces the content in the views that make up the menu item view and the
     * Native ad view. This method is invoked by the layout manager.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final TumbnailAdapter.MenuItemViewHolder menuItemHolder = (TumbnailAdapter.MenuItemViewHolder) holder;
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
                .transform(new CircleTransform())
                .into(menuItemHolder.menuItemImage);



        menuItemHolder.menuItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> imageItems = new ArrayList<>();
                imageItems.add(masterData.getImageName());
                Intent intent = new Intent(mContext, ImagePreview.class);
                intent.putExtra("position","0");
                intent.putExtra("imagelist", (ArrayList<String>) imageItems);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }

        });





        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

        int final_width = (int) (0.5 * widthPixels);

        menuItemHolder.menuItemImage.getLayoutParams().width =  final_width;
        menuItemHolder.menuItemImage.getLayoutParams().height = final_width;
    }


}
