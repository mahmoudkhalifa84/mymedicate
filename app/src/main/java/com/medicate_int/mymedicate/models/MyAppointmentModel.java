package com.medicate_int.mymedicate.models;

public class MyAppointmentModel {
    private String docname;
    private String CompanyArabicName;
    private String date;
    private String day;
    private String timeStart;
    private String timeEnd;
    private String dateTime;


    // Getter Methods

    public String getDocname() {
        return docname;
    }

    public String getCompanyArabicName() {
        return CompanyArabicName;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getDateTime() {
        return dateTime;
    }

    // Setter Methods

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public void setCompanyArabicName(String CompanyArabicName) {
        this.CompanyArabicName = CompanyArabicName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
