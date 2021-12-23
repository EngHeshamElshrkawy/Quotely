package com.example.quotely;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchDialog.SearchDialogListener {
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
        class GetQuotes extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                if(query.equals(getString(R.string.EMPTY_STRING))){
                    quoteList =  DatabaseClient.getInstance(getApplicationContext())
                            .getAppDatabase()
                            .quoteDao()
                            .getAll();
                }else{
                    if(id == R.id.quoteRb){
                        quoteList =  DatabaseClient.getInstance(getApplicationContext())
                                .getAppDatabase()
                                .quoteDao()
                                .loadByText(query);
                    }else{
                        quoteList =  DatabaseClient.getInstance(getApplicationContext())
                                .getAppDatabase()
                                .quoteDao()
                                .loadByAuthor(query);
                    }

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                updateUI();
            }
        }
        GetQuotes getQuotes = new GetQuotes();
        getQuotes.execute();
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

}