package ru.dvteam.itcollabhub.view.projectmenusviews.activities.editRole;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityEditRoleBinding;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.EditRoleViewModel;

public class EditRoleActivity extends AppCompatActivity implements ColorPickerDialogListener {

    ActivityEditRoleBinding binding;
    int color;
    EditRoleViewModel viewModel;
    Fragment editMainRoleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsersChosenTheme.setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityEditRoleBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(EditRoleViewModel.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        color = ContextCompat.getColor(this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.projectName.setText(viewModel.getProjectName());
        Glide
                .with(this)
                .load(viewModel.getProjectLog())
                .into(binding.prLogo);

        editMainRoleFragment = Fragment.instantiate(this, EditMainRoleFragment.class.getName());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rolesPlace, editMainRoleFragment)
                .commit();
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