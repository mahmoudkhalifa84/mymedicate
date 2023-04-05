package com.medicate_int.mymedicate.models;

public class MakeAppointmentSendModel {
    private String namePatient;
    private String phonePatient;
    private String emaiPatient;
    private int IdDoctor;
    private int IdClinic;
    private String date;
    private String day;
    private String timeStart;
    private String timeEnd;
    private int state;
    private int App_id;
    private String dateTime;
    private String cardnum;
    private int cast;

    public String getNamePatient() {
        return namePatient;
    }

    public String getPhonePatient() {
        return phonePatient;
    }

    public String getEmaiPatient() {
        return emaiPatient;
    }

    public int getIdDoctor() {
        return IdDoctor;
    }

    public int getIdClinic() {
        return IdClinic;
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

    public int getState() {
        return state;
    }

    public int getApp_id() {
        return App_id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getCardnum() {
        return cardnum;
    }

    public int getCast() {
        return cast;
    }

    // Setter Methods 

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient = phonePatient;
    }

    public void setEmaiPatient(String emaiPatient) {
        this.emaiPatient = emaiPatient;
    }

    public void setIdDoctor(int IdDoctor) {
        this.IdDoctor = IdDoctor;
    }

    public void setIdClinic(int IdClinic) {
        this.IdClinic = IdClinic;
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

    public void setState(int state) {
        this.state = state;
    }

    public void setApp_id(int App_id) {
        this.App_id = App_id;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public void setCast(int cast) {
        this.cast = cast;
    }
}
