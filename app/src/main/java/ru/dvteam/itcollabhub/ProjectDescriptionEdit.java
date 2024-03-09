package ru.dvteam.itcollabhub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class ProjectDescriptionEdit extends Fragment {

    EditText projectDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_description_edit, container, false);

        EditProject editProject = (EditProject) getActivity();
        assert editProject != null;
        int score = editProject.getScore();
        String description = editProject.getDescription();

        projectDescription = v.findViewById(R.id.descriptionProject);
        projectDescription.setText(description);
        Button save = v.findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject.saveDescription(projectDescription.getText().toString());
            }
        });

        return v;
    }

    public String getChangedDescr(){
        if(projectDescription.getText().toString().isEmpty()){
            return "null";
        }else{
            return projectDescription.getText().toString();
        }
    }

    public void setColor(int score, EditText prDesc, Button btn){
        if(score < 100){
            prDesc.setBackgroundResource(R.drawable.blue_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.blue));
        }
        else if(score < 300){
            prDesc.setBackgroundResource(R.drawable.green_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.green));
        }
        else if(score < 1000){
            prDesc.setBackgroundResource(R.drawable.brown_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.brown));
        }
        else if(score < 2500){
            prDesc.setBackgroundResource(R.drawable.grey_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.light_gray));
        }
        else if(score < 7000){
            prDesc.setBackgroundResource(R.drawable.ohra_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.ohra));
        }
        else if(score < 17000){
            prDesc.setBackgroundResource(R.drawable.red_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.red));
        }
        else if(score < 30000){
            prDesc.setBackgroundResource(R.drawable.orange_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
        }
        else if(score < 50000){
            prDesc.setBackgroundResource(R.drawable.violete_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.violete));
        }
        else{
            prDesc.setBackgroundResource(R.drawable.blue_green_rounded);
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.main_green));
        }
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        return super.onGetLayoutInflater(savedInstanceState);

    }
}