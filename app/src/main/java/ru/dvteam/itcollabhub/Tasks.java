package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Tasks extends Fragment {

    private int countTasks = 0;

    LinearLayout problemPlace;
    String tasks_name, tasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasks, container, false);

        ImageView addTask = v.findViewById(R.id.add_but);
        EditText task1 = v.findViewById(R.id.name_friend);
        EditText task2 = v.findViewById(R.id.name_friend2);
        problemPlace = v.findViewById(R.id.problems_place);

        CreateProject2 createProject = (CreateProject2) getActivity();
        int score = createProject.getScore();
        tasks_name = createProject.getTasks_name();
        tasks = createProject.getTasks();
        create();

        if(score < 100){
            addTask.setBackgroundResource(R.drawable.ad);
        }
        else if(score < 300){
            addTask.setBackgroundResource(R.drawable.green_add);
        }
        else if(score < 1000){
            addTask.setBackgroundResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            addTask.setBackgroundResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            addTask.setBackgroundResource(R.drawable.ohra_add);
        }
        else if(score < 17000){
            addTask.setBackgroundResource(R.drawable.red_add);
        }
        else if(score < 30000){
            addTask.setBackgroundResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            addTask.setBackgroundResource(R.drawable.violete_add);
        }
        else{
            addTask.setBackgroundResource(R.drawable.blue_green_add);
        }

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = task1.getText().toString();
                String task = task2.getText().toString();
                if (countTasks == 30) {
                    Toast.makeText(createProject, "Вы достигли предела", Toast.LENGTH_SHORT).show();
                }
                if (taskName.isEmpty()) {
                    Toast.makeText(createProject, "Нет названия", Toast.LENGTH_SHORT).show();
                } else if (task.isEmpty()) {
                    Toast.makeText(createProject, "Нет описания", Toast.LENGTH_SHORT).show();
                } else {
                    task1.setText("");
                    task2.setText("");
                    Toast.makeText(v.getContext(), "Задача добавлена", Toast.LENGTH_SHORT).show();
                    CreateProject2 createProject = (CreateProject2) getActivity();
                    createProject.setTask(taskName, task);
                    countTasks++;

                    View custom = inflater.inflate(R.layout.problem_panel, null);
                    TextView name = custom.findViewById(R.id.problemName);
                    TextView main = custom.findViewById(R.id.textView20);
                    TextView descr = custom.findViewById(R.id.problemDescription);
                    ImageView edit = custom.findViewById(R.id.editProblem);
                    ImageView main1 = custom.findViewById(R.id.problemImage);

                    Uri uri = Uri.parse(createProject.getUriPath());

                    main1.setImageURI(uri);

                    main.setText("Описание задачи");

                    descr.setText(task);

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), EditProblemPurpose.class);
                            i.putExtra("names", createProject.getTasks_name());
                            i.putExtra("descriptions", createProject.getTasks());
                            i.putExtra("uriPath", createProject.getUriPath());
                            i.putExtra("prTitle", createProject.getPrName());
                            i.putExtra("id", countTasks - 1);
                            startActivityForResult(i, 1);
                        }
                    });

                    name.setText(taskName);

                    Transition t = null;
                    t = new Fade(Visibility.MODE_IN);
                    t.setDuration(200);

                    TransitionManager.beginDelayedTransition(problemPlace, t);

                    problemPlace.addView(custom);
                }
            }
        });

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            tasks = data.getStringExtra("descriptions");
            tasks_name = data.getStringExtra("names");
            CreateProject2 createProject2 = (CreateProject2) getActivity();
            createProject2.setEdit2(tasks_name, tasks);
            problemPlace.removeAllViews();
            countTasks = 0;
            create();
        }
    }

    private void create(){
        CreateProject2 createProject = (CreateProject2) getActivity();
        if(!tasks_name.isEmpty()){
            String[] purpName = tasks_name.split("✴\uFE0F");
            String[] purps = tasks.split("✴\uFE0F");
            for(int i = 0; i < purpName.length; i++){
                View custom = getLayoutInflater().inflate(R.layout.problem_panel, null);
                TextView name = custom.findViewById(R.id.problemName);
                TextView descr = custom.findViewById(R.id.problemDescription);
                ImageView edit = custom.findViewById(R.id.editProblem);
                ImageView main = custom.findViewById(R.id.problemImage);
                countTasks += 1;

                Uri uri = Uri.parse(createProject.getUriPath());

                main.setImageURI(uri);

                descr.setText(purps[i]);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), EditProblemPurpose.class);
                        i.putExtra("names", createProject.getTasks_name());
                        i.putExtra("descriptions", createProject.getTasks());
                        i.putExtra("uriPath", createProject.getUriPath());
                        i.putExtra("prTitle", createProject.getPrName());
                        i.putExtra("id", countTasks - 1);
                        startActivityForResult(i, 1);
                    }
                });

                name.setText(purpName[i]);

                problemPlace.addView(custom);
            }
        }
    }
}