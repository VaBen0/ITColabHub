package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.FragmentLinksProjectEditBinding;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.EditProject;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditProjectViewModel;


public class FragmentLinksProjectEdit extends Fragment {

    FragmentLinksProjectEditBinding binding;
    EditProjectViewModel editProjectViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLinksProjectEditBinding.inflate(inflater, container, false);

        EditProject editProject = (EditProject) getActivity();
        editProjectViewModel = new ViewModelProvider(requireActivity()).get(EditProjectViewModel.class);
        initEditText();
        editProjectViewModel.getPrInfo().observe(getViewLifecycleOwner(), projectInformation -> {
            binding.tgLink.setText(projectInformation.getProjectTgLink());
            binding.vkLink.setText(projectInformation.getProjectVkLink());
            binding.webLink.setText(projectInformation.getProjectWebLink());
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProjectViewModel.onSaveLinks(new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        editProject.showPanel();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    public void initEditText(){
        binding.tgLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProjectViewModel.setProjectTgLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.vkLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProjectViewModel.setProjectVkLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.webLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProjectViewModel.setProjectWebLink(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setColorEditText(int score, EditText prDesc){
        if(score < 100){
            prDesc.setBackgroundResource(R.drawable.blue_rounded);
        }
        else if(score < 300){
            prDesc.setBackgroundResource(R.drawable.green_rounded);
        }
        else if(score < 1000){
            prDesc.setBackgroundResource(R.drawable.brown_rounded);
        }
        else if(score < 2500){
            prDesc.setBackgroundResource(R.drawable.grey_rounded);
        }
        else if(score < 7000){
            prDesc.setBackgroundResource(R.drawable.ohra_rounded);
        }
        else if(score < 17000){
            prDesc.setBackgroundResource(R.drawable.red_rounded);
        }
        else if(score < 30000){
            prDesc.setBackgroundResource(R.drawable.orange_rounded);
        }
        else if(score < 50000){
            prDesc.setBackgroundResource(R.drawable.violete_rounded);
        }
        else{
            prDesc.setBackgroundResource(R.drawable.blue_green_rounded);
        }
    }
    public void setColorBtn(int score, Button btn){
        if(score < 100){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.blue));
        }
        else if(score < 300){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.green));
        }
        else if(score < 1000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.brown));
        }
        else if(score < 2500){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.light_gray));
        }
        else if(score < 7000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.ohra));
        }
        else if(score < 17000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.red));
        }
        else if(score < 30000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
        }
        else if(score < 50000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.violete));
        }
        else{
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.main_green));
        }
    }
}