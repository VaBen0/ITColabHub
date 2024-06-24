package ru.dvteam.itcollabhub.view.projectmenusviews.activities.roles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.databinding.FragmentAllRolesBinding;
import ru.dvteam.itcollabhub.view.adapter.RolesIconsAdapter;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;


public class AllRolesFragment extends Fragment {
    FragmentAllRolesBinding binding;
    RolesViewModel viewModel;
    RolesIconsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RolesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllRolesBinding.inflate(inflater, container, false);

        binding.rolesList.setLayoutManager(new LinearLayoutManager(getContext()));
        RolesActivity rolesActivity = (RolesActivity) getActivity();

        viewModel.getRoles().observe(getViewLifecycleOwner(), rolesInformations -> {
            assert rolesActivity != null;
            adapter = new RolesIconsAdapter(rolesInformations, getContext(), rolesActivity.getColor(), new CallBack() {
                @Override
                public void invoke() {

                }
            });

            binding.rolesList.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}