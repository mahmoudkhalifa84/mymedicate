package com.medicate_int.mymedicate.trash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.models.CountriesModel;

import java.util.ArrayList;

public class countryCustomAdapterSendData extends ArrayAdapter<CountriesModel> {
    public countryCustomAdapterSendData(Context context, ArrayList<CountriesModel> countryList)
    {
        super(context,0,countryList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView( position,  convertView,  parent );
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView( position,  convertView,  parent );
    }
    private View initView(int position, View convertView, ViewGroup parent )
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.country_row_spinner_send_data, parent, false);
        }
        TextView txtCountryName = convertView.findViewById(R.id.txtCountryName);
        TextView txtCountryId = convertView.findViewById(R.id.txtCountryId);
        CountriesModel currentItem = getItem(position);
        if(currentItem != null)
        {
            txtCountryName.setText(currentItem.getCountryName());
            txtCountryId.setText(currentItem.getCountryId());
        }
        return convertView;
    }
}

