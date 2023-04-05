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

import com.medicate_int.mymedicate.models.InsideNotificationsModel;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

import java.util.List;

public class insideNotificationsAdapter extends RecyclerView.Adapter<insideNotificationsAdapter.News> {
    double comp;
    double you;
    double total;
    CacheHelper statics ;
    Context context;
    insideNotificationsAdapter.onCLickLis MonCLickLi;
    List<InsideNotificationsModel> InsideNotificationsModel;
    List<InsideNotificationsModel> insideNotificationsModel_filttered;


    public insideNotificationsAdapter(Context context, List<InsideNotificationsModel> InsideNotificationsModel) {
        this.context = context;
        this.InsideNotificationsModel = InsideNotificationsModel;
        insideNotificationsModel_filttered = InsideNotificationsModel;
        statics = new CacheHelper(context);
    }

    @NonNull
    @Override
    public insideNotificationsAdapter.News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bail_row, parent, false);
        return new insideNotificationsAdapter.News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull insideNotificationsAdapter.News holder, int position) {
        if (!InsideNotificationsModel.isEmpty()) {
            holder.constraintLayout.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
            if (statics.getPricesIns().equals("true")){
                holder.bil_p_constrate.setVisibility(View.VISIBLE);
            }
            else {
                holder.bil_p_constrate.setVisibility(View.GONE);
            }
            holder.Title.setText(insideNotificationsModel_filttered.get(position).getServicesName());
            holder.from_you.setText(insideNotificationsModel_filttered.get(position).getPercentageClinic());
            holder.tm.setText((String.valueOf((position)+1) + "- "));
            holder.total_price.setText(String.valueOf((Double.parseDouble(insideNotificationsModel_filttered.get(position).getPercentageComp()) +
                    Double.parseDouble(insideNotificationsModel_filttered.get(position).getPercentageClinic()))));
            holder.from_comp.setText(insideNotificationsModel_filttered.get(position).getPercentageComp());
            comp +=
                    Double.parseDouble(insideNotificationsModel_filttered.get(position).getPercentageComp());
            you +=
                    Double.parseDouble(insideNotificationsModel_filttered.get(position).getPercentageClinic());

            holder.c_c.setText(("" + comp));
            holder.c_y.setText(("" + you));
        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return insideNotificationsModel_filttered.size();
    }

    public Double getComp() {
        comp = 0;
        for (int i = 0; i < InsideNotificationsModel.size() ; i++)
            comp += Double.parseDouble(InsideNotificationsModel.get(i).getPercentageComp());
        return comp;
    }

    public Double getYou() {
        you = 0;
        for (int i = 0; i < InsideNotificationsModel.size() ; i++)
            you += Double.parseDouble(InsideNotificationsModel.get(i).getPercentageClinic());
        return you;
    }

    public Double getTotal() {
        total = 0;
        for (int i = 0; i < InsideNotificationsModel.size() ; i++) {
            total += Double.parseDouble(InsideNotificationsModel.get(i).getPercentageClinic());
            total += Double.parseDouble(InsideNotificationsModel.get(i).getPercentageComp());
        }
        return total;
    }

    public List<InsideNotificationsModel> getIm() {
        return insideNotificationsModel_filttered;
    }


    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, tm,
                from_comp,
                from_you,
                total_price ,
        c_y,
        c_c,
        c_t;
        insideNotificationsAdapter.onCLickLis onCLickLi;
        ConstraintLayout constraintLayout,bil_p_constrate;

        public News(@NonNull View itemView, insideNotificationsAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            constraintLayout = itemView.findViewById(R.id.bail_row_lay);
            bil_p_constrate = itemView.findViewById(R.id.bil_p_constrate);
            Title = itemView.findViewById(R.id.bail_name);
            tm = itemView.findViewById(R.id.bial_tm);
            from_comp = itemView.findViewById(R.id.bail_comp_v);
            from_you = itemView.findViewById(R.id.bail_you_v);
            total_price = itemView.findViewById(R.id.bail_total_v);

            c_y = itemView.findViewById(R.id.bail_t_y);
            c_c = itemView.findViewById(R.id.bail_t_c);
            c_t = itemView.findViewById(R.id.bail_t_t);
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
