package ru.dvteam.itcollabhub.view.profileviews.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import ru.dvteam.itcollabhub.databinding.FragmentRatingBinding;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.ProfileViewModel;

public class Rating extends Fragment {
    private int max, min;
    private int selectedColor, num;

    FragmentRatingBinding binding;
    ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRatingBinding.inflate(inflater, container, false);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getUserAllInfo().observe(getViewLifecycleOwner(), profileInformation -> {
            binding.activeProjects.setText("Активных проектов: " + profileInformation.getUserActivityProjects());
            binding.endProjects.setText("Завершённых проектов: " + profileInformation.getUserArchiveProjects());
            binding.lvl.setProgress(0);
            binding.percents.setText("0%");

            String status;
            String chast;
            int score = profileInformation.getUserScore();

            if(score < 100){
                binding.nextstatus.setText("Следующая цель: Активный пользователь");
                max = 100;
                min = 0;
                chast = "До следующей цели: " + (max - score);
                status = "Новый пользователь";
            }
            else if(score < 300){
                binding.nextstatus.setText("Следующая цель: Бронзовый пользователь");
                max = 300;
                min = 100;
                chast = "До следующей цели: " + (max - score);
                status = "Активный пользователь";
            }
            else if(score < 1000){
                binding.nextstatus.setText("Следующая цель: Пользователь розового золота");
                max = 1000;
                min = 300;
                chast = "До следующей цели: " + (max - score);
                status = "Бронзовый пользователь";
            }
            else if(score < 2500){
                binding.nextstatus.setText("Следующая цель: Золотой пользователь");
                max = 2500;
                min = 1000;
                chast = "До следующей цели: " + (max - score);
                status = "Пользователь розового золота";
            }
            else if(score < 7000){
                selectedColor = Color.parseColor("#FF0000");
                binding.nextstatus.setText("Следующая цель: Гранд-пользователь I");
                max = 7000;
                min = 2500;
                chast = "До следующей цели: " + (max - score);
                status = "Золотой пользователь";
            }
            else if(score < 17000){
                binding.nextstatus.setText("Следующая цель: Гранд-пользователь II");
                max = 17000;
                min = 7000;
                chast = "До следующей цели: " + (max - score);
                status = "Гранд-пользователь I";
            }
            else if(score < 30000){
                binding.nextstatus.setText("Следующая цель: Гранд-пользователь III");
                max = 30000;
                min = 17000;
                chast = "До следующей цели: " + (max - score);
                status = "Гранд-пользователь II";
            }
            else if(score < 50000){
                binding.nextstatus.setText("Следующая цель: Бриллиантовый пользователь");
                max = 50000;
                min = 30000;
                chast = "До следующей цели: " + (max - score);
                status = "Гранд-пользователь III";
            }
            else{
                binding.nextstatus.setText("Вы достигли предела");
                max = 50000;
                min = 0;
                chast = "";
                status = "Бриллиантовый пользователь";
            }

            float med = (float)(score - min)/(float)(max - min);
            med = Math.round(med * 1000);
            med /= 10.0;

            binding.lvl.setMax(max - min);

            binding.status.setText(status);
            binding.nextScore.setText(chast);

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

                    binding.percents.setText(res);
                }
            });
            anim.setInterpolator(new DecelerateInterpolator());
            anim.start();

            ObjectAnimator animation = ObjectAnimator.ofInt(binding.lvl, "progress", binding.lvl.getProgress(), score - min);
            animation.setStartDelay(300);
            animation.setDuration(1500);
            animation.setAutoCancel(true);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        });

        return binding.getRoot();
    }
}