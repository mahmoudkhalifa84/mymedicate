package com.medicate_int.mymedicate.ui.main_screen.user_profile;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserPersonalData extends Fragment {

    View view;
    String name1= "----", name2= "----",name3= "----",name4= "----",sex= "----",dob= "----",
            phone= "----",email= "----",cityId= "----",place= "----",contry= "----",socialNo = "----";
    String dataURL,cityURL;
    TextView user_f_name , user_s_name , user_t_name , user_n_name  , user_mail ,user_SNN ,
            user_phone,gender,user_contry,user_city,user_place,user_dob;
    Dialog d;
    boolean internet;
    CacheHelper statics;
    ArrayList<BookingSpecializationModel> itemsCity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_show_personal_data, container, false);
        user_f_name = view.findViewById(R.id.u_f_name);
        user_s_name = view.findViewById(R.id.u_s_name);
        user_t_name = view.findViewById(R.id.u_t_name);
        user_n_name = view.findViewById(R.id.u_n_name);
        gender = view.findViewById(R.id.u_gender);
        user_contry = view.findViewById(R.id.u_contry);
        user_city = view.findViewById(R.id.u_city);
        user_place = view.findViewById(R.id.u_place);
        user_SNN = view.findViewById(R.id.u_nsn);
        user_mail = view.findViewById(R.id.u_email);
        user_phone = view.findViewById(R.id.u_phone);
        user_dob = view.findViewById(R.id.u_dob);
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (internet) {
            d = new Dialog(getActivity(),R.style.PauseDialog);
            d.setContentView(R.layout.loading_layout);
            d.setCancelable(true);
            d.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getActivity().finish();
                    getActivity().onBackPressed();
                }
            });
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getJSON(cityURL);
            getJSON(dataURL);
        }

    }

    @Override
    public void  onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
        dataURL = "http://www.medicateint.com/data2/getContacts/" + statics.getID();
        cityURL = "http://www.medicateint.com/data2/getCeties";
        internet = false;
        if(CheckConnection.isNetworkConnected(requireActivity())){
            internet = true;
        }
        else {
            NoInternet();
        }
    }
    private void getJSON(final String urlWebService) {
        {
            class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    d.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (urlWebService.equals(cityURL)) {
                        iniSpinnerCity(s);
                    } else {
                        try {
                            check(s);
                        } catch (JSONException e) {
                            Log.d("TAG", "onPostExecute4: " + e.getMessage());
                        }

                    }
                }

                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        URL url = new URL(urlWebService);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json).append("\n");
                        }
                        return sb.toString().trim();
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
            GetJSON_Hospital getJSON = new GetJSON_Hospital();
            getJSON.execute();
        }


    }

    private void iniSpinnerCity(String json) {
        try {
            if (!(json == null)) {
                itemsCity = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String name = obj.getString("CityName");
                    String id = obj.getString("CityID");
                    String con = obj.getString("CountryID"); //todo
                    itemsCity.add(new BookingSpecializationModel(name,id,con));
                }
            }
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    private String getCityName(String id) {
        for (int i = 0 ; i < itemsCity.size() ; i++){
            if (itemsCity.get(i).getId().equals(id))
                return itemsCity.get(i).getCity_name();
        }
        return "";
    }

    private void check(String json) throws JSONException {
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                name1 = obj.getString("FirstName");
                name2 = obj.getString("FatherName");
                name3 = obj.getString("GrandFatherName");
                name4 = obj.getString("FamilyName");
                dob = obj.getString("DateOfBirth");
                phone = obj.getString("Phone");
                email = obj.getString("Email");
                cityId = obj.getString("CityID");
               // socialNo = obj.getString("SocialNo");
            }
            setDataToTextViews();
        }
        else
        {
            Snackbar.make(view,view.getResources().getString(R.string.cheack_int_con) , Snackbar.LENGTH_SHORT ).show();
        }
    }

    private void setDataToTextViews() {
        user_f_name.setText(name1);
        user_s_name.setText(name2);
        user_t_name.setText(name3);
        user_n_name.setText(name4);
        user_dob.setText(dob);
        user_mail.setText(email);
        user_phone.setText(phone);
        user_city.setText(getCityName(cityId));
        user_SNN.setText(socialNo);
        gender.setText(sex);
        user_contry.setText(contry);
        user_place.setText(place);
        d.dismiss();
    }
    public void NoInternet(){
        Dialog dialog =new Dialog(getActivity(),R.style.PauseDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_con);
        dialog.findViewById(R.id.di_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                statics.setMY_PLACE("المعلومات الشخصية");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        dialog.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                statics.setMY_PLACE("المنزل");
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

}