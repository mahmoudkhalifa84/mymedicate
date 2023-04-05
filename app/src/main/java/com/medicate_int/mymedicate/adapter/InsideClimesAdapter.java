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

import com.medicate_int.mymedicate.models.InsideClimesModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

import java.util.List;

public class InsideClimesAdapter extends RecyclerView.Adapter<InsideClimesAdapter.News> {
    CacheHelper statics;
    Context context;

    List<InsideClimesModel> full;
    List<InsideClimesModel> demo;

    public InsideClimesAdapter(Context context, List<InsideClimesModel> full) {
        this.context = context;
        this.full = full;
        statics = new CacheHelper(context.getApplicationContext());
        demo = full;
    }

    @NonNull
    @Override
    public InsideClimesAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inside_clame_row, parent, false);
        statics = new CacheHelper(context);
        return new InsideClimesAdapter.News(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsideClimesAdapter.News holder, int position) {
        holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
        if (statics.getPricesIns().equals("true")){
            holder.txt_price.setVisibility(View.VISIBLE);
            holder.price.setVisibility(View.VISIBLE);
        }
        else {
            holder.txt_price.setVisibility(View.GONE);
            holder.price.setVisibility(View.GONE);
        }
        if (demo.get(position).getName().equals("null"))
            holder.Title.setText(context.getResources().getString(R.string.known));
        else
            holder.Title.setText(demo.get(position).getName());
        if (demo.get(position).getPrice().equals("null"))
            holder.price.setText(context.getResources().getString(R.string.known));
        else
            holder.price.setText(demo.get(position).getPrice());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return demo.size();
    }

    public List<InsideClimesModel> getIm() {
        return demo;
    }

    public static class News extends RecyclerView.ViewHolder {
        TextView Title, txt_price, price;
        CardView constraintLayout;

        public News(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.cxzczx);
            Title = itemView.findViewById(R.id.inside_climes_name);
            price = itemView.findViewById(R.id.inside_climes_price);
            txt_price = itemView.findViewById(R.id.textView43);
        }


    }


}