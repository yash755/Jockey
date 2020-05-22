package com.gappydevelopers.xsarcasm.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.activity.ImagePreview;
import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;
import com.gappydevelopers.xsarcasm.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private List<String> imageItems;
    private final LayoutInflater mInflater;

    Context context;


    public GridAdapter(Context context, List<String> x) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.imageItems = x;

    }

    @Override
    public int getCount() {
        return imageItems.size();
    }

    @Override
    public String getItem(int i) {
        return imageItems.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

   /* @Override
    public long getItemId(int i) {
        return imageItems.get(i);
    }*/

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture, remove;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.custom_gridview, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.remove, v.findViewById(R.id.remove));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        remove = (ImageView) v.getTag(R.id.remove);

        final String item = getItem(i);


        Picasso.get()
                .load(item)
                .error( R.drawable.error )
                .placeholder( R.drawable.progress_animation )
                .transform(new CircleTransform())
                .into(picture);


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                dataBaseHelper.deleteImage(item);
                imageItems.remove(i);
                notifyDataSetChanged();
                Toast.makeText(context, "Removed from favourtie list.", Toast.LENGTH_SHORT).show();
            }

        });


        return v;
    }
}