package com.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ART_F on 2017-05-11.
 */

public class SearchActivity extends AppCompatActivity {
    public static final String QUERY = "query";

    @BindView(R.id.search) EditText search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submitButton)
    public void submit() {
        if (checkConnection()) {
            searchBooks();
        } else {
            Toast.makeText(SearchActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchBooks() {
        String searchPhrase = search.getText().toString();
        if (searchPhrase.isEmpty()) {
            Toast.makeText(SearchActivity.this, "Please enter a query in the box above", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SearchActivity.this, BookListActivity.class);
            intent.putExtra(QUERY, searchPhrase);
            startActivity(intent);
        }
    }

    private boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) SearchActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
