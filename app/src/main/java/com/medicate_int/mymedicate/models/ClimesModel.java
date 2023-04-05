package com.medicate_int.mymedicate.models;

public class ClimesModel {
    String ArabicName;
    String EnglishName;
    String FrancsName;
    String ClaimID;
    String ClaimAmount;
    String date;

    public String getDate() {
        return date;
    }

    public String getArabicName() {
        return ArabicName;
    }

    public ClimesModel(String arabicName, String englishName, String francsName, String claimID, String claimAmount, String date) {
        ArabicName = arabicName;
        EnglishName = englishName;
        FrancsName = francsName;
        ClaimID = claimID;
        ClaimAmount = claimAmount;
        this.date = date;
    }

    public String getClaimID() {
        return ClaimID;
    }

    public String getClaimAmount() {
        return ClaimAmount;
    }
}
