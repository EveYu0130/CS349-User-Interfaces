package com.example.x224yu.fotag;

import android.widget.RatingBar;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ImageModel implements Parcelable {
    private ImageView Iview;
    private ImageCollectionModel ICmodel;
    private int id;
    private int rateValue;

    ImageModel(ImageCollectionModel ICmodel, int id) {
        this.ICmodel = ICmodel;
        this.id = id;
    }

    ImageModel(Parcel in) {
        ;
        this.id = in.readInt();

        this.rateValue = 0;

    }

    public void setIview(ImageView iview) {
        this.Iview = iview;
        Iview.updateView();
    }

    public int getId() {
        return id;
    }


    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
        Iview.updateView();

    }

    public int getRateValue() {
        return rateValue;
    }


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rateValue);
        dest.writeInt(id);
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
