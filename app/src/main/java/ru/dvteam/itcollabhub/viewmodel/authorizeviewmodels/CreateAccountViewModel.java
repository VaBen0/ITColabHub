package ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.model.authorizemodels.CreateAccountModel;

public class CreateAccountViewModel extends ViewModel {
    private String userName;
    private String chosenMediaPath;
    private MutableLiveData<Boolean> correctlyName;
    private MutableLiveData<Boolean> chosenImage;

    public void setName(String name) {
        userName = name;
        correctlyName.setValue(CreateAccountModel.checkCorrectlyName(name));
    }

    public void setImage(String mediaPath){
        chosenMediaPath = mediaPath;
        chosenImage.setValue(true);
    }

    public MutableLiveData<Boolean> getCorrectlyName(){
        if(correctlyName == null){
            correctlyName = new MutableLiveData<>();
        }
        return correctlyName;
    }

    public MutableLiveData<Boolean> getChosenImage(){
        if(chosenImage == null){
            chosenImage = new MutableLiveData<>();
        }
        return chosenImage;
    }

    public void onSaveClicked(CallBackBoolean callback){
        CreateAccountModel createAccountModel = new CreateAccountModel(userName, chosenMediaPath);
        createAccountModel.createAccount(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(res);
            }
        });
    }


}
