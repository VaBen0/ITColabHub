package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import ru.dvteam.itcollabhub.databinding.ActivityEndedProjectBinding;

public class ActivityEndedProject extends AppCompatActivity {

    ActivityEndedProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEndedProjectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


    }
}