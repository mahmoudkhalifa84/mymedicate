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

import com.medicate_int.mymedicate.models.DrugsModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class DrugsAdapter extends RecyclerView.Adapter<DrugsAdapter.News> {
    Context context;
    CacheHelper statics;
    DrugsAdapter.onCLickLis MonCLickLi;
    List<DrugsModel> list_all;
    List<DrugsModel> list_filter;


    public DrugsAdapter(Context context, List<DrugsModel> list_all, DrugsAdapter.onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    @NonNull
    @Override
    public DrugsAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hc_drugs_row, parent, false);
        statics = new CacheHelper(context);
        return new DrugsAdapter.News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugsAdapter.News holder, int position) {
        final DrugsModel item = list_filter.get(position);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        holder.name.setText(item.getName());
        StringBuilder txt = new StringBuilder();
        txt.append(context.getString(R.string.day_num));
        txt.append(" ").append(getNumDays(item.getNumDay()));
        txt.append("\n");
        txt.append(context.getString(R.string.drugs_al3dd));
        txt.append(" ").append(item.getInDay());
        txt.append(" ").append(context.getString(R.string.drugs_b3d_kl));
        holder.expandableTextView.setText(txt);
        holder.expandableTextView.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                DrugsModel items = list_filter.get(position);
                items.setSh(isShrink);
                list_filter.set(position,items);
            }
        });
        holder.expandableTextView.setText(txt);
        holder.expandableTextView.resetState(item.isSh());



    }
    public String getNumDays(String res) {
        String s = res;
        if (s.equals("1"))
            s = context.getString(R.string.one_day);
        else if (s.equals("2"))
            s = context.getString(R.string.two_days);
        else if (Integer.parseInt(s)<=10)
            s = res + " " + context.getString(R.string.days);
        else
            s = res + " " + context.getString(R.string.day);
        return s;
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list_filter.size();
    }

    public List<DrugsModel> getIm() {
        return list_filter;
    }



    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, But;
        ExpandableTextView expandableTextView;
        DrugsAdapter.onCLickLis onCLickLi;
        CardView cardView;

        public News(@NonNull View itemView, DrugsAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            cardView = itemView.findViewById(R.id.llll_drugs_main);
            name = itemView.findViewById(R.id.textView49);
            expandableTextView = itemView.findViewById(R.id.expandableTextView);
            itemView.setOnClickListener(this);
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
