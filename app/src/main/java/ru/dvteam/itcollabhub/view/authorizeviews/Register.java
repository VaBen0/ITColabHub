package ru.dvteam.itcollabhub.view.authorizeviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.R;

import ru.dvteam.itcollabhub.databinding.ActivityRegisterBinding;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.RegisterViewModel;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;

    private Boolean correctlyEmail = false,
            correctlyPass = false,
            correctlyPassAgain = false,
            shown = false,
            shown2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        initEditTexts();

        float width = binding.itCollabHubText.getPaint().measureText("ITCollabHub");

        Shader textShader1 = new LinearGradient(0, 0, width, binding.itCollabHubText.getTextSize(),
                new int[]{Color.rgb(199, 8, 225), Color.rgb(236, 54, 75)},
                null, Shader.TileMode.CLAMP);

        binding.itCollabHubText.getPaint().setShader(textShader1);

        float width2 = binding.itCollabHubText2.getPaint().measureText("Вместе в IT");

        Shader textShader2 = new LinearGradient(0, 0, width2, binding.itCollabHubText2.getTextSize(),
                new int[]{Color.rgb(246, 168, 253), Color.rgb(207, 177, 241)},
                null, Shader.TileMode.CLAMP);

        binding.itCollabHubText2.getPaint().setShader(textShader2);

        float width3 = binding.version.getPaint().measureText("1.0.0");

        Shader textShader3 = new LinearGradient(0, 0, width3, binding.version.getTextSize(),
                new int[]{Color.rgb(119, 96, 212), Color.rgb(37, 201, 159)},
                null, Shader.TileMode.CLAMP);

        binding.version.getPaint().setShader(textShader3);

        float width4 = binding.stadium.getPaint().measureText("Beta");

        Shader textShader4 = new LinearGradient(0, 0, width4, binding.stadium.getTextSize(),
                new int[]{Color.rgb(119, 96, 212), Color.rgb(37, 201, 159)},
                null, Shader.TileMode.CLAMP);

        binding.stadium.getPaint().setShader(textShader4);

        registerViewModel.getCorrectlyEmailAddress().observe(this, aBoolean -> {
            if(!binding.passu.getText().toString().isEmpty()) {
                if (aBoolean) {
                    binding.mailuBg.setBackgroundColor(getResources().getColor(R.color.green));
                    correctlyEmail = true;
                } else {
                    binding.mailuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                    correctlyEmail = false;
                }
            }
        });

        registerViewModel.getCorrectlyPassword().observe(this, aBoolean -> {
            if(aBoolean){
                binding.passuBg.setBackgroundColor(getResources().getColor(R.color.green));
                correctlyPass = true;
            }else{
                binding.passuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                correctlyPass = false;
            }
        });

        registerViewModel.getCorrectlyPasswordAgain().observe(this, aBoolean -> {
            if(aBoolean){
                binding.rightPassuBg.setBackgroundColor(getResources().getColor(R.color.green));
                correctlyPassAgain = true;
            }else{
                binding.rightPassuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                correctlyPassAgain = false;
            }
        });

        binding.hideShowPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!shown){
                    shown = true;
                    binding.passuAgain.setInputType(InputType.TYPE_CLASS_TEXT);
                    binding.hideShowPass2.setImageResource(R.drawable.hide);
                }else{
                    shown = false;
                    binding.passuAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.hideShowPass2.setImageResource(R.drawable.view);
                }
            }
        });

        binding.hideShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!shown2){
                    shown2 = true;
                    binding.passu.setInputType(InputType.TYPE_CLASS_TEXT);
                    binding.hideShowPass.setImageResource(R.drawable.hide);
                }else{
                    shown2 = false;
                    binding.passu.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.hideShowPass.setImageResource(R.drawable.view);
                }
            }
        });

        binding.Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctlyEmail && correctlyPass && correctlyPassAgain){
                    registerViewModel.onRegisterClicked(new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            if(res){
                                Intent intent = new Intent(Register.this, ConfirmReg.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Register.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(Register.this, "Данные введены не корректно", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initEditTexts(){
        binding.mailu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.passu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.setPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.passuAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.checkPassAgain(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
