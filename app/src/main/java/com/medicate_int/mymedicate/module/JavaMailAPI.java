package com.medicate_int.mymedicate.module;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.models.CardRequestModel;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI extends AsyncTask<Void, Void, Void> implements Moudle.OkClicked {

    private String txt;
    private Context context;

    private Session session;
    private String email, subject,phone,pro_type;
    CardRequestModel items;
    LoadingDialog loadingDialog;
    private static final String TAG = "JavaMailAPI-TAG";
    boolean card_rec = false;



    public JavaMailAPI(Context context, CardRequestModel items, LoadingDialog loadingDialog1) {
        this.context = context;
        this.email = Statics.SEND_TO_EMAIL;
        this.subject = "طلب بطاقة MyMedicate";
        this.items = items;
        loadingDialog = loadingDialog1;
        card_rec = true;
    }
    public JavaMailAPI(Context context,String _phone , String _pt,String txt){
        card_rec = false;
        this.context = context;
        this.phone = _phone;
        this.pro_type = _pt;
        this.txt = txt;
        this.email = Statics.SEND_TO_EMAIL;
        this.subject = "التبيلغ عن مشكلة";
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (card_rec)
        loadingDialog.show();

    }

    @Override
    protected Void doInBackground(Void... voids) {
            String gender = (items.getGender().trim().equals("1")) ? "مذكر" : "أنثى";
        try {
            Object any = "";
            if (card_rec)
                any = "<!DOCTYPE html>\n" +
                    "<html charset=\"UTF-8\">\n" + "<head><meta charset=\"utf-8\"/></head>" +
                    "<body>\n" +
                    "<style>\n" +
                    "    table.GeneratedTable {\n" +
                    "      width: 100%;\n" +
                    "      background-color: #ffffff;\n" +
                    "      border-collapse: collapse;\n" +
                    "      border-width: 1px;\n" +
                    "      border-color: #ffffff;\n" +
                    "      border-style: dotted;\n" +
                    "      color: #175700;\n" +
                    "    }\n" +
                    "    \n" +
                    "    table.GeneratedTable td, table.GeneratedTable th {\n" +
                    "      border-width: 1px;\n" +
                    "      border-color: #ffffff;\n" +
                    "      border-style: solid;\n" +
                    "      padding: 8px;\n" +
                    "    }\n" +
                    "    \n" +
                    "    table.GeneratedTable thead {\n" +
                    "      background-color: #ffcc00;\n" +
                    "    }\n" +
                    "    </style>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "<table class=\"GeneratedTable\" style=\"width: 100%; float: right; direction: rtl;\" cellpadding=\"8\" >\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td>نوع الخدمة</td>\n" +
                    "        <td>طلب بطاقة MyMedicate</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>الأسم الأول</td>\n" +
                    "        <td>"+ items.getFirstName()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>أسم الأب</td>\n" +
                    "        <td>"+ items.getFatherName()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>أسم الجد</td>\n" +
                    "        <td>"+ items.getGrandFatherName()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>اللقب</td>\n" +
                    "        <td>"+ items.getFamilyName()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>الجنس</td>\n" +
                    "        <td>"+gender+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>تاريخ الميلاد</td>\n" +
                    "        <td>"+ items.getDob()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>رقم الهاتف</td>\n" +
                    "        <td>"+ items.getPhone()+"</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>المدينة</td>\n" +
                    "        <td>"+ items.getCityId() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>العنوان</td>\n" +
                    "        <td>"+ items.getAddress() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>الرقم الوطني</td>\n" +
                    "        <td>"+ items.getSocialNo() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>الحالة الاجتماعية</td>\n" +
                    "        <td>"+ items.getStatus() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"2\"style=\"text-align: center\">تم ﻹرسال هذا الطلب من خلال تطبيق ماي ميديكيت </td>\n" +
                    "    </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";
            else if (!card_rec){
                any = "<!DOCTYPE html>\n" +
                        "<html charset=\"UTF-8\">\n" + "<head><meta charset=\"utf-8\"/></head>" +
                        "<body>\n" +
                        "<style>\n" +
                        "    table.GeneratedTable {\n" +
                        "      width: 100%;\n" +
                        "      background-color: #ffffff;\n" +
                        "      border-collapse: collapse;\n" +
                        "      border-width: 1px;\n" +
                        "      border-color: #ffffff;\n" +
                        "      border-style: dotted;\n" +
                        "      color: #175700;\n" +
                        "    }\n" +
                        "    \n" +
                        "    table.GeneratedTable td, table.GeneratedTable th {\n" +
                        "      border-width: 1px;\n" +
                        "      border-color: #ffffff;\n" +
                        "      border-style: solid;\n" +
                        "      padding: 8px;\n" +
                        "    }\n" +
                        "    \n" +
                        "    table.GeneratedTable thead {\n" +
                        "      background-color: #ffcc00;\n" +
                        "    }\n" +
                        "    </style>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "<table class=\"GeneratedTable\" style=\"width: 100%; float: right; direction: rtl;\" cellpadding=\"8\" >\n" +
                        "    <tbody>\n" +
                        "    <tr>\n" +
                        "        <td>نوع الخدمة</td>\n" +
                        "        <td>التبليغ عن مشكلة</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>رقم الهاتف</td>\n" +
                        "        <td>"+ phone +"</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>نوع المشكلة</td>\n" +
                        "        <td>"+ pro_type +"</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td>المحتوى</td>\n" +
                        "        <td>"+ txt +"</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td colspan=\"2\"style=\"text-align: center\">تم ﻹرسال هذا الطلب من خلال تطبيق ماي ميديكيت </td>\n" +
                        "    </tr>\n" +
                        "    </tbody>\n" +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>";
            }
            send(any);
        } catch ( Exception e) {
            Log.d(TAG, "doInBackground: ERROR 51" + e.getMessage());
        }

        return null;

    }
    private void send(Object str){
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.setProperty("charset","utf-8");
            properties.put("mail.smtp.port", "465");
            session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Statics.EMAIL, Statics.PASSWORD);
                }
            });
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(Statics.EMAIL));
            mimeMessage.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(email)));
            mimeMessage.setSubject(subject,"UTF-8");
            mimeMessage.setContent(str,"text/html; charset=utf-8");
            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Statics.SEND_TO_EMAIL));
            // mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            Log.d(TAG, "ERROR > " + e.getMessage());
              Login.Message(context.getString(R.string.uknown_error),context);
        }
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(card_rec)
        loadingDialog.dismiss();
        if (null!= context)
        Moudle.DialogOkLesener("تم إرسال طلبك بنجاح \n  سيتم التواصل معك قريباً",context, this);
    }

    @Override
    public void isClicked() {
        if (card_rec)
        new CacheHelper(context).setMY_PLACE("المنزل");
        context.startActivity(new Intent(context, HomeActivity.class));    }
}
