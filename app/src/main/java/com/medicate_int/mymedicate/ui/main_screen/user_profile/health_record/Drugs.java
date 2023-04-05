package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.adapter.DrugsAdapter;
import com.medicate_int.mymedicate.models.DrugsModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
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

import pl.droidsonroids.gif.GifImageView;


public class Drugs extends Fragment implements DrugsAdapter.onCLickLis {
    View view;
    List<DrugsModel> items;
    CacheHelper statics;
    Dialog dialog, Loading;
    DrugsAdapter adapter;
    RecyclerView recyclerView;
    String URL = "https://www.medicateint.com/data2/getDugsForBen/";
    ConstraintLayout main, sec;
    String type;
    Context context;
    boolean internet = false;
    Dialog aljr3a_diag;
    private static final String TAG = "HC_Aladwia";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aladwia, container, false);
        aljr3a_diag = new Dialog(getActivity(), R.style.PauseDialog);
        aljr3a_diag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        main = view.findViewById(R.id.ll_drugs_main);
        main.setVisibility(View.VISIBLE);
        sec = view.findViewById(R.id.ll_drugs_sec);
        sec.setVisibility(View.GONE);
        context = getActivity();
        type = statics.getHelthRecordType();
        recyclerView = view.findViewById(R.id.drugs_rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.imageView44).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.textView34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.dr_but_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        view.findViewById(R.id.dr_img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go();
            }
        });
        if (internet) {
            getJSON(URL.concat(statics.getID()));
            Log.d(TAG, "URL > " + URL.concat(statics.getID()));
        } else Login.Message(getString(R.string.no_internet_con),context);

        Loading.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (items.isEmpty()) {
                    main.setVisibility(View.VISIBLE);
                    sec.setVisibility(View.GONE);
                } else {
                    main.setVisibility(View.GONE);
                    sec.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    public void NoInternet() {
        Dialog dialog3 = new Dialog(getActivity(), R.style.PauseDialog);
        dialog3.setCancelable(false);
        dialog3.setContentView(R.layout.no_internet_con);
        dialog3.findViewById(R.id.di_try).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
        dialog3.findViewById(R.id.di_but_cancal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
                getActivity().onBackPressed();
            }
        });
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!dialog3.isShowing())
            dialog3.show();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statics = new CacheHelper(getActivity().getApplicationContext());
        items = new ArrayList<>();
        Loading = new Dialog(getActivity(), R.style.PauseDialog);
        Loading.setContentView(R.layout.loading_layout);
        Loading.setCancelable(true);
        Loading.setCanceledOnTouchOutside(false);
        Loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Loading.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().onBackPressed();
            }
        });
        if (CheckConnection.isNetworkConnected(getActivity())) {
            internet = true;
        }
    }

    private void go() {
        dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.setContentView(R.layout.add_drugs_dialog);
        TextView button = dialog.findViewById(R.id.feed_back_ok_byt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ConstraintLayout main = dialog.findViewById(R.id.feed_back_main);
        main.setVisibility(View.VISIBLE);
        ConstraintLayout thanki = dialog.findViewById(R.id.feed_back_thank);
        GifImageView gifImageView = dialog.findViewById(R.id.gifImageView);
        thanki.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setVisibility(View.GONE);
                thanki.setVisibility(View.VISIBLE);
                gifImageView.setEnabled(true);
                waait();
            }
        });
        dialog.show();
    }

    private void waait() {
        final Handler handler = new Handler();
        final Runnable doNextActivity = new Runnable() {
            @Override
            public void run() {
                // Intent to jump to the next activity.
                dialog.dismiss(); // so the splash activity goes away
            }
        };

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                handler.post(doNextActivity);
            }
        }.start();

    }

    private void getJSON(final String urlWebService) {
        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                Loading.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (!(null == getActivity())) {
                    try {
                        if (null != s) {
                            if (s.length() > 10) {
                                Log.d(TAG, "loadIntoList > " + s);
                                loadIntoList(s);
                            } else {
                                Loading.dismiss();

                                Log.d(TAG, "EMPTY > " + s);
                            }
                        } else {
                            Log.d(TAG, "getOfflineData: ");
                            Login.Message(getString(R.string.cheack_int_con),context);
                        }
                    } catch (JSONException e) {
                        Login.Message(getString(R.string.cheack_int_con),context);
                        Log.d(TAG, "CAT " + e.getMessage());
                        e.printStackTrace();
                    }
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
                    Log.d(TAG, "ERR > " + e.getMessage());
                    return null;
                }
            }
        }
        GetJSON_Hospital getJSON = new GetJSON_Hospital();
        getJSON.execute();
    }

    private void loadIntoList(String json) throws JSONException {
        try {
            if (!(json == null)) {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String ServicesName = obj.getString("ServiceEnglishName");
                    String NumDay = obj.getString("NumDay");
                    String inDay = obj.getString("inDay");
                    String numinday = obj.getString("numinday");

                    if (obj.getString("ParentID").trim().equals(type))
                        items.add(new DrugsModel(ServicesName, NumDay, inDay, numinday));
                    else if(type.equals("1000051") || type.equals("10197603")){
                        items.add(new DrugsModel(ServicesName, NumDay, inDay, numinday));
                    }
                }
                if (items.size() > 0) {
                    adapter = new DrugsAdapter(getActivity(), items, this);
                    recyclerView.setAdapter(adapter);
                }
                else noData();


            }
        } catch (Exception e) {
            Log.d(TAG, "loadIntoList: ERROR > " + e.getMessage());
        }
        finally {
            if (Loading.isShowing())
                Loading.dismiss();
        }

    }

 /*   private void getOfflineData() {
        try {
            Cursor cursor = new UserDrugsDatabase(getActivity()).getData();
            if (cursor.getCount() > 0) {
                if (!Loading.isShowing())
                    Loading.show();
                while (cursor.moveToNext()) {
                    items.add(new DrugsItems(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)));
                }
                adapter = new DrugsAdapter(getActivity(), items, this::onCLick);
                recyclerView.setAdapter(adapter);
                if (Loading.isShowing())
                    Loading.dismiss();
            } else {
                NoInternet();
            }
        } catch (NullPointerException e) {
            if (Loading.isShowing())
                Loading.dismiss();
            NoInternet();
        }

    }*/
    public void noData(){
        ((TextView) view.findViewById(R.id.textView35)).setVisibility(View.VISIBLE);
        ((ImageView) view.findViewById(R.id.imageView24)).setVisibility(View.VISIBLE);
    }

    @Override
    public void onCLick(int p) {

    }


}