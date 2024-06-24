package ru.dvteam.itcollabhub.view.projectmenusviews.activities.roles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.callbackclasses.CallBackParticipantWithRole;
import ru.dvteam.itcollabhub.classmodels.ParticipantWithRole;
import ru.dvteam.itcollabhub.databinding.FragmentAddedPeoplesBinding;
import ru.dvteam.itcollabhub.view.adapter.RolesAddParticipantAdapter;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;

public class AddedPeoplesFragment extends Fragment {

    FragmentAddedPeoplesBinding binding;
    RolesViewModel viewModel;
    RolesAddParticipantAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(RolesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddedPeoplesBinding.inflate(inflater);
        RolesActivity rolesActivity = (RolesActivity) getActivity();

        binding.peoplesList.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAddedPeoples().observe(getViewLifecycleOwner(), participantWithRoles -> {
            adapter = new RolesAddParticipantAdapter(participantWithRoles, rolesActivity.getColor(), getContext(), new CallBackParticipantWithRole() {
                @Override
                public void invoke(ParticipantWithRole participant, int r) {
                    viewModel.deletePeople(participant);
                }
            });

            binding.peoplesList.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}