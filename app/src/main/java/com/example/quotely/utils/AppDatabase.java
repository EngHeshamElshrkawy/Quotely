package com.example.quotely.utils;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.quotely.interfaces.QuoteDao;
import com.example.quotely.models.Quote;

@Database(entities = {Quote.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuoteDao quoteDao();

}
