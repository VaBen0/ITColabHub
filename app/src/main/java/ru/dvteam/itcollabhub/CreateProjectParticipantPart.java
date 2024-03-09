package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.dvteam.itcollabhub.databinding.FragmentCreateProjectParticipantPartBinding;

public class CreateProjectParticipantPart extends Fragment {

    FragmentCreateProjectParticipantPartBinding binding;

    Fragment fragmentFriends, fragmentFindPeople, fragmentAddedParticipants;
    int score;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateProjectParticipantPartBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CreateProject2 createProject2 = (CreateProject2) getActivity();
        score = createProject2.getScore();

        fragmentFriends = Fragment.instantiate(getContext(), FriendAsPartisipant.class.getName());
        fragmentFindPeople = Fragment.instantiate(getContext(), AddParticipantCreateProject.class.getName());
        fragmentAddedParticipants = Fragment.instantiate(getContext(), AddedParticipants.class.getName());

        binding.addFriendInProjectLine.setVisibility(View.INVISIBLE);
        binding.findPeopleLine.setVisibility(View.VISIBLE);
        binding.addedParticipantLine.setVisibility(View.INVISIBLE);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.participantTypes, fragmentFindPeople)
                .commit();

        binding.addFriendInProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.findPeopleLine.setVisibility(View.INVISIBLE);
                binding.addFriendInProjectLine.setVisibility(View.VISIBLE);
                binding.addedParticipantLine.setVisibility(View.INVISIBLE);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.participantTypes, fragmentFriends)
                        .commit();
            }
        });
        binding.addedParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addFriendInProjectLine.setVisibility(View.INVISIBLE);
                binding.addedParticipantLine.setVisibility(View.VISIBLE);
                binding.findPeopleLine.setVisibility(View.INVISIBLE);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.participantTypes, fragmentAddedParticipants)
                        .commit();
            }
        });
        binding.findPeoples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addFriendInProjectLine.setVisibility(View.INVISIBLE);
                binding.findPeopleLine.setVisibility(View.VISIBLE);
                binding.addedParticipantLine.setVisibility(View.INVISIBLE);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.participantTypes, fragmentFindPeople)
                        .commit();
            }
        });
    }

    private void changeLine(View firstLine, View line, View thirdLine){
        firstLine.setBackgroundResource(0);
        thirdLine.setBackgroundResource(0);
        if(score < 100){
            line.setBackgroundResource(R.drawable.blue_line);
        }
        else if(score < 300){
            line.setBackgroundResource(R.drawable.green_line);
        }
        else if(score < 1000){
            line.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 2500){
            line.setBackgroundResource(R.drawable.light_gray_line);
        }
        else if(score < 7000){
            line.setBackgroundResource(R.drawable.ohra_line);
        }
        else if(score < 17000){
            line.setBackgroundResource(R.drawable.red_line);
        }
        else if(score < 30000){
            line.setBackgroundResource(R.drawable.orange_line);
        }
        else if(score < 50000){
            line.setBackgroundResource(R.drawable.violete_line);
        }
        else{
            line.setBackgroundResource(R.drawable.blue_green_line);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}