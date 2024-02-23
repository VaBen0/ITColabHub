package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import ru.dvteam.itcollabhub.databinding.ActivityEditProblemPurposeBinding;

public class EditProblemPurpose extends AppCompatActivity {

    ActivityEditProblemPurposeBinding binding;

    String descriptions, names, title, uriPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProblemPurposeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Bundle arguments = getIntent().getExtras();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        assert arguments != null;
        descriptions = arguments.getString("descriptions");
        names = arguments.getString("names");
        title = arguments.getString("prTitle");
        uriPath = arguments.getString("uriPath");
        int id = arguments.getInt("id");

        binding.nameProject.setText(title);
        if(!uriPath.isEmpty()){
            Uri uri = Uri.parse(uriPath);
            binding.prLogo.setImageURI(uri);
            binding.problemPhoto.setImageURI(uri);
        }

        String[] pNames = names.split("✴\uFE0F");
        String[] p = descriptions.split("✴\uFE0F");

        binding.problemTitle.setHint(pNames[id]);
        binding.problemDescription.setHint(p[id]);

        binding.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.problemTitle.getText().toString().isEmpty()){
                    pNames[id] = binding.problemTitle.getText().toString();
                }
                if(!binding.problemDescription.getText().toString().isEmpty()){
                    p[id] = binding.problemDescription.getText().toString();
                }
                names = String.join("✴\uFE0F", pNames);
                descriptions = String.join("✴\uFE0F", p);
                Intent intent = new Intent();
                intent.putExtra("names", names);
                intent.putExtra("descriptions", descriptions);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        /*Intent intent = new Intent();
        intent.putExtra("editTextValue", "value_here");
        setResult(RESULT_OK, intent);
        finish();*/
    }
}