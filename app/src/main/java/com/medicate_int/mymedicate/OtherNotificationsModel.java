package com.medicate_int.mymedicate;

public class OtherNotificationsModel {
    String title;
    String date;
    String state;

    public String getDate() {
        return date;
    }



    public OtherNotificationsModel(String title, String date, String state) {
        this.title = title;
        this.date = date;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }


}
