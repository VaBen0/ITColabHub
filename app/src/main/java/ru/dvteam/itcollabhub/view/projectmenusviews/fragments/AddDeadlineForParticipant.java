package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.databinding.FragmentAddDeadlineForParticipantBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.CreateDedline2;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.PartisipantTasks;


public class AddDeadlineForParticipant extends Fragment {

    FragmentAddDeadlineForParticipantBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddDeadlineForParticipantBinding.inflate(inflater);

        CreateDedline2 partisipantTasks = (CreateDedline2) getActivity();

        String mail = partisipantTasks.getMail();
        String id = partisipantTasks.getId();

        PostDatas post = new PostDatas();
        post.postDataGetProjectParticipants("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String s1, String s2, String s3) {
                String[] ids = s1.split("\uD83D\uDD70");
                String[] names = s2.split("\uD83D\uDD70");
                String[] photos = s3.split("\uD83D\uDD70");

                for (int i = 0; i < ids.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.friend_window3, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);

                    userCircle.setVisibility(View.GONE);
                    project1.setVisibility(View.GONE);

                    Glide
                            .with(getContext())
                            .load(photos[i])
                            .into(loadImage);
                    nameu.setText(names[i]);

                    int finalI = i;

                    messege.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            partisipantTasks.addId(ids[finalI]);
                            System.out.println(ids[finalI]);
                            messege.setVisibility(View.GONE);
                        }
                    });
                    binding.main.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.main.addView(empty);
            }
        });

        return binding.getRoot();
    }
}