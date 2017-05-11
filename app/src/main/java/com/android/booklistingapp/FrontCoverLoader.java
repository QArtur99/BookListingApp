package com.android.booklistingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by ART_F on 2017-05-11.
 */

public class FrontCoverLoader extends AsyncTaskLoader<Bitmap> {

    String id;
    public FrontCoverLoader(Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    public Bitmap loadInBackground() {

        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(BookAPI.getFrontCover(id, "6"));
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }
}
