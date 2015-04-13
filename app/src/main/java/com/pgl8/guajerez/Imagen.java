package com.pgl8.guajerez;

import android.graphics.Bitmap;


public class Imagen {
    private Bitmap image;

    public Imagen(Bitmap image, String title) {
        super();
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
}
