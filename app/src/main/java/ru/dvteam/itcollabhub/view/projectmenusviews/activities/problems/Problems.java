package ru.dvteam.itcollabhub.view.projectmenusviews.activities.problems;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.view.UsersChosenTheme;
import ru.dvteam.itcollabhub.view.adapter.ProblemAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDelOrChangeAd;
import ru.dvteam.itcollabhub.databinding.ActivityProblemsBinding;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProblemViewModel;

public class Problems extends AppCompatActivity {

    ActivityProblemsBinding binding;
    ProblemViewModel problemViewModel;
    private Boolean acces = false, acces2 = false;
    private static final int PICK_IMAGES_CODE = 0;
    private String mediaPath = "";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProblemsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        registerResult();
        problemViewModel = new ViewModelProvider(this).get(ProblemViewModel.class);
        initEditText();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(Problems.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.blockMenu.setVisibility(View.GONE);

        binding.blockMenu.setVisibility(View.VISIBLE);
        final Animation show = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.block_menu_add2);
        binding.blockMenu.startAnimation(show);

        binding.nameProject.setText(problemViewModel.getProjectTitle());
        Glide
                .with(Problems.this)
                .load(problemViewModel.getProjectLog())
                .into(binding.prLogo);
        Glide
                .with(Problems.this)
                .load(problemViewModel.getProjectLog())
                .into(binding.problemImage);


        binding.addProblem.setOnClickListener(v -> {
            if(problemViewModel.getProblemsCnt() < 31 && acces && acces2){
                problemViewModel.onCreateClick();
                problemViewModel.setProblems();
                binding.name.setText("");
                binding.description1.setText("");
                Glide
                        .with(this)
                        .load(problemViewModel.getProjectLog())
                        .into(binding.problemImage);
            }
        });

