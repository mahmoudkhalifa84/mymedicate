package com.medicate_int.mymedicate.adapter;

import android.content.Context;
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

import com.medicate_int.mymedicate.models.ClimesModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.database.NetworkDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClimesAdapter extends RecyclerView.Adapter<ClimesAdapter.News> implements Filterable {
    ;
    Context context;
    ClimesAdapter.onCLickLis MonCLickLi;
    List<ClimesModel> clinicsItems;
    NetworkDatabase networkDatabase;
    List<ClimesModel> clinicsItemsDemo;

    public ClimesAdapter(Context context, List<ClimesModel> clinicsItems, onCLickLis monCLickLi) {
        this.context = context;
        MonCLickLi = monCLickLi;
        this.clinicsItems = clinicsItems;
        clinicsItemsDemo = clinicsItems;
    }

    @NonNull
    @Override
    public ClimesAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cliams_row, parent, false);
        return new ClimesAdapter.News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull ClimesAdapter.News holder, int position) {
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        holder.Title.setText(clinicsItemsDemo.get(position).getArabicName());
        holder.num.setText((context.getResources().getString(R.string.main_cm_num) + " " + clinicsItemsDemo.get(position).getClaimID()));
        holder.price.setText((context.getResources().getString(R.string.main_cm_price) + " " + clinicsItemsDemo.get(position).getClaimAmount()));

       if (clinicsItemsDemo.get(position).getDate().length() == 19)
            holder.date.setText((clinicsItemsDemo.get(position).getDate().substring(0,10) + "\n" + clinicsItemsDemo.get(position).getDate().substring(11,16)));
        else
           holder.date.setText((clinicsItemsDemo.get(position).getDate()));

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return clinicsItemsDemo.size();
    }

    public List<ClimesModel> getIm() {
        return clinicsItemsDemo;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                String key = constraint.toString().toLowerCase().trim();
                List<ClimesModel> newList = new ArrayList<>();
                // EmptyFilter(statics.getMY_NETWORK());
                if (constraint == null || key.length() == 0) {
                    newList = clinicsItems;
                } else {
                    for (ClimesModel row : clinicsItems) {
                        if (row.getClaimID().toLowerCase().contains(key.toLowerCase())
                                || row.getArabicName().toLowerCase().contains(key.toLowerCase())) {
                            newList.add(row);
                        }
                    }
                }
                filterResults.count = newList.size();
                filterResults.values = newList;
                clinicsItemsDemo = newList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clinicsItemsDemo = (List<ClimesModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, num, price, date, but;
        ClimesAdapter.onCLickLis onCLickLi;
        CardView constraintLayout;

        public News(@NonNull View itemView, ClimesAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            constraintLayout = itemView.findViewById(R.id.climes_main_lay);
            Title = itemView.findViewById(R.id.climes_main_title);
            num = itemView.findViewById(R.id.climes_main_clim_num);
            price = itemView.findViewById(R.id.climes_main_price);
            date = itemView.findViewById(R.id.climes_main_date);
            but = itemView.findViewById(R.id.climes_main_but);
            but.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick(getAdapterPosition());
        }
    }

    public interface onCLickLis {
        void onCLick(int p);
    }
}