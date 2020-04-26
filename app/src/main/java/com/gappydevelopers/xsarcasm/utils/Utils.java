package com.gappydevelopers.xsarcasm.utils;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.helper.GetJSONArray;

import org.json.JSONArray;

import dmax.dialog.SpotsDialog;

/**
 * Created by yash on 2/11/17.
 */

public class Utils {

    public boolean check_connection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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
            textView.setGravity(Gravity.LEFT);
        }
        snackbar.show();
    }


    public void getAllList(final Context context, final GetJSONArray getResult){
       // final AlertDialog dialog = new SpotsDialog.Builder().setContext(context).setTheme(R.style.Custom).build();
       // dialog.show();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, "https://gappydevelopers.com/sarcasm.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
         //       dialog.hide();
                getResult.done(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                //dialog.hide();
                if(error.networkResponse != null && error.networkResponse.data!=null) {

                    Toast.makeText(context, "Network Error.....Try Later!!!",Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(context, "Time Out Error.....Try Later!!!",Toast.LENGTH_SHORT).show();
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof AuthFailureError) {

                    Toast.makeText(context, "Authentication Error.....Try Later!!!",Toast.LENGTH_SHORT).show();
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ServerError) {

                    Toast.makeText(context,"Server Error.....Try Later!!!",Toast.LENGTH_SHORT).show();
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof NetworkError) {

                    Toast.makeText(context, "Network Error.....Try Later!!!",Toast.LENGTH_SHORT).show();
                    System.out.println("Resonse" + error.toString());
                } else if (error instanceof ParseError) {

                    System.out.println("Resonse" + error.toString());
                    Toast.makeText(context, "Unknown Error.....Try Later!!!",Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Resonse" + error.toString());

                }
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

}
