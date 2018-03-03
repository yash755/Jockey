package com.pappydevelopers.extremesarcasm.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pappydevelopers.extremesarcasm.R;
import com.pappydevelopers.extremesarcasm.activity.Privacy;

/**
 * Created by yash on 26/1/18.
 */

public class ThirdFragment extends Fragment implements View.OnClickListener {
    // Store instance variables
    private String title, base_url;
    private int page;
    private LinearLayout contact,share,rate,privacy;

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
        View view = inflater.inflate(R.layout.third_fragment, container, false);

        contact = (LinearLayout) view.findViewById(R.id.contact);
        share = (LinearLayout) view.findViewById(R.id.share);
        rate = (LinearLayout) view.findViewById(R.id.rate);
        privacy = (LinearLayout) view.findViewById(R.id.privacy);


        contact.setOnClickListener(this);
        share.setOnClickListener(this);
        rate.setOnClickListener(this);
        privacy.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == contact) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gappydevelopers@gmail.com"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Write you name or contact details.");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Your valuable suggestions.");
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } else if (view == share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Checkout this amazing Sarcasm jokes app.\n\n";
            shareBody = shareBody + "https://play.google.com/store/apps/details?id="+ getContext().getPackageName() + "\n\n";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Sarcasm Fun");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (view == rate) {
            Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
            }
        } else if (view == privacy){
            Intent i = new Intent(getContext(), Privacy.class);
            startActivity(i);
        }

    }
}