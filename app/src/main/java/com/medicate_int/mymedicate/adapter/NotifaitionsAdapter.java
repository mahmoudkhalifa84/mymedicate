package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.medicate_int.mymedicate.models.BillsNotificationModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import java.util.List;

public class NotifaitionsAdapter extends RecyclerView.Adapter<NotifaitionsAdapter.News> {
    Context context;
    CacheHelper statics;
    NotifaitionsAdapter.onCLickLis MonCLickLi;
    List<BillsNotificationModel> items;

    public NotifaitionsAdapter(Context context, List<BillsNotificationModel> list_all, NotifaitionsAdapter.onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.items = list_all;
        statics = new CacheHelper(context);
    }

    @NonNull
    @Override
    public NotifaitionsAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notifications_row, parent, false);
        return new NotifaitionsAdapter.News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifaitionsAdapter.News holder, int position) {
        if (SetLocal.getLong(context).equals("ar")) {
            holder.title.setText(items.get(position).getArabicName());
        } else if (SetLocal.getLong(context).equals("fr")) {
            holder.title.setText(items.get(position).getFrancsName());
        } else {
            holder.title.setText(items.get(position).getEnglishName());
        }

         /* if (items.get(position).getClaimDate().length() == 19)
            holder.date.setText((items.get(position).getClaimDate().substring(0, 10) + "\n" + items.get(position).getClaimDate().substring(11, 16)));
        else*/

        holder.date.setText(items.get(position).getClaimDate());
        if (statics.getPricesIns().equals("true"))
            holder.price.setVisibility(View.VISIBLE);
        else holder.price.setVisibility(View.GONE);

        holder.price.setText(context.getResources().getString(R.string.total_bail).concat(items.get(position).getClinicClaimAmount()));
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));

        if (items.get(position).getState().equals("1") )
            holder.new_text.setVisibility(View.VISIBLE);
        else
            holder.new_text.setVisibility(View.GONE);

    }
    public interface onCLickLis {
        void onCLick(int p);
    }
    public void hideNew(int p) {

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<BillsNotificationModel> getIm() {
        return items;
    }


    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, cont, price, date, but, new_text;
        NotifaitionsAdapter.onCLickLis onCLickLi;
        ConstraintLayout cardView;


        public News(@NonNull View itemView, NotifaitionsAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            cardView = itemView.findViewById(R.id.noti_lay);
            title = itemView.findViewById(R.id.climes_main_clim_num);
            new_text = itemView.findViewById(R.id.textView55);
            price = itemView.findViewById(R.id.noti_price);
            date = itemView.findViewById(R.id.noti_date);
            but = itemView.findViewById(R.id.noti_but);
            but.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onCLickLi.onCLick(getAdapterPosition());
            new_text.setVisibility(View.GONE);

        }
    }


}
