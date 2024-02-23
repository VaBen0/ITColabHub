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

public class ConfirmForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_forgot_password);
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            String mail = arguments.getString("mail");


            Button conf = findViewById(R.id.confirmBut);
            EditText User_code = findViewById(R.id.code);
            TextView Or_Enter = findViewById(R.id.enterBut);
            TextView col = findViewById(R.id.collaborotory);
            TextView it = findViewById(R.id.it);
            TextView hub = findViewById(R.id.hub);

            Typeface face = Typeface.createFromAsset(getAssets(), "font/ArchitectsDaughter-Regular.ttf");
            it.setTypeface(face);
            hub.setTypeface(face);
            col.setTypeface(face);

            Or_Enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConfirmForgotPassword.this, LogIn.class);
                    startActivity(intent);
                }
            });

            conf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (User_code.getText().toString().equals("")) {
                        User_code.setHint("Введите ваш логин");
                    } else {
                        PostDatas post = new PostDatas();
                        post.postDataConfirm("UserLogInMai2l", mail, User_code.getText().toString(), new CallBackInt() {
                            @Override
                            public void invoke(String res) {
                                Toast.makeText(ConfirmForgotPassword.this, res, Toast.LENGTH_SHORT).show();
                                if(res.equals("Проверка почты прошла успешно")) {
                                    Intent intent = new Intent(ConfirmForgotPassword.this, LogIn.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    /*public void postData(String mail, String code){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.confirm("UserLogInMai2l", mail, code);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                if(response.body().getReturn().equals("Правильный код")) {
                    change(response.body().getReturn());
                }
                else{
                    Toast.makeText(ConfirmForgotPassword.this, response.body().getReturn(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(ConfirmForgotPassword.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void change(String res){
        Toast toast = Toast.makeText(this, res, Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(ConfirmForgotPassword.this, LogIn.class);
        startActivity(intent);
    }*/
}