package com.example.quotely.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.quotely.R;
import com.example.quotely.fragments.SearchDialog;
import com.example.quotely.adapters.QuoteAdapter;
import com.example.quotely.interfaces.QuotesHandling;
import com.example.quotely.models.Quote;
import com.example.quotely.utils.GetQuotes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchDialog.SearchDialogListener, QuotesHandling {
    List<Quote> quoteList;
    ListView listView;
    String query = "";
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.quotesListView);
        getQuotes(query);

    }

    /**
     *
     * @param query
     * Queries the database to get the search query of the user in another thread.
     *
     */
    public void getQuotes(String query){
        GetQuotes getQuotes = new GetQuotes(this);
        getQuotes.delegate = this;
        getQuotes.execute(query, String.valueOf(id));
    }

    /**
     *Uses the list of quotes to populate the listview using QuoteAdapter
     */
    public void updateUI(){
        ArrayList<Quote> quoteArrayList = new ArrayList<Quote>(quoteList);
        QuoteAdapter adapter = new QuoteAdapter(this, quoteArrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            search();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Launches the search dialog
     */
    public void search(){
        SearchDialog searchDialog = new SearchDialog();
        searchDialog.show(getSupportFragmentManager(), "searchDialog");

    }
    @Override
    public void getQuery(String query, int id) {
        this.query = query;
        this.id = id;
        getQuotes(query);
    }

    @Override
    public void reset(){
        query = getString(R.string.EMPTY_STRING);
        getQuotes(query);
    }

    @Override
    public void gettingFinished(List<Quote> quoteList) {
        this.quoteList = quoteList;
        updateUI();
    }

    @Override
    public void fetchingFinished(String response) {
        Log.v("MainActivityTag", "Interface called from MainActivity");
    }

    @Override
    public void addingFinished() {
        Log.v("MainActivityTag", "Interface called from MainActivity");
    }
}