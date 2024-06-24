package ru.dvteam.itcollabhub.view.projectmenusviews.activities.editRole;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentEditMainBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditRoleViewModel;


public class EditAddPeoplesFragment extends Fragment {

    FragmentEditMainBinding binding;
    EditRoleViewModel viewModel;
    Fragment peoplesWithRolesFragment, peoplesWithoutRolesFragment, addedPeoplesFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EditRoleViewModel.class);

        peoplesWithRolesFragment = Fragment.instantiate(getContext(), EditPeoplesWithRolesFragment.class.getName());
        peoplesWithoutRolesFragment = Fragment.instantiate(getContext(), EditPeoplesWithoutRolesFragment.class.getName());
        addedPeoplesFragment = Fragment.instantiate(getContext(), EditAddedPeoplesFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditMainBinding.inflate(inflater);

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

        return binding.getRoot();
    }
}