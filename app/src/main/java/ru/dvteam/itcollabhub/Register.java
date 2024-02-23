package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String tg, vk, web;

        EditText user_pass = findViewById(R.id.passu);
        EditText user_second_pass = findViewById(R.id.passuagain);
        EditText user_mail = findViewById(R.id.mailu);
        TextView col = findViewById(R.id.collaborotory);
        TextView it = findViewById(R.id.it);
        TextView hub = findViewById(R.id.hub);
        //EditText tg_link = findViewById(R.id.tg_link);
        //EditText vk_link = findViewById(R.id.vk_link);
        //EditText web_link = findViewById(R.id.web_link);

        /*if(tg_link.getText().toString().isEmpty()){
            tg = "non";
        }else{
            tg = tg_link.getText().toString();
        }
        if(vk_link.getText().toString().isEmpty()){
            vk = "non";
        }else{
            vk = vk_link.getText().toString();
        }
        if(web_link.getText().toString().isEmpty()){
            web = "non";
        }else{
            web = web_link.getText().toString();
        }*/

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        it.setTypeface(face);
        hub.setTypeface(face);
        col.setTypeface(face);

        Button conf = findViewById(R.id.Reg);

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_mail.getText().toString().equals("")){
                    user_mail.setHint("Введите вашу почту");
                }
                else if(user_pass.getText().toString().equals("")){
                    user_pass.setHint("Введите пароль");
                }
                else{
                    if(user_pass.getText().toString().equals(user_second_pass.getText().toString())) {
                        PostDatas post = new PostDatas();
                        post.postDataPostCodeMail("PostToNewUserCode", user_mail.getText().toString(), new CallBackInt() {
                            @Override
                            public void invoke(String res) {
                                Toast.makeText(Register.this, res, Toast.LENGTH_SHORT).show();

                                if(res.equals("Код отправлен")) {
                                    Intent intent = new Intent(Register.this, ConfirmReg.class);
                                    intent.putExtra("pass", user_pass.getText().toString());
                                    intent.putExtra("mail", user_mail.getText().toString());
                                    intent.putExtra("tg_link", "non");
                                    intent.putExtra("vk_link", "non");
                                    intent.putExtra("web_link", "non");
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                    else{
                        user_second_pass.setHint("Пароли не совпадают");
                        user_second_pass.setText(null);
                    }
                }

            }
        });
    }
}
