package com.medicate_int.mymedicate.models;

import com.medicate_int.mymedicate.R;

public class BookingDoctorAppointmentModel {
    private String specialization;
    private String docname;
    private String degree;
    private String gender;
    private String wait;
    private String phone;
    private String image;
    private String description;
    private String price_appointment;
    private String clinic_id;
    private String doctor_id;
    private String id;
    private String CompanyArabicName;
    private String address;
    private String dayOfWeek;
    private String start_appointment;
    private String end_appointment;


    // Getter Methods

    public String getSpecialization() {
        return specialization;
    }

    public String getDocname() {
        return docname;
    }

    public String getDegree() {
        return degree;
    }

    public String getGender() {
        return gender;
    }

    public String getWait() {
        return wait;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice_appointment() {
        return price_appointment;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getId() {
        return id;
    }

    public String getCompanyArabicName() {
        return CompanyArabicName;
    }

    public String getAddress() {
        return address;
    }

    public int getDayOfWeek() {
        switch (dayOfWeek.trim()){
            case "Mon":
                return R.string.mon;

            case "Tue":
                return R.string.tue;

            case "Wed":
                return R.string.wed;

            case "Thu":
                return R.string.thu;

            case "Fri":
                return R.string.fir;

            case "Sat":
                return R.string.sat;

            case "Sun":
                return R.string.sun;
        }
        return R.string.known;
    }
    public String getPureDayOfWeek() {
        return dayOfWeek.trim();

    }

    public String getStart_appointment() {
        return start_appointment.substring(0,5);
    }

    public String getEnd_appointment() {
        return end_appointment.substring(0,5);
    }
}
