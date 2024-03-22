package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.dvteam.itcollabhub.databinding.FragmentOtherEditBinding;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.EndProject;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;

import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.EditProject;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditProjectViewModel;


public class FragmentOtherEdit extends Fragment {

    FragmentOtherEditBinding binding;
    EditProjectViewModel editProjectViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOtherEditBinding.inflate(inflater, container, false);

        EditProject editProject = (EditProject) getActivity();
        editProjectViewModel = new ViewModelProvider(requireActivity()).get(EditProjectViewModel.class);
        init();
        if(GlobalProjectInformation.getInstance().getEnd()){
            binding.endBtn.setVisibility(View.GONE);
        }

        editProjectViewModel.getPrInfo().observe(getViewLifecycleOwner(), projectInformation -> {
            binding.open.setChecked(projectInformation.getProjectIsOpen().equals("1"));
            binding.visible.setChecked(projectInformation.getProjectIsVisible().equals("1"));
        });

        binding.endBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EndProject.class);
            startActivity(intent);
        });
        binding.saveBtn.setOnClickListener(v -> {
            editProjectViewModel.onSaveOther(new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    editProject.showPanel();
                }
            });
        });

        return binding.getRoot();
    }

    public void init(){
        binding.open.setOnClickListener(v -> {
            editProjectViewModel.setIsOpen(binding.open.isChecked());
        });
        binding.visible.setOnClickListener(v -> {
            editProjectViewModel.setIsVisible(binding.visible.isChecked());
        });
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