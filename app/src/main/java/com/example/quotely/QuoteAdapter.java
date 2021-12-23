package com.example.quotely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuoteAdapter extends ArrayAdapter<Quote> {
    public QuoteAdapter(Context context, ArrayList<Quote> quotes) {
        super(context, 0, quotes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Quote quote = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quote, parent, false);
        }
        TextView textTv = convertView.findViewById(R.id.quote_text);
        TextView authorTv =  convertView.findViewById(R.id.quote_author);
        textTv.setText(quote.text);
        String authorName = quote.author;
        if(authorName.equals("null")){
            authorName = "Unknown";
        }
        authorTv.setText(String.format("-%s", authorName));

        return convertView;
    }
}