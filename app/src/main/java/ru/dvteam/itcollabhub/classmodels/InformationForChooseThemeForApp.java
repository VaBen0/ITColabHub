package ru.dvteam.itcollabhub.classmodels;

public class InformationForChooseThemeForApp {
    private String name, urlPhoto;
    private int themNum, score;

    public InformationForChooseThemeForApp(String name, String urlPhoto, int themNum, int score) {
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.themNum = themNum;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public int getThemNum() {
        return themNum;
    }

    public int getScore() {
        return score;
    }
}
