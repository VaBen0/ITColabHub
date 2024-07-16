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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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

        forgotPasswordViewModel.getCorrectlyMail().observe(this, aBoolean -> {
            if(aBoolean){
                binding.bg.setBackgroundColor(getResources().getColor(R.color.green));
                access = true;
            }else{
                binding.bg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
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
                                Toast.makeText(Forgot.this, "Ошибка. Попробуйте позже", Toast.LENGTH_SHORT).show();
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
