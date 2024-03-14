package ru.dvteam.itcollabhub.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.CallBackBoolean;
import ru.dvteam.itcollabhub.CallBackInt;
import ru.dvteam.itcollabhub.model.RegisterModel;

public class RegisterViewModel extends ViewModel {
    private String enteredEmailAddress;
    private String enteredPassword;
    private MutableLiveData<Boolean> correctlyEmailAddress;
    private MutableLiveData<Boolean> correctlyPassword;
    private MutableLiveData<Boolean> correctlyPasswordAgain;

    public void setEmail(String email){
        enteredEmailAddress = email;
        correctlyEmailAddress.setValue(RegisterModel.checkEmail(email));
    }
    public void setPassword(String pass){
        enteredPassword = pass;
        correctlyPassword.setValue(RegisterModel.checkPassLength(pass));
    }
    public void checkPassAgain(String passAgain){
        correctlyPasswordAgain.setValue(RegisterModel.checkPassesSimilarity(enteredPassword, passAgain));
    }

    public void onRegisterClicked(CallBackBoolean callBack){
        RegisterModel registerModel = new RegisterModel(enteredEmailAddress, enteredPassword);
        registerModel.registerUser(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callBack.invoke(res);
            }
        });
    }

    public MutableLiveData<Boolean> getCorrectlyEmailAddress(){
        if(correctlyEmailAddress == null){
            correctlyEmailAddress = new MutableLiveData<>();
        }
        return correctlyEmailAddress;
    }

    public MutableLiveData<Boolean> getCorrectlyPassword(){
        if(correctlyPassword == null){
            correctlyPassword = new MutableLiveData<>();
        }
        return correctlyPassword;
    }

    public MutableLiveData<Boolean> getCorrectlyPasswordAgain(){
        if(correctlyPasswordAgain == null){
            correctlyPasswordAgain = new MutableLiveData<>();
        }
        return correctlyPasswordAgain;
    }

}
