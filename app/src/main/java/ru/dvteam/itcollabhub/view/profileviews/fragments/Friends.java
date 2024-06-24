package ru.dvteam.itcollabhub.view.profileviews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackFriendInfromation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.FriendInformation;
import ru.dvteam.itcollabhub.view.adapter.FriendsAdapter;
import ru.dvteam.itcollabhub.view.profileviews.activities.FriendProfile;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.FragmentFriendsBinding;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.profileviewmodels.ProfileViewModel;

public class Friends extends Fragment {

    FragmentFriendsBinding binding;

    ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getUserAllInfo().observe(getViewLifecycleOwner(), profileInformation -> {
                    if (profileInformation.getUserRfr().equals("0")) {
                        binding.notification.setBackgroundResource(R.drawable.notification_false);
                    } else {
                        binding.notification.setBackgroundResource(R.drawable.red_notify);
                    }
                });

            binding.notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = (Profile) getActivity();
                    assert profile != null;
                    profile.changeActivity();
                }
            });

            binding.frindsPlace.setLayoutManager(new LinearLayoutManager(getContext()));

            profileViewModel.getFriendList().observe(getViewLifecycleOwner(), friendInformations -> {
                FriendsAdapter adapter = new FriendsAdapter(friendInformations, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        profileViewModel.adFriend(res);
                    }
                }, new CallBackFriendInfromation() {
                    @Override
                    public void invoke(FriendInformation friend) {
                        Intent intent = new Intent(getContext(), FriendProfile.class);
                        intent.putExtra("id", friend.getFriendId());
                        intent.putExtra("name", friend.getFriendName());
                        intent.putExtra("score", friend.getScore());
                        intent.putExtra("image_url", friend.getFriendPhoto());
                        intent.putExtra("project", friend.getProject());
                        startActivity(intent);
                    }
                }, getContext());
                binding.frindsPlace.setAdapter(adapter);
            });

            profileViewModel.setFriends();

            binding.find.setOnClickListener(v -> profileViewModel.findFriend(() -> binding.cancel2.setVisibility(View.VISIBLE)));
            binding.cancel2.setOnClickListener(v -> {
                profileViewModel.setFriends();
                binding.cancel2.setVisibility(View.GONE);
                binding.nameFriend.setText("");
            });

            binding.nameFriend.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    profileViewModel.setUserName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        return binding.getRoot();
    }
}