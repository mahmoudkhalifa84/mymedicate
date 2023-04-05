package com.medicate_int.mymedicate;


import com.google.gson.GsonBuilder;
import com.medicate_int.mymedicate.models.BookingDoctorAppointmentModel;
import com.medicate_int.mymedicate.models.BookingDoctorDataModel;
import com.medicate_int.mymedicate.models.ChatModel;
import com.medicate_int.mymedicate.models.HealthRecordsModel;
import com.medicate_int.mymedicate.models.MedicalNetworkModel;
import com.medicate_int.mymedicate.models.MyAppointmentModel;
import com.medicate_int.mymedicate.models.UserCheckBalanceModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    Retrofit retrofit;
    APIs apIs;
    public static final String BASE_URL = "https://www.medicateint.com/data2/";
    public static final String MAIN_SYSTEM_URL = "http://www.medicateint.com/data2/";
    public static final String WEBSITE_URL = "https://www.medicate.ly/";


    public ApiClient(String base_url) {
        if (base_url.isEmpty()) base_url = BASE_URL;
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .client(new OkHttpClient.Builder()
                        .callTimeout(15, TimeUnit.SECONDS)
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build())
                .build();
        apIs = retrofit.create(APIs.class);
    }

    public Call<List<BookingDoctorDataModel>> getDoctorsData() {
        return apIs.getDoctorData();

    }

    public Call<List<HealthRecordsModel>> getHelthRecordsData(String userID) {
        return apIs.getHelthRecordsData(userID);
    }

    public Call<List<UserCheckBalanceModel>> getBeneficiaryDetails(String cardNumber) {
        return apIs.getBeneficiaryDetails(cardNumber);
    }

    public Call<List<BookingDoctorAppointmentModel>> getDoctorAppointment(String docID) {
        return apIs.getDoctorAppointment(docID);
    }

    public Call<ResponseBody> makeAnAppointment(RequestBody requestBody) {
        return apIs.makeAnAppointment(requestBody);
    }

    public Call<ResponseBody> sendMessage(String msg, String id, String receiver_id, String type) {
        return apIs.sendMessage(msg, id, receiver_id, type);
    }

    public Call<List<MyAppointmentModel>> getMyAppointment(String cardnumber) {
        return apIs.getMyAppointment(cardnumber);
    }

    public Call<List<MedicalNetworkModel>> getMedicalNetwork() {
        return apIs.getMedicalNetwork();
    }


}