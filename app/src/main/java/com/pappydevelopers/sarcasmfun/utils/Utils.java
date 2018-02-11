package com.pappydevelopers.sarcasmfun.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pappydevelopers.sarcasmfun.R;
import com.pappydevelopers.sarcasmfun.helper.GetJSONObject;

import org.json.JSONObject;

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


    public void getAllList(final Context context, final GetJSONObject getResult){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://139.59.88.39/get_image_list.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                getResult.done(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
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
