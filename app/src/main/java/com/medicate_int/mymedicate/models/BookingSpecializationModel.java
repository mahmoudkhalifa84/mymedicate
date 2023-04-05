package com.medicate_int.mymedicate.models;

public class BookingSpecializationModel {
    String city_name;
    int img;
    String contry,id;

    public BookingSpecializationModel(String city_name, String contry, String id) {
        this.city_name = city_name;
        this.contry = contry;
        this.id = id;
    }

    public BookingSpecializationModel(String contry, String id) {
        this.contry = contry;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }



    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public BookingSpecializationModel(String city_name, int img) {
        this.city_name = city_name;
        this.img = img;
    }
}
