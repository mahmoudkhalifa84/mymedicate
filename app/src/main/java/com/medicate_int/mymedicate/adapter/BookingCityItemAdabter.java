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
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.models.BookingSpecializationModel;

import java.util.ArrayList;
import java.util.List;

public class BookingCityItemAdabter extends RecyclerView.Adapter<BookingCityItemAdabter.News> implements Filterable {
    List<BookingSpecializationModel> full;
    List<BookingSpecializationModel> demo;
    Context context;
    CacheHelper statics;
    BookingCityItemAdabter.onCLickLis2 MonCLickLi;

    public BookingCityItemAdabter(Context context, List<BookingSpecializationModel> bookingSpecializationModel, BookingCityItemAdabter.onCLickLis2 monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.full = bookingSpecializationModel;
        demo = this.full;
        statics = new CacheHelper(context.getApplicationContext());
    }

    public List<BookingSpecializationModel> getIm() {
        return demo;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_city_row, parent, false);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        if (null != demo.get(position).getCity_name()) {
                holder.text.setText(demo.get(position).getCity_name());
        }
            else holder.text.setText(demo.get(position).getContry());

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
                Log.d("NETWORK123", "Run Filter > ");
                String key = constraint.toString();
                if (key.trim().isEmpty() || key.trim().length() == 0) {
                    demo.clear();
                    for (int i = 0; i < full.size(); i++) {
                        Log.d("NETWORK123", "For Status > "  + full.get(i).getContry());
                       // if (full.get(i).getContry().trim().equals(statics.restorePrefData("net_w_country").trim()))
                            demo.add(full.get(i));
                    }
                } else {
                    List<BookingSpecializationModel> newList = new ArrayList<>();
                    for (BookingSpecializationModel row : full
                    ) {
                        if (null != row.getCity_name())
                            if ((row.getCity_name().toLowerCase().contains(key.toLowerCase())) && (row.getContry().trim().equals(statics.restorePrefData("net_w_country")))) {
                                newList.add(row);
                            }
                    }
                    demo = newList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = demo;
                filterResults.count = demo.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                demo = (List<BookingSpecializationModel>) results.values;
                notifyDataSetChanged();

            }
        };

    }

    public List<BookingSpecializationModel> getDemo(){
        return demo;
    }
    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        BookingCityItemAdabter.onCLickLis2 onCLickLi;

        public News(@NonNull View itemView, BookingCityItemAdabter.onCLickLis2 onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            text = itemView.findViewById(R.id.txt_city_row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCLickLi.onCLick2(getAbsoluteAdapterPosition());
        }
    }

    public interface onCLickLis2 {
        void onCLick2(int p);
    }
}
