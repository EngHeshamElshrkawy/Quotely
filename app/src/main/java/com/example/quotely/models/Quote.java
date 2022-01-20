package com.example.quotely.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "quotes", indices = {@Index(value = {"quote-text"},
        unique = true)})
public class Quote {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "author-name")
    public String author;

    @ColumnInfo(name = "quote-text")
    public String text;

}