        problemViewModel.getDescrIsEmpty().observe(this, aBoolean -> {
            acces = aBoolean;
        });
        problemViewModel.getNameIsEmpty().observe(this, aBoolean -> {
            acces2 = aBoolean;
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.problemsPlace.setLayoutManager(layoutManager);

        problemViewModel.getProblemsArray().observe(this, purposeInformations -> {
            ProblemAdapter adapter = new ProblemAdapter(purposeInformations, this, new CallBackDelOrChangeAd() {
                @Override
                public void delete(String id) {
                    problemViewModel.onCompleteClick(id);
                }

                @Override
                public void change(int position) {
                    problemViewModel.onChange(position, res -> {
                        Intent intent = new Intent(Problems.this, EditProblem.class);
                        startActivity(intent);
                    });
                }
            });
            binding.problemsPlace.setAdapter(adapter);
        });

        problemViewModel.setProblems();

        if(Build.VERSION.SDK_INT >= 33) {
            binding.addPhoto.setOnClickListener(view -> pickImage());
        }
        else{
            binding.addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(Problems.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Problems.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
                    }
                }
            });
        }
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), PICK_IMAGES_CODE);
            } else {
                Toast.makeText(Problems.this, "You loser", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_CODE){

            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                problemViewModel.setMediaPath(cursor.getString(columnIndex));
                binding.problemImage.setImageURI(imageUri);
                cursor.close();
                acces = true;
            }

        }
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            Uri imageUri = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            //imageUri.getPath();
                            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                            assert cursor != null;
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            problemViewModel.setMediaPath(cursor.getString(columnIndex));
                            binding.problemImage.setImageURI(imageUri);
                            cursor.close();
                            acces = true;
                        }catch (Exception e){
                            Toast.makeText(Problems.this, "LOSER", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }



//    public void getProblems(String id3){
//        PostDatas post = new PostDatas();
//        post.postDataGetProblems("GetProblems", id3, new CallBackInt() {
//            @Override
//            public void invoke(String res) {
//                String[] inf = res.split("\uD83D\uDD70");
//                assert id3 != null;
//                String[] idm = id3.split(",");
//                for(int i = 0; i < inf.length; i += 4){
//                    View custom = getLayoutInflater().inflate(R.layout.problem_panel, null);
//                    ImageView loadImg = custom.findViewById(R.id.problemImage);
//                    TextView name = custom.findViewById(R.id.problemName);
//                    TextView descr = custom.findViewById(R.id.problemDescription);
//                    TextView title = custom.findViewById(R.id.problemTitlePanel);
//                    View back = custom.findViewById(R.id.view8);
//                    LinearLayout yesOrNo = custom.findViewById(R.id.yes_or_no);
//                    LinearLayout descript = custom.findViewById(R.id.description_purpose);
//                    Button yes = custom.findViewById(R.id.yes);
//                    Button no = custom.findViewById(R.id.no);
//                    ImageView editBut = custom.findViewById(R.id.editProblem);
//
//                    if(inf[i + 2].equals("1")){
//                        back.setBackgroundResource(R.drawable.green_transperent);
//                        countTicked += 1;
//                    }
//                    int finalI = i;
//
//                    title.setText(inf[i]);
//                    name.setText(inf[i]);
//                    descr.setText(inf[i+1]);
//                    Glide
//                            .with(Problems.this)
//                            .load(inf[i+3])
//                            .into(loadImg);
//
//                    custom.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if(countTicked >= idm.length - 1 && inf[finalI + 2].equals("0")){
//                                Toast.makeText(Problems.this, "Эту задачу нельзя отметить выполненной", Toast.LENGTH_SHORT).show();
//                            }
//                            else if(!clicked && inf[finalI + 2].equals("0")) {
//                                back.setBackgroundResource(R.drawable.progress_panel_background2);
//                                descript.setVisibility(View.GONE);
//                                yesOrNo.setVisibility(View.VISIBLE);
//                                clicked = true;
//                            }
//                        }
//                    });
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            post.postDatasetProblemIsEnd("SetProblemComplete", idm[finalI / 4], prId, mail, new CallBackInt() {
//                                @Override
//                                public void invoke(String res) {
//                                    back.setBackgroundResource(R.drawable.progress_panel_background);
//                                    descript.setVisibility(View.VISIBLE);
//                                    yesOrNo.setVisibility(View.GONE);
//                                    clicked = false;
//                                    back.setBackgroundResource(R.drawable.green_transperent);
//                                    inf[finalI + 2] = "1";
//                                    countTicked += 1;
//                                }
//                            });
//                        }
//                    });
//
//                    no.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            back.setBackgroundResource(R.drawable.progress_panel_background);
//                            descript.setVisibility(View.VISIBLE);
//                            yesOrNo.setVisibility(View.GONE);
//                            clicked = false;
//                        }
//                    });
//
//                    editBut.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(Problems.this, EditProblem.class);
//                            intent.putExtra("problemPhoto", inf[finalI+3]);
//                            intent.putExtra("projectTitle", projectTitle);
//                            intent.putExtra("projectUrlPhoto", photoProject);
//                            intent.putExtra("projectId1", prId);
//                            intent.putExtra("problemName", inf[finalI]);
//                            intent.putExtra("problemDescription", inf[finalI + 1]);
//                            intent.putExtra("problemId", idm[finalI / 4]);
//                            startActivity(intent);
//                        }
//                    });
//
//                    countProblems++;
//                    binding.reminderPlace.addView(custom, 0);
//                }
//            }
//        });
//    }

//    private void postProblems(){
//        PostDatas postDatas = new PostDatas();
//        postDatas.postDataGetProjectProblems("GetProjectProblemsIDs", prId, new CallBackInt() {
//            @Override
//            public void invoke(String res) {
//                getProblems(res);
//            }
//        });
//    }

    private void initEditText(){
        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                problemViewModel.setProblemName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.description1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                problemViewModel.setProblemDescr(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        problemViewModel.setProblems();
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