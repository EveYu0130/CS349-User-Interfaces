package com.example.x224yu.fotag;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IView {

    private ImageCollectionModel ICmodel;
    private RatingBar filter;
    private ViewGroup contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ICmodel = new ImageCollectionModel();
        this.filter = (RatingBar) findViewById(R.id.rating_filter);
        filter.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ICmodel.setRating((int) v);

            }
        });

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        contentView = (ViewGroup) findViewById(R.id.content);

        ICmodel.addView(this);
        updateView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("images", ICmodel.getImages());
        outState.putInt("filter", ICmodel.getRating());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<ImageModel> images = savedInstanceState.getParcelableArrayList("images");
        int rating = savedInstanceState.getInt("filter");
        ICmodel.setRating(rating);
        ICmodel.setImages(images);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.loadBtn) {
            if (ICmodel.getImages().size() == 0) {
                ICmodel.load();
            }
            return true;
        } else if (id == R.id.resetBtn) {
            filter.setRating(0);
            if (ICmodel.getImages().size() != 0) {
                ICmodel.clear();
            }
            return true;
        }
//        else if () {
//            filter.setRating(0);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    public void updateView() {
        ArrayList<ImageModel> images = ICmodel.getImages();
        contentView.removeAllViews();

        for (ImageModel im : images) {
            ImageView iv = new ImageView(this, im);
            contentView.addView(iv);

        }

    }
}
