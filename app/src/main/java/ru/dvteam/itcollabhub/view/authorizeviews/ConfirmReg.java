package ru.dvteam.itcollabhub.view.authorizeviews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

        appComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        appComponent.inject(this);

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        binding.it.setTypeface(face);
        binding.hub.setTypeface(face);
        binding.collaborotory.setTypeface(face);

        initEditText();

        confirmRegViewModel.getCorrectlyCode().observe(this, aBoolean -> {
            if(aBoolean){
                binding.checkCodeText.setText("");
                correctlyCode = true;
            }else{
                binding.checkCodeText.setTextColor(getResources().getColor(R.color.red));
                binding.checkCodeText.setText("Длина кода не корректна");
                correctlyCode = false;
            }
        });

        binding.confirmBut.setOnClickListener(v -> {
            if(correctlyCode){
                confirmRegViewModel.onConfirmClick(sPref, new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        Toast.makeText(ConfirmReg.this, "Код не верный", Toast.LENGTH_SHORT).show();
                    }
                }, new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        if(res){
                            Intent intent = new Intent(ConfirmReg.this, CreateAccount.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ConfirmReg.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(this, "Код не удовлетворяет требованиям", Toast.LENGTH_SHORT).show();
            }
        });

        binding.enterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmReg.this, LogIn.class);
                startActivity(intent);
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
                confirmRegViewModel.setCorrectlyCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}