package com.medicate_int.mymedicate.models;

public class CitiesModel {
    String cityName ;
    String cityId;
    String country;
    public CitiesModel(String cityName, String cityId, String contry)
    {
        this.country =contry;
        this.cityName = cityName;
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCountry() {
        return country;
    }
}
