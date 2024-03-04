package ru.dvteam.itcollabhub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityCreateDedline2Binding;

public class CreateDedline2 extends AppCompatActivity {

    ActivityCreateDedline2Binding binding;
    private String dayCalendar, monthCalendar, yearCalendar;
    private String mail, id, title, prPhoto, taskName, queue, idsArrStr;
    private ArrayList<String> idsArr, idsTextBlocks, idsLinkBlocks, idsYouTubeBlocks, idsImageBlocks;
    String[] textBlockArr, linkBlockArr, youtubeBlockArr, imageBlockArr;
    private Boolean click = true;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateDedline2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);
        setActivityFormat(score);

        idsArr = new ArrayList<String>();
        idsTextBlocks = new ArrayList<String>();
        idsLinkBlocks = new ArrayList<String>();
        idsYouTubeBlocks = new ArrayList<String>();
        idsImageBlocks = new ArrayList<String>();

        id = Objects.requireNonNull(getIntent().getExtras()).getString("projectId");
        title = Objects.requireNonNull(getIntent().getExtras()).getString("projectTitle");
        prPhoto = Objects.requireNonNull(getIntent().getExtras()).getString("projectUrlPhoto");
        taskName = Objects.requireNonNull(getIntent().getExtras()).getString("TaskName");
        queue = Objects.requireNonNull(getIntent().getExtras()).getString("Queue");
        String stringBlock = Objects.requireNonNull(getIntent().getExtras()).getString("StringBlock");
        String linkBlock = Objects.requireNonNull(getIntent().getExtras()).getString("LinkBlock");
        String youtubeBlock = Objects.requireNonNull(getIntent().getExtras()).getString("YouTubeBlock");
        String imageBlock = Objects.requireNonNull(getIntent().getExtras()).getString("ImageBlock");

        binding.nameProject.setText(title);

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

        PostDatas post = new PostDatas();
        post.postDataGetProjectParticipants("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String s1, String s2, String s3) {
                String[] ids = s1.split("\uD83D\uDD70");
                String[] names = s2.split("\uD83D\uDD70");
                String[] photos = s3.split("\uD83D\uDD70");

                for (int i = 0; i < ids.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);

                    userCircle.setVisibility(View.GONE);
                    project1.setVisibility(View.GONE);
                    setButtonColor(score, messege);

                    Glide
                            .with(CreateDedline2.this)
                            .load(photos[i])
                            .into(loadImage);
                    nameu.setText(names[i]);

                    int finalI = i;

                    messege.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idsArr.add(ids[finalI]);
                            messege.setVisibility(View.GONE);
                        }
                    });
                    binding.main.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.main.addView(empty);
            }
        });

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
                if(click) {
                    binding.load.setVisibility(View.VISIBLE);
                    binding.back.setVisibility(View.VISIBLE);
                    click = false;
                    /*if (!idsArr.isEmpty()) {
                        idsArrStr = String.join(",", idsArr);
                        createAllTextBlocks();
                    } else {
                        //Toast.makeText(PartisipantTasks.this, "Добавьте людей", Toast.LENGTH_SHORT).show();
                        idsArrStr = "";
                        createAllTextBlocks();
                    }*/
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
        post.postDataCreateDeadline("CreateDeadline", id, mail, taskName, queue, idsTextBlocksStr,
                idsLinkBlocksStr, idsArrStr, idsImageBlocksArr,
                idsYouTubeBlocksArr, date, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        /*Intent intent = new Intent(PartisipantTasks.this, TasksActivityMain.class);
                        intent.putExtra("projectId", id);
                        intent.putExtra("projectTitle", title);
                        intent.putExtra("projectUrlPhoto", prPhoto);
                        startActivity(intent);
                        finish();*/
                        CreateDeadline.fa.finish();
                        finish();
                    }
                });
    }

    private void setActivityFormat(int score){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.red));
        }
        else if(score < 30000) {
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateDedline2.this,R.color.main_green));
        }
    }

    private void setButtonColor(int score, ImageView but){
        if(score < 100){
            but.setImageResource(R.drawable.ad);
        }
        else if(score < 300){
            but.setImageResource(R.drawable.green_add);
        }
        else if(score < 1000){
            but.setImageResource(R.drawable.brown_add);
        }
        else if(score < 2500){
            but.setImageResource(R.drawable.light_gray_add);
        }
        else if(score < 7000){
            but.setImageResource(R.drawable.ohra_add);
        }
        else if(score < 17000){
            but.setImageResource(R.drawable.red_add);
        }
        else if(score < 30000) {
            but.setImageResource(R.drawable.brown_add);
        }
        else if(score < 50000){
            but.setImageResource(R.drawable.violete_add);
        }
        else{
            but.setImageResource(R.drawable.blue_green_add);
        }
    }


}