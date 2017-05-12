package com.android.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookAdapter extends ArrayAdapter<Book> {

    private List<Book> books = new ArrayList<>();

    public BookAdapter(Activity context, List<Book> books) {
        super(context, 0, books);
        this.books = books;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Book book = getItem(position);
        final JSONObject jsonObject = book.volumeInfo;

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageBitmap(book.frontCover);
        setData(jsonObject, holder);


        return convertView;
    }

    private void setData(JSONObject jsonObject, ViewHolder holder) {
        try {
            JSONArray authors = jsonObject.getJSONArray("authors");
            String authorsString = "";
            for (int i = 0; authors.length() > i; i++) {
                authorsString += authors.get(i);
                if ((authors.length() - 1) > i) {
                    authorsString += ", ";
                }
            }
            holder.author.setText(authorsString);
        } catch (JSONException e) {
            e.printStackTrace();
            String authorEmpty = "Author N/A";
            holder.author.setText(authorEmpty);
        }
        try {
        holder.title.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String publishedDateString = "Published: " + jsonObject.getString("publishedDate");
            holder.publishedDate.setText(publishedDateString);
        } catch (JSONException e) {
            e.printStackTrace();
            String publishDateEmpty = "Published: N/A";
            holder.publishedDate.setText(publishDateEmpty);
        }
    }


    public void setBooks(List<Book> data) {
        books.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.itemImage) ImageView imageView;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.author) TextView author;
        @BindView(R.id.publishedDate) TextView publishedDate;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}