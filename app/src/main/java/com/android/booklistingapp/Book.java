package com.android.booklistingapp;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * Created by ART_F on 2017-05-10.
 */

public class Book {

    final String id;
    final JSONObject volumeInfo;
    final Bitmap frontCover;

    Book(String id, JSONObject volumeInfo, Bitmap frontCover) {
        this.id = id;
        this.volumeInfo = volumeInfo;
        this.frontCover = frontCover;
    }
}
