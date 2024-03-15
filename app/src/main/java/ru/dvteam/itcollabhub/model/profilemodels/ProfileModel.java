package ru.dvteam.itcollabhub.model.profilemodels;

import android.content.SharedPreferences;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt2;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.ProfileInformation;

public class ProfileModel {
    private final PostDatas postDatas = new PostDatas();
    public void getUserProfileInformation(SharedPreferences sPref, CallBackProfileInformation callback){
        String mail = sPref.getString("UserMail", "");
        postDatas.postDataGetUserData(mail, new CallBackInt2() {
            @Override
            public void invoke(String name, String urlImage, int topScore,
                               String topStatus, String rfr, int activityProjects,
                               int archiveProjects) {
                callback.invoke(new ProfileInformation(name, urlImage, topScore, topStatus,
                        rfr, activityProjects, archiveProjects, mail));
            }
        });
    }
}
