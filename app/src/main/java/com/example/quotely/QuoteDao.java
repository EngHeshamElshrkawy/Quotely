package com.example.quotely;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuoteDao {

    @Query("SELECT * FROM quotes")
    List<Quote> getAll();

    @Query("SELECT * FROM quotes WHERE `quote-text` LIKE '% ' || :text || ' %'")
    List<Quote> loadByText(String text);

    @Query("SELECT * FROM quotes WHERE `author-name` LIKE '%' || :text || '%'")
    List<Quote> loadByAuthor(String text);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Quote quote);

    @Delete
    void delete(Quote quote);

    @Update
    void update(Quote quote);




}
