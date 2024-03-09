package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DifferentActivityDemo extends Fragment {

    Fragment fragmentPurposes, fragmentTasks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_different_activity_demo, container, false);
        LinearLayout purpose = v.findViewById(R.id.purpose);
        LinearLayout task = v.findViewById(R.id.task);
        View pur = v.findViewById(R.id.linear_projects);
        View tas = v.findViewById(R.id.linear_rating);

        fragmentPurposes = Fragment.instantiate(getContext(), CreateProjectPurposesDemo.class.getName());
        fragmentTasks = Fragment.instantiate(getContext(), CreateProjectTasksDemo.class.getName());

        getParentFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_demo2, fragmentPurposes)
                .commit();

        CreateProject2Demo createProject = (CreateProject2Demo) getActivity();
        int score = createProject.getScore();

        tas.setVisibility(View.INVISIBLE);
        pur.setVisibility(View.VISIBLE);

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pur.setVisibility(View.INVISIBLE);
                tas.setVisibility(View.VISIBLE);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_demo2, fragmentTasks)
                        .commit();
            }
        });
        purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tas.setVisibility(View.INVISIBLE);
                pur.setVisibility(View.VISIBLE);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_demo2, fragmentPurposes)
                        .commit();
            }
        });

        return v;
    }

}