package com.medicate_int.mymedicate.models;

import org.jetbrains.annotations.Nullable;

public class MedicalNetworkModel {
    //   int img;
    @Nullable
    String CompanyArabicName, BusnisName, CompanyEnglishName, CompanyFrenshName, GooGleMaps, CompanyID, CityID, CompanyType, Email, Phone, AddressLine1, CityName, image,CountryID;
    @Nullable
    String lat, log;


/*
    public int getImg() {
        return img;
    }*/

    public void setCompanyArabicName(@Nullable String companyArabicName) {
        CompanyArabicName = companyArabicName;
    }

    public void setBusnisName(@Nullable String busnisName) {
        BusnisName = busnisName;
    }

    public void setCompanyEnglishName(@Nullable String companyEnglishName) {
        CompanyEnglishName = companyEnglishName;
    }

    public void setCompanyFrenshName(@Nullable String companyFrenshName) {
        CompanyFrenshName = companyFrenshName;
    }

    @Nullable
    public String getCountryID() {
        return CountryID;
    }

    public void setCountryID(@Nullable String countryID) {
        CountryID = countryID;
    }

    public void setGooGleMaps(@Nullable String gooGleMaps) {
        GooGleMaps = gooGleMaps;
    }

    public void setCompanyID(@Nullable String companyID) {
        CompanyID = companyID;
    }

    public void setCityID(@Nullable String cityID) {
        CityID = cityID;
    }

    public void setCompanyType(@Nullable String companyType) {
        CompanyType = companyType;
    }

    public void setEmail(@Nullable String email) {
        Email = email;
    }

    public void setPhone(@Nullable String phone) {
        Phone = phone;
    }

    public void setAddressLine1(@Nullable String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public void setCityName(@Nullable String cityName) {
        CityName = cityName;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    public void setLat(@Nullable String lat) {
        this.lat = lat;
    }

    public void setLog(@Nullable String log) {
        this.log = log;
    }

    public String getCompanyArabicName() {
        return CompanyArabicName;
    }

    public String getBusnisName() {
        return BusnisName;
    }

    public String getCompanyEnglishName() {
        return CompanyEnglishName;
    }

    public String getCompanyFrenshName() {
        return CompanyFrenshName;
    }

    public String getGooGleMaps() {
        return GooGleMaps;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public String getCityID() {
        return CityID;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public String getCityName() {
        return CityName;
    }

    public Float getLat() {
        return Float.parseFloat(lat);
    }

    public Float getLog() {
        return Float.parseFloat(log);
    }

    public String getImage() {
        return image;
    }

}