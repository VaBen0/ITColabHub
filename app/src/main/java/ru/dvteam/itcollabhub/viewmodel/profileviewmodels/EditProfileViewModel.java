package ru.dvteam.itcollabhub.viewmodel.profileviewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import ru.dvteam.itcollabhub.classmodels.ProfileInformation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.classmodels.UserLinks;
import ru.dvteam.itcollabhub.model.profilemodels.EditProfileModel;

public class EditProfileViewModel extends ViewModel {

    private String mediaPath = "", name = "", tgLink = "", vkLink = "", webLink = "";
    private MutableLiveData<ProfileInformation> profileInformation;
    private MutableLiveData<UserLinks> linksData;
    private final EditProfileModel profileModel = new EditProfileModel();
    private SharedPreferences shPref;

    public LiveData<UserLinks> getUserLinks(){
        if(linksData == null){
            linksData = new MutableLiveData<>();
        }
        return linksData;
    }

    public void setShPref(SharedPreferences shPref) {
        this.shPref = shPref;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTgLink(String tgLink) {
        this.tgLink = tgLink;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

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

    public void getUserAllLinks(SharedPreferences sPref){
        profileModel.getUserLinks(sPref, new CallBackInt5() {
            @Override
            public void invoke(String s1, String s2, String s3) {
                linksData.setValue(new UserLinks(s1, s2, s3));
            }
        });
    }

    public void onClickSaveChanges(CallBackBoolean callback){
        String nameStr;
        String tg, vk, web;
        EditProfileModel editProfileModel;
        if(name.isEmpty()){nameStr = Objects.requireNonNull(profileInformation.getValue()).getUserName();}
        else {nameStr = name;}
        if(tgLink.isEmpty()) {tg = Objects.requireNonNull(linksData.getValue()).getTgLink();}
        else{tg = tgLink;}
        if(vkLink.isEmpty()) {vk = Objects.requireNonNull(linksData.getValue()).getVkLink();}
        else{vk = vkLink;}
        if(webLink.isEmpty()) {web = Objects.requireNonNull(linksData.getValue()).getWebLink();}
        else{web = webLink;}
        if(mediaPath.isEmpty()){
            editProfileModel = new EditProfileModel(tg, vk, web, nameStr, "");
            editProfileModel.saveChangesWithoutImage(shPref, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    callback.invoke(res);
                }
            });
        }else{
            editProfileModel = new EditProfileModel(tg, vk, web, nameStr, mediaPath);
            editProfileModel.saveChanges(shPref, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    callback.invoke(res);
                }
            });
        }
    }
}
