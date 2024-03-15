package ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.model.authorizemodels.ForgotPasswordModel;

public class ForgotPasswordViewModel extends ViewModel {
    private String userMail;
    private MutableLiveData<Boolean> correctlyMail;

    public void setUserMail(String mail){
        userMail = mail;
        correctlyMail.setValue(ForgotPasswordModel.checkMail(mail));
    }

    public MutableLiveData<Boolean> getCorrectlyMail(){
        if(correctlyMail == null){
            correctlyMail = new MutableLiveData<>();
        }
        return correctlyMail;
    }

    public void onGetClick(CallBackBoolean callback){
        ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel(userMail);
        forgotPasswordModel.getMailCode(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(res);
            }
        });
    }
}
