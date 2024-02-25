package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.databinding.ActivityCompltedTasksByParticipantsBinding;

public class CompltedTasksByParticipants extends AppCompatActivity {

    ActivityCompltedTasksByParticipantsBinding binding;
    private String mail, id, title, prPhoto, taskTitle, taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCompltedTasksByParticipantsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        taskTitle = arguments.getString("taskTitle");
        taskId = arguments.getString("taskId");

        binding.taskTitle.setText(taskTitle);
        binding.nameProject.setText(title);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.advertPhoto);

        PostDatas post = new PostDatas();
        post.postDataGetPeoplesComplitedWork("GetWorks", id, mail, taskId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                for(int i = 0; i < res.length(); i++){
                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);

                    userCircle.setVisibility(View.GONE);
                    project1.setVisibility(View.GONE);
                    messege.setImageResource(R.drawable.upload_button);

                    /*Glide
                            .with(CompltedTasksByParticipants.this)
                            .load(photos[i])
                            .into(loadImage);
                    nameu.setText(names[i]);*/

                    int finalI = i;

                    messege.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //idsArr.add(ids[finalI]);
                            messege.setVisibility(View.GONE);
                        }
                    });
                    binding.main.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.main.addView(empty);
            }
        });
    }
}