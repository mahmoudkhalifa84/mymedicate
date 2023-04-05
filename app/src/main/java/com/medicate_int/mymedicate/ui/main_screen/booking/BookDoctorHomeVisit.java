package com.medicate_int.mymedicate.ui.main_screen.booking;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.medicate_int.mymedicate.adapter.BookingCityItemAdabter;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.module.CheckConnection;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.adapter.BookingAdvanseItemAdabter;
import com.medicate_int.mymedicate.ui.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BookDoctorHomeVisit extends Fragment implements BookingAdvanseItemAdabter.onCLickLis, BookingCityItemAdabter.onCLickLis2
        , View.OnClickListener, Moudle.MoudleInterface {
    View view;
    Dialog dialog_shp, dialog_city;
    String cityURL;
    ArrayList<BookingSpecializationModel> item, lists;
    BookingAdvanseItemAdabter adabter;
    BookingCityItemAdabter cityAd;
    TextInputEditText txt_shp, txt_city, txt_erya, p_name, p_age, p_gender;
    TextInputLayout txt_shp_lay, txt_city_lay, p_gender_lay;
    EditText editText;
    Dialog select_gender;
    private static final String TAG = "BookingHome";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.booking_to_home, container, false);
        p_gender = view.findViewById(R.id.home_s_gender);
        p_gender.setOnClickListener(this::onClick);
        view.findViewById(R.id.imageView51).setOnClickListener((a)-> getActivity().onBackPressed());
        view.findViewById(R.id.textView95).setOnClickListener((a)-> getActivity().onBackPressed());
        view.findViewById(R.id.textView95).setOnClickListener((a)-> {
            Login.Message(getString(R.string.not_av_yet),getActivity());
        });
        view.findViewById(R.id.home_s_gender_lay).setOnClickListener(this::onClick);
        view.findViewById(R.id.home_s_city_lay).setOnClickListener(this::onClick);
        view.findViewById(R.id.home_s_city).setOnClickListener(this::onClick);
        txt_shp = view.findViewById(R.id.home_s_shp);
        txt_city = view.findViewById(R.id.home_s_city);
        view.findViewById(R.id.home_s_shp).setOnClickListener(this::onClick);
        view.findViewById(R.id.home_s_shp_lay).setOnClickListener(this::onClick);
        HtmlTextView htmlTextView = view.findViewById(R.id.legal_html);
        htmlTextView.setHtml(R.raw.ar, new HtmlHttpImageGetter(htmlTextView));
        if (CheckConnection.isNetworkConnected(getActivity())) {
            cityURL = "http://www.medicateint.com/data2/getCeties";
            getJSON(cityURL);
        } else {
            // todo
            Moudle.NoInternet(getActivity(), this);
        }

        return view;

    }

    private void iniSpinnerCity(String json) throws JSONException {
        lists = new ArrayList<>();
        if (!(json == null)) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("CityName");
                lists.add(new BookingSpecializationModel(name, R.drawable.white));
            }
            cityAd = new BookingCityItemAdabter(getActivity(), lists, this::onCLick2);
        } else {
            Snackbar.make(view, view.getResources().getString(R.string.cheack_int_con), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void iniSpinnerShp() {
        // View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.choase_shp_doc_lay,null);
        // RecyclerView  recyclerView = view2.findViewById(R.id.rec_123);
        RecyclerView recyclerView = new RecyclerView(view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(addItems());
        dialog_shp = new BottomSheetDialog(view.getContext());
        dialog_shp.setContentView(recyclerView);
        dialog_shp.show();
    }

    private BookingAdvanseItemAdabter addItems() {
        item = new ArrayList<>();
        item.add(new BookingSpecializationModel(getString(R.string.sp_1), R.drawable.s_1));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_2), R.drawable.s_2));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_3), R.drawable.s_3));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_4), R.drawable.s_4));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_5), R.drawable.s_5));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_6), R.drawable.s_6));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_7), R.drawable.s_7));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_8), R.drawable.s_8));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_9), R.drawable.s_9));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_10), R.drawable.s_10));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_11), R.drawable.s_11));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_12), R.drawable.s_12));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_13), R.drawable.s_13));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_14), R.drawable.s_14));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_15), R.drawable.s_15));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_16), R.drawable.s_16));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_17), R.drawable.s_17));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_18), R.drawable.s_18));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_19), R.drawable.s_19));


        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_20), R.drawable.s_20));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_21), R.drawable.s_21));
        // item.add(new BookingSpItem(getResources().getString(R.string.sp_22), R.drawable.s_22));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_23), R.drawable.s_23));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_24), R.drawable.s_24));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_26), R.drawable.s_26));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_27), R.drawable.s_27));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_28), R.drawable.s_28));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_29), R.drawable.s_29));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_30), R.drawable.s_30));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_31), R.drawable.s_31));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_32), R.drawable.s_32));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_33), R.drawable.s_33));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_34), R.drawable.s_34));
        // item.add(new BookingSpItem(getResources().getString(R.string.sp_35), R.drawable.s_35));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_36), R.drawable.s_36));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_37), R.drawable.s_37));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_38), R.drawable.s_38));
        // item.add(new BookingSpItem(getResources().getString(R.string.sp_39), R.drawable.s_39));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_40), R.drawable.s_40));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_41), R.drawable.s_41));

        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_42), R.drawable.s_42));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_43), R.drawable.s_43));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_44), R.drawable.s_44));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_45), R.drawable.s_45));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_46), R.drawable.s_46));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_47), R.drawable.s_47));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_48), R.drawable.s_48));
        //item.add(new BookingSpItem(getResources().getString(R.string.sp_49), R.drawable.s_49));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_50), R.drawable.s_50));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_51), R.drawable.s_51));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_52), R.drawable.s_52));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_53), R.drawable.s_53));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_54), R.drawable.s_54));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_55), R.drawable.s_55));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_56), R.drawable.s_56));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_57), R.drawable.s_57));
        //  item.add(new BookingSpItem(getResources().getString(R.string.sp_58), R.drawable.s_58));
        //  item.add(new BookingSpItem(getResources().getString(R.string.sp_59), R.drawable.s_59));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_60), R.drawable.s_60));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_61), R.drawable.s_62));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_63), R.drawable.s_63));
        //   item.add(new BookingSpItem(getResources().getString(R.string.sp_64), R.drawable.s_64));// todo
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_65), R.drawable.s_65));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_66), R.drawable.s_66));
        //  item.add(new BookingSpItem(getResources().getString(R.string.sp_67), R.drawable.s_67));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_68), R.drawable.s_68));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_69), R.drawable.s_69));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_70), R.drawable.s_70));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_71), R.drawable.s_71));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_72), R.drawable.s_72));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_73), R.drawable.s_73));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_74), R.drawable.s_74));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_75), R.drawable.s_75));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_76), R.drawable.s_76));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_77), R.drawable.s_77));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_78), R.drawable.s_78));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_79), R.drawable.s_79));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_80), R.drawable.s_80));
        //  item.add(new BookingSpItem(getResources().getString(R.string.sp_81), R.drawable.s_81)); // todo
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_82), R.drawable.s_82));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_83), R.drawable.s_28)); // todo
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_84), R.drawable.s_84));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_85), R.drawable.s_85));
        //   item.add(new BookingSpItem(getResources().getString(R.string.sp_86), R.drawable.s_86));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_87), R.drawable.s_87));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_88), R.drawable.s_88));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_89), R.drawable.s_89));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_90), R.drawable.s_90));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_91), R.drawable.s_91));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_92), R.drawable.s_92));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_93), R.drawable.s_93));
        //   item.add(new BookingSpItem(getResources().getString(R.string.sp_94), R.drawable.s_94));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_95), R.drawable.s_95));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_96), R.drawable.s_96));
        //   item.add(new BookingSpItem(getResources().getString(R.string.sp_97), R.drawable.s_97));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_98), R.drawable.s_98));
        //     item.add(new BookingSpItem(getResources().getString(R.string.sp_99), R.drawable.s_99));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_100), R.drawable.s_100));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_101), R.drawable.s_101));
        item.add(new BookingSpecializationModel(getResources().getString(R.string.sp_102), R.drawable.s_102));
        adabter = new BookingAdvanseItemAdabter(getActivity(), item, this::onCLick);
      /*  String print  = "";
        for (BookingSpItem row: item
             ) {
            print += row.getCity_name() + "\n";

        }
        Log.d(TAG, print);*/
        return adabter;

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
                    if (urlWebService.equals(cityURL)) {
                        try {
                            iniSpinnerCity(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    @Override
    public void onCLick(int p) {
        Log.d(TAG, "onCLick: > " + item.get(p).getCity_name());
       txt_shp.setText(item.get(p).getCity_name());
        dialog_shp.dismiss();

    }

    @Override
    public void onCLick2(int p) {
        txt_city.setText(cityAd.getIm().get(p).getCity_name());
        editText.setText("");
        dialog_city.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_s_shp:
            case R.id.home_s_shp_lay:
                iniSpinnerShp();
                break;
            case R.id.home_s_city:
            case R.id.home_s_city_lay:
                CitySpinner();
                break;
            case R.id.home_s_gender:
            case R.id.home_s_gender_lay:
                SelectGender();
                break;


        }
    }

    public void CitySpinner() {
        View view1 = LayoutInflater.from(view.getContext()).inflate(R.layout.bottom_select_city_home, null);
        RecyclerView recyclerView = view1.findViewById(R.id.booking_list_city);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(cityAd);
        editText = view1.findViewById(R.id.searchViewCity);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityAd.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog_city = new BottomSheetDialog(view.getContext());
        dialog_city.setContentView(view1);
        dialog_city.show();

    }

    public void SelectGender() {
        select_gender = new Dialog(getActivity(), R.style.PauseDialog);
        select_gender.setContentView(R.layout.select_soical_state);
        TextView man = select_gender.findViewById(R.id.sex_man);
        select_gender.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView woman = select_gender.findViewById(R.id.sex_woman);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_gender.setText(R.string.men);
                select_gender.dismiss();
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_gender.setText(R.string.women);
                select_gender.dismiss();
            }
        });
        select_gender.setCancelable(true);
        select_gender.show();
    }

    @Override
    public void onBack(boolean bool) {
        getActivity().onBackPressed();
    }

    @Override
    public void okClicked(boolean bool) {

    }
}