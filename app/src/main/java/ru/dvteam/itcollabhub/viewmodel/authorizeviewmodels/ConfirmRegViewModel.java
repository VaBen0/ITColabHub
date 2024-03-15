package ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.model.authorizemodels.ConfirmRegModel;

public class ConfirmRegViewModel extends ViewModel {
    private String userCode;
    private MutableLiveData<Boolean> correctlyCode;

    public void setCorrectlyCode(String code){
        userCode = code;
        correctlyCode.setValue(ConfirmRegModel.checkUserCode(code));
    }

    public MutableLiveData<Boolean> getCorrectlyCode(){
        if(correctlyCode == null){
            correctlyCode = new MutableLiveData<>();
        }

        return correctlyCode;
    }

    public void onConfirmClick(SharedPreferences sPref, CallBackBoolean callback, CallBackBoolean callback2){
        ConfirmRegModel confirmRegModel = new ConfirmRegModel(userCode);
        confirmRegModel.confirmReg(sPref, new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(res);
            }
        }, new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback2.invoke(res);
            }
        });
    }
}
