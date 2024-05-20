package ru.dvteam.itcollabhub.classmodels;

public class StatesClass {
    private String title;
    private String text;
    private String img;
    public StatesClass(String title, String text, String img){
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
