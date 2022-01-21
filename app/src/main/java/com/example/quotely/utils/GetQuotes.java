package com.example.quotely.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.quotely.R;
import com.example.quotely.interfaces.QuotesHandling;
import com.example.quotely.models.Quote;

import java.util.List;

public class GetQuotes extends AsyncTask<String, Void, Void> {
        private Context context;
        private List<Quote> quoteList;
        public QuotesHandling delegate = null;



    public GetQuotes(Context context){
            this.context = context;
        }

            @Override
            protected Void doInBackground(String... strings) {
                if(strings[0].equals(context.getString(R.string.EMPTY_STRING))){
                    quoteList =  DatabaseClient.getInstance(context)
                            .getAppDatabase()
                            .quoteDao()
                            .getAll();
                }else{
                    if(strings[1] == String.valueOf(R.id.quoteRb)){
                        quoteList =  DatabaseClient.getInstance(context)
                                .getAppDatabase()
                                .quoteDao()
                                .loadByText(strings[0]);
                    }else{
                        quoteList =  DatabaseClient.getInstance(context)
                                .getAppDatabase()
                                .quoteDao()
                                .loadByAuthor(strings[0]);
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                delegate.gettingFinished(quoteList);
            }
        }

