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

import com.medicate_int.mymedicate.models.BookingSpecializationModel;
import com.medicate_int.mymedicate.R;

import java.util.ArrayList;
import java.util.List;

public class BookingAdvanseItemAdabter extends RecyclerView.Adapter<BookingAdvanseItemAdabter.News> implements Filterable {
    List<BookingSpecializationModel> full;
    List<BookingSpecializationModel> demo;
    Context context;
    BookingAdvanseItemAdabter.onCLickLis MonCLickLi;

    public BookingAdvanseItemAdabter(Context context, List<BookingSpecializationModel> bookingSpecializationModel, BookingAdvanseItemAdabter.onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.full = bookingSpecializationModel;
        demo = full;
    }
    public List<BookingSpecializationModel> getIm(){
        return demo;
    }
    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spha_list_row,parent,false);
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
        BookingAdvanseItemAdabter.onCLickLis onCLickLi;

        public News(@NonNull View itemView, BookingAdvanseItemAdabter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            imageView = itemView.findViewById(R.id.img_shp_row);
            text = itemView.findViewById(R.id.txt_spa_row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick(getAbsoluteAdapterPosition());
        }
    }
    public interface onCLickLis{
        void onCLick(int p);
    }
}
