package com.medicate_int.mymedicate.models;

public class MedicalFilesModel {
    String date,img,title,body,doc_type,key;
    boolean deleted;

    public MedicalFilesModel(String date, String img, String title, String body, String doc_type,boolean deleted) {
        this.date = date;
        this.img = img;
        this.title = title;
        this.body = body;
        this.deleted = deleted;
        this.doc_type = doc_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }
}
