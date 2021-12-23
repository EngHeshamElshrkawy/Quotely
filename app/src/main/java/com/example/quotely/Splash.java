package com.example.quotely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.captaindroid.tvg.Tvg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Splash extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = findViewById(R.id.splash_text);
        Tvg.change(textView, Color.parseColor(getString(R.string.starting_color)),  Color.parseColor(getString(R.string.ending_color)));

        sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefrences_name), Context.MODE_PRIVATE);
        boolean hasData = sharedPreferences.getBoolean(getString(R.string.shared_prefrences_key), false);
        if(hasData){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToMain();
                }
            }, 3000);
        }else{
            if(isOnline()){
                fetchQuotes();
            }else{
                textView.setText(R.string.no_internet_text);
                Tvg.change(textView, Color.parseColor(getString(R.string.starting_color)),  Color.parseColor(getString(R.string.ending_color)));
            }

        }


    }

    public void fetchQuotes(){
        String url = "https://type.fit/api/quotes";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        addQuotes(response);

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




    public void addQuotes(String quotesJSON){
        class AddQuote extends AsyncTask<Void, Void, Void> {

            int i = 0;
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    JSONArray quotesArray = new JSONArray(quotesJSON);
                    for(i = 0; i < 400; i++){
                        JSONObject quoteObj = quotesArray.getJSONObject(i);
                        Quote quote = new Quote();
                        quote.author = quoteObj.getString("author");
                        quote.text = quoteObj.getString("text");
                        DatabaseClient.getInstance(getApplicationContext())
                                .getAppDatabase()
                                .quoteDao()
                                .insert(quote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                //sharedIsTrue
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("hasData",true);
                editor.apply();
                goToMain();
            }
        }
        AddQuote addQuote = new AddQuote();
        addQuote.execute();
    }



    public void goToMain(){
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}