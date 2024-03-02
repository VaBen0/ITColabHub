package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ru.dvteam.itcollabhub.databinding.ActivityForumBinding;

public class Forum extends AppCompatActivity {

    ActivityForumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForumBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.writeArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum.this, CreateArticle.class);
                startActivity(intent);
            }
        });
    }
}