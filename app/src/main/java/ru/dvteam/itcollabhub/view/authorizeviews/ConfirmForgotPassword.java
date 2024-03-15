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
import ru.dvteam.itcollabhub.databinding.ActivityConfirmForgotPasswordBinding;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.ConfirmForgotPasswordViewModel;

public class ConfirmForgotPassword extends AppCompatActivity {

    ActivityConfirmForgotPasswordBinding binding;
    ConfirmForgotPasswordViewModel confirmForgotPasswordViewModel;
    private Boolean access = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmForgotPasswordBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        initEditText();
        confirmForgotPasswordViewModel = new ViewModelProvider(this).get(ConfirmForgotPasswordViewModel.class);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/ArchitectsDaughter-Regular.ttf");
        binding.it.setTypeface(face);
        binding.hub.setTypeface(face);
        binding.collaborotory.setTypeface(face);

        binding.enterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmForgotPassword.this, LogIn.class);
                startActivity(intent);
            }
        });

        confirmForgotPasswordViewModel.getCorrectlyCode().observe(this, aBoolean -> {
            if(aBoolean){
                binding.checkCode.setText("");
                access = true;
            }else{
                binding.checkCode.setText("Вы ввели некорректный код");
                binding.checkCode.setTextColor(getResources().getColor(R.color.red));
                access = false;
            }
        });

        binding.confirmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access) {
                    confirmForgotPasswordViewModel.onConfirmClick(new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            if(res){
                                Intent intent = new Intent(ConfirmForgotPassword.this, LogIn.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ConfirmForgotPassword.this, "Некорректный код", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initEditText(){
        binding.code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmForgotPasswordViewModel.setCorrectlyCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*public void postData(String mail, String code){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.confirm("UserLogInMai2l", mail, code);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                if(response.body().getReturn().equals("Правильный код")) {
                    change(response.body().getReturn());
                }
                else{
                    Toast.makeText(ConfirmForgotPassword.this, response.body().getReturn(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(ConfirmForgotPassword.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void change(String res){
        Toast toast = Toast.makeText(this, res, Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(ConfirmForgotPassword.this, LogIn.class);
        startActivity(intent);
    }*/
}