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
import com.medicate_int.mymedicate.models.CitiesModel;

import java.util.ArrayList;
public class cityCustomAdapterSendData extends ArrayAdapter<CitiesModel> {
        public cityCustomAdapterSendData(Context context, ArrayList<CitiesModel> cityList)
        {
            super(context,0,cityList);
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_row_spinner_senddata, parent, false);
            }
            TextView txtCityName = convertView.findViewById(R.id.txtCityName);
            TextView txtCityId = convertView.findViewById(R.id.txtCityId);
            CitiesModel currentItem = getItem(position);
            if(currentItem != null)
            {
                txtCityName.setText(currentItem.getCityName());
                txtCityId.setText(currentItem.getCityId());
            }
            return convertView;
        }
}

