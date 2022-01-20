package com.example.quotely.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quotely.interfaces.AddingQuotesResponse;
import com.example.quotely.interfaces.FetchingQuotesResponse;

public class FetchQuotes {

    private Context context;
    public FetchingQuotesResponse delegate = null;

    public FetchQuotes(Context context){
        this.context = context;
    }


    public void execute(){

        String url = "https://type.fit/api/quotes";
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        delegate.fetchingFinished(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }




}
