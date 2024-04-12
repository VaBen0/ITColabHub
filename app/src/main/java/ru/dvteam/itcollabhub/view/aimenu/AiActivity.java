package ru.dvteam.itcollabhub.view.aimenu;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityAiBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;

public class AiActivity extends AppCompatActivity {

    ActivityAiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityAiBinding.inflate(getLayoutInflater());

        setContentView(binding. getRoot());

        binding.sendTextForAi.setOnClickListener(v -> {
            if(!binding.textForAi.getText().toString().isEmpty()) {
                if(binding.textForAi.getText().toString().length() <= 9){
                    Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
                }else {
                    PostDatas post = new PostDatas();
                    post.postDataGetAiCheck(binding.textForAi.getText().toString(), new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            binding.getResForAi.setText(res);
                        }
                    });
                }
            }
        });
        binding.cancel.setOnClickListener(v -> {
            binding.textForAi.setText("");
        });
    }
}