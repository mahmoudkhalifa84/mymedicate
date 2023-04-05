package com.medicate_int.mymedicate.ui.main_screen.booking.booking_doctor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.adapter.BookingShowDaiesAdapter;
import com.medicate_int.mymedicate.ui.Login;
import com.medicate_int.mymedicate.models.BookingDoctorAppointmentModel;
import com.medicate_int.mymedicate.models.UserCheckBalanceModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDoctorAppointments extends AppCompatActivity implements BookingShowDaiesAdapter.MyHandler {
    View addingLayout;
    Context context;
    LoadingDialog loadingDialog;
    private static final String TAG = "BookingAppNowTAG";
    Call<List<BookingDoctorAppointmentModel>> call;
    List<BookingDoctorAppointmentModel> list;
    String selectedDoctor = null;
    CacheHelper statics;

    boolean canLoad = true;
    ArrayList<String> arrayList = new ArrayList<>();
    List<List<BookingDoctorAppointmentModel>> finalList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appoentment_now);
        context = this;
        loadingDialog = new LoadingDialog(context);
        addingLayout = findViewById(R.id.lay_123456);
        statics = new CacheHelper(context);
        getSupportActionBar().hide();
        list = new ArrayList<>();
        selectedDoctor = getIntent().getStringExtra("selectedDoc");
        findViewById(R.id.linearLayout27).setOnClickListener((q) -> finish());
        Log.d(TAG, "onCreate: Selected Doctor ID -> " + selectedDoctor);
        if (selectedDoctor == null || selectedDoctor.isEmpty() || selectedDoctor.equals("null"))
            Moudle.DialogOkLesener(getString(R.string.uknown_error), context, this::finish);
        else {
            Picasso.with(context).load(getIntent().getStringExtra("docImage")).into((CircleImageView) findViewById(R.id.bok_now_img));
            ((TextView) findViewById(R.id.bok_now_doc_name)).setText(getIntent().getStringExtra("docName"));
            ((TextView) findViewById(R.id.bok_now_sp)).setText(getIntent().getStringExtra("docSp"));
            ((TextView) findViewById(R.id.bok_now_degree)).setText("- " + getIntent().getStringExtra("dg"));
            ((TextView) findViewById(R.id.bok_now_phone)).setText(getIntent().getStringExtra("phone"));
            call = new ApiClient(ApiClient.BASE_URL).getDoctorAppointment(getIntent().getStringExtra("selectedDoc").trim());
            loadingDialog.show();
            call.enqueue(new Callback<List<BookingDoctorAppointmentModel>>() {
                @Override
                public void onResponse(Call<List<BookingDoctorAppointmentModel>> call, Response<List<BookingDoctorAppointmentModel>> response) {
                    loadingDialog.dismiss();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().size() > 0) {
                            list = response.body();
                            //  addRecycleView();
                            spiltClinics(0);
                        } else {
                            Moudle.DialogOkLesener(getString(R.string.no_data), context, () -> finish());
                        }
                    } else
                        Moudle.DialogOkLesener(getString(R.string.cheack_int_con), context, () -> finish());
                }

                @Override
                public void onFailure(Call<List<BookingDoctorAppointmentModel>> call, Throwable t) {
                    loadingDialog.dismiss();

                    Log.d(TAG, "onFailure: > " + t.getMessage());
                    Moudle.DialogOkLesener(getString(R.string.cheack_int_con), context, () -> finish());
                }
            });
        }

    }


    private void spiltClinics(int index) {

        int count = howMany(list);
        List<BookingDoctorAppointmentModel> mylist = new ArrayList<>();
        List<BookingDoctorAppointmentModel> mylist2 = new ArrayList<>();
        Log.d(TAG, "spiltClinics: COUNT > " + count);
        //    Log.d(TAG, "spiltClinics: DATA > " + new Gson().toJson(arrayList));
        for (BookingDoctorAppointmentModel model : list
        ) {
            Log.d(TAG, model.getCompanyArabicName().trim() + " - {{{" + arrayList.get(index).trim() + "}}}");
            if (model.getCompanyArabicName().trim().equals(arrayList.get(index).trim())) {
                mylist.add(model);
                Log.d(TAG, "spiltClinics: ADDED > " + model.getCompanyArabicName());
            }
        }
        //   Log.d(TAG, "spiltClinics: END > " + new Gson().toJson(mylist));
        mylist2 = mylist;
        addRecycleView(mylist2);
          /*  try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
           /* finalList.add(mylist);
            Log.d(TAG, "spiltClinics: ADDED TO FINAL > " + finalList.get(finalList.size() - 1).size());*/

  /*      addToViews();
        Log.d(TAG, "spiltClinics: END > " + new Gson().toJson(finalList));*/

    }
