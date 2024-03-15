package ru.dvteam.itcollabhub.view.authorizeviews;

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

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityForgotBinding;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.ForgotPasswordViewModel;

public class Forgot extends AppCompatActivity {

    ActivityForgotBinding binding;
    ForgotPasswordViewModel forgotPasswordViewModel;
    private Boolean access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityForgotBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        initEditText();

        forgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        binding.it.setTypeface(face);
        binding.hub.setTypeface(face);
        binding.collaborotory.setTypeface(face);

        binding.enterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forgot.this, LogIn.class);
                startActivity(intent);
            }
        });

        forgotPasswordViewModel.getCorrectlyMail().observe(this, aBoolean -> {
            if(aBoolean){
                binding.checkEmail.setText("");
                access = true;
            }else{
                binding.checkEmail.setText("Вы ввели не почту");
                binding.checkEmail.setTextColor(getResources().getColor(R.color.red));
                access = false;
            }
        });

        binding.confirmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access){
                    forgotPasswordViewModel.onGetClick(new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            if(res){
                                Intent intent = new Intent(Forgot.this, ConfirmForgotPassword.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Forgot.this, "Ошибка. Попробуйте позже.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(Forgot.this, "Почта введена не верно", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initEditText(){
        binding.mailu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forgotPasswordViewModel.setUserMail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
