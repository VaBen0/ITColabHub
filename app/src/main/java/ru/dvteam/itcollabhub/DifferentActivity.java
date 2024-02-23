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

public class DifferentActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_different_activity, container, false);
        LinearLayout purpose = v.findViewById(R.id.purpose);
        LinearLayout task = v.findViewById(R.id.task);
        LinearLayout deadline = v.findViewById(R.id.deadline);
        View pur = v.findViewById(R.id.linear_projects);
        View tas = v.findViewById(R.id.linear_rating);
        View dead = v.findViewById(R.id.linear_friends);

        CreateProject2 createProject = (CreateProject2) getActivity();
        int score = createProject.getScore();

        if(score < 100){
            pur.setBackgroundResource(R.drawable.blue_line);
        }
        else if(score < 300){
            pur.setBackgroundResource(R.drawable.green_line);
        }
        else if(score < 1000){
            pur.setBackgroundResource(R.drawable.brown_line);
        }
        else if(score < 2500){
            pur.setBackgroundResource(R.drawable.light_gray_line);
        }
        else if(score < 7000){
            pur.setBackgroundResource(R.drawable.ohra_line);
        }
        else if(score < 17000){
            pur.setBackgroundResource(R.drawable.red_line);
        }
        else if(score < 30000){
            pur.setBackgroundResource(R.drawable.orange_line);
        }
        else if(score < 50000){
            pur.setBackgroundResource(R.drawable.violete_line);
        }
        else{
            pur.setBackgroundResource(R.drawable.blue_green_line);
        }

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    dead.setBackgroundColor(0);
                    pur.setBackgroundColor(0);
                    tas.setBackgroundResource(R.drawable.blue_green_line);
                }

                CreateProject2 createProject = (CreateProject2) getActivity();
                createProject.taskSet();
            }
        });
        purpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    dead.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    pur.setBackgroundResource(R.drawable.blue_green_line);
                }

                CreateProject2 createProject = (CreateProject2) getActivity();
                createProject.purpose();
            }
        });
        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    pur.setBackgroundColor(0);
                    tas.setBackgroundColor(0);
                    dead.setBackgroundResource(R.drawable.blue_green_line);
                }

                CreateProject2 createProject = (CreateProject2) getActivity();
                createProject.deadlineSet();
            }
        });

        return v;
    }

}