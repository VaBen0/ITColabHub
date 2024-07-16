package ru.dvteam.itcollabhub.view.authorizeviews;

import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityConfirmRegBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.ConfirmRegViewModel;


public class ConfirmReg extends AppCompatActivity {

    ActivityConfirmRegBinding binding;
    ConfirmRegViewModel confirmRegViewModel;
    private Boolean correctlyCode = false;
    private AppComponent appComponent;
    @Inject
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmRegBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        confirmRegViewModel = new ViewModelProvider(this).get(ConfirmRegViewModel.class);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        appComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

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

        appComponent.inject(this);

        initEditText();

        confirmRegViewModel.getCorrectlyCode().observe(this, aBoolean -> {
            correctlyCode = aBoolean;
        });

        binding.confirmBut.setOnClickListener(v -> {
            if(correctlyCode){
                confirmRegViewModel.onConfirmClick(sPref, new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        Toast.makeText(ConfirmReg.this, "Код не верный", Toast.LENGTH_SHORT).show();
                        binding.bg1.setBackgroundColor(getResources().getColor(R.color.red));
                        binding.bg2.setBackgroundColor(getResources().getColor(R.color.red));
                        binding.bg3.setBackgroundColor(getResources().getColor(R.color.red));
                        binding.bg4.setBackgroundColor(getResources().getColor(R.color.red));
                        binding.bg5.setBackgroundColor(getResources().getColor(R.color.red));
                        binding.bg6.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }, new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        if(res){
                            Intent intent = new Intent(ConfirmReg.this, CreateAccount.class);
                            startActivity(intent);
                        }else{
                            binding.bg1.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.bg2.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.bg3.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.bg4.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.bg5.setBackgroundColor(getResources().getColor(R.color.red));
                            binding.bg6.setBackgroundColor(getResources().getColor(R.color.red));
                            Toast.makeText(ConfirmReg.this, "Код неверный", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                binding.bg1.setBackgroundColor(getResources().getColor(R.color.red));
                binding.bg2.setBackgroundColor(getResources().getColor(R.color.red));
                binding.bg3.setBackgroundColor(getResources().getColor(R.color.red));
                binding.bg4.setBackgroundColor(getResources().getColor(R.color.red));
                binding.bg5.setBackgroundColor(getResources().getColor(R.color.red));
                binding.bg6.setBackgroundColor(getResources().getColor(R.color.red));
                Toast.makeText(this, "Вы не ввели код полностью", Toast.LENGTH_SHORT).show();
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
                confirmRegViewModel.setCorrectlyCode(
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
                confirmRegViewModel.setCorrectlyCode(
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
                confirmRegViewModel.setCorrectlyCode(
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
                confirmRegViewModel.setCorrectlyCode(
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
                confirmRegViewModel.setCorrectlyCode(
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
                confirmRegViewModel.setCorrectlyCode(
                        binding.code1.getText().toString() + binding.code2.getText().toString() + binding.code3.getText().toString()
                        + binding.code4.getText().toString() + binding.code5.getText().toString() + binding.code6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}