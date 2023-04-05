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

import com.medicate_int.mymedicate.models.OffersModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.News>  {
        Context context;
        CacheHelper statics;
        OffersAdapter.onCLickLis MonCLickLi;
        List<OffersModel> list_all;
        String lang;

        public OffersAdapter(Context context, List<OffersModel> list_all, OffersAdapter.onCLickLis monCLickLi) {
            this.MonCLickLi = monCLickLi;
            this.context = context;
            this.list_all = list_all;
            if (null != context)
            lang = SetLocal.getLong(context);
        }

        @NonNull
        @Override
        public OffersAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.offers_row,parent,false);
            statics = new CacheHelper(context);
            return new OffersAdapter.News(view,MonCLickLi);
        }

        @Override
        public void onBindViewHolder(@NonNull OffersAdapter.News holder, int position) {
            switch (lang){
                case "en" :
                    holder.title.setText(list_all.get(position).getOFFERS_2_TITLE_EN());
                    holder.cont.setText(list_all.get(position).getOFFERS_5_COUNT_EN());
                    holder.date.setText(list_all.get(position).getOFFERS_7_DATE());
                    break;
                case "fr" :
                    holder.title.setText(list_all.get(position).getOFFERS_3_TITLE_FR());
                    holder.cont.setText(list_all.get(position).getOFFERS_6_COUNT_FR());
                    holder.date.setText(list_all.get(position).getOFFERS_7_DATE());
                    break;
                default: {
                    holder.title.setText(list_all.get(position).getOFFERS_1_TITLE_AR());
                    holder.cont.setText(list_all.get(position).getOFFERS_4_COUNT_AR());
                    holder.date.setText(list_all.get(position).getOFFERS_7_DATE());
                }
            }
        //    holder.imageView.setImageDrawable(context.getDrawable(R.drawable.home_long_logo));
            holder.imageView.setBackground(context.getDrawable(R.drawable.offers_img_back));
            holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.offers_card));
      //      Picasso.get().load(list_all.get(position).getOFFERS_8_IMG()).fit().placeholder(context.getDrawable(R.drawable.new_loading)).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return list_all.size();
        }

        public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title,cont,date;
            GifImageView imageView;
            TextView but;
            CardView constraintLayout;
            OffersAdapter.onCLickLis onCLickLi;
            public News(@NonNull View itemView, OffersAdapter.onCLickLis onCLickLi) {
                super(itemView);
                this.onCLickLi = onCLickLi;
                constraintLayout = itemView.findViewById(R.id.off_ly);
                imageView = itemView.findViewById(R.id.of_img);
                title = itemView.findViewById(R.id.of_title);
                cont = itemView.findViewById(R.id.of_cont);
                date = itemView.findViewById(R.id.of_date);
                but = itemView.findViewById(R.id.of_but);
                but.setOnClickListener(this);
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
