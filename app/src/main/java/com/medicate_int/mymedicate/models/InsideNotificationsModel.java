package com.medicate_int.mymedicate.models;

public class InsideNotificationsModel {
    String
            climeId,
            ServicesName ,
            ClinicServiceAmount ,
            PercentageComp ,
            PercentageClinic;

    public String getClimeId() {
        return climeId;
    }

    public String getServicesName() {
        return ServicesName;
    }

    public String getClinicServiceAmount() {
        return ClinicServiceAmount;
    }

    public String getPercentageComp() {
        return PercentageComp;
    }

    public String getPercentageClinic() {
        return PercentageClinic;
    }

    public InsideNotificationsModel(String claimid, String servicesName, String clinicServiceAmount, String percentageComp, String percentageClinic) {
        this.climeId = claimid;
        ServicesName = servicesName;
        ClinicServiceAmount = clinicServiceAmount;
        PercentageComp = percentageComp;
        PercentageClinic = percentageClinic;
    }
}
