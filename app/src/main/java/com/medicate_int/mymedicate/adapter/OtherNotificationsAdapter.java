package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.OtherNotificationsModel;
import com.medicate_int.mymedicate.R;

import java.util.List;

public class OtherNotificationsAdapter  extends RecyclerView.Adapter<OtherNotificationsAdapter.News> {
    Context context;
    List<OtherNotificationsModel> items;

    public OtherNotificationsAdapter(Context context, List<OtherNotificationsModel> items) {
        this.context = context;
        this.items = items;
    }

    public OtherNotificationsAdapter(Context activity) {
        this.context = activity;
    }

    @NonNull
    @Override
    public OtherNotificationsAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.other_notification_row, parent, false);
        return new OtherNotificationsAdapter.News(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OtherNotificationsAdapter.News holder, int position) {
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        if (items.get(position).getState().equals("1"))
            holder.nnew.setVisibility(View.VISIBLE);
        else
            holder.nnew.setVisibility(View.GONE);
        holder.Title.setText(items.get(position).getTitle().trim());
        holder.date.setText(items.get(position).getDate().trim());

    }

    @Override
    public int getItemCount() {
        if (null == items) return 0;
        return items.size();
    }
    public static class News extends RecyclerView.ViewHolder{
        TextView Title,date,nnew;
        CardView constraintLayout;

        public News(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.noti_lay);
            Title = itemView.findViewById(R.id.noti_title);
            date = itemView.findViewById(R.id.noti_date);
            nnew = itemView.findViewById(R.id.new_noti);
        }


    }
}
