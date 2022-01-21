package com.example.quotely.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.quotely.R;
import com.example.quotely.interfaces.QuotesHandling;
import com.example.quotely.models.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AddQuotes extends AsyncTask<String, Void, Void> {


    private Context context;
    SharedPreferences sharedPreferences;
    public QuotesHandling delegate = null;

    public AddQuotes(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_prefrences_name), Context.MODE_PRIVATE);
    }



    int i = 0;
    @Override
    protected Void doInBackground(String... strings) {
        try {
            JSONArray quotesArray = new JSONArray(strings[0]);
            for(i = 0; i < 5; i++){
                JSONObject quoteObj = quotesArray.getJSONObject(i);
                Quote quote = new Quote();
                if(quoteObj.getString("author").equals("null")){
                    quote.author = "Unknown";
                }else{
                    quote.author = quoteObj.getString("author");
                }
                quote.text = quoteObj.getString("text");
                DatabaseClient.getInstance(context)
                        .getAppDatabase()
                        .quoteDao()
                        .insert(quote);

                PredictQuoteTopic predictQuoteTopic = new PredictQuoteTopic(context);
                Log.v("PredictionLog", quote.text + " === " +predictQuoteTopic.getQuoteTopic(quote.text));

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
        delegate.addingFinished();
    }

}



