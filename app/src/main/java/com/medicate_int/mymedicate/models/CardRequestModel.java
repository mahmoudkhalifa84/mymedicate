package com.medicate_int.mymedicate.models;

public class CardRequestModel {
    String firstName,
            FatherName,
            GrandFatherName,
            FamilyName,
            gender,
            dob,
            phone,
            email,
            cityId,
            Address,
            socialNo,
            status;

    public CardRequestModel() {
        email = "غير متوفر";
        Address = "غير متوفر";
        socialNo = "غير متوفر";
        firstName = "غير متوفر";
        FatherName = "غير متوفر";
        GrandFatherName = "غير متوفر";
        FamilyName = "غير متوفر";
        gender = "غير متوفر";
        dob = "غير متوفر";
        phone = "غير متوفر";
        status = "غير متوفر";
        Address = "غير متوفر";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getGrandFatherName() {
        return GrandFatherName;
    }

    public void setGrandFatherName(String grandFatherName) {
        GrandFatherName = grandFatherName;
    }

    public String getFamilyName() {
        return FamilyName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSocialNo() {
        return socialNo;
    }

    public void setSocialNo(String socialNo) {
        this.socialNo = socialNo;
    }
}
