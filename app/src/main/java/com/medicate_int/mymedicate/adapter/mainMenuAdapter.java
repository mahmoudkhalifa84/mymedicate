package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicate_int.mymedicate.R;


public class mainMenuAdapter extends BaseAdapter {

    Context context;
    private final int[] images;
    private final int[] values;
    View view;
    LayoutInflater layoutInflater;

    public mainMenuAdapter(Context context, int[] images, int[] values) {
        this.context = context;
        this.images = images;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.items_main_menu,null);
            ImageView imageView = view.findViewById(R.id.main_menu_item_icon);
            TextView textView = view.findViewById(R.id.main_menu_item_title);
            textView.setTextSize(16);
           // textView
            imageView.setImageResource(images[position]);
            //textView.setText(values[position]);
            textView.setText(view.getResources().getString(values[position]));
        }
        return view;
    }
}
