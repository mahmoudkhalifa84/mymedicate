package com.medicate_int.mymedicate.models;

public class SliderModel {
    String title, text;
    int imageView;

    public SliderModel(String title, String text, int imageView) {
        this.title = title;
        this.text = text;
        this.imageView = imageView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
