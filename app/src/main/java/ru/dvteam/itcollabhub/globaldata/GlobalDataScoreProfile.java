package ru.dvteam.itcollabhub.globaldata;

public class GlobalDataScoreProfile {
    private static GlobalDataScoreProfile INSTANCE;
    private GlobalDataScoreProfile(){}
    private int score, themeNum;
    private String name, urlPhoto;

    public static GlobalDataScoreProfile getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GlobalDataScoreProfile();
        }
        return INSTANCE;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setThemeNum(int themeNum) {
        this.themeNum = themeNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public int getScore() {
        return score;
    }

    public int getThemeNum() {
        return themeNum;
    }

    public String getName() {
        return name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }
}
