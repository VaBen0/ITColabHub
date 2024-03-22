package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.FragmentProjectDescriptionEditBinding;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.EditProject;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditProjectViewModel;

public class ProjectDescriptionEdit extends Fragment {

    FragmentProjectDescriptionEditBinding binding;
    EditProjectViewModel editProjectViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectDescriptionEditBinding.inflate(inflater, container, false);
        editProjectViewModel = new ViewModelProvider(requireActivity()).get(EditProjectViewModel.class);
        initEditText();

        editProjectViewModel.getPrInfo().observe(getViewLifecycleOwner(), projectInformation -> {
            binding.descriptionProject.setText(projectInformation.getProjectDescription());
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProjectViewModel.onSaveDescr(new CallBackBoolean() {
                    @Override
                    public void invoke(Boolean res) {
                        EditProject editProject = (EditProject) getActivity();
                        assert editProject != null;
                        editProject.showPanel();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    public void initEditText(){
        binding.descriptionProject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editProjectViewModel.setProjectDescr(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setColor(int score, EditText prDesc, Button btn){
        if(score < 100){
            prDesc.setBackgroundResource(R.drawable.blue_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.blue));
        }
        else if(score < 300){
            prDesc.setBackgroundResource(R.drawable.green_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.green));
        }
        else if(score < 1000){
            prDesc.setBackgroundResource(R.drawable.brown_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.brown));
        }
        else if(score < 2500){
            prDesc.setBackgroundResource(R.drawable.grey_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.light_gray));
        }
        else if(score < 7000){
            prDesc.setBackgroundResource(R.drawable.ohra_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.ohra));
        }
        else if(score < 17000){
            prDesc.setBackgroundResource(R.drawable.red_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.red));
        }
        else if(score < 30000){
            prDesc.setBackgroundResource(R.drawable.orange_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
        }
        else if(score < 50000){
            prDesc.setBackgroundResource(R.drawable.violete_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.violete));
        }
        else{
            prDesc.setBackgroundResource(R.drawable.blue_green_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.main_green));
        }
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        return super.onGetLayoutInflater(savedInstanceState);

    }
}