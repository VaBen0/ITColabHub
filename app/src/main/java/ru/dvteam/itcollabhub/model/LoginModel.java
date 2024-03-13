package ru.dvteam.itcollabhub.model;

import android.content.SharedPreferences;
import android.util.Patterns;

import ru.dvteam.itcollabhub.CallBackBoolean;
import ru.dvteam.itcollabhub.CallBackInt;
import ru.dvteam.itcollabhub.CallBackInt1;
import ru.dvteam.itcollabhub.PostDatas;

public class LoginModel {
    private String strEmailAddress;
    private String strPassword;
    private PostDatas postDatas = new PostDatas();

    public LoginModel(String EmailAddress, String Password) {
        strEmailAddress = EmailAddress;
        strPassword = Password;
    }

    static public boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static public boolean isPasswordLengthGreaterThan5(String pass) {
        return pass.length() > 1;
    }

    public void authorizeUser(SharedPreferences sPref, CallBackBoolean result){
        postDatas.postDataLogIn("UserLogIn", strEmailAddress, strPassword, new CallBackInt1() {
            @Override
            public void invoke(String res, String name) {
                if(res.equals("Успешный вход")) {

                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("UserReg", "true");
                    ed.putString("UserMail", strEmailAddress);
                    ed.apply();

                    result.invoke(true);
                }else{
                    result.invoke(false);
                }
            }
        });
    }
}
