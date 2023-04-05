package com.medicate_int.mymedicate.ui.main_screen.user_profile.health_record;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.models.HealthRecordsModel;
import com.medicate_int.mymedicate.trash.MyDialogsHandluer;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HealthRecords extends Fragment {
    View view;
    CacheHelper statics;
    Call<List<HealthRecordsModel>> call;
    List<HealthRecordsModel> temp = new ArrayList<>();

    HealthRecordsModel healthRecordsModel ;
    private static final String TAG = "HealthRecords";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_helth_record, container, false);
        statics = new CacheHelper(getActivity());
        loadData();
        view.findViewById(R.id.rec_pers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pers_info();

            }
        });
        view.findViewById(R.id.up_appo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicPeker();

            }
        });
        view.findViewById(R.id.imageView71).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.textView16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.up_sergeon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurgaryPeker();
            }
        });
        view.findViewById(R.id.up_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicesPeker();
            }
        });

        view.findViewById(R.id.up_drugs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrugsPeker();
            }
        });
        return view;

    }
    private void loadData() {
        call = new ApiClient(ApiClient.MAIN_SYSTEM_URL).getHelthRecordsData(statics.getID());
        call.clone().enqueue(new Callback<List<HealthRecordsModel>>() {
            @Override
            public void onResponse(Call<List<HealthRecordsModel>> call, Response<List<HealthRecordsModel>> response) {
                if (!response.isSuccessful()) {
                    return;

                }
                temp = response.body() ;
                }

            @Override
            public void onFailure(Call<List<HealthRecordsModel>> call, Throwable t) {

            }
        });
    }

    public int SettexTviewData(String type){
        loadData();
        int counter = 0 ;
        for(HealthRecordsModel item : temp){
            if (item.getParentID().trim().equals(type))
            counter++;
        }
        return counter ;
    }

    private void MedicPeker() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.medical_records_checks_choser_ly);
        TextView kshofat_count = bottomSheetDialog.findViewById(R.id.kshofat_count);
        TextView thalel_count = bottomSheetDialog.findViewById(R.id.thalel_count);
        TextView xray_count = bottomSheetDialog.findViewById(R.id.xray_count);
        TextView vrsical_count = bottomSheetDialog.findViewById(R.id.vrsical_count);
        TextView eya_count = bottomSheetDialog.findViewById(R.id.eya_count);
        TextView general_count = bottomSheetDialog.findViewById(R.id.general_count);

       kshofat_count.setText(String.valueOf(SettexTviewData("21")));
        thalel_count.setText(String.valueOf(SettexTviewData("15")));
        xray_count.setText(String.valueOf(SettexTviewData("3")));
        vrsical_count.setText(String.valueOf(SettexTviewData("25")));
        eya_count.setText(String.valueOf(SettexTviewData("16")));
        general_count.setText(String.valueOf(SettexTviewData("119")));



        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.findViewById(R.id.ch_kshofat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("21", "السجل الطبي/الفحوصات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_thalel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("15", "السجل الطبي/الفحوصات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_xray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("3", "السجل الطبي/الفحوصات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_vrsical).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("25", "السجل الطبي/الفحوصات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_eya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("16", "السجل الطبي/الفحوصات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_general).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("119", "السجل الطبي/الفحوصات الطبية");

            }
        });

        bottomSheetDialog.show();
    }

    private void DrugsPeker() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.medical_records_drugs_choser_ly);
        bottomSheetDialog.setCancelable(true);
        TextView awram_count = bottomSheetDialog.findViewById(R.id.awram_count);
        TextView mozmna_count = bottomSheetDialog.findViewById(R.id.mozmna_count);
        TextView drugs_other_count = bottomSheetDialog.findViewById(R.id.drugs_other_count);


        awram_count.setText(String.valueOf(SettexTviewData("26")));
        mozmna_count.setText(String.valueOf(SettexTviewData("8")));
        drugs_other_count.setText(String.valueOf(SettexTviewData("27")));


        bottomSheetDialog.findViewById(R.id.ch_awram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("26", "السجل الطبي/الادوية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_mozmna).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("8", "السجل الطبي/الادوية");
            }
        });
        bottomSheetDialog.findViewById(R.id.ch_drugs_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("27", "السجل الطبي/الادوية");
            }
        });
        bottomSheetDialog.show();
    }

    private void ServicesPeker() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.medical_records_services_choser_ly);
        bottomSheetDialog.setCancelable(true);
        TextView tagmel_count = bottomSheetDialog.findViewById(R.id.tagmel_count);
        TextView dental_count = bottomSheetDialog.findViewById(R.id.dental_count);
        TextView glasses_count = bottomSheetDialog.findViewById(R.id.glasses_count);
        TextView alsam3_count = bottomSheetDialog.findViewById(R.id.alsam3_count);

        tagmel_count.setText(String.valueOf(SettexTviewData("23")));
        dental_count.setText(String.valueOf(SettexTviewData("6")));
        glasses_count.setText(String.valueOf(SettexTviewData("13")));
        alsam3_count.setText(String.valueOf(SettexTviewData("14")));

        bottomSheetDialog.findViewById(R.id.ch_tagmel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("23", "السجل الطبي/الخدمات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_dental).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("6", "السجل الطبي/الخدمات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_glasses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("13", "السجل الطبي/الخدمات الطبية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_alsam3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("14", "السجل الطبي/الخدمات الطبية");

            }
        });
        bottomSheetDialog.show();
    }

    private void SurgaryPeker() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.medical_records_surgrys_choser_ly);
        bottomSheetDialog.setCancelable(true);
        TextView masalck_count = bottomSheetDialog.findViewById(R.id.masalck_count);
        TextView nessa_count = bottomSheetDialog.findViewById(R.id.nessa_count);
        TextView adam_count = bottomSheetDialog.findViewById(R.id.adam_count);
        TextView ent_count = bottomSheetDialog.findViewById(R.id.ent_count);
        TextView general_count = bottomSheetDialog.findViewById(R.id.general_count);
        TextView eyes_count = bottomSheetDialog.findViewById(R.id.eyes_count);
        TextView hart_count = bottomSheetDialog.findViewById(R.id.hart_count);
        TextView a3sap_count = bottomSheetDialog.findViewById(R.id.a3sap_count);
        TextView gldya_count = bottomSheetDialog.findViewById(R.id.gldya_count);
        TextView face_count = bottomSheetDialog.findViewById(R.id.face_count);

        masalck_count.setText(String.valueOf(SettexTviewData("12")));
        nessa_count.setText(String.valueOf(SettexTviewData("18")));
        adam_count.setText(String.valueOf(SettexTviewData("272")));
        ent_count.setText(String.valueOf(SettexTviewData("316")));
        a3sap_count.setText(String.valueOf(SettexTviewData("343")));
        gldya_count.setText(String.valueOf(SettexTviewData("1019024")));
        face_count.setText(String.valueOf(SettexTviewData("10196210")));
        general_count.setText(String.valueOf(SettexTviewData("605")));
        eyes_count.setText(String.valueOf(SettexTviewData("17")));
        hart_count.setText(String.valueOf(SettexTviewData("5")));



        if (!statics.getUserGender().equals("2")) {
            bottomSheetDialog.findViewById(R.id.ch_nessa).setVisibility(View.GONE);
            bottomSheetDialog.findViewById(R.id.dsfs).setVisibility(View.GONE);
        }

        bottomSheetDialog.findViewById(R.id.ch_masalck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("12", "السجل الطبي/العمليات الجراحية");
            }
        });
        bottomSheetDialog.findViewById(R.id.ch_nessa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("18", "السجل الطبي/العمليات الجراحية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_adam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("272", "السجل الطبي/العمليات الجراحية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_ent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("316", "السجل الطبي/العمليات الجراحية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_a3sap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("343", "السجل الطبي/العمليات الجراحية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_gldya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("1019024", "السجل الطبي/العمليات الجراحية");
            }
        });
        bottomSheetDialog.findViewById(R.id.ch_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("10196210", "السجل الطبي/العمليات الجراحية");
            }
        });
        bottomSheetDialog.findViewById(R.id.ch_general).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("605", "السجل الطبي/العمليات الجراحية");

            }
        });
        bottomSheetDialog.findViewById(R.id.ch_eyes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("17", "السجل الطبي/العمليات الجراحية");
            }
        });
        bottomSheetDialog.findViewById(R.id.ch_hart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                openActivity("5", "السجل الطبي/العمليات الجراحية");
            }
        });

        bottomSheetDialog.show();
    }

    private void openActivity(String type, String place) {
        statics.setHelthRecordType(type);
        statics.setMY_PLACE(place);
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }


    private void serg() {
        statics.setMY_PLACE("السجل الطبي/العمليات الجراحية");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void pers_info() {
        statics.setMY_PLACE("السجل الطبي/بيانات الشخصية");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void Medc_chack() {
        statics.setMY_PLACE("السجل الطبي/الفحوصات الطبية");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void mzmna() {
        statics.setMY_PLACE("السجل الطبي/الامراض المزمنة");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void hassiat() {
        statics.setMY_PLACE("السجل الطبي/الحساسيات");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void drugs() {
        statics.setMY_PLACE("السجل الطبي/الادوية");
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void Temp() {
        MyDialogsHandluer.YouAreNotInFamiily(getActivity());
    }
}