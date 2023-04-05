package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.models.BookingDoctorAppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class BookingShowDaiesAdapter extends RecyclerView.Adapter<BookingShowDaiesAdapter.News> {
    Context context;
    CacheHelper statics;
    MyHandler myHandler;
    int conter = 0;
    List<BookingDoctorAppointmentModel> list_all;
    private static final String TAG = "BookingShowDaiesAdapter";


    public BookingShowDaiesAdapter(Context context, List<BookingDoctorAppointmentModel> list_all, MyHandler myHandler) {
        //     Log.d(TAG, "BookingShowDaiesAdapter: DATA >>>> " + new Gson().toJson(list_all) + "\n\n");
        Log.d(TAG, "BookingShowDaiesAdapter: ADAPTER CONT > " + list_all.size());
        this.myHandler = myHandler;
        this.context = context;
        this.list_all = list_all;
    }

    public BookingShowDaiesAdapter(Context context, MyHandler myHandler) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.myHandler = myHandler;
    }


    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_apoentment_days_row_ui, parent, false);
        statics = new CacheHelper(context);
        return new News(view, myHandler, list_all);
    }


    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        BookingDoctorAppointmentModel items = list_all.get(position);
        try {
            if (conter < list_all.size()) {
                Log.d(TAG, "onBindViewHolder: >> VIEW NOW > " + items.getCompanyArabicName());
                holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img_ar));
                holder.bok_day.setText(items.getDayOfWeek());
                holder.bok_end.setText(context.getString(R.string.simale_to).concat(" ").concat(items.getEnd_appointment()));
                holder.bok_start.setText(context.getString(R.string.simale_from).concat(" ").concat(items.getStart_appointment()));
                conter++;
                myHandler.isLoaded(conter);
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException: " + e.getMessage());
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void RemoveAll() {
        list_all.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_all.size();
    }

    public List<BookingDoctorAppointmentModel> getIm() {
        return list_all;
    }

  /*  public void removeAt(int position) {
        list_all.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_all.size());
    }
*/
  /*  public void addNew(BookingDoctorAppointmentModel items) {
        list_all.add(items);
        notifyItemInserted(list_all.size() - 1);
        notifyItemRangeChanged(list_all.size() - 1, list_all.size());
        list_all.add(items);
    }*/

    public void addAll(List<BookingDoctorAppointmentModel> items) {
        list_all.clear();
        list_all.addAll(items);
        notifyItemInserted(list_all.size() - 1);
        notifyItemRangeChanged(list_all.size() - 1, list_all.size());
    }

    public static class News extends RecyclerView.ViewHolder {
        MyHandler onCLickLi;
        TextView bok_day, bok_start, bok_end, bok_bok;
        CardView card;

        public News(@NonNull View itemView, MyHandler onCLickLi, List<BookingDoctorAppointmentModel> model) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            bok_day = itemView.findViewById(R.id.bok_now_day);
            bok_start = itemView.findViewById(R.id.bok_now_from);
            bok_end = itemView.findViewById(R.id.bok_now_to);
            bok_bok = itemView.findViewById(R.id.bok_now_book);
            card = itemView.findViewById(R.id.card123);
            bok_bok.setOnClickListener((a) -> onCLickLi.onCLickBook(getAbsoluteAdapterPosition(), model.get(getAbsoluteAdapterPosition())));
        }


    }

    public interface MyHandler {
        void onCLickBook(int p, BookingDoctorAppointmentModel model);

        void isLoaded(int p);
    }
}
