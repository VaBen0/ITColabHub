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
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(binding.getRoot());
        initEditText();
        confirmForgotPasswordViewModel = new ViewModelProvider(this).get(ConfirmForgotPasswordViewModel.class);

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

        confirmForgotPasswordViewModel.getCorrectlyCode().observe(this, aBoolean -> {
            access = aBoolean;
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
                            }else{
                                binding.bg1.setBackgroundColor(getResources().getColor(R.color.red));
                                binding.bg2.setBackgroundColor(getResources().getColor(R.color.red));
                                binding.bg3.setBackgroundColor(getResources().getColor(R.color.red));
                                binding.bg4.setBackgroundColor(getResources().getColor(R.color.red));
                                binding.bg5.setBackgroundColor(getResources().getColor(R.color.red));
                                binding.bg6.setBackgroundColor(getResources().getColor(R.color.red));
                            }
                        }
                    });
                } else {
                    Toast.makeText(ConfirmForgotPassword.this, "Некорректный код", Toast.LENGTH_SHORT).show();binding.bg1.setBackgroundColor(getResources().getColor(R.color.red));
                    binding.bg2.setBackgroundColor(getResources().getColor(R.color.red));
                    binding.bg3.setBackgroundColor(getResources().getColor(R.color.red));
                    binding.bg4.setBackgroundColor(getResources().getColor(R.color.red));
                    binding.bg5.setBackgroundColor(getResources().getColor(R.color.red));
                    binding.bg6.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });

    }

    private void initEditText(){
        binding.code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!binding.code1.getText().toString().isEmpty()){
                    binding.code2.requestFocus();
                }
                confirmForgotPasswordViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                                + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!binding.code2.getText().toString().isEmpty()){
                    binding.code3.requestFocus();
                }else {
                    binding.code1.requestFocus();
                }
                confirmForgotPasswordViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                                + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!binding.code3.getText().toString().isEmpty()){
                    binding.code4.requestFocus();
                }else {
                    binding.code2.requestFocus();
                }
                confirmForgotPasswordViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                                + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!binding.code4.getText().toString().isEmpty()){
                    binding.code5.requestFocus();
                }else {
                    binding.code3.requestFocus();
                }
                confirmForgotPasswordViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                                + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.code5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!binding.code5.getText().toString().isEmpty()){
                    binding.code6.requestFocus();
                }else {
                    binding.code4.requestFocus();
                }
                confirmForgotPasswordViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                                + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.code6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.code6.getText().toString().isEmpty()){
                    binding.code5.requestFocus();
                }
                confirmForgotPasswordViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                                + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}