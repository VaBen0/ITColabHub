package ru.dvteam.itcollabhub.view.projectmenusviews.activities.editRole;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentEditTasksAccessForRolesBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditRoleViewModel;


public class EditTasksAccessForRolesFragment extends Fragment {

    FragmentEditTasksAccessForRolesBinding binding;
    EditRoleViewModel viewModel;
    Fragment editAddPeoples;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EditRoleViewModel.class);
        editAddPeoples = instantiate(getContext(), EditAddPeoplesFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditTasksAccessForRolesBinding.inflate(inflater);



        binding.next.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, editAddPeoples)
                    .commit();
        });

        return binding.getRoot();
    }
}