package com.example.quotely.interfaces;

import com.example.quotely.models.Quote;

import java.util.List;

public interface GettingQuotesResponse {
    void gettingFinished(List<Quote> quoteList);
}
