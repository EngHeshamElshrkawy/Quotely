package com.example.quotely.interfaces;

import com.example.quotely.models.Quote;

import java.util.List;

public interface QuotesHandling {
    void gettingFinished(List<Quote> quoteList);
    void fetchingFinished(String response);
    void addingFinished();



}
