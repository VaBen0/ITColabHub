package ru.dvteam.itcollabhub.view.profileviews.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.profileviews.activities.EditProfile;
import ru.dvteam.itcollabhub.databinding.FragmentAccountLinksBinding;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.EditProfileViewModel;

public class AccountLinks extends Fragment {

    FragmentAccountLinksBinding binding;
    EditProfileViewModel editProfileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountLinksBinding.inflate(inflater, container, false);
        editProfileViewModel = new ViewModelProvider(requireActivity()).get(EditProfileViewModel.class);
        initEditTexts();

        editProfileViewModel.getUserLinks().observe(getViewLifecycleOwner(), userLinks -> {
            binding.tg.setText(userLinks.getTgLink());
            binding.vk.setText(userLinks.getVkLink());
            binding.web.setText(userLinks.getWebLink());
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileViewModel.onClickSaveChanges(new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        Intent intent = new Intent(getContext(), Profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void initEditTexts(){
        binding.tg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProfileViewModel.setTgLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.vk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProfileViewModel.setVkLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.web.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProfileViewModel.setWebLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}