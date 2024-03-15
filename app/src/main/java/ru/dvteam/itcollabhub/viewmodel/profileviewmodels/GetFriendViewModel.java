package ru.dvteam.itcollabhub.viewmodel.profileviewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.InformationForChooseThemeForApp;
import ru.dvteam.itcollabhub.model.profilemodels.GetFriendModel;

public class GetFriendViewModel extends ViewModel {
    private MutableLiveData<InformationForChooseThemeForApp> info;
    GetFriendModel getFriendModel = new GetFriendModel();

    public void getAllInfo(){
        info.setValue(getFriendModel.getInfo());
    }

    public LiveData<InformationForChooseThemeForApp> getInfo(){
        if(info == null){
            info = new MutableLiveData<>();
        }
        return info;
    }

    public String getMail(SharedPreferences sPref){
        return getFriendModel.getMail(sPref);
    }
}
