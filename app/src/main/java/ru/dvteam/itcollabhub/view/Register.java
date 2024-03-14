package ru.dvteam.itcollabhub.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import ru.dvteam.itcollabhub.CallBackBoolean;
import ru.dvteam.itcollabhub.R;

import ru.dvteam.itcollabhub.databinding.ActivityRegisterBinding;
import ru.dvteam.itcollabhub.viewmodel.RegisterViewModel;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;

    private Boolean correctlyEmail = false,
            correctlyPass = false,
            correctlyPassAgain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        initEditTexts();

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        binding.it.setTypeface(face);
        binding.hub.setTypeface(face);
        binding.collaborotory.setTypeface(face);

        registerViewModel.getCorrectlyEmailAddress().observe(this, aBoolean -> {
            if(aBoolean){
                binding.rightEmail.setText("");
                correctlyEmail = true;
            }else{
                binding.rightEmail.setText("Вы ввели не почту");
                binding.rightEmail.setTextColor(getResources().getColor(R.color.red));
                correctlyEmail = false;
            }
        });

        registerViewModel.getCorrectlyPassword().observe(this, aBoolean -> {
            if(aBoolean){
                binding.rightPass.setText("");
                correctlyPass = true;
            }else{
                binding.rightPass.setText("Слишком короткий пароль");
                binding.rightPass.setTextColor(getResources().getColor(R.color.red));
                correctlyPass = false;
            }
        });

        registerViewModel.getCorrectlyPasswordAgain().observe(this, aBoolean -> {
            if(aBoolean){
                binding.rightPassAgain.setText("");
                correctlyPassAgain = true;
            }else{
                binding.rightPassAgain.setText("Пароли не совпадают");
                binding.rightPassAgain.setTextColor(getResources().getColor(R.color.red));
                correctlyPassAgain = false;
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

        binding.passuagain.addTextChangedListener(new TextWatcher() {
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
