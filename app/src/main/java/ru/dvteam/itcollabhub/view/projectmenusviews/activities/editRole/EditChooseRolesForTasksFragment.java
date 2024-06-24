package ru.dvteam.itcollabhub.view.projectmenusviews.activities.editRole;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentEditChooseRolesForTasksBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditRoleViewModel;


public class EditChooseRolesForTasksFragment extends Fragment {

    FragmentEditChooseRolesForTasksBinding binding;
    EditRoleViewModel viewModel;
    Fragment tasksAccess;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tasksAccess = instantiate(getContext(), EditTasksAccessForRolesFragment.class.getName());

        viewModel = new ViewModelProvider(requireActivity()).get(EditRoleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditChooseRolesForTasksBinding.inflate(inflater);


        binding.next.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, tasksAccess)
                    .commit();
        });

        return binding.getRoot();
    }
}