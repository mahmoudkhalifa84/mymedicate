package com.medicate_int.mymedicate.models;

public class ChatModel {

     String msg  , msg_date ,
            send_by ,
            type,
            file;
        int status ;



    public ChatModel(String msg, String msg_date, String type, String send_by, String file, int status) {
        this.msg = msg;
        this.msg_date = msg_date;
        this.send_by = send_by;
        this.type = type;
        this.file = file;
        this.status = status;
    }

    public ChatModel() {
    }


    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getMsg_date() {
        return msg_date;
    }
    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    public String getSend_by() {
        return send_by;
    }
    public void setSend_by(String send_by) {
        this.send_by = send_by;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {this.status = status;}




}
