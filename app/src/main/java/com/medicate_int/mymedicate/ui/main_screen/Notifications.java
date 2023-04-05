package com.medicate_int.mymedicate.ui.main_screen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.models.InsideNotificationsModel;
import com.medicate_int.mymedicate.adapter.NotifaitionsAdapter;
import com.medicate_int.mymedicate.models.BillsNotificationModel;
import com.medicate_int.mymedicate.adapter.OtherNotificationsAdapter;
import com.medicate_int.mymedicate.OtherNotificationsModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.database.NotificationsDatabase;
import com.medicate_int.mymedicate.database.OtherNotificationsDatabase;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.adapter.insideNotificationsAdapter;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Notifications extends Fragment implements NotifaitionsAdapter.onCLickLis {

    View view;
    CacheHelper statics;
    NotifaitionsAdapter adapter;
    OtherNotificationsAdapter other_adapter;
    Dialog loading_diag;
    Snackbar snackbar;
    List<BillsNotificationModel> items;
    List<OtherNotificationsModel> other_items;
    RecyclerView recyclerView;
    RecyclerView recyclerView_det;
    boolean internet;
    BottomSheetDialog view_dit_dai;
    TabLayout tabLayout;
    int count = 0;
    com.medicate_int.mymedicate.adapter.insideNotificationsAdapter insideNotificationsAdapter;
    List<InsideNotificationsModel> insideNotificationsItems;
    NotificationsDatabase database;
    OtherNotificationsDatabase otherNotificationsDatabase;
    static String TAG = "NOTI-TAG";
    static String phar = "https://www.medicateint.com/data2/getClaimsPhar/";
    static String dental = "https://www.medicateint.com/data2/getClaimsDental/";
    static String clinics = "https://www.medicateint.com/data2/getClaimsClinic/";
    String getDetPar = "https://www.medicateint.com/data2/getClaimsdetailsPhar/";
    String getDetClin = "https://www.medicateint.com/data2/getClaimsdetailsClinic/";
    String getDetDen = "https://www.medicateint.com/data2/getClaimsdetailsDental/";
    String OTHER = "https://www.medicateint.com/data2/getNotification/";
    TabItem tabItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifications_layout, container, false);
        recyclerView = view.findViewById(R.id.noti_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NotifaitionsAdapter(getActivity(), items, this);
        other_adapter = new OtherNotificationsAdapter(getActivity());
        view.findViewById(R.id.imageView36).setOnClickListener((a) -> getActivity().onBackPressed());
        tabLayout = view.findViewById(R.id.noti_tab_layout);
        if (!statics.getID().equals("null")) {
            tabLayout.getTabAt(0).view.setClickable(true);
        } else {
            tabLayout.getTabAt(0).view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                   // Log.d(TAG, "onTouch: " + motionEvent.getAction());
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        Login.Message(getString(R.string.login_to_contune), getActivity());
                    return false;
                }
            });
            tabLayout.getTabAt(0).view.setClickable(false);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    recyclerView.setAdapter(adapter);
                else {
                    recyclerView.setAdapter(other_adapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (statics.getMY_PLACE().contains("2")) {
            tabLayout.selectTab(tabLayout.getTabAt(1));
            recyclerView.setAdapter(other_adapter);
            if (internet)
                LoadOtherData();
            else viewOtherNoti();
            //  statics.setMY_PLACE("الاشعارات");
        } else {
            if (internet) {
                if (!statics.getID().equals("null")) {
                    LoadData();
                }
                LoadOtherData();
            } else viewOtherNoti();
        }
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    if (adapter.getItemCount() > 0)
                        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                } else {
                    if (other_adapter.getItemCount() > 0)
                        recyclerView.smoothScrollToPosition(other_adapter.getItemCount() - 1);
                }
            }
        });

        return view;
    }

    private void LoadOtherData() {
        if (!loading_diag.isShowing())
            loading_diag.show();
        getJSON(OTHER);
    }

    public void LoadData() {
        if (!loading_diag.isShowing())
            loading_diag.show();
        getJSON(clinics.concat(statics.getID()));
        getJSON(dental.concat(statics.getID()));
        getJSON(phar.concat(statics.getID()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!internet)
            viewOfLineData();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != snackbar)
            snackbar.dismiss();
    }

    private void viewOfLineData() {
        if (null != database.getData())
            if (database.getData().getCount() > 0) {
                snackbar =
                        Snackbar.make(view, getString(R.string.you_are_ofline_not), Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.try_agin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                    }
                }).show();
                Finally();
            } else {
                if (other_adapter.getItemCount() < 1)
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
                getActivity().onBackPressed();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void getCli(String json) throws JSONException {
        Cursor cursor = database.getData();
        boolean found = false;
        Log.d("RUNing >", "CHAKE: ");
        if (!(json == null)) {
            Log.d("RUNing >", "JASON: NOTNULL " + json);
            String arabic, english, francs, ClaimID,
                    ProductionCode, ClinicClaimAmount, ClaimDate;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                found = false;
                JSONObject obj = jsonArray.getJSONObject(i);
                arabic = obj.getString("CompanyArabicName");
                english = obj.getString("CompanyEnglishName");
                francs = obj.getString("CompanyEnglishName");
                ProductionCode = obj.getString("ProductionCode");
                ClaimID = obj.getString("ClaimID");
                ClinicClaimAmount = obj.getString("ClinicClaimAmount");
                ClaimDate = obj.getString("ClaimDate");
                if (cursor.getCount() > 0) {
                    if (database.Serach("c", ClaimID) > 0) {
                        Log.d("EXxxxxx", arabic);
                        found = true;
                    }
                }
                if (!found) {
                    database.insertData(arabic, english, francs, ProductionCode, ClaimID, ClinicClaimAmount, ClaimDate, "1", "c");
                }
            }
        }
    }

    private void getPhar(String json) throws JSONException {
        Cursor cursor = database.getData();
        boolean found = false;
        Log.d("RUNing >", "CHAKE: ");
        if (!(json == null)) {
            Log.d("RUNing >", "JASON: NOTNULL " + json);
            String arabic, english, francs, ClaimID, PharmaceClaimAmount,
                    ProductionCode, ClaimDate;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                found = false;
                JSONObject obj = jsonArray.getJSONObject(i);
                arabic = obj.getString("CompanyArabicName");
                english = obj.getString("CompanyEnglishName");
                francs = obj.getString("CompanyEnglishName");
                ProductionCode = obj.getString("ProductionCode");
                ClaimID = obj.getString("ClaimID");
                PharmaceClaimAmount = obj.getString("ClinicClaimAmount");
                ClaimDate = obj.getString("ClaimDate");
                if (cursor.getCount() > 0) {
                    if (database.Serach("p", ClaimID) > 0) {
                        found = true;
                        Log.d("EXxxxxx", arabic);
                    }
                }
                if (!found) {
                    database.insertData(arabic, english, francs, ProductionCode, ClaimID, PharmaceClaimAmount, ClaimDate, "1", "p");
                }
            }
        }
    }

    private void getDental(String json) throws JSONException {
        Cursor cursor = database.getData();
        boolean found = false;
        Log.d("RUNing >", "CHAKE: ");
        if (!(json == null)) {
            Log.d("RUNing >", "JASON: NOTNULL " + json);
            String arabic, english, francs, ClaimID, DentalClaimAmount,
                    ProductionCode, ClaimDate;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                found = false;
                JSONObject obj = jsonArray.getJSONObject(i);
                arabic = obj.getString("CompanyArabicName");
                english = obj.getString("CompanyEnglishName");
                francs = obj.getString("CompanyEnglishName");
                ProductionCode = obj.getString("ProductionCode");
                ClaimID = obj.getString("ClaimID");
                DentalClaimAmount = obj.getString("ClinicClaimAmount");
                ClaimDate = obj.getString("ClaimDate");
                if (cursor.getCount() > 0) {
                    if (database.Serach("d", ClaimID) > 0) {
                        found = true;
                        Log.d("EXxxxxx", arabic);
                    }
                }
                if (!found) {
                    database.insertData(arabic, english, francs, ProductionCode, ClaimID, DentalClaimAmount, ClaimDate, "1", "d");
                }
            }
        }
    }

    public void Finally() {
        Cursor cursor = database.getData();
        if (null != cursor && cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                items.add(new BillsNotificationModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)));
            }
        Collections.reverse(items);
        adapter = new NotifaitionsAdapter(getActivity(), items, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        statics = new CacheHelper(context);
        //  notifications_url = "http://www.medicateint.com/index.php/data2/Notifications/10001675";
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            internet = true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new NotificationsDatabase(getActivity().getApplicationContext());
        otherNotificationsDatabase = new OtherNotificationsDatabase(getActivity().getApplicationContext());
        items = new ArrayList<>();

        loading_diag = new Dialog(getActivity(), R.style.PauseDialog);
        loading_diag.setContentView(R.layout.loading_layout);
        loading_diag.setCancelable(false);
        loading_diag.setCanceledOnTouchOutside(true);
        loading_diag.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().onBackPressed();
            }
        });
        loading_diag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void getJSON(final String urlWebService) {
        {
            class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    if (!loading_diag.isShowing())
                        loading_diag.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        if (!(null == getActivity())) {
                            if (null != s) {
                                if (s.length() > 10) {
                                    if (urlWebService.contains(clinics))
                                        getCli(s);
                                    else if (urlWebService.contains(phar))
                                        getPhar(s);
                                    else if (urlWebService.contains(dental))
                                        getDental(s);
                                    else if (urlWebService.contains(getDetClin))
                                        getCli_det(s);
                                    else if (urlWebService.contains(getDetPar))
                                        getPhar_det(s);
                                    else if (urlWebService.contains(getDetDen))
                                        getDental_det(s);
                                    else if (urlWebService.equals(OTHER))
                                        getOtherNoti(s);
                                } else {
                                    Log.d(TAG, "onPostExecute: S < 10");
                                    loading_diag.dismiss();
                                    cancel(true);
                                }
                            } else {
                                Log.d(TAG, "onPostExecute: S Null");
                                loading_diag.dismiss();
                                cancel(true);
                            }
                        } else {
                            loading_diag.dismiss();
                            cancel(true);
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "onPostExecute4123: " + e.getMessage());
                        loading_diag.dismiss();
                    } finally {
                        if (!urlWebService.equals(OTHER)) {
                            if (urlWebService.contains(clinics))
                                count++;
                            else if (urlWebService.contains(phar))
                                count++;
                            else if (urlWebService.contains(dental))
                                count++;
                            else {
                                if (null != s)
                                    if (s.length() < 10) {
                                        Log.d(TAG, s);
                                        loading_diag.dismiss();
                                    } else
                                        Log.d(TAG, "nulll");
                            }
                            if (count == 3) {
                                Finally();
                                loading_diag.dismiss();
                                count = 0;
                            }
                        }
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

    private void getOtherNoti(String json) throws JSONException {
        Log.d(TAG, "OTHERnot" + json);
        other_items = new ArrayList<>();
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            //  otherNotificationsDatabase.delOldData();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String id = obj.getString("id");
                String body = obj.getString("body");
                String date = obj.getString("date");
                String state = obj.getString("state");
                Log.d(TAG, "getOtherNotiADDEDb" + id);

                if (otherNotificationsDatabase.Search(id).getCount() == 0) {
                    Log.d(TAG, "getOtherNotiADDED" + id);
                    otherNotificationsDatabase.insertData(body, date, id);
                    //    other_items.add(new OtherNotificationsItem(body,date,"1"));
                }
                if (state.trim().equals("0")) otherNotificationsDatabase.delete(id);
            }
        }
        viewOtherNoti();
    }

    private void viewOtherNoti() {
        other_items = new ArrayList<>();
        if (null != otherNotificationsDatabase.getData()) {
            Log.d(TAG, "onCreate: CONUT" + otherNotificationsDatabase.getData().getCount());
            Cursor cursor = otherNotificationsDatabase.getData();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    other_items.add(new OtherNotificationsModel(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                }
            }
            Collections.reverse(other_items);
            other_adapter = new OtherNotificationsAdapter(getActivity(), other_items);

        }
        if (loading_diag.isShowing())
            loading_diag.dismiss();
        if (tabLayout.getSelectedTabPosition() == 1) {
            if (null != ((Object) other_adapter.getItemCount())) {
                recyclerView.setAdapter(other_adapter);
                otherNotificationsDatabase.SetStateToOld();
            }
        }
    }

    private void getPhar_det(String json) throws JSONException {
        Log.d(TAG, "phar > " + json);
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String claimid = obj.getString("ClaimID");
                String ServicesName = obj.getString("ServicesName");
                String ClinicServiceAmount = obj.getString("ClinicServiceAmount");
                String comp = obj.getString("PercentageComp");
                String you = obj.getString("PercentageClinic");
                insideNotificationsItems.add(new InsideNotificationsModel(claimid, ServicesName, ClinicServiceAmount, comp, you));
            }
            insideNotificationsAdapter =
                    new insideNotificationsAdapter(getActivity(), insideNotificationsItems);
            loading_diag.dismiss();
            recyclerView_det.setAdapter(insideNotificationsAdapter);
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total_you)).setText(("" + insideNotificationsAdapter.getYou()));
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total_comp)).setText(("" + insideNotificationsAdapter.getComp()));
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total)).setText(("" + insideNotificationsAdapter.getTotal()));
            view_dit_dai.show();
        }
    }


    private void getDental_det(String json) throws JSONException {
        Log.d(TAG, "dent > " + json);
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String claimid = obj.getString("ClaimID");
                String ServicesName = obj.getString("ServicesName");
                String ClinicServiceAmount = obj.getString("ClinicServiceAmount");
                String comp = obj.getString("PercentageComp");
                String you = obj.getString("PercentageClinic");
                insideNotificationsItems.add(new InsideNotificationsModel(claimid, ServicesName, ClinicServiceAmount, comp, you));
            }
            insideNotificationsAdapter =
                    new insideNotificationsAdapter(getActivity(), insideNotificationsItems);
            loading_diag.dismiss();
            recyclerView_det.setAdapter(insideNotificationsAdapter);
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total_you)).setText(("" + insideNotificationsAdapter.getYou()));
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total_comp)).setText(("" + insideNotificationsAdapter.getComp()));
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total)).setText(("" + insideNotificationsAdapter.getTotal()));
            view_dit_dai.show();
        }
    }

    private void getCli_det(String json) throws JSONException {
        Log.d(TAG, "clin > " + json);
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String claimid = obj.getString("ClaimID");
                String ServicesName = obj.getString("ServicesName");
                String ClinicServiceAmount = obj.getString("ClinicServiceAmount");
                String comp = obj.getString("PercentageComp");
                String you = obj.getString("PercentageClinic");
                insideNotificationsItems.add(new InsideNotificationsModel(claimid, ServicesName, ClinicServiceAmount, comp, you));
            }
            insideNotificationsAdapter =
                    new insideNotificationsAdapter(getActivity(), insideNotificationsItems);
            loading_diag.dismiss();
            recyclerView_det.setAdapter(insideNotificationsAdapter);
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total_you)).setText(("" + insideNotificationsAdapter.getYou()));
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total_comp)).setText(("" + insideNotificationsAdapter.getComp()));
            ((TextView) view_dit_dai.findViewById(R.id.bail_bot_total)).setText(("" + insideNotificationsAdapter.getTotal()));
            view_dit_dai.show();
        }
    }

    @Override
    public void onCLick(int p) {
        Log.d(TAG, "OnCLICK " + p);
        insideNotificationsItems = new ArrayList<>();
        view_dit_dai = new BottomSheetDialog(getActivity());
        view_dit_dai.setContentView(R.layout.show_bail_detil);
        if (statics.getPricesIns().equals("true")) {
            (view_dit_dai.findViewById(R.id.view18)).setVisibility(View.VISIBLE);
            view_dit_dai.findViewById(R.id.bail_lay1).setVisibility(View.VISIBLE);
            view_dit_dai.findViewById(R.id.bail_lay2).setVisibility(View.VISIBLE);
            view_dit_dai.findViewById(R.id.bail_lay3).setVisibility(View.VISIBLE);
        } else {
            view_dit_dai.findViewById(R.id.view18).setVisibility(View.GONE);
            view_dit_dai.findViewById(R.id.bail_lay1).setVisibility(View.GONE);
            view_dit_dai.findViewById(R.id.bail_lay2).setVisibility(View.GONE);
            view_dit_dai.findViewById(R.id.bail_lay3).setVisibility(View.GONE);
        }
        if (CheckConnection.isNetworkConnected(requireActivity())) {
            Log.d(TAG, items.get(p).getType());
            if (items.get(p).getType().equals("c"))
                getJSON(getDetClin.concat(items.get(p).getClaimID()).concat("cli"));
            else if (items.get(p).getType().equals("d"))
                getJSON(getDetDen.concat(items.get(p).getClaimID()));
            else
                getJSON(getDetPar.concat(items.get(p).getClaimID()));

            database.SetStateToOld(adapter.getIm().get(p).getType(), adapter.getIm().get(p).getClaimID());
            adapter.notifyItemChanged(p);
            recyclerView_det = view_dit_dai.findViewById(R.id.show_bail_recview);
            recyclerView_det.setLayoutManager(new LinearLayoutManager(getActivity()));
            view_dit_dai.findViewById(R.id.imageView37).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view_dit_dai.dismiss();
                    view_dit_dai.cancel();
                }
            });

            view_dit_dai.setCancelable(true);
            view_dit_dai.getBehavior().setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            view_dit_dai.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        } else {
            Login.Message(getResources().getString(R.string.no_internet_con), getActivity());
        }
    }
}