package com.medicate_int.mymedicate.ui.main_screen.medical_network;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.adapter.CitiesPickerAdapter;
import com.medicate_int.mymedicate.models.CitiesModel;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.ui.MapsActivity;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.adapter.MedicalNetworkAdapter;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.database.CitysDatabase;
import com.medicate_int.mymedicate.database.NetworkDatabase;
import com.medicate_int.mymedicate.models.MedicalNetworkModel;
import com.medicate_int.mymedicate.ui.Login;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalNetwork extends Fragment implements MedicalNetworkAdapter.onCLickLis
        , CitiesPickerAdapter.CitiesClicksInterface {
    public static final String TAG = "MNT";
    /*public static String medical_city;
    public static String medical_country;*/
    NetworkDatabase networkDatabase;
    CitysDatabase citysDatabase;
    CacheHelper cacheHelper;
    Dialog loadingDialog, showProviderDitailes;
    boolean ft = true;
    EditText searchEditText;
    BottomSheetDialog sheetBehavior, fiter_dig;
    View view;
    Dialog noInternetDialog;
    int selected_index = 0;
    float lat = 0, log = 0;
    private RecyclerView recyclerView;
    private List<MedicalNetworkModel> medicalNetworkList;
    private List<CitiesModel> citiesList, citiesListFiltered;
    private BookingCityItemAdabter cityAdapter;
    private MedicalNetworkAdapter medicalNetworkAdapter;
    private TextView city_picker, country_picker, noData;
    private CharSequence searchText;
    private Context context;
    BottomSheetDialog countryPickerDialog;
    public static String selectedCountry, selectedCity, filter_type;
    Call<List<MedicalNetworkModel>> medicalNetworkCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medical_network, container, false);
        recyclerView = view.findViewById(R.id.rec_vew);
        noData = view.findViewById(R.id.textView41);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        filter_type = cacheHelper.getMY_NETWORK();
        context = getActivity();
        searchText = "";
        loadingDialog = new LoadingDialog(getActivity());
        selectedCountry = cacheHelper.restorePrefData("net_w_country");
        Log.d(TAG, "Selected Country = " + selectedCountry);
        view.findViewById(R.id.linearLayout28).setOnClickListener(v -> back());
        noInternetDialog = Moudle.noInternet(context, new Moudle.OkCancelInterface() {
            @Override
            public void OK() {
                getData();
            }

            @Override
            public void Cancel() {
                back();
            }
        });
        providerDetailedDialogSetup();
        medicalNetworkCall = new ApiClient(ApiClient.MAIN_SYSTEM_URL).getMedicalNetwork();
        searchEditText = view.findViewById(R.id.etSearchBar);
        searchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEditText.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            searchEditText.clearFocus();
                            Moudle.hideKeyboard(context, view);

                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    //  if (!ft)
                    searchText = s;
                    //   Log.d(TAG, "onTextChanged:  " + medicalNetworkAdapter.getItemCount() + " - " + searchText);
                    medicalNetworkAdapter.getFilter().filter(s);
                    // else ft = false;
                } catch (Exception e) {
                    Log.d(TAG, "onTextChanged: ERROR " + e.getMessage());
                    return;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        city_picker = view.findViewById(R.id.new_city_pic);
        country_picker = view.findViewById(R.id.new_country_pic);
        country_picker.setText(Moudle.getMyCountyName(context));
        city_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityPicker();
            }
        });

        country_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountryPicker();
            }
        });


        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                /*statics.setMY_PLACE("الشبكات الطبية");
                startActivity(new Intent(context, HomeActivity.class))                ;*/
                back();
            }
        });

        getData();

        return view;
    }

    private void getData() {
        if (CheckConnection.isNetworkConnected(context)) {
            loadingDialog.show();
            medicalNetworkCall.clone().enqueue(new Callback<List<MedicalNetworkModel>>() {
                @Override
                public void onResponse(Call<List<MedicalNetworkModel>> call, Response<List<MedicalNetworkModel>> response) {
                    noData.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: SUCCESS");
                        try {
                            List<MedicalNetworkModel> body = response.body();
                            List<String> addedToCities = new ArrayList<>();
                            if (body == null) throw new NullPointerException();
                            for (MedicalNetworkModel model : body) {
                                switch (filter_type) {
                                    case "1786":
                                        if ((model.getCompanyType().equals("1")) || (model.getCompanyType().equals("7"))
                                                || (model.getCompanyType().equals("8"))) {
                                            medicalNetworkList.add(model);
                                            if (notInCities(addedToCities, model.getCityID())) {
                                                addedToCities.add(model.getCityID());
                                                citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                                            }
                                        }
                                        break;
                                    case "2":
                                        if ((model.getCompanyType().equals("2"))) {
                                            medicalNetworkList.add(model);
                                            if (notInCities(addedToCities, model.getCityID())) {
                                                addedToCities.add(model.getCityID());
                                                citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                                            }
                                        }
                                        break;
                                    case "6":
                                        if ((model.getCompanyType().equals("6"))) {
                                            medicalNetworkList.add(model);
                                            if (notInCities(addedToCities, model.getCityID())) {
                                                addedToCities.add(model.getCityID());
                                                citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                                            }
                                        }
                                        break;

                                    case "1415":
                                        if ((model.getCompanyType().equals("14")) || (model.getCompanyType().equals("15"))) {
                                            medicalNetworkList.add(model);
                                            if (notInCities(addedToCities, model.getCityID())) {
                                                addedToCities.add(model.getCityID());
                                                citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                                            }
                                        }
                                        break;
                                    case "910":
                                        if ((model.getCompanyType().equals("9")) || (model.getCompanyType().equals("10"))) {
                                            medicalNetworkList.add(model);
                                            if (notInCities(addedToCities, model.getCityID())) {
                                                addedToCities.add(model.getCityID());
                                                citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                                            }
                                        }
                                        break;
                                    case "1112":
                                        if ((model.getCompanyType().equals("11")) || (model.getCompanyType().equals("12"))) {
                                            medicalNetworkList.add(model);
                                            if (notInCities(addedToCities, model.getCityID())) {
                                                addedToCities.add(model.getCityID());
                                                citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                                            }
                                        }
                                        break;
                                }
                            }
                            Log.d(TAG, "loadIntoList: LIST Loaded > " + medicalNetworkList.size());
                            if (medicalNetworkList.isEmpty()) noData.setVisibility(View.VISIBLE);
                            medicalNetworkAdapter = new MedicalNetworkAdapter(context, medicalNetworkList, MedicalNetwork.this);
                            recyclerView.setAdapter(medicalNetworkAdapter);
                            //    loadCitiesFilterContries();
                            loadingDialog.dismiss();
                            //   removeDublicatedCitites();
                            addDataToDatabase();
                        } catch (NullPointerException e) {
                            Log.d(TAG, "onResponse: Network ERROR : " + e.getMessage());
                            getMedicalNetworkFromLocalDatabase();
                        }
                    } else {
                        getMedicalNetworkFromLocalDatabase();
                    }
                }

                @Override
                public void onFailure(Call<List<MedicalNetworkModel>> call, Throwable t) {
                    getMedicalNetworkFromLocalDatabase();
                }
            });
        } else {
            getMedicalNetworkFromLocalDatabase();
        }
    }

    private boolean notInCities(List<String> cities, String cityID) {
        for (String cityId : cities
        ) {
            if (cityId.equals(cityID)) return false;

        }
        return true;
    }

    private void showCountryPicker() {
        countryPickerDialog = new BottomSheetDialog(context);
        countryPickerDialog.setContentView(R.layout.network_country_choaser_layout);
        countryPickerDialog.setCancelable(true);
        countryPickerDialog.findViewById(R.id.dd_country_tuis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCountryChange("2", getString(R.string.county_tunis));
            }
        });
        countryPickerDialog.findViewById(R.id.dd_country_libya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCountryChange("1", getString(R.string.county_libya));
            }
        });
        countryPickerDialog.findViewById(R.id.dd_country_egp).setOnClickListener(v ->
                onCountryChange("4", getString(R.string.egy)));
        countryPickerDialog.findViewById(R.id.dd_country_turkish).setOnClickListener(v ->
                onCountryChange("6", getString(R.string.county_turkia)));
        countryPickerDialog.findViewById(R.id.dd_country_germany).setOnClickListener(v ->
                onCountryChange("6", getString(R.string.county_gaemny)));
        countryPickerDialog.findViewById(R.id.dd_country_italy).setOnClickListener(v ->
                onCountryChange("17", getString(R.string.county_italya)));
        countryPickerDialog.show();
    }

    private void onCountryChange(String id, String name) {
        countryPickerDialog.dismiss();
        country_picker.setText(name);
        selectedCountry = id;
        selectedCity = "all";
        city_picker.setText(getText(R.string.evry_city));
        medicalNetworkAdapter.getFilter().filter(searchText);

    }

    private void getMedicalNetworkFromLocalDatabase() {
        Cursor cursor = null;
        Log.d(TAG, "viewOfLineData: ");
        switch (filter_type) {
            case "1786":
                cursor = networkDatabase.getAllClinics();
                break;
            case "2":
                cursor = networkDatabase.getAllPharmacies();
                break;
            case "1415":
                cursor = networkDatabase.getAllHearingAndSeying();
                break;
            case "910":
                cursor = networkDatabase.getAllLaps();
                break;
            case "1112":
                cursor = networkDatabase.getAllVisicalAndGym();
                break;
        }
        if (null != cursor) {
            if (cursor.getCount() > 0) {
                getNetworkFromLocalDatabase(cursor);
            } else {
                showNoInternetConnectionDialog();
            }
        } else showNoInternetConnectionDialog();
    }

    private void getNetworkFromLocalDatabase(Cursor cursor) {
        medicalNetworkList.clear();
        List<String> addedToCities = new ArrayList<>();
        Log.d(TAG, "getNetworkFromLocalDatabase: COUNT > " + cursor.getCount());
        MedicalNetworkModel model = new MedicalNetworkModel();
        if (null != cursor && cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                model = new MedicalNetworkModel();
                Log.d(TAG, "getNetworkFromLocalDatabase: Arabic Name : " + cursor.getString(1));
                model.setCompanyArabicName(cursor.getString(1));
                model.setCompanyEnglishName(cursor.getString(2));
                model.setCompanyFrenshName(cursor.getString(3));
                model.setGooGleMaps(cursor.getString(4));
                model.setCompanyID(cursor.getString(5));
                model.setCityID(cursor.getString(6));
                model.setCompanyType(cursor.getString(7));
                model.setLat(cursor.getString(8));
                model.setLog(cursor.getString(9));
                model.setEmail(cursor.getString(10));
                model.setPhone(cursor.getString(11));
                model.setAddressLine1(cursor.getString(12));
                model.setCityName(cursor.getString(13));
                model.setImage(cursor.getString(14));
                model.setCountryID(cursor.getString(15));
                medicalNetworkList.add(model);
                if (notInCities(addedToCities, model.getCityID())) {
                    addedToCities.add(model.getCityID());
                    citiesList.add(new CitiesModel(model.getCityName(), model.getCityID(), model.getCountryID()));
                }
            }
        // Log.d(TAG, "load from Database : COUNT LOADED > " + medicalNetworkList.size());
        medicalNetworkAdapter = new MedicalNetworkAdapter(context, medicalNetworkList, this);
        // my adapter = new medicalNetworkCustomAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //removeDublicatedCitites();
        recyclerView.setAdapter(medicalNetworkAdapter);
        if (loadingDialog.isShowing()) loadingDialog.dismiss();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        medicalNetworkList = new ArrayList<>();
        citiesList = new ArrayList<>();
        citiesListFiltered = new ArrayList<>();
        cacheHelper = new CacheHelper(context);
        //  statics.savePrefsData("net_w_country", statics.getMY_CONTRY()); // todo
        cacheHelper.savePrefsData("net_w_country", "1");
        //    Log.d(TAG, "OnAttach Status > " + cacheHelper.getMY_CONTRY() + " -  NET > " + cacheHelper.restorePrefData("net_w_country"));
    }


    public int getCountyNumber(String con) {
        if (getResources().getString(R.string.county_libya).equals(con))
            return 1;
        if (getResources().getString(R.string.egy).equals(con))
            return 4;
        if (getResources().getString(R.string.county_tunis).equals(con))
            return 2;
        if (getResources().getString(R.string.county_aljaz).equals(con))
            return 3;
        if (getResources().getString(R.string.county_espain).equals(con))
            return 7;
        if (getResources().getString(R.string.county_gaemny).equals(con))
            return 6;
        if (getResources().getString(R.string.county_italya).equals(con))
            return 17;
        if (getResources().getString(R.string.county_ukranya).equals(con))
            return 9;
        if (getResources().getString(R.string.county_turkia).equals(con))
            return 6;
        return 1;
    }

    private void showFilterPicker() {
        CustomCheckBox dental, eua, non_eua;
        fiter_dig = new BottomSheetDialog(context);
        fiter_dig.setContentView(R.layout.network_filter);
        dental = fiter_dig.findViewById(R.id.id_fiter_dental);
        eua = fiter_dig.findViewById(R.id.id_fiter_eua);
        non_eua = fiter_dig.findViewById(R.id.id_fiter_non_eua);
        if (filter_type.contains("7")) eua.setChecked(true);
        if (filter_type.contains("8")) non_eua.setChecked(true);
        if (filter_type.contains("6")) dental.setChecked(true);
        fiter_dig.findViewById(R.id.id_fiter_dental_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dental.setChecked(!dental.isChecked(), true);
            }
        });
        fiter_dig.findViewById(R.id.id_fiter_eua_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eua.setChecked(!eua.isChecked(), true);
            }
        });
        fiter_dig.findViewById(R.id.id_fiter_non_eua_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                non_eua.setChecked(!non_eua.isChecked(), true);
            }
        });
        fiter_dig.findViewById(R.id.id_fiter_ok_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_type = "";
                if (dental.isChecked()) filter_type = filter_type + "6";
                if (eua.isChecked()) filter_type = filter_type + "17";
                if (non_eua.isChecked()) filter_type = filter_type + "18";
                medicalNetworkAdapter.getFilter().filter(searchText);
                fiter_dig.dismiss();


                Log.d(TAG, "onClick: FILTER STR 1 > " + filter_type);
            }
        });
        fiter_dig.setCancelable(true);
        fiter_dig.show();
    }

    private void providerDetailedDialogSetup() {
        showProviderDitailes = new Dialog(context, R.style.PauseDialog);
        showProviderDitailes.setContentView(R.layout.show_medica_network_ditails);
        showProviderDitailes.setCancelable(true);
        showProviderDitailes.setCanceledOnTouchOutside(true);
        showProviderDitailes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showProviderDitailes.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                lat = 0;
                log = 0;
            }
        });
        (showProviderDitailes.findViewById(R.id.show_n_d_cancel)).setOnClickListener(a -> showProviderDitailes.dismiss());
        ((LinearLayout) showProviderDitailes.findViewById(R.id.show_n_d_loca)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicalNetworkModel item = medicalNetworkList.get(selected_index);
                if (Moudle.checkFromLocationData(item.getLog(), item.getLat())) {
                    cacheHelper.setMY_PLACE("داخل الشبكة الطبية");
                    Intent map = new Intent(context, MapsActivity.class);
                    if (SetLocal.getLong(v.getContext()).equals("ar"))
                        map.putExtra("title", item.getCompanyArabicName());
                    if (SetLocal.getLong(v.getContext()).equals("fr"))
                        map.putExtra("title", item.getCompanyFrenshName());
                    if (SetLocal.getLong(v.getContext()).equals("en"))
                        map.putExtra("title", item.getCompanyEnglishName());

                    map.putExtra("lat", medicalNetworkAdapter.getIm().get(selected_index).getLat());
                    map.putExtra("log", medicalNetworkAdapter.getIm().get(selected_index).getLog());
                    startActivity(map);
                } else {
                    Login.Message(getString(R.string.no_loac), context);
                }
            }
        });


    }


    private void showProviderDetail(String name, String email, String phone, String address, float lat, float log) {
        ((TextView) showProviderDitailes.findViewById(R.id.show_n_d_name)).setText(name);

        if (email.trim().isEmpty())
            ((TextView) showProviderDitailes.findViewById(R.id.show_n_d_email)).setText(getString(R.string.not_avitable));
        else
            ((TextView) showProviderDitailes.findViewById(R.id.show_n_d_email)).setText(email);

        // ((TextView) netword_dit_gig.findViewById(R.id.show_n_d_phone)).setText(phone);

        if (address.trim().isEmpty())
            ((TextView) showProviderDitailes.findViewById(R.id.show_n_d_address)).setText(getString(R.string.not_avitable));
        else
            ((TextView) showProviderDitailes.findViewById(R.id.show_n_d_address)).setText(address);

        this.lat = lat;
        this.log = log;
        showProviderDitailes.show();

    }

    private void showCityPicker() {
        // View view2 = v.findViewById(R.id.bb_ll);
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (citiesList.size() > 0) {
            //   Log.d(TAG, "MY  CONT NUM " + citysDatabase.getData().getCount());
            recyclerView.setAdapter(loadCitiesFilterContries());
            //     Log.d(TAG, "DATA count > " + citysDatabase.getData().getCount());
            sheetBehavior = new BottomSheetDialog(context);
            sheetBehavior.setContentView(recyclerView);
            sheetBehavior.setCancelable(true);
            sheetBehavior.show();
        } else {
            if (!noInternetDialog.isShowing())
                Login.Message(getResources().getString(R.string.no_internet_con), getActivity());
        }
    }

    private void showNoInternetConnectionDialog() {
        if (loadingDialog.isShowing()) loadingDialog.dismiss();
        if (!noInternetDialog.isShowing())
            noInternetDialog.show();
    }



    private CitiesPickerAdapter loadCitiesFilterContries() { // 2 all ---- 1 picker
        citiesListFiltered.clear();
        citiesListFiltered.add(new CitiesModel(getString(R.string.evry_city), "all", "all"));
        for (CitiesModel model : citiesList
        ) {
            if (model.getCountry().equals(selectedCountry)) citiesListFiltered.add(model);
        }
        return new CitiesPickerAdapter(context, citiesListFiltered, this);


        /*if (null != citysDatabase.getData()) {
            if (citysDatabase.getData().getCount() > 0) {
                citiesList.clear();
                Cursor cursor = citysDatabase.getData();
                Log.d(TAG, "ITEMS: not EQUAL ");
                if (i == 1) {
                    citiesList.set(0, getString(R.string.all_contrys));
                    while (cursor.moveToNext()) {
                        if (cursor.getString(3).trim().equals(("" + getCountyNumber(country_picker.getText().toString())))
                                || cursor.getString(3).equals("all")) {
                            citiesList.add(cursor.getString(1));
                            cityAdapter = new BookingCityItemAdabter(context, citiesList, this::onCLick2);
                        }
                    }
                } else if (i == 2) {
                    while (cursor.moveToNext()) {
                        citiesList.add(cursor.getString(1));
                        cityAdapter = new BookingCityItemAdabter(getActivity(), citiesList, this::onCLick2);
                    }
                }
                Log.d(TAG, "ITEMS: SIZE NOW > " + citiesList.size());
            } else {
                getJSON(cityURL);
                Log.d(TAG, "GET CITY NOW 2");
            }
        } else {
            Log.d(TAG, "GET CITY NOW 2");
            getJSON(cityURL);
        }*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedCity = "all";
        networkDatabase = new NetworkDatabase(getActivity().getApplicationContext());
        citysDatabase = new CitysDatabase(getActivity().getApplicationContext());
    }

    private void addDataToDatabase() {
        Log.d(TAG, "addDataToDatabase: START");
        switch (filter_type) {
            case "1786":
                networkDatabase.deleteOldClinics();
                break;
            case "2":
                networkDatabase.deleteOldPharmacies();
                break;
            case "1415":
                networkDatabase.deleteOldHearingAndSeying();
                break;
            case "910":
                networkDatabase.deleteOldLaps();
                break;
            case "1112":
                networkDatabase.deleteOldVisicalAndGym();
                break;
        }
        if (medicalNetworkList.size() > 0) {
            for (int i = 0; i < medicalNetworkList.size(); i++) {
                networkDatabase.insertData(
                        medicalNetworkList.get(i).getCompanyArabicName(),
                        medicalNetworkList.get(i).getCompanyEnglishName(),
                        medicalNetworkList.get(i).getCompanyFrenshName(),
                        medicalNetworkList.get(i).getCompanyID(),
                        medicalNetworkList.get(i).getCityID(),
                        medicalNetworkList.get(i).getCompanyType(),
                        "" + medicalNetworkList.get(i).getLat(),
                        "" + medicalNetworkList.get(i).getLog(),
                        medicalNetworkList.get(i).getEmail(),
                        medicalNetworkList.get(i).getPhone(),
                        medicalNetworkList.get(i).getAddressLine1(),
                        medicalNetworkList.get(i).getCityName(),
                        medicalNetworkList.get(i).getImage(),
                        medicalNetworkList.get(i).getCountryID()
                );

            }
        }
    }
    /*private void addtoDatabaseCitys() {
        if (citiesList.size() > 0) {
            citysDatabase.delOldData();
            for (int i = 0; i < citiesList.size(); i++) {
                citysDatabase.insertData(
                        citiesList.get(i).getCity_name(),
                        citiesList.get(i).getId(),
                        citiesList.get(i).getContry());
            }
        }
        getCitiesFromLocalDatabase(2);
    }*/

   /* private String getType(String type) {
        String t = "الكل";
        switch (type) {
            case "1":
                return "مصحات";
            case "2":
                return "صيدليات";
            case "3":
                return "شركات";
            case "4":
                return "شركات تامين";
            case "5":
                return "محرر عقود";
            case "6":
                return "اسنان";
            case "7":
                return "مصحة إيوائية";
            case "8":
                return "مصحة غير إيوائية";
            case "9":
                return "معامل تحاليل / مختبرات";
            case "10":
                return "تطوير طبي";
            case "11":
                return "علاج طبيعي";
            case "12":
                return "صالة رياضة";
            case "14":
                return "بصريات";
            case "15":
                return "سمعيات";
        }
        return t;
    }*/

   /* private String getCityName(String id) {
        Log.d("NETWORK123 > ", " > (" + cityAdapter.getDemo().size() + ")- " + id);
        for (int i = 0; i < cityAdapter.getDemo().size(); i++) {
            if (cityAdapter.getDemo().get(i).getId().trim().equals(id.trim()))
                return cityAdapter.getDemo().get(i).getCity_name();
        }
        return id;
    }*/

    @Override
    public void onCLick(int p) {
        selected_index = p;
        MedicalNetworkModel item = medicalNetworkList.get(selected_index);
        showProviderDetail(item.getCompanyArabicName(), item.getEmail(), item.getPhone(), item.getAddressLine1(), item.getLat(), item.getLog());
    }

    @Override
    public void listIsEmpty(boolean b) {
        Log.d(TAG, "listIsEmpty: " + b);
        if (b)
            noData.setVisibility(View.VISIBLE);
        else {
            if (noData.getVisibility() == View.VISIBLE) noData.setVisibility(View.GONE);
        }
    }




    private void back() {
        getActivity().onBackPressed();
    }

    @Override
    public void onCityClicked(int p) {
        city_picker.setText(citiesListFiltered.get(p).getCityName());
        /*if (p == 0)
            selectedCity = citiesList.get(p).getCityId();
        else selectedCity = citiesList.get(p).getCityName();*/
        selectedCity = citiesListFiltered.get(p).getCityId();
        medicalNetworkAdapter.getFilter().filter(searchEditText.getText().toString().trim());
        sheetBehavior.dismiss();
    }
}
