package com.android.booklistingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ART_F on 2017-05-11.
 */

public class BookInfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {
    private static final String NO_ANSWER = "N/A";
    @BindView(R.id.title) TextView title;
    @BindView(R.id.itemImage) ImageView frontCover;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.averageRating) TextView averageRating;
    @BindView(R.id.pageCount) TextView pageCount;
    @BindView(R.id.publishedDate) TextView publishedDate;
    private Intent intent;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        intent = getIntent();
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        setData();

    }

    private void setData() {
        try {
            jsonObject = new JSONObject(intent.getStringExtra("book"));
            title.setText(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray authors = jsonObject.getJSONArray("authors");
            String authorsString = "";
            for (int i = 0; authors.length() > i; i++) {
                authorsString += authors.get(i);
                if ((authors.length() - 1) > i) {
                    authorsString += ", ";
                }
            }
            author.setText(authorsString);
        } catch (JSONException e) {
            e.printStackTrace();
            String authorEmpty = "Author N/A";
            author.setText(authorEmpty);
        }

        try {
            String descriptionString = jsonObject.getString("description");
            description.setText(descriptionString);
        } catch (JSONException e) {
            e.printStackTrace();
            description.setText("");
        }

        try {
            String averageRatingString = jsonObject.getString("averageRating");
            averageRating.setText(averageRatingString);
        } catch (JSONException e) {
            averageRating.setText(NO_ANSWER);
        }

        try {
            String pageCountString = jsonObject.getString("pageCount");
            pageCount.setText(pageCountString);
        } catch (JSONException e) {
            pageCount.setText(NO_ANSWER);
        }

        try {
            String publishedDateString = jsonObject.getString("publishedDate");
            publishedDate.setText(publishedDateString);
        } catch (JSONException e) {
            publishedDate.setText(NO_ANSWER);
        }
    }

    @OnClick({R.id.description, R.id.title})
    public void createDialogGameOver(View view) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_text)
                .create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        if (view.getId() == R.id.description) {
            TextView textDialog = (TextView) dialog.findViewById(R.id.textDialog);
            textDialog.setText(description.getText());
        } else {
            TextView textDialog = (TextView) dialog.findViewById(R.id.textDialog);
            textDialog.setText(title.getText());
        }

    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new FrontCoverLoader(BookInfoActivity.this, intent.getStringExtra("id"));
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap frontCoverBitmap) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (frontCover != null) {
            frontCover.setImageBitmap(frontCoverBitmap);
        }
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
        //empty
    }
}
