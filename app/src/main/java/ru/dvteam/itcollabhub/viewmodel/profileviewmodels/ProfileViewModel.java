package ru.dvteam.itcollabhub.viewmodel.profileviewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.classmodels.ProfileInformation;
import ru.dvteam.itcollabhub.model.profilemodels.ProfileModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileInformation> profileInformation;
    private final ProfileModel profileModel = new ProfileModel();
    private MutableLiveData<Boolean> banned;

    public LiveData<ProfileInformation> getUserAllInfo(){
        if(profileInformation == null){
            profileInformation = new MutableLiveData<>();
        }
        return profileInformation;
    }

    public void getProfileInformation(SharedPreferences sPref){
        profileModel.getUserProfileInformation(sPref, new CallBackProfileInformation() {
            @Override
            public void invoke(ProfileInformation profileInform) {
                profileInformation.setValue(profileInform);
            }
        });
    }
    public LiveData<Boolean> getBanned(){
        if(banned == null){
            banned = new MutableLiveData<>();
        }
        return banned;
    }

    public void setDataForChooseTheme(int themeNum){
        profileModel.setDataForChooseThemeActivity(themeNum, Objects.requireNonNull(profileInformation.getValue()));
    }

    public void isbanned(){
        banned.setValue(profileModel.isbanned());
    }
}
