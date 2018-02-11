package com.pappydevelopers.sarcasmfun.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pappydevelopers.sarcasmfun.activity.ImagePreview;
import com.pappydevelopers.sarcasmfun.utils.imageutils.ImageLoader;
import com.pappydevelopers.sarcasmfun.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private List<String> imageItems = new ArrayList<String>();
    private final LayoutInflater mInflater;
    public ImageLoader imageLoader;
    Context context;
    Integer list_type;

    public GridAdapter(Context context, List x, Integer list_type) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list_type = list_type;

        imageLoader = new ImageLoader(context);

        this.imageItems = x;
        for (int i=0;i<imageItems.size();i++){
            mItems.add(new Item(imageItems.get(i),R.drawable.ic_eighth));
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.custom_gridview, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        Item item = getItem(i);
        //imageLoader.DisplayImage(item.name, picture);
        Picasso.with(context).load(item.name).into(picture);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImagePreview.class);
                intent.putExtra("position",String.valueOf(i));
                intent.putExtra("imagelist", (ArrayList<String>) imageItems);
                intent.putExtra("type",list_type);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}