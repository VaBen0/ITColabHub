package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentOtherEdit extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other_edit, container, false);

        EditProject editProject = (EditProject) getActivity();
        assert editProject != null;
        int score = editProject.getScore();

        Button btn = v.findViewById(R.id.saveBtn);
        setColorBtn(score, btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject.saveOther();
            }
        });

        return v;
    }

    public void setColorBtn(int score, Button btn){
        if(score < 100){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.blue));
        }
        else if(score < 300){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.green));
        }
        else if(score < 1000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.brown));
        }
        else if(score < 2500){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.light_gray));
        }
        else if(score < 7000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.ohra));
        }
        else if(score < 17000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.red));
        }
        else if(score < 30000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
        }
        else if(score < 50000){
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.violete));
        }
        else{
            btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.main_green));
        }
    }
}