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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.ApiClient;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.models.BookingDoctorDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingDoctorAdapter extends RecyclerView.Adapter<BookingDoctorAdapter.News> implements Filterable {
    Context context;
    CacheHelper statics;
    MyHandler myHandler;
    List<BookingDoctorDataModel> list_all;
    List<BookingDoctorDataModel> list_filter;
    private static final String TAG = "BookingDoctorAdapter";


    public BookingDoctorAdapter(Context context, List<BookingDoctorDataModel> list_all, MyHandler myHandler) {
        this.myHandler = myHandler;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    public BookingDoctorAdapter(Context context, MyHandler myHandler) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.list_filter = new ArrayList<>();
        this.myHandler = myHandler;
    }


    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_doctor_row_ui, parent, false);
        statics = new CacheHelper(context);
        return new News(view, myHandler);
    }


    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        BookingDoctorDataModel items = list_filter.get(position);
        try {
            holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
            holder.bok_name.setText(items.getDoctorName());
            holder.bok_degree.setText(" - ".concat(items.getDegree(context)));
            holder.bok_time.setText(items.getWaitTime());
            Picasso.with(context).load(ApiClient.WEBSITE_URL + "/ci_sereen/upload/" + items.getImage()).placeholder(R.drawable.bok_user).into(holder.bok_user);
            holder.bok_time.setText(items.getWaitTime());
            holder.bok_sp.setText(items.getSpecialization());
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException: " + e.getMessage());
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

    public List<BookingDoctorDataModel> getIm() {
        return list_filter;
    }

    public void removeAt(int position) {
        list_filter.remove(position);
        list_all.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_filter.size());
    }

    public void addNew(BookingDoctorDataModel items) {
        list_filter.add(items);
        notifyItemInserted(list_filter.size() - 1);
        notifyItemRangeChanged(list_filter.size() - 1, list_filter.size());
        list_all.add(items);
    }

    public void addAll(List<BookingDoctorDataModel> items) {
        list_filter.clear();
        list_all.clear();
        list_all.addAll(items);
        list_filter.addAll(items);
        notifyItemInserted(list_filter.size() - 1);
        notifyItemRangeChanged(list_filter.size() - 1, list_filter.size());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter_text = "" + constraint.toString().toLowerCase().trim();
                FilterResults filterResults = new FilterResults();
                List<BookingDoctorDataModel> newList = new ArrayList<>();
                // EmptyFilter(statics.getMY_NETWORK());
                if (constraint == null || filter_text.length() < 1) {
                    Log.d(TAG, "performFiltering: EMPTY");
                    newList = list_all;
                } else {
                    for (BookingDoctorDataModel row : list_all) {
                        if (row.getDoctorName().toLowerCase().trim().contains(filter_text)) {
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
                list_filter = (List<BookingDoctorDataModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class News extends RecyclerView.ViewHolder {
        MyHandler onCLickLi;
        TextView bok_name, bok_degree, bok_sp,bok_time;
        ImageView bok_user;
        CardView card;

        public News(@NonNull View itemView, BookingDoctorAdapter.MyHandler onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            bok_name = itemView.findViewById(R.id.bok_name);
            bok_degree = itemView.findViewById(R.id.bok_degree);
            bok_time = itemView.findViewById(R.id.bok_time);
            bok_user = itemView.findViewById(R.id.bok_img);
            bok_sp = itemView.findViewById(R.id.bok_sp);
            card = itemView.findViewById(R.id.bok_ly);
            card.setOnClickListener((a)-> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
            itemView.findViewById(R.id.bok_view).setOnClickListener((a) -> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
            itemView.findViewById(R.id.bok_bok).setOnClickListener((a) -> onCLickLi.onCLickBook(getAbsoluteAdapterPosition()));
        }


    }

    public interface MyHandler {
        void onCLickView(int p);

        void onCLickBook(int p);
    }
}
