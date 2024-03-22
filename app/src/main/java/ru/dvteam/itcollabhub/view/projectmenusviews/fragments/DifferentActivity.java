package ru.dvteam.itcollabhub.view.projectmenusviews.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.create.CreateProject2;

public class DifferentActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_different_activity, container, false);
        LinearLayout purpose = v.findViewById(R.id.purpose);
        LinearLayout task = v.findViewById(R.id.task);
        View pur = v.findViewById(R.id.linear_projects);
        View tas = v.findViewById(R.id.linear_rating);

        CreateProject2 createProject = (CreateProject2) getActivity();
        int score = createProject.getScore();

        pur.setVisibility(View.VISIBLE);
        tas.setVisibility(View.INVISIBLE);

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tas.setVisibility(View.VISIBLE);
                pur.setVisibility(View.INVISIBLE);

                CreateProject2 createProject = (CreateProject2) getActivity();
                createProject.taskSet();
            }
        });
        purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pur.setVisibility(View.VISIBLE);
                tas.setVisibility(View.INVISIBLE);

                CreateProject2 createProject = (CreateProject2) getActivity();
                createProject.purpose();
            }
        });

        return v;
    }

}