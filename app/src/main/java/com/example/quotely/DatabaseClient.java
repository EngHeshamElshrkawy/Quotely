package com.example.quotely;

import android.content.Context;

import androidx.room.Room;

/**
 * Returning an instance of the DatabaseClient using singleton pattern
 */

public class DatabaseClient {
    private Context mContext;
    private static DatabaseClient mInstance;
    private AppDatabase appDatabase;

    private DatabaseClient(Context mContext){
        this.mContext = mContext;
        appDatabase = Room.databaseBuilder(mContext, AppDatabase.class, mContext.getString(R.string.database_name))
                .fallbackToDestructiveMigration()
                .build();

    }
    public static synchronized DatabaseClient getInstance(Context mContext){
        if(mInstance == null){
            mInstance = new DatabaseClient(mContext);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }


}