/*

    private void spiltClinics(List<BookingDoctorAppointmentModel> allData) {
        int count = howMany(allData);
        List<BookingDoctorAppointmentModel> mylist = new ArrayList<>();
        List<BookingDoctorAppointmentModel> mylist2 = new ArrayList<>();
        Log.d(TAG, "spiltClinics: COUNT > " + count);
        Log.d(TAG, "spiltClinics: DATA > " + new Gson().toJson(arrayList));
        for (int i = 0; i < arrayList.size(); i++) {
            mylist.clear();
            for (BookingDoctorAppointmentModel model : allData
            ) {
                Log.d(TAG, model.getCompanyArabicName().trim() + " - {{{" + arrayList.get(i).trim() + "}}}");
                if (model.getCompanyArabicName().trim().equals(arrayList.get(i).trim())) {
                    mylist.add(model);
                    Log.d(TAG, "spiltClinics: ADDED > " + model.getCompanyArabicName());
                }
            }
            Log.d(TAG, "spiltClinics: END > " + new Gson().toJson(mylist));
            mylist2 = mylist;
            addRecycleView(mylist2);
          */
/*  try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*//*

     */
/* finalList.add(mylist);
            Log.d(TAG, "spiltClinics: ADDED TO FINAL > " + finalList.get(finalList.size() - 1).size());*//*

        }
  */
/*      addToViews();
        Log.d(TAG, "spiltClinics: END > " + new Gson().toJson(finalList));*//*

    }
*/

    private void addToViews() {
        for (int i = 0; i < finalList.size(); i++) {
            addRecycleView(finalList.get(i));
        }
    }

    private int howMany(List<BookingDoctorAppointmentModel> mlist) {
        for (int i = 0; i < mlist.size(); i++) {
            if (!isFound(mlist.get(i).getCompanyArabicName().trim())) {
                arrayList.add(mlist.get(i).getCompanyArabicName().trim());
            }
        }
        return arrayList.size();
    }

    private boolean isFound(String name) {
        for (String str : arrayList) {
            if (str.trim().equals(name)) return true;
        }
        return false;
    }
