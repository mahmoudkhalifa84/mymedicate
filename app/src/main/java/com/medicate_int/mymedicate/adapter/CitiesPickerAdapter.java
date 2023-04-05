package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.models.CitiesModel;
import com.medicate_int.mymedicate.module.CacheHelper;

import java.util.ArrayList;
import java.util.List;

public class CitiesPickerAdapter extends RecyclerView.Adapter<CitiesPickerAdapter.News> implements Filterable {
    List<CitiesModel> full, filterd;
    Context context;
    CacheHelper statics;
    CitiesPickerAdapter.CitiesClicksInterface MonCLickLi;

    public CitiesPickerAdapter(Context context, List<CitiesModel> CitiesModel, CitiesPickerAdapter.CitiesClicksInterface onClick) {
        this.MonCLickLi = onClick;
        this.context = context;
        this.full = CitiesModel;
        filterd = this.full;
        statics = new CacheHelper(context.getApplicationContext());
    }

    public List<CitiesModel> getFilteredList() {
        return filterd;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_city_row, parent, false);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        holder.text.setText(filterd.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return filterd.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                String key = constraint.toString();
                if (key.trim().isEmpty() || key.trim().length() == 0) {
                    filterd = full;
                    filterResults.values = filterd;
                    filterResults.count = filterd.size();
                } else {
                    List<CitiesModel> newList = new ArrayList<>();
                    for (CitiesModel row : full) {
                        if ((row.getCityName().toLowerCase().contains(key.toLowerCase()))) {
                            newList.add(row);
                        }
                    }
                    filterResults.values = newList;
                    filterResults.count = newList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterd = (List<CitiesModel>) results.values;
                notifyDataSetChanged();

            }
        };

    }


    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        CitiesPickerAdapter.CitiesClicksInterface onCityClicked;

        public News(@NonNull View itemView, CitiesPickerAdapter.CitiesClicksInterface onCityClicked) {
            super(itemView);
            this.onCityClicked = onCityClicked;
            text = itemView.findViewById(R.id.txt_city_row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCityClicked.onCityClicked(getAbsoluteAdapterPosition());
        }
    }

    public interface CitiesClicksInterface {
        void onCityClicked(int p);
    }
}
