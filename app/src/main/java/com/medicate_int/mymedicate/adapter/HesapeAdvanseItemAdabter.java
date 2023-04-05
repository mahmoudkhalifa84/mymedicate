package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;

import java.util.ArrayList;
import java.util.List;

public class HesapeAdvanseItemAdabter extends RecyclerView.Adapter<HesapeAdvanseItemAdabter.News> implements Filterable {
    List<BookingSpecializationModel> full;
    List<BookingSpecializationModel> demo;
    Context context;
    HesapeAdvanseItemAdabter.onCLickLis MonCLickLi;

    public HesapeAdvanseItemAdabter(Context context, List<BookingSpecializationModel> bookingSpecializationModel, HesapeAdvanseItemAdabter.onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.full = bookingSpecializationModel;
        demo = full;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hesabe_layout,parent,false);
        return new News(view,MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        holder.text.setText(demo.get(position).getCity_name());
        holder.imageView.setImageResource(demo.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return demo.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if (key.isEmpty()){
                    demo = full;
                }
                else{
                    List<BookingSpecializationModel> newList = new ArrayList<>();
                    for (BookingSpecializationModel row : full
                    ) {
                        if (row.getCity_name().toLowerCase().contains(key.toLowerCase())){
                            newList.add(row);

                        }

                    }
                    demo = newList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = demo;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                demo = (List<BookingSpecializationModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView imageView;
        HesapeAdvanseItemAdabter.onCLickLis onCLickLi;

        public News(@NonNull View itemView, HesapeAdvanseItemAdabter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            imageView = itemView.findViewById(R.id.hesape_row_img);
            text = itemView.findViewById(R.id.hesape_row_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick(getAdapterPosition());
        }
    }
    public interface onCLickLis{
        void onCLick(int p);
    }
}
