package com.medicate_int.mymedicate.ui.main_screen.booking;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.LoadingDialog;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BookAppointmentServiceOrOperation extends Fragment implements BookingCityItemAdabter.onCLickLis2 {

    View view;
    private ArrayList<BookingSpecializationModel> itemsCity;
    String cityURL;
    RecyclerView recyclerView;
    EditText editText;
    LinearLayout sec;
    BookingCityItemAdabter adapter;
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.booking_service, container, false);
        loadingDialog = new LoadingDialog(getActivity(), R.style.PauseDialog);
        cityURL = "http://www.medicateint.com/data2/getCeties";
        editText = view.findViewById(R.id.searchViewCity);
        sec = view.findViewById(R.id.ll_ser_sec);
        sec.setVisibility(View.GONE);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapter.getFilter().filter(s);
                }catch (NullPointerException e){
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerView = view.findViewById(R.id.booking_list_city);
        if (CheckConnection.isNetworkConnected(requireActivity())) { //returns true if internet available
            getJSON(cityURL);

        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.no_internet_con), Toast.LENGTH_LONG).show();
            onDestroy();
        }

        return view;
    }

    private void getJSON(final String urlWebService) {
        class GetJSON_Hospital extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    iniSpinnerCity(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    loadingDialog.dismiss();
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

    private void iniSpinnerCity(String json) throws JSONException {
        if (!(json == null)) {
            itemsCity = new ArrayList<>();
            itemsCity.add(new BookingSpecializationModel("جميع المدن", 0));
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("CityName");
                itemsCity.add(new BookingSpecializationModel(name, 1));
            }
            adapter = new BookingCityItemAdabter(getActivity(), itemsCity, this::onCLick2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            //citySpinner.setVisibility(View.VISIBLE);
        } else {
            Snackbar.make(view, getActivity().getResources().getString(R.string.cheack_int_con), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCLick2(int p) {
        recyclerView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
        recyclerView.setVisibility(View.GONE);
        sec.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.popout));
        sec.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), adapter.getIm().get(p).getCity_name(), Toast.LENGTH_LONG).show();

    }
}