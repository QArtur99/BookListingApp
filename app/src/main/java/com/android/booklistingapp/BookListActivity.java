package com.android.booklistingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    BookAdapter bookAdapter;
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyStateTextView = (TextView) findViewById(R.id.emptyView);
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(emptyStateTextView);
        listView.setAdapter(bookAdapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookListActivity.this, BookInfoActivity.class);
                Book book = bookAdapter.getItem(position);
                intent.putExtra("book", book.volumeInfo.toString());
                intent.putExtra("id", book.id);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Intent intent = getIntent();
        String query = intent.getStringExtra(SearchActivity.QUERY);
        getSupportActionBar().setTitle(query);

        return new BookLoader(BookListActivity.this, query);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView.setText(R.string.no_books);
        bookAdapter.clear();
        if (data != null && !data.isEmpty()) {
            bookAdapter.setBooks(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.setBooks(new ArrayList<Book>());
    }

}
