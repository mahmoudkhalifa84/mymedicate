package com.medicate_int.mymedicate.models;

import android.content.Context;

import com.medicate_int.mymedicate.R;

public class BookingDoctorDataModel {
    String id,specialization,docname,degree,gender,wait,phone,filename,description;

    public String getDoctorID() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDoctorName() {
        return docname;
    }

    public String getDegree(Context context) {
        switch (degree.trim().toUpperCase()){
            case "10" : return context.getString(R.string.doctor_md);
            case "11" : return context.getString(R.string.doctor_bsc);
            case "12" : return context.getString(R.string.doctor_msc);
            case "13" : return context.getString(R.string.doctor_psyd);
            case "14" : return context.getString(R.string.doctor_do);
            case "15" :
            case "16" : return context.getString(R.string.doctor_mbbs);
            case "17" : return context.getString(R.string.doctor_dmd);
            case "18" : return context.getString(R.string.doctor_dds);
            case "21" : return context.getString(R.string.pharmd);
        }
        return degree.trim();
    }

    public int getGender() {
        return gender.toLowerCase().equals("m") ? R.string.men : R.string.women;
    }

    public String getWaitTime() {
        return wait;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public String getImage() {
        return filename;
    }

    public String getAbout() {
        return description;
    }
}
