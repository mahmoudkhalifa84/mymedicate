package com.medicate_int.mymedicate.askdoctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.SetLocal;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.models.MedicalNetworkModel;

import java.util.List;

public class AskDocNormalAdapter extends RecyclerView.Adapter<AskDocNormalAdapter.News> {
    Context context;
    CacheHelper statics;
    onCLickLis MonCLickLi;
    List<MedicalNetworkModel> list_all;
    List<MedicalNetworkModel> list_filter;


    public AskDocNormalAdapter(Context context, List<MedicalNetworkModel> list_all, onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.list_all = list_all;
        this.list_filter = list_all;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hospital_row, parent, false);
        statics = new CacheHelper(context);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        if (SetLocal.getLong(context).equals("ar")) {

        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return list_filter.size();
    }

    public List<MedicalNetworkModel> getIm() {
        return list_filter;
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnClickListener {
        onCLickLis onCLickLi;
        TextView you_txt, comp_txt;
        LinearLayout user, comp;

        public News(@NonNull View itemView, AskDocNormalAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            user = itemView.findViewById(R.id.from_you);
            comp = itemView.findViewById(R.id.from_comp);
            you_txt = itemView.findViewById(R.id.textView91);
            comp_txt = itemView.findViewById(R.id.textView99);
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
