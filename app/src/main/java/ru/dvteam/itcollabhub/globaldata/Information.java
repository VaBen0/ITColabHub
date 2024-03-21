package ru.dvteam.itcollabhub.globaldata;

public class Information {
    private static Information instance;
    private Information(){}
    private String objTitle, objText, objImage;

    public static Information getInstance(){
        if(instance == null){
            instance = new Information();
        }
        return instance;
    }

    public String getObjTitle() {
        return objTitle;
    }

    public String getObjText() {
        return objText;
    }

    public String getObjImage() {
        return objImage;
    }

    public void setObjTitle(String objTitle) {
        this.objTitle = objTitle;
    }

    public void setObjText(String objText) {
        this.objText = objText;
    }

    public void setObjImage(String objImage) {
        this.objImage = objImage;
    }
}
