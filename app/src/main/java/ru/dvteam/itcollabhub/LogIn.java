package ru.dvteam.itcollabhub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import ru.dvteam.itcollabhub.databinding.ActivityLogInBinding;
import ru.dvteam.itcollabhub.viewmodel.LoginViewModel;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding binding;

    LoginViewModel viewModel;

    private Boolean correctEmail = false;
    private Boolean emptyPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        binding.it.setTypeface(face);
        binding.hub.setTypeface(face);
        binding.collaborotory.setTypeface(face);

        binding.mailu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setEmail(binding.mailu.getText().toString());
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
                viewModel.setPass(binding.passu.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        viewModel.getEmailValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                correctEmail = aBoolean;
            }
        });

        viewModel.getPassNormLength().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                emptyPass = aBoolean;
            }
        });

        binding.enterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                if(!correctEmail){
                    Toast.makeText(LogIn.this, "Вы ввели не почту", Toast.LENGTH_SHORT).show();
                }else if(!emptyPass){
                    Toast.makeText(LogIn.this, "Длина пароля слишком маленькая", Toast.LENGTH_SHORT).show();
                }else {
                    viewModel.onLoginClick(sPref, new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            if (res) {
                                Toast.makeText(LogIn.this, "Успешно", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LogIn.this, Profile.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LogIn.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
            }
        });

        binding.forgotBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Forgot.class);
                startActivity(intent);
            }
        });
    }
}