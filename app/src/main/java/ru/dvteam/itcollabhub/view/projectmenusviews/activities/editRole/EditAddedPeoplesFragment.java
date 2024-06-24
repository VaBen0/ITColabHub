package ru.dvteam.itcollabhub.view.projectmenusviews.activities.editRole;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dvteam.itcollabhub.databinding.FragmentEditAddedPeoplesBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditRoleViewModel;

public class EditAddedPeoplesFragment extends Fragment {

    FragmentEditAddedPeoplesBinding binding;
    EditRoleViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(EditRoleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAddedPeoplesBinding.inflate(inflater);



        return binding.getRoot();
    }
}