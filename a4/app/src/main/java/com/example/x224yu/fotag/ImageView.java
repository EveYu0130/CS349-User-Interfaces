package com.example.x224yu.fotag;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.ArrayList;

public class ImageView extends LinearLayout implements IView {

    private ImageModel Imodel;
    private android.widget.ImageView view;
    private Bitmap image;
    private RatingBar rating;

    ImageView(final Context context, final ImageModel Imodel) {

        super(context);
        this.Imodel = Imodel;

        View.inflate(context, R.layout.imageview, this);
        this.view = findViewById(R.id.imageView);

        Resources res = getResources();

        int imageId = Imodel.getId();

        this.image = BitmapFactory.decodeResource(res, imageId);
        view.setImageBitmap(image);

        this.rating = findViewById(R.id.rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Imodel.setRateValue((int) v);
            }
        });

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog popup = new Dialog(context);
                popup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                popup.setContentView(R.layout.image_popup);
                android.widget.ImageView fullImage = popup.findViewById(R.id.fullImage);
                fullImage.setImageBitmap(image);
                popup.show();
            }
        });

        Imodel.setIview(this);
    }


    public void updateView() {
        rating.setRating(Imodel.getRateValue());
    }
}
