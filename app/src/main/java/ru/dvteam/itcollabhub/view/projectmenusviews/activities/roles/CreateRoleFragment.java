package ru.dvteam.itcollabhub.view.projectmenusviews.activities.roles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentCreateRoleBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;


public class CreateRoleFragment extends Fragment {

    FragmentCreateRoleBinding binding;
    RolesViewModel viewModel;
    Fragment chooseRoles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RolesViewModel.class);

        chooseRoles = instantiate(getContext(), ChoseRolesForTasksFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateRoleBinding.inflate(inflater, container, false);
        RolesActivity rolesActivity = (RolesActivity) getActivity();
        binding.purposes.setMax(2);
        binding.problems.setMax(2);
        binding.files.setMax(2);
        binding.adverts.setMax(2);

        int[] access = new int[12];
        Arrays.fill(access, 0);

        viewModel.getColor().observe(getViewLifecycleOwner(), s -> {
            String h = String.format("#%06X", (0xFFFFFF & s));
            binding.colorCode.setText(h);
            binding.shapeableImageView.setBackgroundColor(s);
        });

        binding.chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rolesActivity.selectColor();
            }
        });

        binding.purposesButtonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[1] == 0) {
                    access[1] = 1;
                    access[0] = 1;
                    binding.purposesButtonWatch.setImageResource(R.drawable.checked_radio_icon);
                    binding.purposesButtonMain.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[1] = 0;
                    access[0] = 0;
                    access[2] = 0;
                    binding.purposesButtonMain.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.purposesButtonWatch.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.purposesButtonEdit.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[1] + access[2]) + "/2";
                binding.tickedCountPurps.setText(s);
                binding.purposes.setProgress(access[1] + access[2]);
            }
        });
        binding.purposesButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[2] == 0) {
                    access[1] = 1;
                    access[0] = 1;
                    access[2] = 1;
                    binding.purposesButtonMain.setImageResource(R.drawable.checked_radio_icon);
                    binding.purposesButtonWatch.setImageResource(R.drawable.checked_radio_icon);
                    binding.purposesButtonEdit.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[2] = 0;
                    binding.purposesButtonEdit.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[1] + access[2]) + "/2";
                binding.tickedCountPurps.setText(s);
                binding.purposes.setProgress(access[1] + access[2]);
            }
        });
        binding.problemsButtonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[4] == 0) {
                    access[4] = 1;
                    access[3] = 1;
                    binding.problemsButtonWatch.setImageResource(R.drawable.checked_radio_icon);
                    binding.problemsButtonMain.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[4] = 0;
                    access[3] = 0;
                    access[5] = 0;
                    binding.problemsButtonMain.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.problemsButtonWatch.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.problemsButtonEdit.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[4] + access[5]) + "/2";
                binding.tickedCountProblems.setText(s);
                binding.problems.setProgress(access[4] + access[5]);
            }
        });
        binding.problemsButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[5] == 0) {
                    access[4] = 1;
                    access[3] = 1;
                    access[5] = 1;
                    binding.problemsButtonMain.setImageResource(R.drawable.checked_radio_icon);
                    binding.problemsButtonWatch.setImageResource(R.drawable.checked_radio_icon);
                    binding.problemsButtonEdit.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[5] = 0;
                    binding.problemsButtonEdit.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[4] + access[5]) + "/2";
                binding.tickedCountProblems.setText(s);
                binding.problems.setProgress(access[4] + access[4]);
            }
        });
        binding.filesWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[7] == 0) {
                    access[7] = 1;
                    access[6] = 1;
                    binding.filesWatchButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.filesMainButton.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[7] = 0;
                    access[6] = 0;
                    access[8] = 0;
                    binding.filesMainButton.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.filesWatchButton.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.filesEditButton.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[7] + access[8]) + "/2";
                binding.tickedCountFiles.setText(s);
                binding.files.setProgress(access[7] + access[8]);
            }
        });
        binding.filesEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[8] == 0) {
                    access[7] = 1;
                    access[6] = 1;
                    access[8] = 1;
                    binding.filesMainButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.filesWatchButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.filesEditButton.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[8] = 0;
                    binding.filesEditButton.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[7] + access[8]) + "/2";
                binding.tickedCountFiles.setText(s);
                binding.files.setProgress(access[7] + access[8]);
            }
        });
        binding.advertsWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[10] == 0) {
                    access[10] = 1;
                    access[9] = 1;
                    binding.advertsWatchButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.advertsMainButton.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[10] = 0;
                    access[9] = 0;
                    access[11] = 0;
                    binding.advertsMainButton.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.advertsWatchButton.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.advertsEditButton.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[10] + access[11]) + "/2";
                binding.tickedCountAdverts.setText(s);
                binding.adverts.setProgress(access[10] + access[11]);
            }
        });
        binding.advertsEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(access[11] == 0) {
                    access[10] = 1;
                    access[9] = 1;
                    access[11] = 1;
                    binding.advertsMainButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.advertsWatchButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.advertsEditButton.setImageResource(R.drawable.checked_radio_icon);
                } else{
                    access[11] = 0;
                    binding.advertsEditButton.setImageResource(R.drawable.not_checked_radio_icon);
                }
                String s = (access[10] + access[11]) + "/2";
                binding.tickedCountAdverts.setText(s);
                binding.adverts.setProgress(access[10] + access[11]);
            }
        });
        binding.next.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, chooseRoles)
                    .commit();
        });

        return binding.getRoot();
    }


}