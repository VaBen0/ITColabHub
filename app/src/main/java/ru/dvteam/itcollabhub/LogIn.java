package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

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

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText UserMail = findViewById(R.id.mailu);
        EditText UserPass = findViewById(R.id.passu);
        TextView RegBut = findViewById(R.id.regBut);
        TextView ForgotBut = findViewById(R.id.forgotBut);
        Button EnterBut = findViewById(R.id.enterBut);
        TextView col = findViewById(R.id.collaborotory);
        TextView it = findViewById(R.id.it);
        TextView hub = findViewById(R.id.hub);

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        it.setTypeface(face);
        hub.setTypeface(face);
        col.setTypeface(face);

        RegBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
            }
        });

        ForgotBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Forgot.class);
                startActivity(intent);
            }
        });

        EnterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserMail.getText().toString().equals("")){
                    UserMail.setHint("Введите ваш логин");
                }
                else if(UserPass.getText().toString().equals("")){
                    UserPass.setHint("Введите пароль");
                }
                else{
                    PostDatas post = new PostDatas();
                    post.postDataLogIn("UserLogIn", UserMail.getText().toString(), UserPass.getText().toString(), new CallBackInt1(){
                        @Override
                        public void invoke(String result, String name){
                            Toast.makeText(LogIn.this, result, Toast.LENGTH_SHORT).show();

                            if(result.equals("Успешный вход")) {
                                SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor ed = sPref.edit();
                                ed.putString("UserReg", "true");
                                ed.putString("UserName", result);
                                ed.putString("UserMail", UserMail.getText().toString());
                                ed.apply();

                                Intent intent = new Intent(LogIn.this, Profile.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}