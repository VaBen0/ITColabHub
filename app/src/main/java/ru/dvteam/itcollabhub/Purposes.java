package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Purposes extends Fragment {

    private int countPurposes = 0;
    String purp_name, purp;

    private static final int RESULT_OK = 1;
    LinearLayout purposePanel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_purposes, container, false);

        ImageView addPurp = v.findViewById(R.id.add_but);
        EditText purp1 = v.findViewById(R.id.name_friend);
        EditText purp2 = v.findViewById(R.id.name_friend2);
        purposePanel = v.findViewById(R.id.purpose_place);

        CreateProject2 createProject = (CreateProject2) getActivity();
        int score = createProject.getScore();
        purp_name = createProject.getPurposes_name();
        purp = createProject.getPurposes();

        create();

        if(score < 100){
            addPurp.setBackgroundResource(R.drawable.ad);
        }
        else if(score < 300){
            addPurp.setBackgroundResource(R.drawable.green_add);
        }
        else if(score < 1000){
            addPurp.setBackgroundResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            addPurp.setBackgroundResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            addPurp.setBackgroundResource(R.drawable.ohra_add);
        }
        else if(score < 17000){
            addPurp.setBackgroundResource(R.drawable.red_add);
        }
        else if(score < 30000){
            addPurp.setBackgroundResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            addPurp.setBackgroundResource(R.drawable.violete_add);
        }
        else{
            addPurp.setBackgroundResource(R.drawable.blue_green_add);
        }

        addPurp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purpName = purp1.getText().toString();
                String purp = purp2.getText().toString();
                if(countPurposes == 3){
                    Toast.makeText(createProject, "Вы достигли предела", Toast.LENGTH_SHORT).show();
                }
                else if(purpName.isEmpty()){
                    Toast.makeText(createProject, "Нет названия", Toast.LENGTH_SHORT).show();
                }
                else if(purp.isEmpty()){
                    Toast.makeText(createProject, "Нет описания", Toast.LENGTH_SHORT).show();
                }
                else {
                    CreateProject2 createProject = (CreateProject2) getActivity();
                    purp1.setText("");
                    purp2.setText("");
                    Toast.makeText(v.getContext(), "Цель добавлена", Toast.LENGTH_SHORT).show();
                    createProject.setPurp(purpName, purp);
                    countPurposes++;

                    View custom = inflater.inflate(R.layout.problem_panel, null);
                    TextView name = custom.findViewById(R.id.problemName);
                    TextView main = custom.findViewById(R.id.textView20);
                    TextView descr = custom.findViewById(R.id.problemDescription);
                    ImageView edit = custom.findViewById(R.id.editProblem);
                    ImageView mainv = custom.findViewById(R.id.problemImage);

                    Uri uri = Uri.parse(createProject.getUriPath());

                    mainv.setImageURI(uri);

                    main.setText("Описание цели");

                    descr.setText(purp);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), EditProblemPurpose.class);
                            i.putExtra("names", createProject.getPurposes_name());
                            i.putExtra("descriptions", createProject.getPurposes());
                            i.putExtra("uriPath", createProject.getUriPath());
                            i.putExtra("prTitle", createProject.getPrName());
                            i.putExtra("id", countPurposes - 1);
                            startActivityForResult(i, 1);
                        }
                    });

                    name.setText(purpName);

                    Transition t = null;
                    t = new Fade(Visibility.MODE_IN);
                    t.setDuration(200);

                    TransitionManager.beginDelayedTransition(purposePanel, t);

                    purposePanel.addView(custom);
                }
            }
        });


        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            purp = data.getStringExtra("descriptions");
            purp_name = data.getStringExtra("names");
            CreateProject2 createProject2 = (CreateProject2) getActivity();
            createProject2.setEdit1(purp_name, purp);
            purposePanel.removeAllViews();
            countPurposes = 0;
            Toast.makeText(createProject2, purp + " | " + purp_name, Toast.LENGTH_SHORT).show();
            create();
        }
    }

    private void create(){
        CreateProject2 createProject = (CreateProject2) getActivity();
        if(!purp_name.isEmpty()){
            String[] purpName = purp_name.split("✴\uFE0F");
            String[] purps = purp.split("✴\uFE0F");
            for(int i = 0; i < purpName.length; i++){
                View custom = getLayoutInflater().inflate(R.layout.problem_panel, null);
                TextView name = custom.findViewById(R.id.problemName);
                TextView descr = custom.findViewById(R.id.problemDescription);
                ImageView edit = custom.findViewById(R.id.editProblem);
                ImageView main = custom.findViewById(R.id.problemImage);
                countPurposes += 1;

                Uri uri = Uri.parse(createProject.getUriPath());

                main.setImageURI(uri);
                descr.setText(purps[i]);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), EditProblemPurpose.class);
                        i.putExtra("names", createProject.getPurposes_name());
                        i.putExtra("descriptions", createProject.getPurposes());
                        i.putExtra("uriPath", createProject.getUriPath());
                        i.putExtra("prTitle", createProject.getPrName());
                        i.putExtra("id", countPurposes - 1);
                        startActivityForResult(i, 1);
                    }
                });

                name.setText(purpName[i]);

                purposePanel.addView(custom);
            }
        }
    }

}