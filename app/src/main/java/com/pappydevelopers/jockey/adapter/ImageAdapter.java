package com.pappydevelopers.jockey.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pappydevelopers.jockey.R;
import com.pappydevelopers.jockey.utils.imageutils.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yash on 26/1/18.
 */

public class ImageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<String> imageItems = new ArrayList<String>();
    public ImageLoader imageLoader;


    public ImageAdapter(Context context, List imageItems) {
        mContext = context;
        this.imageItems = imageItems;
        imageLoader = new ImageLoader(context);
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

        Picasso.with(mContext).load(imageItems.get(position)).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}