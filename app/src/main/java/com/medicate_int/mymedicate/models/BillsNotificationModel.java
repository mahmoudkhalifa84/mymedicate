package com.medicate_int.mymedicate.models;

public class BillsNotificationModel {
    String ArabicName;
    String EnglishName;
    String FrancsName;
    String ProductionCode;
    String ClaimID;
    String ClinicClaimAmount;
    String ClaimDate;
    String State;
    String Type;
    boolean news = false;

    public String getArabicName() {

        return ArabicName;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getFrancsName() {
        return FrancsName;
    }

    public String getProductionCode() {
        return ProductionCode;
    }

    public String getClaimID() {
        return ClaimID;
    }

    public String getClinicClaimAmount() {
        return ClinicClaimAmount;
    }

    public String getClaimDate() {
        return ClaimDate;
    }

    public String getState() {
        return State;
    }

    public String getType() {
        return Type;
    }

    public BillsNotificationModel(String arabicName, String englishName, String francsName, String productionCode, String claimID, String clinicClaimAmount, String claimDate, String state, String type) {
        ArabicName = arabicName;
        EnglishName = englishName;
        FrancsName = francsName;
        ProductionCode = productionCode;
        ClaimID = claimID;
        ClinicClaimAmount = clinicClaimAmount;
        ClaimDate = claimDate;
        State = state;
        Type = type;
    }
}
