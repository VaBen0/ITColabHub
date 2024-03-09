package ru.dvteam.itcollabhub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SlideAdapterBg extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    public int[] imageList = {R.drawable.gradient_blue, R.drawable.gradient_green, R.drawable.gradient_brown,
            R.drawable.gradient_pink_gold, R.drawable.gradient_ohra, R.drawable.gradient_red,
            R.drawable.gradient_orange, R.drawable.gradient_violete, R.drawable.gradient_blue_green};

    public SlideAdapterBg(Context context, int score){

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
