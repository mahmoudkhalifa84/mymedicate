package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.models.MedicalNetworkModel;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.ui.main_screen.medical_network.MedicalNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicalNetworkAdapter extends RecyclerView.Adapter<MedicalNetworkAdapter.News> implements Filterable {
    Context context;
    CacheHelper statics;
    onCLickLis MonCLickLi;
    String filter_text;
    List<MedicalNetworkModel> list_all;
    List<MedicalNetworkModel> list_filter;
    //  List<MedicalNetworkModel> list_all_fiterdr;


    public MedicalNetworkAdapter(Context context, List<MedicalNetworkModel> listAll, onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.list_all = listAll;
        this.list_filter = listAll;
        //  this.list_all_fiterdr = listAll;
        filter_text = "";
    }


    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_row, parent, false);
        statics = new CacheHelper(context);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        if (!list_filter.isEmpty()) {
            MonCLickLi.listIsEmpty(false);
            MedicalNetworkModel model = list_filter.get(position);
            if (SetLocal.getLong(context).equals("ar")) {
                holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img_ar));
                holder.loca.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rows_loca));
                holder.name.setText(model.getCompanyArabicName());

            } else if (SetLocal.getLong(context).equals("fr")) {

                holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img));
                holder.loca.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rows_loca_en));
                holder.name.setText(model.getCompanyFrenshName());

            } else {
                holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img));
                holder.loca.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rows_loca_en));
                holder.name.setText(model.getCompanyEnglishName());
            }
            holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
            holder.city.setText(model.getCityName());
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.new_medicate_logo_smaill));
        } else MonCLickLi.listIsEmpty(true);
    }

    public void clear() {
        int size = list_filter.size();
        list_filter.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list_filter == null ? 0 : list_filter.size();
    }


    public List<MedicalNetworkModel> getIm() {
        return list_filter;
    }

    public void update() {
        getFilter();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filter_text = constraint.toString().toLowerCase().trim();
                FilterResults filterResults = new FilterResults();
                String key = constraint.toString().toLowerCase().trim();
                List<MedicalNetworkModel> newList = new ArrayList<>();
                // EmptyFilter(statics.getMY_NETWORK());
                Log.d(MedicalNetwork.TAG, "performFiltering: SelectedCity = " + MedicalNetwork.selectedCity);
                Log.d(MedicalNetwork.TAG, "performFiltering: selectedCountry = " + MedicalNetwork.selectedCountry);
                try {
                    if (constraint.length() == 0) {
                        for (MedicalNetworkModel row : list_all) {
                            if (row.getCountryID().equals(MedicalNetwork.selectedCountry.trim())
                                    && (MedicalNetwork.selectedCity.equalsIgnoreCase("all") || row.getCityID().trim().equals(MedicalNetwork.selectedCity.trim()))
                                    && (MedicalNetwork.filter_type.contains(Objects.requireNonNull(row.getCompanyType())))) {
                                newList.add(row);
                            }
                        }
                    } else {
                        for (MedicalNetworkModel row : list_all) {
                            if (
                                    ((row.getCompanyArabicName() != null && row.getCompanyArabicName().toLowerCase().contains(key.toLowerCase()))
                                            || (row.getCompanyEnglishName() != null && (row.getCompanyEnglishName()).toLowerCase().contains(key.toLowerCase()))
                                            || (row.getCompanyFrenshName() != null && (row.getCompanyFrenshName()).toLowerCase().contains(key.toLowerCase())))
                                            && (row.getCountryID() != null && row.getCountryID().trim().equals(MedicalNetwork.selectedCountry.trim()))
                                            && (MedicalNetwork.selectedCity.equals("all") || row.getCityID().equals(MedicalNetwork.selectedCity.trim()))
                                            && (MedicalNetwork.filter_type.contains(Objects.requireNonNull(row.getCompanyType())))
                            ) {
                                newList.add(row);
                            }
                        }
                    }

                    //   list_filter = newList;
                } catch (Exception e) {
                    Log.d(MedicalNetwork.TAG, "ERROR -----> " + e.getMessage());
                }
                filterResults.count = newList.size();
                filterResults.values = newList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list_filter = ((List<MedicalNetworkModel>) results.values);

                notifyDataSetChanged();
                if (list_filter == null || list_filter.isEmpty()) MonCLickLi.listIsEmpty(true);

            }
        };
    }

  /*  public void AdvanceFilter(String type) {
        list_all_fiterdr = list_all;
        List<MedicalNetworkModel> newitem = new ArrayList<>();
        for (int i = 0; i < list_all_fiterdr.size(); i++) {
            if (type.contains(list_all_fiterdr.get(i).getCompanyType().toString().trim()))
                newitem.add(list_all_fiterdr.get(i));
            *//*if (list_all_fiterdr.get(i).getCompanyType().contains(type))
                newitem.add(list_all_fiterdr.get(i));
        *//*
        }
        Log.d("MWN", "AdvanceFilter: new TYPE COUNT > " + newitem.size());
        list_all_fiterdr = newitem;
        getFilter().filter(filter_text);
    }*/

    /*   public void RemoveAvanceFilter() {
           list_all_fiterdr = list_all;
           getFilter().filter(filter_text);
       }
   */
    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView city, name;
        ImageView imageView, loca;
        onCLickLis onCLickLi;
        ConstraintLayout constraintLayout;

        public News(@NonNull View itemView, onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            constraintLayout = itemView.findViewById(R.id.rows_news);
            imageView = itemView.findViewById(R.id.net_img);
            city = itemView.findViewById(R.id.item_hosp_city);
            name = itemView.findViewById(R.id.item_hosp_name);
            loca = itemView.findViewById(R.id.img_location);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick(getAdapterPosition());
        }
    }

    public interface onCLickLis {
        void onCLick(int p);

        void listIsEmpty(boolean b);
    }
}
