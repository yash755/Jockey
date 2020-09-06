package com.gappydevelopers.xsarcasm.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gappydevelopers.xsarcasm.helper.GetJSONObject;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

/**
 * Created by yash on 2/11/17.
 */

public class Utils {

    public boolean checkConnection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo == null || !networkInfo.isConnected();
    }

    public static void showSnackBar(View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(18);
        textView.setTypeface(null, Typeface.BOLD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        } else {
            textView.setGravity(Gravity.START);
        }
        snackbar.show();
    }


    public void getAllList(final Context context, final GetJSONObject getResult){
       // final AlertDialog dialog = new SpotsDialog.Builder().setContext(context).setTheme(R.style.Custom).build();
       // dialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.haramibandi.xyz/get_sarcasm_images", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
         //       dialog.hide();
                getResult.done(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Sorry!! Try again", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }


}
