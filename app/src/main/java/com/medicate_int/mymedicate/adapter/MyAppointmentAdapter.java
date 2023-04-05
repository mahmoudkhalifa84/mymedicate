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
import com.medicate_int.mymedicate.models.MyAppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.News> implements Filterable {
    Context context;
    CacheHelper statics;
    MyHandler myHandler;
    List<MyAppointmentModel> list_all;
    List<MyAppointmentModel> list_filter;
    private static final String TAG = "MyAppointmentAdapterTAG";


    public MyAppointmentAdapter(Context context, List<MyAppointmentModel> list_all, MyHandler myHandler) {
        this.myHandler = myHandler;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    public MyAppointmentAdapter(Context context, MyHandler myHandler) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.list_filter = new ArrayList<>();
        this.myHandler = myHandler;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_appointment_row, parent, false);
        statics = new CacheHelper(context);
        return new News(view, myHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        MyAppointmentModel items = list_filter.get(position);
         holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
                holder.name.setText(items.getCompanyArabicName());
                holder.date.setText(items.getDate());
             //   holder.state.setText(items.get()); // todo
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

    public List<MyAppointmentModel> getIm() {
        return list_filter;
    }

    public void removeAt(int position) {
        list_filter.remove(position);
        list_all.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_filter.size());
    }

    public void addNew(MyAppointmentModel items) {
            list_filter.add(items);
            notifyItemInserted(0);
            notifyItemRangeChanged(0, list_filter.size());
            list_all.add(items);
    }/*
    public void addNew(MyAppointmentModel items) {
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
                List<MyAppointmentModel> newList = new ArrayList<>();
                // EmptyFilter(statics.getMY_NETWORK());
                if (constraint == null || filter_text.length() < 1) {
                    Log.d(TAG, "performFiltering: EMPTY");
                    newList = list_all;
                } else {
                    for (MyAppointmentModel row : list_all) {
                        if (row.getCompanyArabicName().toLowerCase().trim().contains(filter_text) ||
                                (row.getDocname().toLowerCase().trim().contains(filter_text))) {
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
                list_filter = (List<MyAppointmentModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        MyHandler onCLickLi;
        TextView name, state, date;
        CardView card;

        public News(@NonNull View itemView, MyAppointmentAdapter.MyHandler onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            name = itemView.findViewById(R.id.app_name);
            state = itemView.findViewById(R.id.app_state);
            date = itemView.findViewById(R.id.app_date);
            card = itemView.findViewById(R.id.app_card);
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
