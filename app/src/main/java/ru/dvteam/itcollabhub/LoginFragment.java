package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.FragmentLoginBinding;
import ru.dvteam.itcollabhub.view.authorizeviews.Forgot;
import ru.dvteam.itcollabhub.view.authorizeviews.LogIn;
import ru.dvteam.itcollabhub.view.authorizeviews.Register;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.LoginViewModel;

public class LoginFragment extends Fragment {

    private Boolean correctEmail = false;
    private Boolean emptyPass = false;
    private Boolean shown = false;

    FragmentLoginBinding binding;

    LoginViewModel viewModel;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        LogIn logIn = (LogIn) getActivity();

        sharedPreferences = logIn.getSharedPreferences();

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

        initEditTexts();

        viewModel.getEmailValid().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                correctEmail = aBoolean;
            }
        });

        viewModel.getPassNormLength().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                emptyPass = aBoolean;
            }
        });

        binding.hideShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.passu.getText().toString().isEmpty()) {
                    if (!shown) {
                        binding.passu.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.hideShowPass.setImageResource(R.drawable.hide);
                        shown = true;
                    } else {
                        binding.passu.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        binding.hideShowPass.setImageResource(R.drawable.view);
                        shown = false;
                    }
                }
            }
        });

        binding.enterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!correctEmail){
                    Toast.makeText(getContext(), "Вы ввели не почту", Toast.LENGTH_SHORT).show();
                }else if(!emptyPass){
                    Toast.makeText(getContext(), "Длина пароля слишком маленькая", Toast.LENGTH_SHORT).show();
                }else {
                    viewModel.onLoginClick(sharedPreferences, new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            if (res) {
                                Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getContext(), Profile.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.forgotBut.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Forgot.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    public void initEditTexts(){
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
                binding.passu.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ofont_blazma));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}