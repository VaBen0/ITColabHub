package ru.dvteam.itcollabhub.model.profilemodels;

import android.content.SharedPreferences;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt2;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalDataScoreProfile;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.classmodels.ProfileInformation;

public class ProfileModel {
    private final PostDatas postDatas = new PostDatas();
    private Boolean banned = false;
    public void getUserProfileInformation(SharedPreferences sPref, CallBackProfileInformation callback){
        String mail = sPref.getString("UserMail", "");

        MailGlobalInfo mailGlobalInfo = MailGlobalInfo.getInstance();
        mailGlobalInfo.setUserMail(mail);

        postDatas.postDataGetUserData(mail, new CallBackInt2() {
            @Override
            public void invoke(String name, String urlImage, int topScore,
                               String topStatus, String rfr, int activityProjects,
                               int archiveProjects) {
                banned = topStatus.equals("Заблокирован");
                System.out.println(topStatus);
                callback.invoke(new ProfileInformation(name, urlImage, topScore, topStatus,
                        rfr, activityProjects, archiveProjects, mail));
            }
        });
    }

    public void setDataForChooseThemeActivity(int themeNum, ProfileInformation profileInformation){
        GlobalDataScoreProfile globalDataScoreProfile = GlobalDataScoreProfile.getInstance();
        globalDataScoreProfile.setScore(profileInformation.getUserScore());
        globalDataScoreProfile.setName(profileInformation.getUserName());
        globalDataScoreProfile.setThemeNum(themeNum);
        globalDataScoreProfile.setUrlPhoto(profileInformation.getUserImageUrl());
    }
    public Boolean isbanned(){
        return banned;
    }
}
