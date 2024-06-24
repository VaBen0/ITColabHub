package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentAddDeadlineForRoleBinding;

public class AddDeadlineForRole extends Fragment {

    FragmentAddDeadlineForRoleBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddDeadlineForRoleBinding.inflate(inflater);

        return binding.getRoot();
    }
}