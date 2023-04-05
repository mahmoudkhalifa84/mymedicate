package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.models.MedicalFilesModel;

import java.util.ArrayList;
import java.util.List;

public class MedicalDataAdapter extends RecyclerView.Adapter<MedicalDataAdapter.News> implements Filterable {
    Context context;
    CacheHelper statics;
    MyHandler myHandler;
    List<MedicalFilesModel> list_all;
    List<MedicalFilesModel> list_filter;
    private static final String TAG = "MedicalFilesAdapter";


    public MedicalDataAdapter(Context context, List<MedicalFilesModel> list_all, MyHandler myHandler) {
        this.myHandler = myHandler;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    public MedicalDataAdapter(Context context, MyHandler myHandler) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.list_filter = new ArrayList<>();
        this.myHandler = myHandler;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_medical_files_row_ui, parent, false);
        statics = new CacheHelper(context);
        return new News(view, myHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        MedicalFilesModel items = list_filter.get(position);
        if (!items.isDeleted()) {
            try {
                holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
                holder.title.setText(items.getTitle());
                if (items.getBody().trim().isEmpty()) {
                    holder.body.setHeight(4);
                    //  holder.body.setVisibility(View.GONE);
                } else holder.body.setText(items.getBody());
                holder.date.setText(items.getDate());
                holder.type.setText(items.getDoc_type());
            } catch (NullPointerException e) {
                Log.d(TAG, "NullPointerException: " + e.getMessage());
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void RemoveAll() {
        list_filter.clear();
        list_all.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_filter.size();
    }

    public List<MedicalFilesModel> getIm() {
        return list_filter;
    }

    public void removeAt(int position) {
        list_filter.remove(position);
        list_all.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_filter.size());
    }

    public void addNew(MedicalFilesModel items) {
        if (!items.isDeleted()) {
            list_filter.add(items);
            notifyItemInserted(0);
            notifyItemRangeChanged(0, list_filter.size());
            list_all.add(items);
        }
    }/*
    public void addNew(MedicalFilesModel items) {
        if (!items.isDeleted()) {
            list_filter.add(items);
            notifyItemInserted(list_filter.size() - 1);
            notifyItemRangeChanged(list_filter.size() - 1, list_filter.size());
            list_all.add(items);
        }
    }*/

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter_text = "" + constraint.toString().toLowerCase().trim();
                FilterResults filterResults = new FilterResults();
                List<MedicalFilesModel> newList = new ArrayList<>();
                // EmptyFilter(statics.getMY_NETWORK());
                if (constraint == null || filter_text.length() < 1) {
                    Log.d(TAG, "performFiltering: EMPTY");
                    newList = list_all;
                } else {
                    for (MedicalFilesModel row : list_all) {
                        if (row.getTitle().toLowerCase().trim().contains(filter_text) ||
                                (row.getBody().toLowerCase().trim().contains(filter_text)) ||
                                (row.getDoc_type().toLowerCase().trim().contains(filter_text)) ||
                                (row.getDate().toLowerCase().trim().contains(filter_text))) {
                            newList.add(row);
                        }
                    }
                    Log.d(TAG, "performFiltering: NOT EMPTY " + newList.size());
                }
                filterResults.count = newList.size();
                filterResults.values = newList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list_filter = (List<MedicalFilesModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        MyHandler onCLickLi;
        TextView title, body, type, date;
        CardView card;

        public News(@NonNull View itemView, MedicalDataAdapter.MyHandler onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            title = itemView.findViewById(R.id.my_m_f_r_name);
            body = itemView.findViewById(R.id.my_m_f_r_body);
            type = itemView.findViewById(R.id.my_m_f_r_type);
            date = itemView.findViewById(R.id.my_m_f_r_date);
            card = itemView.findViewById(R.id.md_file_card);
            itemView.findViewById(R.id.my_m_f_r_all).setOnClickListener((a) -> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
            itemView.findViewById(R.id.my_m_f_r_all).setOnClickListener((a) -> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
            itemView.findViewById(R.id.md_file_download).setOnClickListener((a) -> onCLickLi.onCLickDownload(getAbsoluteAdapterPosition()));
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener((a) -> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            onCLickLi.onLongClick(getAbsoluteAdapterPosition());
            return true;
        }
    }

    public interface MyHandler {
        void onCLickView(int p);

        void onCLickDownload(int p);

        void onLongClick(int p);
    }
}
