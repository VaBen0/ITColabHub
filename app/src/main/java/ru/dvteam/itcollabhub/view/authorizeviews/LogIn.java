package ru.dvteam.itcollabhub.view.authorizeviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.LoginFragment;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.RegisterFragment;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.ActivityLogInBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.LoginViewModel;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding binding;

    LoginViewModel viewModel;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    Fragment registerFragment;
    Fragment logInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        registerFragment = Fragment.instantiate(this, RegisterFragment.class.getName());
        logInFragment = Fragment.instantiate(this, LoginFragment.class.getName());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.register_login_place, logInFragment)
                .commit();

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

        binding.regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.register_login_place, registerFragment)
                        .commit();
                binding.regBg.setVisibility(View.VISIBLE);
                binding.logInBg.setVisibility(View.INVISIBLE);
            }
        });
        binding.logInBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.register_login_place, logInFragment)
                        .commit();
                binding.logInBg.setVisibility(View.VISIBLE);
                binding.regBg.setVisibility(View.INVISIBLE);
            }
        });


    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
}