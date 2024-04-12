package ru.dvteam.itcollabhub.view.projectmenusviews.activities.create;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityCreateProject2Binding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.ActivityProject;

public class CreateProject2 extends AppCompatActivity {

    ActivityCreateProject2Binding binding;
    String mail;

    private ImageView Img;
    private NavController navController;
    private EditText description;
    private static final int PICK_IMAGES_CODE = 0;
    private String purposes_name = "", purposes = "", tasks_name = "", tasks = "";
    private String id1 = "";
    private String mediaPath = "", uriPath = "";
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    int score;
    String title, description1;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        mail = sharedPreferences.getString("UserMail", "");
        score = sharedPreferences.getInt("UserScore", 0);

        binding = ActivityCreateProject2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(CreateProject2.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        mediaPath = arguments.getString("mediaPath");
        uriPath = arguments.getString("uriPath");
        title = arguments.getString("title");
        description1 = arguments.getString("prDescription");

        if(!uriPath.isEmpty()){
            Uri uri = Uri.parse(uriPath);
            binding.prLogo.setImageURI(uri);
        }
        binding.linearFriends.setVisibility(View.INVISIBLE);
        binding.linearProjects.setVisibility(View.VISIBLE);
        
        binding.nameProject.setText(title);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        binding.adParticipiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearProjects.setVisibility(View.INVISIBLE);
                binding.linearFriends.setVisibility(View.VISIBLE);

                Bundle bundle = new Bundle();
                bundle.putString("mail", mail);
                navController.navigate(R.id.participant, bundle);
            }
        });
        binding.adActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linearFriends.setVisibility(View.INVISIBLE);
                binding.linearProjects.setVisibility(View.VISIBLE);

                navController.navigate(R.id.differentActivity);
            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purposeMain = "";
                String taskMain = "";
                String mainId;

                String[] purpose1 = purposes_name.split("✴\uFE0F");
                String[] purpose2 = purposes.split("✴\uFE0F");
                String[] task1 = tasks_name.split("✴\uFE0F");
                String[] task2 = tasks.split("✴\uFE0F");

                if (id1.isEmpty()) {
                    mainId = "Пользователи были не выбраны";
                } else {
                    mainId = id1;
                }
                if (purposes_name.isEmpty()) {
                    Toast.makeText(CreateProject2.this, "Нет целей", Toast.LENGTH_SHORT).show();
                } else if (tasks_name.isEmpty()) {
                    Toast.makeText(CreateProject2.this, "Нет задач", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < purpose1.length; i++) {
                        if (i != purpose1.length - 1) {
                            purposeMain = purposeMain + purpose1[i] + "\uD83D\uDD70" + purpose2[i] + "\uD83D\uDD70";
                        } else {
                            purposeMain = purposeMain + purpose1[i] + "\uD83D\uDD70" + purpose2[i];
                        }
                    }
                    for (int i = 0; i < task1.length; i++) {
                        if (i != task1.length - 1) {
                            taskMain = taskMain + task1[i] + "\uD83D\uDD70" + task2[i] + "\uD83D\uDD70";
                        } else {
                            taskMain = taskMain + task1[i] + "\uD83D\uDD70" + task2[i];
                        }
                    }

                    if (mediaPath.isEmpty()) {
                        PostDatas post = new PostDatas();
                        post.postDataCreateProjectWithoutImage("CreateNewProject", title, mail, purposeMain, taskMain,
                                description1, mainId, new CallBackInt() {
                                    @Override
                                    public void invoke(String res) {
                                        Toast.makeText(CreateProject2.this, res, Toast.LENGTH_SHORT).show();
                                        if (res.equals("Успешно")) {
                                            Intent intent = new Intent(CreateProject2.this, ActivityProject.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    } else {
                        File file = new File(mediaPath);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                        PostDatas post = new PostDatas();
                        post.postDataCreateProject("CreateNewProject", title, requestBody, mail, purposeMain, taskMain,
                                description1, mainId, new CallBackInt() {
                                    @Override
                                    public void invoke(String res) {
                                        Toast.makeText(CreateProject2.this, res, Toast.LENGTH_SHORT).show();
                                        if (res.equals("Успешно")) {
                                            Intent intent = new Intent(CreateProject2.this, ActivityProject.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    }
                }
            }
        });

    }

    private void setActivityFormat(){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.blue));
            binding.linearProjects.setBackgroundResource(R.drawable.blue_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.green));
            binding.linearProjects.setBackgroundResource(R.drawable.green_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.brown));
            binding.linearProjects.setBackgroundResource(R.drawable.brown_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.light_gray));
            binding.linearProjects.setBackgroundResource(R.drawable.light_gray_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.ohra));
            binding.linearProjects.setBackgroundResource(R.drawable.ohra_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.red));
            binding.linearProjects.setBackgroundResource(R.drawable.red_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.red));
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.orange));
            binding.linearProjects.setBackgroundResource(R.drawable.orange_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.violete));
            binding.linearProjects.setBackgroundResource(R.drawable.violete_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2.this,R.color.main_green));
            binding.linearProjects.setBackgroundResource(R.drawable.blue_green_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2.this, R.color.main_green));
        }
    }

    public void setPurp(String purpName, String purp){
        if(purposes_name.isEmpty()){
            purposes_name += purpName;
            purposes += purp;
        }
        else{
            purposes_name = purposes_name + "✴\uFE0F" + purpName;
            purposes = purposes + "✴\uFE0F" + purp;
        }
    }
    public void setTask(String taskName, String task){
        if(tasks_name.isEmpty()){
            tasks_name += taskName;
            tasks = task;
        }
        else{
            tasks_name = tasks_name + "✴\uFE0F" + taskName;
            tasks = tasks + "✴\uFE0F" + task;
        }
    }
    public void setId(String id){
        if(id1.isEmpty()){
            id1 = id;
        }
        else{
            id1 = id1 + "," + id;
        }
        Toast.makeText(this, "Участник добавлен", Toast.LENGTH_SHORT).show();
    }

    public int getScore(){
        return score;
    }
    public String getMail(){
        return mail;
    }

    public void taskSet(){
        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment2);
        navController2.navigate(R.id.clop3);
    }
    public void purpose(){
        NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment2);
        navController2.navigate(R.id.clop1);
    }
    public String getPurposes_name(){
        return purposes_name;
    }
    public String getPurposes(){
        return purposes;
    }
    public String getTasks_name(){
        return tasks_name;
    }
    public String getTasks(){
        return tasks;
    }
    public String getPeoplesIds(){return id1;}
    public void setEdit1(String name, String purp){
        purposes_name = name;
        purposes = purp;
    }
    public String getUriPath(){
        return uriPath;
    }

    public String getPrName(){
        return title;
    }

    public void setEdit2(String name, String problem){
        tasks_name = name;
        tasks = problem;
    }
    public void delPeople(String id){
        ArrayList<String> idArr = new ArrayList<String>(Arrays.asList(id1.split(",")));
        idArr.remove(id);

        id1 = String.join(",", idArr);
    }

    public void setThemeActivity(){
        int themeType = UsersChosenTheme.getThemeNum();

        switch (themeType) {
            case (1):
                setTheme(R.style.Theme_ITCollabHub_Blue);
                break;
            case (2):
                setTheme(R.style.Theme_ITCollabHub_Green);
                break;
            case (3):
                setTheme(R.style.Theme_ITCollabHub_Brown);
                break;
            case (4):
                setTheme(R.style.Theme_ITCollabHub_PinkGold);
                break;
            case (5):
                setTheme(R.style.Theme_ITCollabHub_Ohra);
                break;
            case (6):
                setTheme(R.style.Theme_ITCollabHub_Red);
                break;
            case (7):
                setTheme(R.style.Theme_ITCollabHub_Orange);
                break;
            case (8):
                setTheme(R.style.Theme_ITCollabHub_Violete);
                break;
            case (9):
                setTheme(R.style.Theme_ITCollabHub_BlueGreen);
                break;
        }

    }
}