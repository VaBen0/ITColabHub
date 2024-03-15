package ru.dvteam.itcollabhub.viewmodel.profileviewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.ProfileInformation;
import ru.dvteam.itcollabhub.model.profilemodels.ProfileModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileInformation> profileInformation;
    private final ProfileModel profileModel = new ProfileModel();

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
}
