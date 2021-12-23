package com.example.quotely;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Quote.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuoteDao quoteDao();

}
