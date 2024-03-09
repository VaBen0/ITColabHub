package ru.dvteam.itcollabhub;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.zip.Inflater;

public class Rating extends Fragment {
    private int max, min;
    private int selectedColor, num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        TextView percents = v.findViewById(R.id.percents);
        ProgressBar lvl = v.findViewById(R.id.lvl);
        TextView statusView = v.findViewById(R.id.status);
        TextView nextScore = v.findViewById(R.id.nextScore);
        TextView nextStatus = v.findViewById(R.id.nextstatus);
        TextView activeProjects = v.findViewById(R.id.activeProjects);
        TextView archivProjects = v.findViewById(R.id.endProjects);

        Profile profile = (Profile) getActivity();

        assert profile != null;
        int score = profile.getScore();
        int activePr = profile.getActiveProjects();
        int archivePr = profile.getArchiveProjects();
        String chast;

        activeProjects.setText("Активных проектов: " + activePr);
        archivProjects.setText("Завершённых проектов: " + archivePr);



        String status;

        if(score < 100){
            nextStatus.setText("Следующая цель: Активный пользователь");
            max = 100;
            min = 0;
            chast = "До следующей цели: " + (max - score);
            status = "Новый пользователь";
        }
        else if(score < 300){
            nextStatus.setText("Следующая цель: Бронзовый пользователь");
            max = 300;
            min = 100;
            chast = "До следующей цели: " + (max - score);
            status = "Активный пользователь";
        }
        else if(score < 1000){
            nextStatus.setText("Следующая цель: Пользователь розового золота");
            max = 1000;
            min = 300;
            chast = "До следующей цели: " + (max - score);
            status = "Бронзовый пользователь";
        }
        else if(score < 2500){
            nextStatus.setText("Следующая цель: Золотой пользователь");
            max = 2500;
            min = 1000;
            chast = "До следующей цели: " + (max - score);
            status = "Пользователь розового золота";
        }
        else if(score < 7000){
            selectedColor = Color.parseColor("#FF0000");
            nextStatus.setText("Следующая цель: Гранд-пользователь I");
            max = 7000;
            min = 2500;
            chast = "До следующей цели: " + (max - score);
            status = "Золотой пользователь";
        }
        else if(score < 17000){
            nextStatus.setText("Следующая цель: Гранд-пользователь II");
            max = 17000;
            min = 7000;
            chast = "До следующей цели: " + (max - score);
            status = "Гранд-пользователь I";
        }
        else if(score < 30000){
            nextStatus.setText("Следующая цель: Гранд-пользователь III");
            max = 30000;
            min = 17000;
            chast = "До следующей цели: " + (max - score);
            status = "Гранд-пользователь II";
        }
        else if(score < 50000){
            nextStatus.setText("Следующая цель: Бриллиантовый пользователь");
            max = 50000;
            min = 30000;
            chast = "До следующей цели: " + (max - score);
            status = "Гранд-пользователь III";
        }
        else{
            nextStatus.setText("Вы достигли предела");
            max = 50000;
            min = 0;
            chast = "";
            status = "Бриллиантовый пользователь";
        }

        float med = (float)(score - min)/(float)(max - min);
        med = Math.round(med * 1000);
        med /= 10.0;

        lvl.setMax(max - min);

        statusView.setText(status);
        nextScore.setText(chast);

        final ValueAnimator anim = ValueAnimator.ofFloat(0, med);
        anim.setStartDelay(300);
        anim.setDuration(1500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                String res = anim.getAnimatedValue().toString();
                if(res.length() >= 4){
                    res = res.substring(0, 4) + "%";
                }
                else if(res.length() == 3){
                    res = res.substring(0, 3) + "%";
                }
                else{
                    res = res.substring(0, 2) + "0%";
                }

                percents.setText(res);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        ObjectAnimator animation = ObjectAnimator.ofInt(lvl, "progress", lvl.getProgress(), score - min);
        animation.setStartDelay(300);
        animation.setDuration(1500);
        animation.setAutoCancel(true);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        return v;
    }
}