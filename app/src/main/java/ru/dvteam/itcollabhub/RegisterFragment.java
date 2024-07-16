package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.FragmentRegisterBinding;
import ru.dvteam.itcollabhub.view.authorizeviews.ConfirmReg;
import ru.dvteam.itcollabhub.view.authorizeviews.Register;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.RegisterViewModel;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    RegisterViewModel registerViewModel;

    private Boolean correctlyEmail = false,
            correctlyPass = false,
            correctlyPassAgain = false,
            shown = false,
            shown2 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        initEditTexts();

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

        registerViewModel.getCorrectlyEmailAddress().observe(getViewLifecycleOwner(), aBoolean -> {

                if (aBoolean) {
                    binding.mailuBg.setBackgroundColor(getResources().getColor(R.color.green));
                    correctlyEmail = true;
                } else {
                    binding.mailuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                    correctlyEmail = false;
                }
        });

        registerViewModel.getCorrectlyPassword().observe(getViewLifecycleOwner(), aBoolean -> {
            if(!binding.passu.getText().toString().isEmpty()) {
                if (aBoolean) {
                    binding.passuBg.setBackgroundColor(getResources().getColor(R.color.green));
                    correctlyPass = true;
                } else {
                    binding.passuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                    correctlyPass = false;
                }
            }
        });

        registerViewModel.getCorrectlyPasswordAgain().observe(getViewLifecycleOwner(), aBoolean -> {
            if(!binding.passuAgain.getText().toString().isEmpty()){
                if(aBoolean){
                    binding.rightPassuBg.setBackgroundColor(getResources().getColor(R.color.green));
                    correctlyPassAgain = true;
                }else{
                    binding.rightPassuBg.setBackgroundColor(getResources().getColor(R.color.dark_theme));
                    correctlyPassAgain = false;
                }
            }
        });

        binding.hideShowPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.passuAgain.getText().toString().isEmpty()) {
                    if (!shown) {
                        shown = true;
                        binding.passuAgain.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.hideShowPass2.setImageResource(R.drawable.hide);
                    } else {
                        shown = false;
                        binding.passuAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        binding.hideShowPass2.setImageResource(R.drawable.view);
                    }
                }
            }
        });

        binding.hideShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.passu.getText().toString().isEmpty()) {
                    if (!shown2) {
                        shown2 = true;
                        binding.passu.setInputType(InputType.TYPE_CLASS_TEXT);
                        binding.hideShowPass.setImageResource(R.drawable.hide);
                    } else {
                        shown2 = false;
                        binding.passu.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        binding.hideShowPass.setImageResource(R.drawable.view);
                    }
                }
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
                                Intent intent = new Intent(getContext(), ConfirmReg.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Данные введены не корректно", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    private void initEditTexts(){
        binding.mailu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.setEmail(s.toString());
                binding.passu.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ofont_blazma));
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
                binding.passu.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ofont_blazma));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.passuAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerViewModel.checkPassAgain(s.toString());
                binding.passuAgain.setTypeface(ResourcesCompat.getFont(getContext(), R.font.ofont_blazma));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}