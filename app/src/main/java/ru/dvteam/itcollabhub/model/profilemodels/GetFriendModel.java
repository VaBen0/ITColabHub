package ru.dvteam.itcollabhub.model.profilemodels;

import android.content.SharedPreferences;

import ru.dvteam.itcollabhub.InformationForChooseThemeForApp;
import ru.dvteam.itcollabhub.globaldata.GlobalDataScoreProfile;

public class GetFriendModel {
    public InformationForChooseThemeForApp getInfo(){
        GlobalDataScoreProfile globalDataScoreProfile = GlobalDataScoreProfile.getInstance();
        return new InformationForChooseThemeForApp(globalDataScoreProfile.getName(),
                globalDataScoreProfile.getUrlPhoto(),
                globalDataScoreProfile.getThemeNum(),
                globalDataScoreProfile.getScore());
    }

    public String getMail(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("UserMail", "");
    }
}
