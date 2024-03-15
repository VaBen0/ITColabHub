package ru.dvteam.itcollabhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import ru.dvteam.itcollabhub.R;

public class SlideAdapterBg extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    public int[] imageList = new int[9];

    public SlideAdapterBg(Context context, int score){

        imageList[0] = R.drawable.gradient_blue;
        if(score > 100){
            imageList[1] = R.drawable.gradient_green;
        }else{
            imageList[1] = R.drawable.gradient_light_gray;
        }
        if(score > 300){
            imageList[2] = R.drawable.gradient_brown;
        }else{
            imageList[2] = R.drawable.gradient_light_gray;
        }
        if(score > 1000){
            imageList[3] = R.drawable.gradient_pink_gold;
        }else{
            imageList[3] = R.drawable.gradient_light_gray;
        }
        if(score > 2500){
            imageList[4] = R.drawable.gradient_ohra;
        }else{
            imageList[4] = R.drawable.gradient_light_gray;
        }
        if(score > 7000){
            imageList[5] = R.drawable.gradient_red;
        }else{
            imageList[5] = R.drawable.gradient_light_gray;
        }
        if(score > 17000){
            imageList[6] = R.drawable.gradient_orange;
        }else{
            imageList[6] = R.drawable.gradient_light_gray;
        }
        if(score > 30000){
            imageList[7] = R.drawable.gradient_violete;
        }else{
            imageList[7] = R.drawable.gradient_light_gray;
        }
        if(score > 50000){
            imageList[8] = R.drawable.gradient_blue_green;
        }else{
            imageList[8] = R.drawable.gradient_light_gray;
        }

        this.context = context;
    }

    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slide_bg, container, false);

        ImageView img = view.findViewById(R.id.imageView17);
        img.setImageResource(imageList[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    
}
