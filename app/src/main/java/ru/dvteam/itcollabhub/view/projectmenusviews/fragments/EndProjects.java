package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.databinding.FragmentEndProjectsBinding;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.UsersProject3;
import ru.dvteam.itcollabhub.view.adapter.EndProjectAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackActivityProject;
import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ActivityProjectViewModel;

public class EndProjects extends Fragment {

    String mail;
    int score;
    LinearLayout main;
    FragmentEndProjectsBinding binding;
    ActivityProjectViewModel activityProjectViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEndProjectsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityProjectViewModel = new ViewModelProvider(requireActivity()).get(ActivityProjectViewModel.class);
        binding.mainLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        activityProjectViewModel.getEndProjects().observe(getViewLifecycleOwner(), new Observer<ArrayList<ProjectClass>>() {
            @Override
            public void onChanged(ArrayList<ProjectClass> projectClasses) {
                EndProjectAdapter endProjectAdapter = new EndProjectAdapter(getContext(), projectClasses, new CallBackActivityProject() {
                    @Override
                    public void setActivity(String id) {
                        ProjectId.getInstance().setProjectId(id);
                        Intent intent = new Intent(getContext(), UsersProject3.class);
                        startActivity(intent);
                    }
                });
                binding.mainLayout.setAdapter(endProjectAdapter);
            }
        });

        activityProjectViewModel.setEndProjects();
    }
}