package com.medicate_int.mymedicate.ui.main_screen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.adapter.ClimesAdapter;
import com.medicate_int.mymedicate.models.ClimesModel;
import com.medicate_int.mymedicate.adapter.InsideClimesAdapter;
import com.medicate_int.mymedicate.models.InsideClimesModel;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Climes extends Fragment implements ClimesAdapter.onCLickLis, Moudle.MoudleInterface {
    List<ClimesModel> clinicsItems, denItems, pharItems;
    static String phar = "https://www.medicateint.com/data2/getClaimsPhar/";
    static String dental = "https://www.medicateint.com/data2/getClaimsDental/";
    static String clinics = "https://www.medicateint.com/data2/getClaimsClinic/";
    String getDetPar =  "https://www.medicateint.com/data2/getClaimsdetailsPhar/";
    String getDetClin = "https://www.medicateint.com/data2/getClaimsdetailsClinic/";
    String getDetDen =  "https://www.medicateint.com/data2/getClaimsdetailsDental/";
    View view;
    CacheHelper statics;
    Dialog DetLoadingDig;
    RecyclerView detReacView;
    TextView show_num, show_total;
    List<InsideClimesModel> pharItems_det, climesItems_det, dentItems_det;
    boolean internet;
    int place_Det;
    EditText serach;
    String climeNum, ClimePrice;
    InsideClimesAdapter pharAdapter_det, clincAdapter_det, dentAdapter_det;
    RecyclerView recyclerView;
    TabLayout tab;
    boolean data_cli = false;
    boolean data_den = false;
    boolean data_phar = false;
    ConstraintLayout main, inside;
    ClimesAdapter clinicAdapter, dentAdapter, pharAdapter;
    Dialog dailog;
    TextView textView;
    int place = 0;
    private static final String TAG = "FragmeantClimes";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmeant_climes, container, false);
        serach = view.findViewById(R.id.climes_serach);
        serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (tab.getSelectedTabPosition()) {
                    case 0:
// // TODO: 2020-08-0
                        clinicAdapter.getFilter().filter(s);
                        break;
                    case 1:
                        pharAdapter.getFilter().filter(s);
                        break;
                    case 2:
                        dentAdapter.getFilter().filter(s);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        main = view.findViewById(R.id.ll_clims_main);
        view.findViewById(R.id.back_to_clim_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome(2);
            }
        });
        view.findViewById(R.id.imageView20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome(2);
            }
        });
        view.findViewById(R.id.imageView21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        inside = view.findViewById(R.id.ll_clims_inside);
        inside.setVisibility(View.GONE);
        tab = view.findViewById(R.id.noti_tab_layout);
        detReacView = view.findViewById(R.id.recyclerView_ins_clims);
        detReacView.setLayoutManager(new LinearLayoutManager(getActivity()));
        show_num = view.findViewById(R.id.cl_ins_c_num);
        show_total = view.findViewById(R.id.cl_ins_price_total);
        recyclerView = view.findViewById(R.id.main_climes_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        textView = view.findViewById(R.id.show_nooop);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                serach.setVisibility(View.GONE);
                switch (tab.getPosition()) {
                    case 0:
                        if (data_cli) {
                            serach.setVisibility(View.VISIBLE);
                            serach.setText("");
                            serach.setHint(getString(R.string.climes_hos));
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(clinicAdapter);
                        } else {
                            textView.setText(R.string.no_cliams_in_hos);
                            recyclerView.setVisibility(View.GONE);
                            textView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                            textView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:
                        if (data_phar) {
                            serach.setVisibility(View.VISIBLE);
                            serach.setText("");
                            serach.setHint(getString(R.string.climes_phar));
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(pharAdapter);
                        } else {
                            textView.setText(R.string.no_cliams_in_phar);
                            recyclerView.setVisibility(View.GONE);
                            textView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                            textView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        if (data_den) {
                            serach.setVisibility(View.VISIBLE);
                            serach.setText("");
                            serach.setHint(getString(R.string.climes_dent));
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(dentAdapter);
                        } else {
                            textView.setText(R.string.no_cliams_in_den);
                            recyclerView.setVisibility(View.GONE);
                            textView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                            textView.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tab.selectTab(tab.getTabAt(0));

        return view;
    }

    private void getJSON(final String urlWebService) {
        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                place_Det += 1;
                dailog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d(TAG, "ID > " + statics.getID());
                try {
                    if (null != getActivity()) {
                        if (null != s) {
                            if (urlWebService.contains(clinics)) {
                                Log.d(TAG, "CLINIC > " + s);
                                if (chack(s, 1)) {
                                    data_cli = true;
                                    loadIntoClinics(s);
                                } else {
                                    serach.setVisibility(View.GONE);
                                    data_cli = false;
                                }
                            } else if (urlWebService.contains(phar)) {
                                Log.d(TAG, "phar > " + s);
                                if (chack(s, 2)) {
                                    data_phar = true;
                                    loadIntoPhar(s);
                                } else data_phar = false;
                            } else if (urlWebService.contains(dental)) {
                                Log.d(TAG, "dental > " + s);
                                if (chack(s, 3)) {
                                    data_den = true;
                                    loadIntoDents(s);
                                } else data_den = false;

                            }
                        } else {
                            if (urlWebService.equals(clinics))
                                Moudle.NoInternet(getActivity(), Climes.this);
                        }
                    } else
                        cancel(true);
                } catch (JSONException e) {
                    Log.d(TAG, "CATCH > " + e);
                } finally {
                    dailog.dismiss();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    Log.d("DATA-DATA", sb.toString());
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d("C123", e.getMessage() + "-" + e.getLocalizedMessage());
                    return null;
                }
            }
        }
        GetJSON_Hospital getJSON = new GetJSON_Hospital();
        getJSON.execute();
    }

    private boolean chack(String json, int i) {
        if (null != json)
            return !json.trim().equals("{\"msg\":0}");
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statics = new CacheHelper(getActivity());
        if (internet) {
            dailog = new Dialog(getActivity(), R.style.PauseDialog);
            dailog.setContentView(R.layout.loading_layout);
            dailog.setCancelable(true);
            dailog.setCanceledOnTouchOutside(false);
            dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    getActivity().onBackPressed();
                }
            });
            getJSON((clinics.concat(statics.getID())));
            getJSON((dental.concat(statics.getID())));
            getJSON((phar.concat(statics.getID())));
            Log.d(TAG, "URL > " + (clinics.concat(statics.getID())));
            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {

                    showHome(2);
                }
            };
            getActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);
         /*
            Toast.makeText(getActivity(),(phar.concat(statics.getID())),Toast.LENGTH_LONG).show();
            getJSON(clinics);
            getJSON(dental);
            getJSON(phar);
         */
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            internet = true;
            DetLoadingDig = new Dialog(getActivity(), R.style.PauseDialog);
            DetLoadingDig.setContentView(R.layout.loading_layout);
            DetLoadingDig.setCancelable(true);
            DetLoadingDig.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DetLoadingDig.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    showHome(2);
                }
            });
            climesItems_det = new ArrayList<>();
            pharItems_det = new ArrayList<>();
            dentItems_det = new ArrayList<>();
            clinicsItems = new ArrayList<>();
            pharItems = new ArrayList<>();
            denItems = new ArrayList<>();
        } else Moudle.NoInternet(getActivity(), Climes.this);

    }


    private void loadIntoClinics(String json) throws JSONException {
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String arabic = obj.getString("CompanyArabicName");
                String english = obj.getString("CompanyEnglishName");
                String francs = obj.getString("CompanyFrenshName");
                String ClaimID = obj.getString("ClaimID");
                String ClinicClaimAmount = obj.getString("ClinicClaimAmount");
                String ClaimDate = obj.getString("ClaimDate");
                clinicsItems.add(new ClimesModel(arabic, english, francs, ClaimID, ClinicClaimAmount, ClaimDate));
            }
            clinicAdapter = new ClimesAdapter(getActivity(), clinicsItems, this::onCLick);
            if (clinicAdapter.getItemCount() > 0) {
                textView.setVisibility(View.GONE);
                recyclerView.setAdapter(clinicAdapter);
            }

        }
    }

    private void loadIntoPhar(String json) throws JSONException {
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String arabic = obj.getString("CompanyArabicName");
                String english = obj.getString("CompanyEnglishName");
                String francs = obj.getString("CompanyFrenshName");
                String ClaimID = obj.getString("ClaimID");
                String ClinicClaimAmount = obj.getString("ClinicClaimAmount");
                String ClaimDate = obj.getString("ClaimDate");
                pharItems.add(new ClimesModel(arabic, english, francs, ClaimID, ClinicClaimAmount, ClaimDate));
            }
            pharAdapter = new ClimesAdapter(getActivity(), pharItems, this::onCLick);


        }
    }

    private void loadIntoDents(String json) throws JSONException {
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String arabic = obj.getString("CompanyArabicName");
                String english = obj.getString("CompanyEnglishName");
                String francs = obj.getString("CompanyFrenshName");
                String ClaimID = obj.getString("ClaimID");
                String ClinicClaimAmount = obj.getString("ClinicClaimAmount");
                String ClaimDate = obj.getString("ClaimDate");
                denItems.add(new ClimesModel(arabic, english, francs, ClaimID, ClinicClaimAmount, ClaimDate));
            }
            dentAdapter = new ClimesAdapter(getActivity(), denItems, this::onCLick);
            dailog.dismiss();
        }
    }

    @Override
    public void onCLick(int p) {
        switch (tab.getSelectedTabPosition()) {
            case 0:
                ClimePrice = clinicAdapter.getIm().get(p).getClaimAmount();
                climeNum = clinicAdapter.getIm().get(p).getClaimID();
                break;
            case 1:
                ClimePrice = pharAdapter.getIm().get(p).getClaimAmount();
                climeNum = pharAdapter.getIm().get(p).getClaimID();
                break;
            case 2:
                ClimePrice = dentAdapter.getIm().get(p).getClaimAmount();
                climeNum = dentAdapter.getIm().get(p).getClaimID();
                break;
        }
        /*statics.setMY_PLACE("داخل المطالبات");
        startActivity(new Intent(getActivity(),home_fragment_activity.class));*/
        //  showHome(new FragmentInsideClimes());

        showHome(1);

    }

    public void showHome(int r) {
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            if (r == 1) {
                statics.setMY_PLACE("داخل المطالبات");
                main.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                main.setVisibility(View.GONE);
                inside.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                inside.setVisibility(View.VISIBLE);
                switch (tab.getSelectedTabPosition()) {
                    case 0:
                        getJSON_DET(getDetClin.concat(climeNum));
                        break;
                    case 1:
                        getJSON_DET(getDetPar.concat(climeNum));
                        break;
                    case 2:
                        getJSON_DET(getDetDen.concat(climeNum));
                        break;
                }
            } else {
                pharItems_det.clear();
                dentItems_det.clear();
                climesItems_det.clear();
                detReacView.setVisibility(View.GONE);
                statics.setMY_PLACE("المطالبات");
                inside.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                inside.setVisibility(View.GONE);
                main.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
                main.setVisibility(View.VISIBLE);
            }
        } else {
            Login.Message(getString(R.string.no_internet_con), getActivity());
        }
    }

    private void loadIntoPhar_det(String json) throws JSONException {

        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String claimid = obj.getString("ClaimID");
                String ServicesName = obj.getString("ServicesName");
                String ClinicServiceAmount = obj.getString("ClinicServiceAmount");
                dentItems_det.add(new InsideClimesModel(claimid, ServicesName, ClinicServiceAmount));
            }
            dentAdapter_det = new InsideClimesAdapter(getActivity(), dentItems_det);
            detReacView.setAdapter(pharAdapter_det);
            detReacView.setVisibility(View.VISIBLE);


        }
    }

    private void loadIntoDentDet(String json) throws JSONException {

        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String claimid = obj.getString("ClaimID");
                String ServicesName = obj.getString("ServicesName");
                String ClinicServiceAmount = obj.getString("ClinicServiceAmount");
                dentItems_det.add(new InsideClimesModel(claimid, ServicesName, ClinicServiceAmount));
            }
            dentAdapter_det = new InsideClimesAdapter(getActivity(), dentItems_det);
            detReacView.setAdapter(dentAdapter_det);
            detReacView.setVisibility(View.VISIBLE);


        }
    }

    private void loadIntoCli_DEt(String json) throws JSONException {

        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String claimid = obj.getString("ClaimID");
                String ServicesName = obj.getString("ServicesName");
                String ClinicServiceAmount = obj.getString("ClinicServiceAmount");
                climesItems_det.add(new InsideClimesModel(claimid, ServicesName, ClinicServiceAmount));
            }
            clincAdapter_det = new InsideClimesAdapter(getActivity(), climesItems_det);
            detReacView.setAdapter(clincAdapter_det);
            detReacView.setVisibility(View.VISIBLE);


        }
    }

    private void getJSON_DET(final String urlWebService) {
        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                show_num.setText(getString(R.string.main_cm_num).concat(climeNum));
                if (statics.getPricesIns().equals("true"))
                    show_total.setVisibility(View.VISIBLE);
                else
                    show_total.setVisibility(View.GONE);
                show_total.setText(getString(R.string.main_cm_price).concat(ClimePrice));
                DetLoadingDig.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.d(TAG, "ON POST > " + place_Det + " > " + s);
                try {
                    if (null != getActivity() && inside.isShown()) {
                        if (null != s) {
                            if (tab.getSelectedTabPosition() == 1) {
                                if (s.length() > 10) {
                                    Log.d(TAG, "ENTER 2 > ");
                                    loadIntoPhar_det(s);
                                }
                            } else {
                                if (s.length() > 10) {
                                    Log.d(TAG, "ENTER 3 > ");
                                    loadIntoDentDet(s);
                                }
                            }
                        } else {
                            Login.Message(getString(R.string.cheack_int_con), getActivity());
                        }
                    } else {
                        cancel(true);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "CATCH  > " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (null != getActivity() && inside.isShown())
                        DetLoadingDig.dismiss();
                }

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    Log.d(TAG, sb.toString());
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d(TAG, "ERROR" + e.getMessage());
                    return null;
                }
            }
        }
        GetJSON_Hospital getJSON = new GetJSON_Hospital();
        getJSON.execute();
    }

    @Override
    public void onBack(boolean bool) {
        if (bool)
            getActivity().onBackPressed();
    }

    @Override
    public void okClicked(boolean bool) {

    }
}