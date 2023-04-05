package com.medicate_int.mymedicate.ui.main_screen.my_account;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckBalance extends Fragment implements Moudle.MoudleInterface {

    View view;
    CacheHelper statics;
    boolean internet = false;
    Dialog dailog, no_int_did;
    private String DATA_URL = "https://www.medicateint.com/data2/BeneficiaryDetails/";
    private String TAG = "MYACO";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CheckConnection.isNetworkConnected(getActivity())) {
            internet = true;
            LoadingDigSetup();
        } else Moudle.NoInternet(getActivity(),this);

    }


    public void LoadingDigSetup() {
        dailog = new Dialog(getActivity());
        dailog.setContentView(R.layout.loading_layout);
        dailog.setCancelable(true);
        dailog.setCanceledOnTouchOutside(false);
        dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dailog.dismiss();
                getActivity().onBackPressed();
            }
        });
        dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.my_acount__chack_balance, container, false);
        statics = new CacheHelper(getActivity());
        if (internet)
            getJSON(DATA_URL.concat(statics.getCardNumber()));
        view.findViewById(R.id.imageView57).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void getJSON(final String urlWebService) {
        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dailog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (!(null == getActivity())) {
                    try {
                        if (null != s) {
                            if (s.length() > 10)
                                LoadData(s);
                            else {
                                dailog.dismiss();
                                Login.Message(getString(R.string.uknown_error), getActivity());

                            }
                        } else
                            Login.Message(getString(R.string.cheack_int_con), getActivity());
                    } catch (JSONException e) {
                        dailog.dismiss();
                        Login.Message(getString(R.string.uknown_error), getActivity());
                        Log.d(TAG, "onPostExecute: ERROR 1 " + e.getMessage());
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
                    con.setConnectTimeout(10000);
                    con.setReadTimeout(10000);
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    Log.d(TAG, "DATA > > > " + sb.toString());
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.d(TAG, "onPostExecute: ERROR 2 " + e.getMessage());
                    return null;
                }
            }
        }
        GetJSON_Hospital getJSON = new GetJSON_Hospital();
        getJSON.execute();
    }

    private void LoadData(String json) throws JSONException {
        LinearLayout sho_data = view.findViewById(R.id.ll_user_info);
        if (!(json == null)) {
            JSONArray obj = new JSONArray(json);
            ((TextView) view.findViewById(R.id.data_name)).setText(obj.getJSONObject(0).getString("Name"));
            ((TextView) view.findViewById(R.id.data_card_state)).setText(CardState(obj.getJSONObject(0).getString("CardStatus")));
            ((TextView) view.findViewById(R.id.card_amount)).setText(obj.getJSONObject(0).getString("amount").concat(" د.ل"));
            if (obj.getJSONObject(0).getString("CardStatus").equals("1")) {
                ((TextView) view.findViewById(R.id.data_card_state)).setTextColor(Color.GREEN);
            } else {
                ((TextView) view.findViewById(R.id.data_card_state)).setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            dailog.dismiss();
            sho_data.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.btn_an2));
            sho_data.setVisibility(View.VISIBLE);
        } else {
            dailog.dismiss();
            Login.Message(getString(R.string.no_internet_con), getActivity());
        }
    }

    public String CardState(@NonNull String a) {
        switch (a) {
            case "0":
                return getString(R.string.card_state_disable);
            case "1":
                return getString(R.string.card_state_enable);
            case "2":
                return getString(R.string.card_state_dispanded);
            case "3":
                return getString(R.string.card_state_discorapted);
        }
        return getString(R.string.uknown_error);
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