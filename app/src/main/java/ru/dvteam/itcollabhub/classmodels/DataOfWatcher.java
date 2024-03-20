package ru.dvteam.itcollabhub.classmodels;

public class DataOfWatcher {
    private String objTitle;
    private String objDesc;
    private String objImg;
    private boolean type;
    private String id;

    public boolean isType() {
        return type;
    }

    public DataOfWatcher(String objTitle, String objDesc, String objImg, Boolean type) {
        this.objTitle = objTitle;
        this.objDesc = objDesc;
        this.objImg = objImg;
        this.type = type;
    }

    public DataOfWatcher(String objTitle, String objDesc, String objImg, Boolean type, String id) {
        this.objTitle = objTitle;
        this.objDesc = objDesc;
        this.objImg = objImg;
        this.type = type;
        this.id = id;
    }

    public String getObjTitle() {
        return objTitle;
    }

    public String getObjDesc() {
        return objDesc;
    }

    public String getObjImg() {
        return objImg;
    }

    public String getId() {
        return id;
    }
}
