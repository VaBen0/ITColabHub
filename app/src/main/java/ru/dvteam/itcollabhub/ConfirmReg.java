package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class ConfirmReg extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reg);

        TextView col = findViewById(R.id.collaborotory);
        TextView it = findViewById(R.id.it);
        TextView hub = findViewById(R.id.hub);

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        it.setTypeface(face);
        hub.setTypeface(face);
        col.setTypeface(face);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            String mail = arguments.getString("mail");
            String pass = arguments.getString("pass");
            String tg = arguments.getString("tg_link");
            String vk = arguments.getString("vk_link");
            String web = arguments.getString("web_link");

            TextView enterBut = findViewById(R.id.enterBut);

            enterBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConfirmReg.this, LogIn.class);
                    startActivity(intent);
                }
            });

            Button conf = findViewById(R.id.confirmBut);
            EditText User_code = findViewById(R.id.code);

            conf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User_code.getText().toString().equals("")) {
                        User_code.setHint("Введите ваш логин");
                    } else {
                        PostDatas post = new PostDatas();
                        post.postDataConfirm("CheckerCode", mail, User_code.getText().toString(), new CallBackInt() {
                            @Override
                            public void invoke(String res) {
                                if(res.equals("Проверка почты прошла успешно")) {
                                    post.postDataRegUser("RegNewUser", mail, pass, "", new CallBackInt() {
                                        @Override
                                        public void invoke(String res1) {
                                            Toast.makeText(ConfirmReg.this, res1, Toast.LENGTH_SHORT).show();
                                            if (res1.equals("Успешная регистрация")) {

                                                if(!tg.equals("non")){
                                                    post.postDataSendLink("SendUserLinkTg", mail, tg);
                                                }
                                                if(!vk.equals("non")){
                                                    post.postDataSendLink("SendUserLinkVk", mail, vk);
                                                }
                                                if(!web.equals("non")){
                                                    post.postDataSendLink("SendUserLinkWeb", mail, web);
                                                }

                                                SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                                                SharedPreferences.Editor ed = sPref.edit();
                                                ed.putString("UserReg", "true");
                                                ed.putString("UserMail", mail);
                                                ed.apply();

                                                Intent intent = new Intent(ConfirmReg.this, CreateAccount.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(ConfirmReg.this, res, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}