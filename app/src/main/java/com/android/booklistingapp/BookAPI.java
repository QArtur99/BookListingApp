package com.android.booklistingapp;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ART_F on 2017-05-10.
 */

public class BookAPI {

    public static String getBooksString(String query) throws JSONException, IOException {
        HashMap<String, String> args = new HashMap<>();
        args.put("q", query);
        args.put("maxResults", "10");

        String firstPart = "https://www.googleapis.com/books/v1/volumes?";
        String url = firstPart + getUri(args);

        String jsonString = getQueryJSONObject(url);
        return jsonString;
    }

    public static String getFrontCover(String id, String zoom) throws JSONException, IOException {
        HashMap<String, String> args = new HashMap<>();
        args.put("id", id);
        args.put("printsec", "frontcover");
        args.put("img", "1");
        args.put("zoom", zoom);

        String firstPart = "https://books.google.com/books/content?";
        String url = firstPart + getUri(args);
        return url;
    }


    private static String getQueryJSONObject(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder jsonString = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            jsonString.append(line).append("\n");
        }
        br.close();

        return jsonString.toString();
    }

    @NonNull
    private static String getUri(HashMap<String, String> args) throws JSONException {
        JSONObject uriBuilder = new JSONObject(args);
        Iterator<?> keys = uriBuilder.keys();
        String postData = "";
        while (keys.hasNext()) {
            String key = (String) keys.next();
            if (postData.length() > 0) {
                postData += "&";
            }
            postData += key + "=" + uriBuilder.getString(key);
        }
        return postData;
    }
}
