package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.module.CacheHelper;

import java.util.List;

public class HelthRecordShowdetailsAdapter extends RecyclerView.Adapter<HelthRecordShowdetailsAdapter.News> {
    Context context;
    CacheHelper statics;
    MyHandler myHandler;
    List<String> links;
    private static final String TAG = "MedicalFilesAdapter";


    public HelthRecordShowdetailsAdapter(Context context, List<String> list_all, MyHandler myHandler) {
        this.myHandler = myHandler;
        this.context = context;
        this.links = list_all;
    }


    @NonNull
    @Override
    public News onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.helth_record_view_row_degsin, parent, false);
        statics = new CacheHelper(context);
        return new News(view, myHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull News holder, int position) {
       // String items = links.get(position);
            try {
              //  holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.network_bob));
                holder.title.setText(context.getString(R.string.doc_num).concat(String.valueOf((position+1))));

            } catch (NullPointerException e) {
                Log.d(TAG, "NullPointerException: " + e.getMessage());
            }
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void RemoveAll(){
        links.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public List<String> getIm() {
        return links;
    }
    public void removeAt(int position) {
        links.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, links.size());
    }

    public void addNew(String items) {
            links.add(items);
            notifyItemInserted(links.size() - 1);
            notifyItemRangeChanged(links.size() - 1, links.size());
    }


    public static class News extends RecyclerView.ViewHolder {
        MyHandler onCLickLi;
        TextView title;
        CardView card;

        public News(@NonNull View itemView, HelthRecordShowdetailsAdapter.MyHandler onCLickLi) {
            super(itemView);
            this.onCLickLi = onCLickLi;
            title = itemView.findViewById(R.id.ht_row_text);
           // card = itemView.findViewById(R.id.md_file_card);
            itemView.findViewById(R.id.ht_row_view).setOnClickListener((a)-> onCLickLi.onCLickView(getAbsoluteAdapterPosition()));
            itemView.findViewById(R.id.ht_row_download).setOnClickListener((a)-> onCLickLi.onCLickDownload(getAbsoluteAdapterPosition()));
        }

    }

    public interface MyHandler {
        void onCLickView(int p);
        void onCLickDownload(int p);
        void onLongClick(int p);
    }
}
