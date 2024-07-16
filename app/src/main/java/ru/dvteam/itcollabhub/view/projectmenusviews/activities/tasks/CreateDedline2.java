package ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.databinding.ActivityCreateDedline2Binding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.AddDeadlineForParticipant;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.AddDeadlineForRole;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.AddTaskForParticipant;
import ru.dvteam.itcollabhub.view.projectmenusviews.fragments.AddTaskForRole;

public class CreateDedline2 extends AppCompatActivity {

    ActivityCreateDedline2Binding binding;
    private String dayCalendar, monthCalendar, yearCalendar;
    private String mail, id, title, prPhoto, taskName, queue, idsArrStr;
    private ArrayList<String> idsArr, idsTextBlocks, idsLinkBlocks, idsYouTubeBlocks, idsImageBlocks;
    String[] textBlockArr, linkBlockArr, youtubeBlockArr, imageBlockArr;
    private Boolean click = true;
    public static Activity fa;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;
    Fragment addTaskForParticipant, addTaskForRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityCreateDedline2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        mail = sharedPreferences.getString("UserMail", "");

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(CreateDedline2.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        addTaskForParticipant = Fragment.instantiate(this, AddDeadlineForParticipant.class.getName());
        addTaskForRole = Fragment.instantiate(this, AddDeadlineForRole.class.getName());

        idsArr = new ArrayList<>();
        idsTextBlocks = new ArrayList<>();
        idsLinkBlocks = new ArrayList<>();
        idsYouTubeBlocks = new ArrayList<>();
        idsImageBlocks = new ArrayList<>();

        id = Objects.requireNonNull(getIntent().getExtras()).getString("projectId");
        title = Objects.requireNonNull(getIntent().getExtras()).getString("projectTitle");
        prPhoto = Objects.requireNonNull(getIntent().getExtras()).getString("projectUrlPhoto");
        taskName = Objects.requireNonNull(getIntent().getExtras()).getString("TaskName");
        queue = Objects.requireNonNull(getIntent().getExtras()).getString("Queue");
        String stringBlock = Objects.requireNonNull(getIntent().getExtras()).getString("StringBlock");
        String linkBlock = Objects.requireNonNull(getIntent().getExtras()).getString("LinkBlock");
        String youtubeBlock = Objects.requireNonNull(getIntent().getExtras()).getString("YouTubeBlock");
        String imageBlock = Objects.requireNonNull(getIntent().getExtras()).getString("ImageBlock");
        System.out.println(taskName);

        getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, addTaskForParticipant)
                                .commit();

        binding.nameProject.setText(title);

