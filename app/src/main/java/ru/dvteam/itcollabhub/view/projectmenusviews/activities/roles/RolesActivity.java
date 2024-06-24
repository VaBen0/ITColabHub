package ru.dvteam.itcollabhub.view.projectmenusviews.activities.roles;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.classmodels.ParticipantWithRole;
import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.databinding.ActivityRolesBinding;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.RolesViewModel;

public class RolesActivity extends AppCompatActivity implements ColorPickerDialogListener {

    ActivityRolesBinding binding;
    Fragment rolesFragment, createRoleFragment;
    int color;
    RolesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityRolesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RolesViewModel.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        color = ContextCompat.getColor(this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        viewModel.getRoles().observe(this, new Observer<ArrayList<RolesInformation>>() {
            @Override
            public void onChanged(ArrayList<RolesInformation> rolesInformations) {

            }
        });
        viewModel.getPeoplesWithoutRole().observe(this, new Observer<ArrayList<ParticipantWithRole>>() {
            @Override
            public void onChanged(ArrayList<ParticipantWithRole> participantWithRoles) {

            }
        });
        viewModel.getPeoplesWithRole().observe(this, new Observer<ArrayList<ParticipantWithRole>>() {
            @Override
            public void onChanged(ArrayList<ParticipantWithRole> participantWithRoles) {

            }
        });
        viewModel.getAddedPeoples().observe(this, new Observer<List<ParticipantWithRole>>() {
            @Override
            public void onChanged(List<ParticipantWithRole> participantWithRoles) {

            }
        });

        viewModel.getAllRoles();
        viewModel.getPeoplesRolesWith();
        viewModel.getPeoplesRolesWithout();

        binding.projectName.setText(viewModel.getProjectName());
        Glide
                .with(this)
                .load(viewModel.getProjectLog())
                .into(binding.prLogo);

        rolesFragment = Fragment.instantiate(this, AllRolesFragment.class.getName());
        createRoleFragment = Fragment.instantiate(this, CreateRoleFragment.class.getName());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rolesPlace, rolesFragment)
                .commit();

        binding.allRoles.setOnClickListener(v -> {
            binding.createRoleLine.setVisibility(View.GONE);
            binding.allRolesLine.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, rolesFragment)
                    .commit();
        });
        binding.createRole.setOnClickListener(v -> {
            binding.createRoleLine.setVisibility(View.VISIBLE);
            binding.allRolesLine.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rolesPlace, createRoleFragment)
                    .commit();
        });
    }

    public void setVisibility(){
        binding.createRoleLine.setVisibility(View.GONE);
        binding.allRolesLine.setVisibility(View.VISIBLE);
    }

    public int getColor() {
        return color;
    }
    public void selectColor(){
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.CIRCLE)
                .show(this);
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        viewModel.setColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}