package com.example.x224yu.fotag;

import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ImageCollectionModel {

    private ArrayList<ImageModel> images = new ArrayList<ImageModel>();
    private ArrayList<IView> views = new ArrayList<IView>();
    private int rating;

    private Integer[] srcImages = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,
            R.drawable.g,
            R.drawable.h,
            R.drawable.i,
            R.drawable.j
    };


    ImageCollectionModel() {
        rating = 0;
    }

    public void addView(IView view) {
        views.add(view);
    }


    public ArrayList<ImageModel> getImages() {
        if (rating == 0) {
            return images;
        } else {
            ArrayList<ImageModel> filteredImages = new ArrayList<ImageModel>();
            for (ImageModel im : images) {
                if (im.getRateValue() >= rating) {
                    filteredImages.add(im);
                }
            }
            return filteredImages;
        }
    }

    public void setImages(ArrayList<ImageModel> images) {
        this.images = images;
        notifyViews();
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        notifyViews();
    }


    public void load() {
        for (int i : srcImages) {
            ImageModel imageModel = new ImageModel(this, i);
            images.add(imageModel);
        }
        notifyViews();

    }

    public void clear() {
        images.clear();
        notifyViews();
    }


    public void notifyViews() {
        for (IView view : this.views) {
            view.updateView();
        }
    }
}