        binding.addParticipant.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView2, addTaskForParticipant)
                    .commit();
        });
        binding.addRole.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView2, addTaskForParticipant)
                    .commit();
        });

        Glide
                .with(this)
                .load(prPhoto)
                .into(binding.prLogo);

        assert stringBlock != null;
        if(stringBlock.equals("Empty")){
            textBlockArr = new String[0];
        }else{
            textBlockArr = stringBlock.split("_/-");
        }
        assert linkBlock != null;
        if(linkBlock.equals("Empty")){
            linkBlockArr = new String[0];
        }else{
            linkBlockArr = linkBlock.split("_/-");
        }
        assert youtubeBlock != null;
        if(youtubeBlock.equals("Empty")){
            youtubeBlockArr = new String[0];
        }else{
            youtubeBlockArr = youtubeBlock.split("_/-");
        }
        assert imageBlock != null;
        if(imageBlock.equals("Empty")){
            imageBlockArr = new String[0];
        }else{
            imageBlockArr = imageBlock.split("_/-");
        }



        int i = Calendar.getInstance().get(Calendar.YEAR);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Russia"));
        calendar.set(i, 0, 1);
        long startYear = calendar.getTimeInMillis();

        calendar.set(i + 1, 0, 1);
        long endYear = calendar.getTimeInMillis();

        CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();

        calendarConstraintBuilder.setValidator(DateValidatorPointForward.now());
        calendarConstraintBuilder.setStart(startYear);
        calendarConstraintBuilder.setEnd(endYear);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Выбри дату для участников")
                .setCalendarConstraints(calendarConstraintBuilder.build())
                .setTheme(R.style.datePicker)
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("Russia"));
                utc.setTimeInMillis(selection);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd,MM,yyyy");
                String formatted = format.format(utc.getTime());
                String[] dateArr = formatted.split(",");
                dayCalendar = dateArr[0];
                monthCalendar = dateArr[1];
                yearCalendar = dateArr[2];
                String res = "Выбранная дата: " + formatted.replace(",", ".");
                binding.date.setText(res);
            }
        });

        binding.chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //StartTime.show();
                datePicker.show(getSupportFragmentManager(), "you_can_give_any_name");
            }
        });

        binding.uploadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(id + mail + taskName + queue + idsArrStr);
                if(click) {
                    binding.load.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.VISIBLE);
                    click = false;
                    if (!idsArr.isEmpty()) {
                        idsArrStr = String.join(",", idsArr);
                        createAllTextBlocks();
                    } else {
                        //Toast.makeText(PartisipantTasks.this, "Добавьте людей", Toast.LENGTH_SHORT).show();
                        idsArrStr = "";
                        createAllTextBlocks();
                    }
                }
            }
        });

    }

    public void createAllTextBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < textBlockArr.length; i++){
            int finalI = i;
            post.postDataCreateStringBlock("CreateTextBlock", id, mail, textBlockArr[i], new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsTextBlocks.add(res);
                    if(finalI == textBlockArr.length - 1){
                        createAllLinksBlocks();
                    }
                }
            });
        }
        if(textBlockArr.length == 0){
            createAllLinksBlocks();
        }
    }

    public void createAllLinksBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < linkBlockArr.length; i++){
            String[] s = linkBlockArr[i].split("\uD83D\uDD70");
            int finalI = i;
            post.postDataCreateLinkBlock("CreateLinkBlock", id, mail, s[0], s[1], new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsLinkBlocks.add(res);
                    if(finalI == linkBlockArr.length - 1){
                        createAllYoutubeBlocks();
                    }
                }
            });
        }
        if(linkBlockArr.length == 0){
            createAllYoutubeBlocks();
        }
    }

    public void createAllYoutubeBlocks(){
        PostDatas post = new PostDatas();
        for(int i = 0; i < youtubeBlockArr.length; i++){
            String[] s = youtubeBlockArr[i].split("\uD83D\uDD70");
            int finalI = i;
            post.postDataCreateYoutubeBlock("CreateYouTubeBlock", id, mail, s[0], s[1], new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsYouTubeBlocks.add(res);
                    if(finalI == youtubeBlockArr.length - 1){
                        createAllImageBlocks(0);
                    }
                }
            });
        }
        if(youtubeBlockArr.length == 0){
            createAllImageBlocks(0);
        }
    }

    public void createAllImageBlocks(int i){
        PostDatas post = new PostDatas();
        if(imageBlockArr.length == 0){
            createTask();
        }else{
            String[] s = imageBlockArr[i].split("\uD83D\uDD70");
            File file = new File(s[0]);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            int finalI = i;
            post.postDataCreateImageBlock("CreateImageBlock", s[1], requestBody, id, mail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    idsImageBlocks.add(res);
                    if(finalI == imageBlockArr.length - 1){
                        createTask();
                    }else{
                        createAllImageBlocks(finalI + 1);
                    }
                }
            });
        }
    }

    public void createTask(){
        String idsTextBlocksStr;
        String idsLinkBlocksStr;
        String idsImageBlocksArr;
        String idsYouTubeBlocksArr;
        String date = dayCalendar + "." + monthCalendar + "." + yearCalendar;

        if(idsTextBlocks.isEmpty()){
            idsTextBlocksStr = "";
        }else{
            idsTextBlocksStr = String.join(",", idsTextBlocks);
        }
        if(idsLinkBlocks.isEmpty()){
            idsLinkBlocksStr = "";
        }else{
            idsLinkBlocksStr = String.join(",", idsLinkBlocks);
        }
        if(idsImageBlocks.isEmpty()){
            idsImageBlocksArr = "";
        }else{
            idsImageBlocksArr = String.join(",", idsImageBlocks);
        }
        if(idsYouTubeBlocks.isEmpty()){
            idsYouTubeBlocksArr = "";
        }else{
            idsYouTubeBlocksArr = String.join(",", idsYouTubeBlocks);
        }

        PostDatas post = new PostDatas();

        post.postDataCreateDeadline("CreateTask", id, mail, taskName, queue, idsTextBlocksStr,
                idsLinkBlocksStr, idsArrStr, idsImageBlocksArr,
                idsYouTubeBlocksArr, date, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        CreateDeadline.fa.finish();
                        finish();
                    }
                });
    }

    public String getMail(){
        return mail;
    }
    public String getId(){
        return id;
    }
    public void addId(String id){
        idsArr.add(id);
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