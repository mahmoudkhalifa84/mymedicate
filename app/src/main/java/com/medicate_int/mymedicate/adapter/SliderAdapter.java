package com.medicate_int.mymedicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.medicate_int.mymedicate.R;
import com.medicate_int.mymedicate.models.SliderModel;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    List<SliderModel> sliderItems;
    Context context;

    public SliderAdapter(List<SliderModel> sliderItems, Context context) {
        this.sliderItems = sliderItems;
        this.context = context;
    }


    @Override
    public int getCount() {
        return sliderItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layou = layoutInflater.inflate(R.layout.slider_adapter_layout,null);
        ImageView imageView = layou.findViewById(R.id.slider_image);
        ImageView imageViewFinal = layou.findViewById(R.id.slider_image_final);
        TextView title = layou.findViewById(R.id.slider_title);
        TextView text = layou.findViewById(R.id.slider_text);

        imageView.setImageResource(sliderItems.get(position).getImageView());
        text.setText(sliderItems.get(position).getText());
        title.setText(sliderItems.get(position).getTitle());
        if (position == sliderItems.size()-1)
        {
            imageView.setVisibility(View.INVISIBLE);
            imageViewFinal.setImageResource(sliderItems.get(position).getImageView());
            imageViewFinal.setVisibility(View.VISIBLE);
            title.setVisibility(View.INVISIBLE);
            text.setTextSize(30);

        }
        container.addView(layou);
        return layou;
    }
}
