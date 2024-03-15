package ru.dvteam.itcollabhub.model.profilemodels;

import ru.dvteam.itcollabhub.InformationForChooseThemeForApp;
import ru.dvteam.itcollabhub.globaldata.GlobalDataScoreProfile;

public class ChooseThemeForAppModel {
    public InformationForChooseThemeForApp getImportantInfo(){
        GlobalDataScoreProfile globalDataScoreProfile = GlobalDataScoreProfile.getInstance();
        System.out.println(globalDataScoreProfile.getThemeNum() + " " + globalDataScoreProfile.getName());
        return new InformationForChooseThemeForApp(globalDataScoreProfile.getName(),
                globalDataScoreProfile.getUrlPhoto(),
                globalDataScoreProfile.getThemeNum(),
                globalDataScoreProfile.getScore());
    }
}
