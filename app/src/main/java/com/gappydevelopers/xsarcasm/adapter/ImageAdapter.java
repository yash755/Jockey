package com.gappydevelopers.xsarcasm.adapter;

import android.content.Context;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by yash on 26/1/18.
 */

public class ImageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<String> imageItems = new ArrayList<String>();


    public ImageAdapter(Context context, List imageItems) {
        mContext = context;
        this.imageItems = imageItems;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_adapter_view, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        //   imageLoader.DisplayImage(imageItems.get(position), imageView);

        Picasso.get()
                .load(imageItems.get(position))
                .error( R.drawable.error )
                .placeholder( R.drawable.progress_animation )
                .into(imageView);
        

        container.addView(itemView);


        /*DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

        int final_width = (int) (0.5 * widthPixels);

        menuItemHolder.menuItemImage.getLayoutParams().width =  final_width;
        menuItemHolder.menuItemImage.getLayoutParams().height = final_width;*/

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
