package ru.dvteam.itcollabhub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    public int[] imageList = new int[9];
    public String[] titles = {"Синий", "Зелёный", "Бронзовый", "Розовое Золото", "Охра", "Красный", "Оранжевый", "Фиолетовый", "Морская волна"};

    public SlideAdapter(Context context, int score){
        imageList[0] = R.drawable.color0;
        if(score > 100){
            imageList[1] = R.drawable.color1;
        }else{
            imageList[1] = R.drawable.notcolor;
        }
        if(score > 300){
            imageList[2] = R.drawable.color2;
        }else{
            imageList[2] = R.drawable.notcolor;
        }
        if(score > 1000){
            imageList[3] = R.drawable.color3;
        }else{
            imageList[3] = R.drawable.notcolor;
        }
        if(score > 2500){
            imageList[4] = R.drawable.color4;
        }else{
            imageList[4] = R.drawable.notcolor;
        }
        if(score > 7000){
            imageList[5] = R.drawable.color5;
        }else{
            imageList[5] = R.drawable.notcolor;
        }
        if(score > 17000){
            imageList[6] = R.drawable.color6;
        }else{
            imageList[6] = R.drawable.notcolor;
        }
        if(score > 30000){
            imageList[7] = R.drawable.color7;
        }else{
            imageList[7] = R.drawable.notcolor;
        }
        if(score > 50000){
            imageList[8] = R.drawable.color8;
        }else{
            imageList[8] = R.drawable.notcolor;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.slide_image, container, false);

        ImageView img = view.findViewById(R.id.imageView17);
        TextView title = view.findViewById(R.id.title);
        img.setImageResource(imageList[position]);
        title.setText(titles[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    
}
