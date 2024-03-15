package ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.model.authorizemodels.ConfirmForgotPasswordModel;

public class ConfirmForgotPasswordViewModel extends ViewModel {
    private String userCode;
    private MutableLiveData<Boolean> correctlyCode;

    public void setCorrectlyCode(String code){
        userCode = code;
        correctlyCode.setValue(ConfirmForgotPasswordModel.checkUserCode(code));
    }

    public MutableLiveData<Boolean> getCorrectlyCode(){
        if(correctlyCode == null){
            correctlyCode = new MutableLiveData<>();
        }

        return correctlyCode;
    }

    public void onConfirmClick(CallBackBoolean callback){
        ConfirmForgotPasswordModel confirmRegModel = new ConfirmForgotPasswordModel(userCode);
        confirmRegModel.confirmForgotPass(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(res);
            }
        });
    }
}
