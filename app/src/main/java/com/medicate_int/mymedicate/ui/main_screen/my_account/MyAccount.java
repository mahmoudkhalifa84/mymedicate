package com.medicate_int.mymedicate.ui.main_screen.my_account;

import android.content.Intent;
import android.os.Bundle;

import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.adapter.HesapeAdvanseItemAdabter;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.adapter.MedicalNetworkAdapter;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.HomeActivity;
import com.medicate_int.mymedicate.ui.Login;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyAccount extends Fragment implements MedicalNetworkAdapter.onCLickLis {
    View view;
    CacheHelper statics;
    RecyclerView recyclerView;
    List<BookingSpecializationModel> list;
    HesapeAdvanseItemAdabter adabter;
    int img = R.drawable.user_prof_holder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_my_account, container, false);
        view.findViewById(R.id.imageView48).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        addItems();
        statics = new CacheHelper(getActivity());
        recyclerView = view.findViewById(R.id.rec_view_my_account);
        recyclerView.setAdapter(adabter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void addItems() {
        list = new ArrayList<>();
        list.add(new BookingSpecializationModel(getResources().getString(R.string.balance), R.drawable.hesape_cha_balance));
        list.add(new BookingSpecializationModel(getResources().getString(R.string.ch_balance), R.drawable.heshpe_kshf_hesap));
     //   list.add(new BookingSpItem(getResources().getString(R.string.going_account), R.drawable.hesape_going_account));
        list.add(new BookingSpecializationModel(getResources().getString(R.string.int_card), R.drawable.hesape_card));
        list.add(new BookingSpecializationModel(getResources().getString(R.string.motalpat_rep), R.drawable.hesape_cliame_report));
        list.add(new BookingSpecializationModel(getResources().getString(R.string.logout), R.drawable.logout));
        adabter = new HesapeAdvanseItemAdabter(getActivity(), list, this::onCLick);
    }

    @Override
    public void onCLick(int p) {
        switch (p) {
            case 4:
                statics.Logout();
                statics.setMY_PLACE("المنزل");
                Login.Message(getResources().getString(R.string.logout_sec), getActivity());
                startActivity(new Intent(getActivity(), HomeActivity.class));
                break;
            case 0:
                statics.setMY_PLACE("الاستعلام عن الرصيد");
                startActivity(new Intent(getActivity(), HomeActivity.class));
                break;
            default: {
                Login.Message(getString(R.string.wil_be_av_soon),getActivity());
            }
        }
    }

    @Override
    public void listIsEmpty(boolean b) {

    }
}