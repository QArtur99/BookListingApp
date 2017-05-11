package com.android.booklistingapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ART_F on 2017-05-10.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private List<Book> list = new ArrayList<>();
    private String query;

    public BookLoader(Context context, String query) {
        super(context);
        this.query = query;
    }


    @Override
    public List<Book> loadInBackground() {

        try {
            getBooksList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private void getBooksList() throws IOException, JSONException {
        String jsonString = BookAPI.getBooksString(query);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        int bookAmount = jsonArray.length();
        for(int i = 0; bookAmount > i; i++ ){
            JSONObject jsonBookData =  jsonArray.getJSONObject(i);
            String bookId = jsonBookData.getString("id");
            URL url = new URL(BookAPI.getFrontCover(bookId, "1"));
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            JSONObject volumeInfo =  jsonBookData.getJSONObject("volumeInfo");
            list.add(new Book(bookId, volumeInfo, bmp));
        }
    }
}
