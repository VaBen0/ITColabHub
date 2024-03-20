package ru.dvteam.itcollabhub.classmodels;

public class ClassForChangeInfo {
    private static ClassForChangeInfo instance;
    private String name;
    private String id;
    private String photo;
    private String text;

    private ClassForChangeInfo(){

    }

    public static ClassForChangeInfo getInstance(){
        if(instance == null){
            instance = new ClassForChangeInfo();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getText() {
        return text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setText(String text) {
        this.text = text;
    }
}
