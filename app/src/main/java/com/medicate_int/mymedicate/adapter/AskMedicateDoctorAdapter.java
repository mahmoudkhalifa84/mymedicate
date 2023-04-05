package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.medicate_int.mymedicate.module.Moudle;
import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;
import com.medicate_int.mymedicate.models.AskMedicateDoctorModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

public class AskMedicateDoctorAdapter extends RecyclerView.Adapter<AskMedicateDoctorAdapter.News> {
    Context context;
    CacheHelper statics;
    onCLickLis MonCLickLi;
    List<AskMedicateDoctorModel> list_all;
    private static String TAG = "askMedicateDocAdapter";


    public AskMedicateDoctorAdapter(Context context, List<AskMedicateDoctorModel> list_all, onCLickLis monCLickLi) {
        this.MonCLickLi = monCLickLi;
        this.context = context;
        this.list_all = list_all;
    }

    public AskMedicateDoctorAdapter(Context context, onCLickLis monCLickLi) {
        this.context = context;
        this.list_all = new ArrayList<>();
        this.MonCLickLi = monCLickLi;
    }

    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ask_doctor_row_ui, parent, false);
        statics = new CacheHelper(context);
        return new News(view, MonCLickLi);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
        AskMedicateDoctorModel items = list_all.get(position);
        items.setMsg(Moudle.convertToEnglishDigits(items.getMsg(), context));
        items.setMsg_date(Moudle.convertToEnglishDigits(items.getMsg_date(), context));
        holder.comp.setVisibility(View.GONE);
        holder.user.setVisibility(View.GONE);
        holder.you_media.setVisibility(View.GONE);
        try {
            holder.medicate.setText(context.getString(R.string.medicate));
            if (!items.getSend_by().contains("admin")) {
                if (items.getType().trim().equals("image")) {
                    holder.you_text_media.setVisibility(View.GONE);
                    holder.you_media.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img_ar));
                    holder.you_media.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(items.getFile()).placeholder(R.drawable.chat_loading).into(holder.imageView);
                    if (items.getMsg_date().length() > 10)
                        holder.media_date.setText(items.getMsg_date().substring(10));
                    else holder.media_date.setText(items.getMsg_date());

                } else if (items.getType().trim().equals("text")) {
                    holder.user.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img_ar));
                    holder.user.setVisibility(View.VISIBLE);
                    holder.you_txt.setText(items.getMsg());
                    if (items.getMsg_date().length() > 10)
                        holder.you_date.setText(items.getMsg_date().substring(10));
                    else holder.you_date.setText(items.getMsg_date());

                }

            } else {
                holder.comp.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_img));
                holder.comp.setVisibility(View.VISIBLE);
                holder.comp_txt.setText(items.getMsg());
                if (items.getMsg_date().length() > 10)
                    holder.comp_date.setText(items.getMsg_date().substring(10).replace(".", "").replace(",", ""));
                else holder.comp_date.setText(items.getMsg_date());
            }
        } catch (Exception e) {
            Log.d(TAG, "NullPointerException: " + e.getMessage());
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void RemoveAll() {
        Log.d(TAG, "RemoveAll: ");
        list_all.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return list_all.size();
    }

    public List<AskMedicateDoctorModel> getIm() {
        return list_all;
    }

    public void removeAt(int position) {
        list_all.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_all.size());
    }

    public void addNew(AskMedicateDoctorModel items) {
        list_all.add(items);
        notifyItemInserted(list_all.size() - 1);
        notifyItemRangeChanged(list_all.size() - 1, list_all.size());
    }

    public static class News extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        onCLickLis onCLickLi;
        TextView you_txt,
                comp_txt,
                you_date,
                comp_date,
                media_date,
                you_text_media,
                medicate;
        GifImageView imageView;
        LinearLayout user, comp, you_media;
        public News(@NonNull View itemView, AskMedicateDoctorAdapter.onCLickLis onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            user = itemView.findViewById(R.id.from_you);
            comp = itemView.findViewById(R.id.from_comp);
            you_media = itemView.findViewById(R.id.from_you_media);
            you_text_media = itemView.findViewById(R.id.chat_you_txt_media);
            you_txt = itemView.findViewById(R.id.chat_you_txt);
            comp_txt = itemView.findViewById(R.id.chat_comp_txt);
            you_date = itemView.findViewById(R.id.chat_you_date);
            comp_date = itemView.findViewById(R.id.chat_comp_date);
            medicate = itemView.findViewById(R.id.chat_medicate);
            media_date = itemView.findViewById(R.id.chat_you_date_media);
            imageView = itemView.findViewById(R.id.chat_you_img);
            itemView.setOnLongClickListener(this);
            imageView.setOnClickListener(this);

        }

        @Override
        public boolean onLongClick(View view) {
            onCLickLi.onCLick(getAbsoluteAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            onCLickLi.imageClicked(getAdapterPosition());
        }
    }

    public interface onCLickLis {
        void onCLick(int p);

        void imageClicked(int p);
    }
}
