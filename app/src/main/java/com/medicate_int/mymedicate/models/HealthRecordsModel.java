package com.medicate_int.mymedicate.models;

public class HealthRecordsModel {
    private String ServiceDate;
    private String ServiceTime;
    private String StatusID;
    private String TextValue = null;
    private String NumberValue = null;
    private String ImageValue0 = null;
    private String ImageValue1 = null;
    private String ImageValue2 = null;
    private String ImageValue3 = null;
    private String ImageValue4 = null;
    private String ServiceEnglishName;
    private String ServiceArabicName;
    private String ServicePrice;
    private String ComPrice;
    private String CustPrice;


    private String ParentID;


    // Getter Methods

    public String getServiceDate() {
        return ServiceDate;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public String getStatusID() {
        return StatusID;
    }

    public String getTextValue() {
        return TextValue;
    }

    public String getNumberValue() {
        return NumberValue;
    }

    public String getImageValue0() {
        return ImageValue0;
    }

    public String getImageValue1() {
        return ImageValue1;
    }

    public String getImageValue2() {
        return ImageValue2;
    }

    public String getImageValue3() {
        return ImageValue3;
    }

    public String getImageValue4() {
        return ImageValue4;
    }

    public String getServiceEnglishName() {
        return ServiceEnglishName;
    }

    public String getServiceArabicName() {
        return ServiceArabicName;
    }

    public String getServicePrice() {
        return ServicePrice;
    }

    public String getParentID() {
        return ParentID;
    }

    // Setter Methods

    public void setServiceDate(String ServiceDate) {
        this.ServiceDate = ServiceDate;
    }

    public void setServiceTime(String ServiceTime) {
        this.ServiceTime = ServiceTime;
    }

    public void setStatusID(String StatusID) {
        this.StatusID = StatusID;
    }

    public void setTextValue(String TextValue) {
        this.TextValue = TextValue;
    }

    public void setNumberValue(String NumberValue) {
        this.NumberValue = NumberValue;
    }

    public void setImageValue0(String ImageValue0) {
        this.ImageValue0 = ImageValue0;
    }

    public void setImageValue1(String ImageValue1) {
        this.ImageValue1 = ImageValue1;
    }

    public void setImageValue2(String ImageValue2) {
        this.ImageValue2 = ImageValue2;
    }

    public void setImageValue3(String ImageValue3) {
        this.ImageValue3 = ImageValue3;
    }

    public void setImageValue4(String ImageValue4) {
        this.ImageValue4 = ImageValue4;
    }

    public void setServiceEnglishName(String ServiceEnglishName) {
        this.ServiceEnglishName = ServiceEnglishName;
    }

    public void setServiceArabicName(String ServiceArabicName) {
        this.ServiceArabicName = ServiceArabicName;
    }

    public void setServicePrice(String ServiceArabicName) {
        this.ServicePrice = ServicePrice;
    }
    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
    }

    public String getCustPrice() {
        return CustPrice;
    }

    public void setCustPrice(String custPrice) {
        CustPrice = custPrice;
    }

    public String getComPrice() {
        return ComPrice;
    }

    public void setComPrice(String comPrice) {
        ComPrice = comPrice;
    }
}
