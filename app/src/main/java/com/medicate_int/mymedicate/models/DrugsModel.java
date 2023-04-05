package com.medicate_int.mymedicate.models;

public class DrugsModel {
    String Name ,
            NumDay // عدد الايام
        // كل يوم كل يومين
            , inDay
        // عدد الجرعة
            , NumInDay ;
    boolean isSh =  false;

    public void setSh(boolean sh) {
        isSh = sh;
    }

    public String getName() {
        return Name;
    }

    public String getNumDay() {
        return NumDay;
    }

    public String getInDay() {
        return inDay;
    }

    public String getNumInDay() {
        return NumInDay;
    }

    public DrugsModel(String name, String numDay, String inDay, String numInDay) {
        Name = name;
        NumDay = numDay;
        this.inDay = inDay;
        NumInDay = numInDay;
    }

    public boolean isSh() {
        return isSh;
    }
}
