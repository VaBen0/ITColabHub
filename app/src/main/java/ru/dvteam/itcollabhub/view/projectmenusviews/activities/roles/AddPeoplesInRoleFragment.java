package ru.dvteam.itcollabhub.view.projectmenusviews.activities.roles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentAddPeoplesInRoleBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;

public class AddPeoplesInRoleFragment extends Fragment {

    FragmentAddPeoplesInRoleBinding binding;
    Fragment peoplesWithRolesFragment, peoplesWithoutRolesFragment, addedPeoplesFragment, allRoles;
    RolesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        peoplesWithRolesFragment = Fragment.instantiate(getContext(), PeoplesWithRolesFragment.class.getName());
        peoplesWithoutRolesFragment = Fragment.instantiate(getContext(), PeoplesWithoutRolesFragment.class.getName());
        addedPeoplesFragment = Fragment.instantiate(getContext(), AddedPeoplesFragment.class.getName());
        allRoles = Fragment.instantiate(getContext(), AllRolesFragment.class.getName());

        viewModel = new ViewModelProvider(requireActivity()).get(RolesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPeoplesInRoleBinding.inflate(inflater, container, false);
        RolesActivity rolesActivity = (RolesActivity) getActivity();

        getParentFragmentManager().beginTransaction()
                .replace(R.id.participantTypes, peoplesWithoutRolesFragment)
                .commit();

        binding.addedParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addedParticipantLine.setVisibility(View.VISIBLE);
                binding.withoutrolesLine.setVisibility(View.GONE);
                binding.withRolesLine.setVisibility(View.GONE);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.participantTypes, addedPeoplesFragment)
                        .commit();
            }
        });
        binding.withoutRoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addedParticipantLine.setVisibility(View.GONE);
                binding.withoutrolesLine.setVisibility(View.VISIBLE);
                binding.withRolesLine.setVisibility(View.GONE);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.participantTypes, peoplesWithoutRolesFragment)
                        .commit();
            }
        });
        binding.withRolesProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addedParticipantLine.setVisibility(View.GONE);
                binding.withoutrolesLine.setVisibility(View.GONE);
                binding.withRolesLine.setVisibility(View.VISIBLE);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.participantTypes, peoplesWithRolesFragment)
                        .commit();
            }
        });

        binding.next.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, allRoles)
                    .commit();
            rolesActivity.setVisibility();
            viewModel.getPeoplesRolesWithout();
            viewModel.getPeoplesRolesWith();
            viewModel.setAddedParticipants();
        });

        return binding.getRoot();
    }
}