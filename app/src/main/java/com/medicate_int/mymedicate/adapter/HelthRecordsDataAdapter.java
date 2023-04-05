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
import com.medicate_int.mymedicate.models.HealthRecordsModel;

import java.util.ArrayList;
import java.util.List;

public class HelthRecordsDataAdapter extends RecyclerView.Adapter<HelthRecordsDataAdapter.News> implements Filterable {
    Context context;
    CacheHelper statics;
    MyHandler myHandler;
    List<HealthRecordsModel> list_all;
    List<HealthRecordsModel> list_filter;
    private static String TAG = "HelthRecordsDataAdapter";


    public HelthRecordsDataAdapter(Context context, List<HealthRecordsModel> list_all, MyHandler myHandler) {
        this.myHandler = myHandler;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    public HelthRecordsDataAdapter(Context context, MyHandler myHandler) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.list_filter = new ArrayList<>();
        this.myHandler = myHandler;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_helth_record_default_row_ui, parent, false);
        statics = new CacheHelper(context);
        return new News(view, myHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        HealthRecordsModel items = list_filter.get(position);
      //  if (items.getParentID().trim().equals(type))
            try {
                holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
                holder.title.setText(items.getServiceArabicName().trim());
                holder.date.setText(items.getServiceDate().trim());
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

    public List<HealthRecordsModel> getIm() {
        return list_filter;
    }

    public void removeAt(int position) {
        list_filter.remove(position);
        list_all.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_filter.size());
    }

    public void addNew(HealthRecordsModel items) {
        list_filter.add(items);
        notifyItemInserted(list_filter.size() - 1);
        notifyItemRangeChanged(list_filter.size() - 1, list_filter.size());
        list_all.add(items);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter_text = "" + constraint.toString().toLowerCase().trim();
                FilterResults filterResults = new FilterResults();
                List<HealthRecordsModel> newList = new ArrayList<>();
                // EmptyFilter(statics.getMY_NETWORK());
                if (constraint == null || filter_text.length() < 1) {
                    Log.d(TAG, "performFiltering: EMPTY");
                    newList = list_all;
                } else {
                    for (HealthRecordsModel row : list_all) {
                        if (row.getServiceArabicName().toLowerCase().trim().contains(filter_text) ||
                                (row.getServiceArabicName().toLowerCase().trim().contains(filter_text))) {
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
                list_filter = (List<HealthRecordsModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        MyHandler onCLickLi;
        TextView title, date;
        CardView card;

        public News(@NonNull View itemView, HelthRecordsDataAdapter.MyHandler onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            title = itemView.findViewById(R.id.my_m_f_r_name);
            date = itemView.findViewById(R.id.my_m_f_r_date);
            card = itemView.findViewById(R.id.md_file_card);
            itemView.findViewById(R.id.my_m_f_r_all).setOnClickListener((a) -> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
            onCLickLi.onCLickView(getAbsoluteAdapterPosition());
            return true;
        }
    }

    public interface MyHandler {
        void onCLickView(int p);
    }
}
