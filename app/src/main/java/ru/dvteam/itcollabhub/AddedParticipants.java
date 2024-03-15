package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt1;
import  ru.dvteam.itcollabhub.databinding.FragmentAddedParticipantsBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class AddedParticipants extends Fragment {

    FragmentAddedParticipantsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddedParticipantsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreateProject2 createProject2 = (CreateProject2) getActivity();
        String ids = createProject2.getPeoplesIds();
        String[] idsArr = ids.split(",");

        if(ids.isEmpty()){
            Toast.makeText(createProject2, "Вы никого не добавили", Toast.LENGTH_SHORT).show();
        }else {
            PostDatas post = new PostDatas();
            post.postDataGetAddedPeoples("GetProjectAddsUsers", ids, new CallBackInt1() {
                @Override
                public void invoke(String res, String name) {
                    String[] namesArr = res.split("\uD83D\uDD70");
                    String[] photosArr = name.split("\uD83D\uDD70");
                    for (int i = 0; i < namesArr.length; i++) {
                        View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                        ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                        TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                        project1.setVisibility(View.GONE);
                        ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                        messege.setImageResource(R.drawable.delete_black);
                        userCircle.setVisibility(View.GONE);
                        System.out.println(photosArr[i]);

                        Glide
                                .with(AddedParticipants.this)
                                .load(photosArr[i])
                                .into(loadImage);
                        nameu.setText(namesArr[i]);

                        int finalI = i;
                        messege.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createProject2.delPeople(idsArr[finalI]);
                                binding.linMain.removeView(custom);
                            }
                        });

                        binding.linMain.addView(custom);
                    }
                    View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                    binding.linMain.addView(empty);
                }
            });
        }
    }
}