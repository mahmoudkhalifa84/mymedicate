package com.medicate_int.mymedicate.models;

public class CountriesModel {
    String countryName ;
    String countryId;
    CountriesModel(String countryName, String countryId)
    {
        this.countryName = countryName;
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
