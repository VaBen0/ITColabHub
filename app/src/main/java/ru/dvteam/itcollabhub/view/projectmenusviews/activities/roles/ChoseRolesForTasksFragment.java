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
import ru.dvteam.itcollabhub.databinding.FragmentChoseRolesForTasksBinding;
import ru.dvteam.itcollabhub.view.adapter.RolesForTasksAdapter;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;

public class ChoseRolesForTasksFragment extends Fragment {

    RolesViewModel viewModel;
    FragmentChoseRolesForTasksBinding binding;
    Fragment tasksAccess;
    RolesForTasksAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RolesViewModel.class);

        tasksAccess = instantiate(getContext(), TasksAccessForRolesFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChoseRolesForTasksBinding.inflate(inflater);

        RolesActivity rolesActivity = (RolesActivity) getActivity();

        binding.rolesPlace.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getRoles().observe(getViewLifecycleOwner(), rolesInformations -> {
            adapter = new RolesForTasksAdapter(rolesInformations, getContext(), rolesActivity.getColor(), new CallBack() {
                @Override
                public void invoke() {

                }
            });

            binding.rolesPlace.setAdapter(adapter);
        });

        binding.next.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, tasksAccess)
                    .commit();
        });

        return binding.getRoot();
    }
}