package com.medicate_int.mymedicate;

import com.medicate_int.mymedicate.models.BookingDoctorAppointmentModel;
import com.medicate_int.mymedicate.models.BookingDoctorDataModel;
import com.medicate_int.mymedicate.models.ChatModel;
import com.medicate_int.mymedicate.models.HealthRecordsModel;
import com.medicate_int.mymedicate.models.MedicalNetworkModel;
import com.medicate_int.mymedicate.models.MyAppointmentModel;
import com.medicate_int.mymedicate.models.UserCheckBalanceModel;
import com.medicate_int.mymedicate.module.Endpoint;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIs {

    @GET(Endpoint.DoctorAppointment + "/{docID}")
    public Call<List<BookingDoctorAppointmentModel>> getDoctorAppointment(@Path("docID") String doctorID);

    @GET(Endpoint.DoctorData)
    public Call<List<BookingDoctorDataModel>> getDoctorData();

    @GET(Endpoint.HelthRecords + "/{userID}")
    public Call<List<HealthRecordsModel>> getHelthRecordsData(@Path("userID") String userID);

    @Headers("Accept:application/json")
    @POST(Endpoint.MAKE_APPOINTMENT)
    public Call<ResponseBody> makeAnAppointment(@Body RequestBody requestBody);

    @GET(Endpoint.USER_ChackBalance + "/{cardnumber}")
    public Call<List<UserCheckBalanceModel>> getBeneficiaryDetails(@Path("cardnumber") String cardNumber);

    @POST(Endpoint.GET_USER_APPOINTMENT)
    public Call<List<MyAppointmentModel>> getMyAppointment(@Part("card") String cardNumber);

    @POST(Endpoint.sendMessage)
    public Call<ResponseBody> sendMessage(@Query("message") String msg, @Query("sender_id") String id , @Query("receiver_id") String receiver_id, @Query("receiver_type") String type);


    @POST(Endpoint.GET_MEDICAL_NETWORK)
    public Call<List<MedicalNetworkModel>> getMedicalNetwork();


}
