package ru.dvteam.itcollabhub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.Objects;

import ru.dvteam.itcollabhub.databinding.ActivityChooseThemeForAppBinding;

public class ChooseThemeForApp extends AppCompatActivity {

    ActivityChooseThemeForAppBinding binding;
    String mail;
    int score;
    private SlideAdapter adapter;
    private SlideAdapterBg adapterBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChooseThemeForAppBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String name = sPref.getString("UserName", "");
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);

        String urlPhoto = Objects.requireNonNull(getIntent().getExtras()).getString("UserPhoto");
        Glide.with(ChooseThemeForApp.this)
                .load(urlPhoto)
                .into(binding.log);

        binding.nameu.setText(name);
        binding.score.setText("Ваши очки" + score);

        adapter = new SlideAdapter(this, score);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(UsersChosenTheme.getThemeNum()-1);

        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPadding(48, 0, 48, 0);
        binding.viewPager.setPageMargin(-550);

        adapterBg = new SlideAdapterBg(this, score);
        binding.viewPagerBg.setAdapter(adapterBg);
        binding.viewPagerBg.setClickable(false);
        binding.viewPagerBg.setCurrentItem(UsersChosenTheme.getThemeNum()-1);

        setCircleResource(binding.userCircle, UsersChosenTheme.getThemeNum()-1);
        setCircleResource(binding.score, binding.linearFriends, UsersChosenTheme.getThemeNum()-1);
        binding.viewPagerBg.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });

        binding.viewPager.setOnTouchListener(new SyncScrollOnTouchListener(binding.viewPagerBg));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                //no-op
            }

            @Override
            public void onPageSelected(int position) {
                binding.viewPagerBg.setCurrentItem(position, true);
                    final Animation anim_out = AnimationUtils.loadAnimation(ChooseThemeForApp.this, android.R.anim.fade_out);
                    final Animation anim_in  = AnimationUtils.loadAnimation(ChooseThemeForApp.this, android.R.anim.fade_in);
                    anim_out.setAnimationListener(new Animation.AnimationListener()
                    {
                        @Override public void onAnimationStart(Animation animation) {}
                        @Override public void onAnimationRepeat(Animation animation) {}
                        @Override public void onAnimationEnd(Animation animation)
                        {
                            setCircleResource(binding.userCircle, position);
                            anim_in.setAnimationListener(new Animation.AnimationListener() {
                                @Override public void onAnimationStart(Animation animation) {}
                                @Override public void onAnimationRepeat(Animation animation) {}
                                @Override public void onAnimationEnd(Animation animation) {}
                            });
                            binding.userCircle.startAnimation(anim_in);
                        }
                    });
                    binding.userCircle.startAnimation(anim_out);
                setCircleResource(binding.score, binding.linearFriends, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //no-op
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int scoreC = 0;
                switch (binding.viewPager.getCurrentItem()){
                    case(0):
                        scoreC = 0;
                        break;
                    case(1):
                        scoreC = 101;
                        break;
                    case(2):
                        scoreC = 301;
                        break;
                    case(3):
                        scoreC = 1001;
                        break;
                    case(4):
                        scoreC = 2501;
                        break;
                    case(5):
                        scoreC = 7001;
                        break;
                    case(6):
                        scoreC = 17001;
                        break;
                    case(7):
                        scoreC = 30001;
                        break;
                    case(8):
                        scoreC = 50001;
                        break;
                }
                if(score > scoreC){
                    UsersChosenTheme.setThemeNum(binding.viewPager.getCurrentItem() + 1);
                    SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putInt("ThemeNum", binding.viewPager.getCurrentItem() + 1);
                    ed.apply();
                }else{
                    Toast.makeText(ChooseThemeForApp.this, "Не разблокирован", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setCircleResource(ImageView circle, int position){
        switch (position){
            case (0):
                circle.setImageResource(R.drawable.circle_blue);
                break;
            case (1):
                circle.setImageResource(R.drawable.circle_green);
                break;
            case (2):
                circle.setImageResource(R.drawable.circle_brown);
                break;
            case (3):
                circle.setImageResource(R.drawable.circle_pink_gold);
                break;
            case (4):
                circle.setImageResource(R.drawable.circle_ohra);
                break;
            case (5):
                circle.setImageResource(R.drawable.circle_red);
                break;
            case (6):
                circle.setImageResource(R.drawable.circle_orange);
                break;
            case (7):
                circle.setImageResource(R.drawable.circle_violete);
                break;
            case (8):
                circle.setImageResource(R.drawable.circle_blue_green);
                break;
        }
    }
    public void setCircleResource(TextView score, View line, int position){
        switch (position){
            case (0):
                score.setTextColor(getResources().getColor(R.color.blue));
                line.setBackgroundResource(R.drawable.blue_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blue));
                break;
            case (1):
                score.setTextColor(getResources().getColor(R.color.green));
                line.setBackgroundResource(R.drawable.green_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
                break;
            case (2):
                score.setTextColor(getResources().getColor(R.color.brown));
                line.setBackgroundResource(R.drawable.brown_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.brown));
                break;
            case (3):
                score.setTextColor(getResources().getColor(R.color.pink_gold));
                line.setBackgroundResource(R.drawable.pink_gold_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.pink_gold));
                break;
            case (4):
                score.setTextColor(getResources().getColor(R.color.ohra));
                line.setBackgroundResource(R.drawable.ohra_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.ohra));
                break;
            case (5):
                score.setTextColor(getResources().getColor(R.color.red));
                line.setBackgroundResource(R.drawable.red_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red));
                break;
            case (6):
                score.setTextColor(getResources().getColor(R.color.orange));
                line.setBackgroundResource(R.drawable.orange_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.orange));
                break;
            case (7):
                score.setTextColor(getResources().getColor(R.color.violete_light));
                line.setBackgroundResource(R.drawable.violete_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.violete_light));
                break;
            case (8):
                score.setTextColor(getResources().getColor(R.color.main_green));
                line.setBackgroundResource(R.drawable.blue_green_line);
                binding.save.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main_green));
                break;
        }
    }
}