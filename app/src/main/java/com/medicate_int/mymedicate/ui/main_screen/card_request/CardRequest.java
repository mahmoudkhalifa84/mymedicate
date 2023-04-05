package com.medicate_int.mymedicate.ui.main_screen.card_request;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.module.SendCardRequest;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.JavaMailAPI;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.database.CitysDatabase;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.models.CardRequestModel;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CardRequest extends Fragment implements BookingCityItemAdabter.onCLickLis2, SendCardRequest.Successful {
    View v;
    String s_name1, s_name2, s_name3,
            s_name4, s_sex, s_dob, s_phone,
            s_email, s_cityId, s_country,
            s_address, s_socialNo, s_year, mounth, day;


    boolean internet,
            checker;

    TextInputEditText user_f_name, user_s_name, user_t_name, user_n_name,
            user_year, user_mail, sGender, user_social_state,
            user_SNN, user_phone, county, city, erya, moth, da;

    Spinner spinMounth, spinDay;
    int swit; // 1 > city ---- 2 > country
    Button try_agin;
    private ArrayList<BookingSpecializationModel> itemsCity;
    private ArrayList<BookingSpecializationModel> itemsCountry;
    private String cityURL, countryURL;
    private ScrollView minLay;
    boolean first_time1 = true, first_time2 = true;
    private LinearLayout no_internet;
    private BookingCityItemAdabter cityAdapter;
    private BookingCityItemAdabter countryAdapter;
    private String selectedCityId, selectedCountryId;
    CacheHelper statics;
    LoadingDialog dailog;
    CitysDatabase citysDatabase;
    Dialog select_city_dig, select_gender, select_county, select_social_state;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_register_card, container, false);
        v.findViewById(R.id.imageView64).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        if (internet) {
            user_f_name = v.findViewById(R.id.txt_f_name);
            user_s_name = v.findViewById(R.id.txt_s_name);
            user_t_name = v.findViewById(R.id.txt_t_name);
            user_n_name = v.findViewById(R.id.txt_n_name);
            user_social_state = v.findViewById(R.id.txt_status);
            user_social_state.setFocusable(false);
            user_social_state.setFocusableInTouchMode(false);
            user_year = v.findViewById(R.id.txt_year);
            user_phone = v.findViewById(R.id.txt_phone_num);
            user_SNN = v.findViewById(R.id.txt_ss);
            user_mail = v.findViewById(R.id.txt_mail);
            city = v.findViewById(R.id.txt_city);
            city.setFocusable(false);
            city.setFocusableInTouchMode(false);
            county = v.findViewById(R.id.txt_country);
            county.setFocusable(false);
            county.setFocusableInTouchMode(false);
            county.setVisibility(View.GONE);
            moth = v.findViewById(R.id.txt_moth);
            erya = v.findViewById(R.id.txt_arya);
            da = v.findViewById(R.id.txt_day);
            checker = false;
            selectedCityId = "all";
            sGender = v.findViewById(R.id.txt_gender);
            sGender.setFocusable(false);
            sGender.setFocusableInTouchMode(false);
            sGender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectGender();
                }
            });
            selectedCountryId = "all";
            cityURL = "http://www.medicateint.com/data2/getCeties";
            countryURL = "http://www.medicateint.com/data2/getCountry";
            dailog.show();
            if (citysDatabase.getData().getCount() > 0)
                getCityOffine();
            else
                getJSON(cityURL);
            //    getJSON(countryURL);
            county.setText(getCountyName());
            user_social_state.setOnClickListener((v) -> SelectSocialState());
            city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swit = 1;
                    SelectCity();
                }
            });
            county.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swit = 2;
                    Snackbar.make(v, R.string.go_change_country, Snackbar.LENGTH_SHORT).setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                }
            });


            TextView buNext = v.findViewById(R.id.next_info);
            //   Button buCancel = v.findViewById(R.id.button2);
            buNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckConnection.isNetworkConnected(getActivity())) {
                        checker = false;
                        getData();
                        if (checker) {
                            try {
                                CardRequestModel CardRequestModel = new CardRequestModel();
                                CardRequestModel.setFirstName(s_name1);
                                CardRequestModel.setFatherName(s_name2);
                                CardRequestModel.setGrandFatherName(s_name3);
                                CardRequestModel.setFamilyName(s_name4);
                                CardRequestModel.setGender(s_sex);
                                CardRequestModel.setEmail(s_email);
                                CardRequestModel.setPhone(s_phone);
                                CardRequestModel.setCityId(s_cityId);
                                CardRequestModel.setAddress(s_address);
                                CardRequestModel.setDob(s_dob);
                                CardRequestModel.setSocialNo(s_socialNo);
                                CardRequestModel.setStatus(user_social_state.getText().toString().trim());

                                new SendCardRequest(getActivity()).execute(s_name1.trim()
                                        , s_name2.trim(), s_name3.trim(), s_name4.trim(),
                                        s_sex.trim(), s_dob.trim(),
                                        s_phone.trim(), s_email.trim(),
                                        s_cityId.trim(), s_address.trim()
                                        , s_socialNo.trim());

                                new JavaMailAPI(getActivity(), CardRequestModel, dailog).execute();
                            } catch (Exception e) {
                                Log.d("CARDREQUST", "SEND ERROR: " + e.getMessage());
                                e.printStackTrace();
                            }
                            //name1, name2, name3, name4, sex, dob, phone, email, cityId, address, idEmp, socialNo
                            //  Rejster();
                        }

                    } else {
                        Login.Message(getString(R.string.cheack_int_con), getActivity());
                    }
                }


            });

        }
        return v;
    }

    public String getCountyName() {
        switch (statics.getMY_CONTRY()) {
            case "1":
                return getResources().getString(R.string.county_libya);

            case "4":
                return getResources().getString(R.string.egy);

            case "2":
                return getResources().getString(R.string.county_tunis);

            case "3":
                return getResources().getString(R.string.county_aljaz);

            case "7":
                return getResources().getString(R.string.county_espain);

            case "6":
                return getResources().getString(R.string.county_gaemny);

            case "17":
                return getResources().getString(R.string.county_italya);

            case "9":
                return getResources().getString(R.string.county_ukranya);

            case "5":
                return getResources().getString(R.string.county_turkia);

        }
        return getString(R.string.known);
    }

    public void SelectCity() {
        select_city_dig = new BottomSheetDialog(getActivity());
        select_city_dig.setContentView(R.layout.bottom_select_city_home);
        RecyclerView recyclerView = select_city_dig.findViewById(R.id.booking_list_city);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cityAdapter);
        EditText editText = select_city_dig.findViewById(R.id.searchViewCity);
        select_city_dig.setCancelable(true);
        select_city_dig.getWindow().setLayout(v.getWidth(), v.getHeight());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        select_city_dig.show();
    }

    public void SelectCounty() {
        select_county = new BottomSheetDialog(getActivity());
        select_county.setContentView(R.layout.bottom_select_city_home);
        RecyclerView recyclerView = select_county.findViewById(R.id.booking_list_city);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(countryAdapter);
        LinearLayout editText = select_county.findViewById(R.id.linearLayout2);
        editText.setVisibility(View.GONE);
        select_county.setCancelable(true);
        select_county.show();
    }

    public void SelectGender() {
        select_gender = new Dialog(getActivity(), R.style.PauseDialog);
        select_gender.setContentView(R.layout.select_gender);
        TextView man = select_gender.findViewById(R.id.sex_man);
        select_gender.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView woman = select_gender.findViewById(R.id.sex_woman);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sGender.setText(R.string.men);
                select_gender.dismiss();
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sGender.setText(R.string.women);
                select_gender.dismiss();
            }
        });
        select_gender.setCancelable(true);
        select_gender.show();
    }

    public void SelectSocialState() {
        select_social_state = new Dialog(getActivity(), R.style.PauseDialog);
        select_social_state.setContentView(R.layout.select_soical_state);
        TextView single = select_social_state.findViewById(R.id.social_state_single);
        TextView marid = select_social_state.findViewById(R.id.social_state_marid);
        TextView armal = select_social_state.findViewById(R.id.social_state_armal);
        if (sGender.getText().toString().equals(getString(R.string.women))) {
            single.setText(R.string.social_state_single_weman);
            marid.setText(R.string.social_state_marid_weman);
            armal.setText(R.string.social_state_armal_weman);
        }
        select_social_state.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_social_state.setText(((TextView) v).getText());
                select_social_state.dismiss();
            }
        });
        marid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_social_state.setText(((TextView) v).getText());
                select_social_state.dismiss();
            }
        });
        armal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_social_state.setText(((TextView) v).getText());
                select_social_state.dismiss();
            }
        });
        select_social_state.setCancelable(true);
        select_social_state.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsCity = new ArrayList<>();
        dailog = new LoadingDialog(getActivity(), R.style.PauseDialog);
        citysDatabase = new CitysDatabase(getActivity().getApplicationContext());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
        internet = false;
        if (CheckConnection.isNetworkConnected(getActivity().getApplicationContext())) {
            internet = true;
        } else {
            NoInternet();
        }
    }

    public void NoInternet() {
        Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_con);
        dialog.findViewById(R.id.di_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
        dialog.setOnDismissListener((d)-> getActivity().onBackPressed());
        dialog.show();

    }


    void getData() {
        if (!user_f_name.getText().toString().trim().matches(""))
            s_name1 = user_f_name.getText().toString();
        else {
            user_f_name.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(user_f_name, InputMethodManager.SHOW_IMPLICIT);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_name), Toast.LENGTH_LONG).show();
            return;
        }
        if (!user_s_name.getText().toString().trim().matches(""))
            s_name2 = user_s_name.getText().toString();
        else {
            user_s_name.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_name), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_s_name, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!user_t_name.getText().toString().trim().matches(""))
            s_name3 = user_t_name.getText().toString();
        else {
            user_t_name.requestFocus();

            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_name), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_t_name, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!user_n_name.getText().toString().trim().matches(""))
            s_name4 = user_n_name.getText().toString();
        else {
            user_n_name.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_name), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_n_name, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!erya.getText().toString().trim().matches(""))
            s_address = erya.getText().toString();
        else {
            erya.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_address), Toast.LENGTH_LONG).show();
            imm.showSoftInput(erya, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!sGender.getText().toString().trim().matches(""))
            if (sGender.getText().toString().equals(getString(R.string.men)))
                s_sex = "1";
            else
                s_sex = "2";
        else {
            sGender.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz__enter_sex), Toast.LENGTH_LONG).show();
            imm.showSoftInput(sGender, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!user_year.getText().toString().trim().matches(""))
            s_year = user_year.getText().toString();
        else {
            user_year.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plzenter_date), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_year, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!da.getText().toString().trim().matches(""))
            day = da.getText().toString();
        else {
            da.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plzenter_date), Toast.LENGTH_LONG).show();
            imm.showSoftInput(da, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (!moth.getText().toString().trim().matches(""))
            mounth = moth.getText().toString();
        else {
            moth.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plzenter_date), Toast.LENGTH_LONG).show();
            imm.showSoftInput(moth, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (user_SNN.getText().toString().trim().isEmpty()) s_socialNo = "غير متوفر";
        else s_socialNo = user_SNN.getText().toString().trim();
        /*if (!user_SNN.getText().toString().trim().matches(""))
            s_socialNo = user_SNN.getText().toString();
        else {
            user_SNN.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_ssn), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_SNN, InputMethodManager.SHOW_IMPLICIT);
            return;
        }*/
        if (!user_phone.getText().toString().trim().matches(""))
            s_phone = user_phone.getText().toString();
        else {
            user_phone.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_phone), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_phone, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (user_mail.getText().toString().trim().isEmpty()) s_email = "غير متوفر";
        else s_email = user_mail.getText().toString().trim();
        /*if (!user_mail.getText().toString().trim().matches(""))
            //s_email = user_mail.getText().toString().replace("@", "-");
            s_email = user_mail.getText().toString().trim();
        else {
            user_mail.requestFocus();
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(),getString(R.string.plz_enter_email), Toast.LENGTH_LONG).show();
            imm.showSoftInput(user_mail, InputMethodManager.SHOW_IMPLICIT);
            return;
        }*/
        if (county.getText().toString().trim().matches("")) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_country), Toast.LENGTH_LONG).show();
            imm.showSoftInput(county, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if (city.getText().toString().trim().matches("")) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Toast.makeText(getActivity(), getString(R.string.plz_enter_city), Toast.LENGTH_LONG).show();
            imm.showSoftInput(city, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        s_dob = s_year + "-" + mounth + "-" + (day);
        s_cityId = selectedCityId;
        s_country = selectedCountryId;
        checker = true;
    }

    private void getJSON(final String urlWebService) {
        {
            class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                }


                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (!(null == getActivity())) {
                        if (urlWebService.equals(cityURL)) {
                            try {
                                iniSpinnerCity(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (urlWebService.equals(countryURL)) {
                            try {
                                iniSpinnerCountry(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        dailog.dismiss();
                    } else {
                        cancel(true);
                    }

                }


                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        URL url = new URL(urlWebService);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();
                        BufferedReader bufferedReader =
                                new BufferedReader(new InputStreamReader(con.getInputStream()));
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


    private void getCityOffine() {
        if (citysDatabase.getData().getCount() > 0) {
            itemsCity.clear();
            Cursor cursor = citysDatabase.getData();
            cursor.moveToNext();
            while (cursor.moveToNext()) {
                itemsCity.add(new BookingSpecializationModel(
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2)
                ));
            }
            cityAdapter =
                    new BookingCityItemAdabter(getActivity(), itemsCity, CardRequest.this);
            dailog.dismiss();
        } else {
            itemsCity.clear();
            cityAdapter =
                    new BookingCityItemAdabter(getActivity(), itemsCity, CardRequest.this);
            cityAdapter.getFilter().filter("");
            dailog.dismiss();
        }
    }

    private void iniSpinnerCity(String json) throws JSONException {
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("CityName");
                String id = obj.getString("CityID");
                String con = obj.getString("CountryID");
                itemsCity.add(new BookingSpecializationModel(name, con, id));
            }
            cityAdapter = new BookingCityItemAdabter(getActivity(), itemsCity, this::onCLick2);
            dailog.dismiss();
        } else {
            NoInternet();
        }
    }

    private void iniSpinnerCountry(String json) throws JSONException {
        itemsCountry = new ArrayList<>();
        //itemsCountry.add(new items_country_sendData("الدولة", "all"));
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("CountryName");
                String id = obj.getString("CountryID");
                itemsCountry.add(new BookingSpecializationModel(name, id));
            }
            countryAdapter =
                    new BookingCityItemAdabter(getActivity(), itemsCountry, this::onCLick2);

            //  cityAdapter.getFilter().filter("");
        } else {

        }
    }

    @Override
    public void onCLick2(int p) {
        if (swit == 1) {
            select_city_dig.dismiss();
            city.setText(cityAdapter.getIm().get(p).getCity_name());
            selectedCityId = cityAdapter.getIm().get(p).getCity_name();
        } else {
            select_county.dismiss();
            county.setText(countryAdapter.getIm().get(p).getContry());
            selectedCountryId = countryAdapter.getIm().get(p).getContry();
        }
    }

    @Override
    public void Done(int res) {
        switch (res) {
            case 0:
                Login.Message(getResources().getString(R.string.erroe_in_serv), getActivity());
                break;
            case 1:
                Login.Message(getResources().getString(R.string.card_order_seucssaf), getActivity());
                getActivity().onBackPressed();
                break;
            case 2:
                user_mail.requestFocus();
                Login.Message(getResources().getString(R.string.dep_email), getActivity());
                break;
            case 3:
                user_SNN.requestFocus();
                Login.Message(getResources().getString(R.string.dep_nn), getActivity());
                break;
        }
    }
}

