package com.medicate_int.mymedicate.models;

public class InsideClimesModel {
    String clems_id;
    String Name;
    String price;

    public InsideClimesModel(String clems_id, String name, String price) {
        this.clems_id = clems_id;
        Name = name;
        this.price = price;
    }



    public String getClems_id() {
        return clems_id;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return price;
    }


}
