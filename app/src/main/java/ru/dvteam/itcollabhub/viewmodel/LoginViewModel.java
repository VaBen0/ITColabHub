package ru.dvteam.itcollabhub.viewmodel;

import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.CallBackBoolean;
import ru.dvteam.itcollabhub.LogIn;
import ru.dvteam.itcollabhub.model.LoginModel;

public class LoginViewModel extends ViewModel {
    private String EmailAddress, Password;

    private MutableLiveData<Boolean> correctlyEmail;
    private MutableLiveData<Boolean> passNormLength;

    public void setEmail(String email) {
        EmailAddress = email;
        correctlyEmail.setValue(LoginModel.isEmailValid(email));
    }

    public void setPass(String pass) {
        Password = pass;
        passNormLength.setValue(LoginModel.isPasswordLengthGreaterThan5(pass));
    }

    public MutableLiveData<Boolean> getEmailValid(){
        if(correctlyEmail == null){
            correctlyEmail = new MutableLiveData<>();
        }
        return correctlyEmail;
    }

    public MutableLiveData<Boolean> getPassNormLength(){
        if(passNormLength == null){
            passNormLength = new MutableLiveData<>();
        }
        return passNormLength;
    }

    public void onLoginClick(SharedPreferences sharedPreferences, CallBackBoolean result) {

        LoginModel loginModel = new LoginModel(EmailAddress, Password);
        loginModel.authorizeUser(sharedPreferences, new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                result.invoke(res);
            }
        });

    }

    public void onRegisterClick(View view) {

        System.out.println("Register");

    }

    public void onForgotClick(View view) {

        System.out.println("Forgot");

    }
}