//    private void backgroundWork(final List<BookingDoctorAppointmentModel> listt) {
//        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                Log.d(TAG, "onPostExecute: ");
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                   addRecycleView(listt);
//                    return null;
//                } catch (Exception e) {
//                    Log.d(TAG, "ERROR 1235 " + e.getMessage() + "-" + e.getLocalizedMessage());
//                    return null;
//                }
//            }
//        }
//        GetJSON_Hospital getJSON = new GetJSON_Hospital();
//        getJSON.execute();
//    }


    private View addView(String name) {
        TextView textView = new TextView(context);
        textView.setText(name);
        textView.setPadding(16, 24, 16, 8);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.main));
        //  textView.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.but_s,getTheme()));
        textView.setTextSize(18);
        textView.setTag(name);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setOnClickListener((a) -> Log.d(TAG, "CLICKED >> " + textView.getTag()));
        return textView;
    }

    private void addRecycleView(List<BookingDoctorAppointmentModel> bookingDoctorAppointmentModels) {
        Log.d(TAG, "addRecycleView: ----------------------------------");
        RecyclerView recyclerView = new RecyclerView(context);
        BookingShowDaiesAdapter adapter = new BookingShowDaiesAdapter(context, bookingDoctorAppointmentModels, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setPadding(16, 16, 16, 8);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        //   Log.d("BookingShowDaiesAdapter", "addRecycleView: SETADAPTER");
        recyclerView.setAdapter(adapter);
        ((LinearLayout) addingLayout).addView(addView(bookingDoctorAppointmentModels.get(0).getCompanyArabicName()));
        ((LinearLayout) addingLayout).addView(recyclerView);
        recyclerView.removeAllViews();
        /*recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (adapter.getItemCount() > 0)
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });*/
    }

    @Override
    public void onCLickBook(int p, BookingDoctorAppointmentModel model) {
        Log.d(TAG, "onCLickBook: >> " + p);
        bookNow(model);


    }

    private void bookNow(BookingDoctorAppointmentModel model) {
        hasEnoughMony(Double.parseDouble(model.getPrice_appointment()), model);

    }

    private void hasEnoughMony(double v, BookingDoctorAppointmentModel model) {
        double[] resualt = {v, 0.0};
        try {
            loadingDialog.show();
            new ApiClient(ApiClient.MAIN_SYSTEM_URL).getBeneficiaryDetails(statics.getCardNumber()).enqueue(new Callback<List<UserCheckBalanceModel>>() {
                @Override
                public void onResponse(Call<List<UserCheckBalanceModel>> call, Response<List<UserCheckBalanceModel>> response) {
                    loadingDialog.dismiss();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: >> " + response.body().get(0).getAmount());
                        resualt[1] = response.body().get(0).getAmount();
                        finalBook(resualt, model);

                    }
                }

                @Override
                public void onFailure(Call<List<UserCheckBalanceModel>> call, Throwable t) {
                    loadingDialog.dismiss();
                    Log.d(TAG, "onFailure: >> " + t.getMessage());
                }
            });

        } catch (IllegalStateException e) {
            loadingDialog.dismiss();
            Log.d(TAG, "onCreate: ERROR CATCH");
        }
    }

    private void finalBook(double[] res, BookingDoctorAppointmentModel model) {
        Log.d(TAG, "finalBook: ");
        Log.d(TAG, "bookNow: > " + res[0] + " | " + res[1]);
        Moudle.YesCancelDailog(context, getString(R.string.saytm_khasm_alkema) + " [ " + model.getPrice_appointment() + " د.ل ]", new Moudle.OkCancelInterface() {
            @Override
            public void OK() {
                if (res[0] <= res[1]) {
                    Log.d(TAG, "OK: CLICKED");
                    String date = Moudle.getDateFromDay(model.getPureDayOfWeek());
               /* MakeAppointmentSendModel appointmentSendModel = new MakeAppointmentSendModel();
                try {
                    appointmentSendModel.setNamePatient(statics.getUserName());
                    appointmentSendModel.setPhonePatient(statics.getUserPhoneNumber());
                    appointmentSendModel.setEmaiPatient(statics.getUserEmail());
                    appointmentSendModel.setIdDoctor(Integer.parseInt(model.getDoctor_id()));
                    appointmentSendModel.setIdClinic(Integer.parseInt(model.getClinic_id()));
                    appointmentSendModel.setDate(date);
                    appointmentSendModel.setDay(Moudle.getCurrentDay());
                    appointmentSendModel.setCardnum(statics.getCardNumber());
                    appointmentSendModel.setCast(Integer.parseInt(model.getPrice_appointment()));
                    appointmentSendModel.setApp_id(Integer.parseInt(model.getId()));
                    appointmentSendModel.setTimeStart(model.getStart_appointment());
                    appointmentSendModel.setTimeEnd(model.getEnd_appointment());
                   // appointmentSendModel.setState(0);
                    appointmentSendModel.setDateTime(Moudle.getDate() + " " + Moudle.getTime24());
                } catch (Exception e) {
                    Log.d(TAG, "OK: ERROR !@#12 >>> " + e.getMessage());
                    e.printStackTrace();
                }*/
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("namePatient", statics.getUserName())
                            .addFormDataPart("phonePatient", statics.getUserPhoneNumber())
                            .addFormDataPart("emaiPatient", statics.getUserEmail())
                            .addFormDataPart("IdDoctor", model.getDoctor_id())
                            .addFormDataPart("IdClinic", model.getClinic_id())
                            .addFormDataPart("date", date)
                            .addFormDataPart("day", Moudle.getCurrentDay())
                            .addFormDataPart("timeStart", model.getStart_appointment())
                            .addFormDataPart("timeEnd", model.getEnd_appointment())
                            .addFormDataPart("App_id", model.getId())
                            .addFormDataPart("dateTime", Moudle.getDate() + " " + Moudle.getTime24())
                            .addFormDataPart("cardnum", statics.getCardNumber())
                            .addFormDataPart("cast", model.getPrice_appointment());
                    RequestBody requestBody = builder.build();
                    loadingDialog.show();
                    Call<ResponseBody> call = new ApiClient(ApiClient.BASE_URL).makeAnAppointment(requestBody);
                    call.clone().enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            loadingDialog.dismiss();
                            if (response.body() == null)
                                Log.d(TAG, "onResponse: NULL RESPONSE  > ");
                            else {
                                try {
                                    Log.d(TAG, "onResponse:  message  > " + response.body().string());
                                    Log.d(TAG, "onResponse:  toString  > " + response.body().toString());

                                    Log.d(TAG, "onResponse:  string  > " + response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (response.isSuccessful()) {
                                Login.Message(getString(R.string.make_appointment_success) + " : " + date, context);

                                // todo : insert into Database
                            } else {
                                Log.d(TAG, "onResponse: ERROR > " + response.message());
                                Log.d(TAG, "onResponse: ERROR > " + response.code());
                                assert response.errorBody() != null;
                                try {
                                    Log.d(TAG, "onResponse: ERROR > " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Login.Message(getString(R.string.erroe_in_serv), context);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            loadingDialog.dismiss();
                            Log.d(TAG, "onFailure: >> " + t.getMessage());
                            Login.Message(getString(R.string.cheack_int_con), context);
                        }
                    });
                } else {
                    Login.Message(getString(R.string.not_enouht_mony) + " " + res[1] + " د.ل", context);
                }

            }

            @Override
            public void Cancel() {

            }
        });
    }

    @Override
    public void isLoaded(int p) {
        Log.d(TAG, "isLoaded: > " + p);
        if ((p) < arrayList.size()) {
            spiltClinics(p);
        }
    }
}