package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class FragmentLinksProjectEdit extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_links_project_edit, container, false);

        EditProject editProject = (EditProject) getActivity();
        assert editProject != null;
        int score = editProject.getScore();

        EditText tg = v.findViewById(R.id.tgLink);
        EditText vk = v.findViewById(R.id.vkLink);
        EditText web = v.findViewById(R.id.webLink);
        Button btn = v.findViewById(R.id.saveBtn);
        setColorBtn(score, btn);
        setColorEditText(score, tg);
        setColorEditText(score, vk);
        setColorEditText(score, web);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject.saveLinks(tg.getText().toString(), vk.getText().toString(), web.getText().toString());
            }
        });

        return v;
    }

    public void setColorEditText(int score, EditText prDesc){
        if(score < 100){
            prDesc.setBackgroundResource(R.drawable.blue_rounded);
        }
        else if(score < 300){
            prDesc.setBackgroundResource(R.drawable.green_rounded);
        }
        else if(score < 1000){
            prDesc.setBackgroundResource(R.drawable.brown_rounded);
        }
        else if(score < 2500){
            prDesc.setBackgroundResource(R.drawable.grey_rounded);
        }
        else if(score < 7000){
            prDesc.setBackgroundResource(R.drawable.ohra_rounded);
        }
        else if(score < 17000){
            prDesc.setBackgroundResource(R.drawable.red_rounded);
        }
        else if(score < 30000){
            prDesc.setBackgroundResource(R.drawable.orange_rounded);
        }
        else if(score < 50000){
            prDesc.setBackgroundResource(R.drawable.violete_rounded);
        }
        else{
            prDesc.setBackgroundResource(R.drawable.blue_green_rounded);
        }
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