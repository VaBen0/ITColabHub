package ru.dvteam.itcollabhub.view.projectmenusviews.activities.editRole;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.databinding.FragmentEditPeoplesWithRolesBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditRoleViewModel;

public class EditPeoplesWithRolesFragment extends Fragment {

    FragmentEditPeoplesWithRolesBinding binding;
    EditRoleViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EditRoleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPeoplesWithRolesBinding.inflate(inflater);



        return binding.getRoot();
    }
}