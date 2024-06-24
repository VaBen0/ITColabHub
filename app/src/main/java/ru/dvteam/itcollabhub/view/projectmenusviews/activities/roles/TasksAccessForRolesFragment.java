package ru.dvteam.itcollabhub.view.projectmenusviews.activities.roles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.databinding.FragmentTasksAccessForRolesBinding;
import ru.dvteam.itcollabhub.view.adapter.TasksAccessAdapter;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;


public class TasksAccessForRolesFragment extends Fragment {

    FragmentTasksAccessForRolesBinding binding;
    RolesViewModel viewModel;
    Fragment addPeoples;
    TasksAccessAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RolesViewModel.class);
        addPeoples = instantiate(getContext(), AddPeoplesInRoleFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksAccessForRolesBinding.inflate(inflater);

        RolesActivity rolesActivity = (RolesActivity) getActivity();

        binding.rolesPlace.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getRoles().observe(getViewLifecycleOwner(), rolesInformations -> {
            adapter = new TasksAccessAdapter(rolesInformations, getContext(), rolesActivity.getColor(), new CallBack() {
                @Override
                public void invoke() {

                }
            });

            binding.rolesPlace.setAdapter(adapter);
        });

        binding.next.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, addPeoples)
                    .commit();
        });

        return binding.getRoot();
    }
}