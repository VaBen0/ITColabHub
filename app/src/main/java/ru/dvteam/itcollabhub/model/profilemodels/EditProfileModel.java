package ru.dvteam.itcollabhub.model.profilemodels;

import android.content.SharedPreferences;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.classmodels.ProfileInformation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt2;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class EditProfileModel {
    private final PostDatas postDatas = new PostDatas();
    private String userTgLink, userVkLink, userWebLink, userName, urlPhoto;
    public int userScore;

    public EditProfileModel(String userTgLink, String userVkLink, String userWebLink, String userName, String urlPhoto){
        this.userTgLink = userTgLink;
        this.userVkLink = userVkLink;
        this.userWebLink = userWebLink;
        this.userName = userName;
        this.urlPhoto = urlPhoto;
    }

    public EditProfileModel(){

    }



    public void getUserProfileInformation(SharedPreferences sPref, CallBackProfileInformation callback){
        String mail = sPref.getString("UserMail", "");
        postDatas.postDataGetUserData(mail, new CallBackInt2() {
            @Override
            public void invoke(String name, String urlImage, int topScore,
                               String topStatus, String rfr, int activityProjects,
                               int archiveProjects) {
                userName = name;
                urlPhoto = urlImage;
                callback.invoke(new ProfileInformation(name, urlImage, topScore, topStatus,
                        rfr, activityProjects, archiveProjects, mail));
            }
        });
    }

    public void getUserLinks(SharedPreferences sPref, CallBackInt5 callback){
        String mail = sPref.getString("UserMail", "");
        postDatas.postDataGetLinks("GetAllLinks", mail, new CallBackInt5() {
            @Override
            public void invoke(String tgLink, String vkLink, String webLink) {
                userTgLink = tgLink;
                userVkLink = vkLink;
                userWebLink = webLink;
                callback.invoke(tgLink, vkLink, webLink);
            }
        });
    }

    public void saveChangesWithoutImage(SharedPreferences sPref, CallBackBoolean callback){
        postDatas.postDataEditProfileWithoutImage("CreateNameLog1", sPref.getString("UserMail", ""), userName, userTgLink,
                userVkLink, userWebLink, new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(res.equals("Сохранено"));
            }
        });
    }

    public void saveChanges(SharedPreferences sPref, CallBackBoolean callback){
        File file = new File(urlPhoto);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataEditProfile("CreateNameLog1", userName, userTgLink, userVkLink, userWebLink, requestBody,
                sPref.getString("UserMail", ""), new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(res.equals("Сохранено"));
            }
        });
    }
}
