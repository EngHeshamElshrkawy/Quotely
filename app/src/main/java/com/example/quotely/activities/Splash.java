package com.example.quotely.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.captaindroid.tvg.Tvg;
import com.example.quotely.R;
import com.example.quotely.interfaces.AddingQuotesResponse;
import com.example.quotely.interfaces.FetchingQuotesResponse;
import com.example.quotely.utils.AddQuotes;
import com.example.quotely.utils.FetchQuotes;
import com.example.quotely.utils.NetworkClient;

public class Splash extends AppCompatActivity implements AddingQuotesResponse, FetchingQuotesResponse {

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
            if(NetworkClient.isOnline()){
                FetchQuotes fetchQuotes = new FetchQuotes(this);
                fetchQuotes.delegate = this;
                fetchQuotes.execute();
            }else{
                textView.setText(R.string.no_internet_text);
                Tvg.change(textView, Color.parseColor(getString(R.string.starting_color)),  Color.parseColor(getString(R.string.ending_color)));
            }

        }
    }

    public void addQuotes(String quotesJSON){
        AddQuotes addQuote = new AddQuotes(this);
        addQuote.delegate = this;
        addQuote.execute(quotesJSON);
    }

    public void goToMain(){
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void addingFinished() {
        goToMain();
    }

    @Override
    public void fetchingFinished(String response) {
        addQuotes(response);
    }
}